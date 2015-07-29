package com.me.clue.screens.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.me.clue.Clue;
import com.me.clue.controller.WorldController;
import com.me.clue.model.BCharacter;
import com.me.clue.model.World;
import com.me.clue.view.WorldRenderer;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class WorldScreen implements Screen, InputProcessor
{
    private World           world;
    private WorldRenderer   renderer;
    private WorldController controller;

    private Stage _stage;
    private Clue _game;
    private Texture _texture;
    private Sprite _sprite;
    private SpriteBatch _batch;
    private OrthographicCamera _camera;

    private ArrayList<String> _selectedPlayers = new ArrayList<String>() { };
    private ArrayList<BCharacter> _playerList = new ArrayList<BCharacter>() { };

    public void setSelectedPlayer(ArrayList<String> list) { _selectedPlayers = list; }

    public WorldScreen(Clue game)
    {
        Gdx.app.log("World Screen", "constructor called");
        _game = game; // ** get Game parameter **//

        initialize();
    }

    private void initialize()
    {
        _stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        world = new World();
        renderer = new WorldRenderer(world, true);
        controller = new WorldController(world);

        loadTextures();

        _camera = new OrthographicCamera(1280, 720);
        _batch = new SpriteBatch();

        _sprite = new Sprite(_texture);
        _sprite.setOrigin(0, 0);
        _sprite.setPosition(-_sprite.getWidth()/2,-_sprite.getHeight()/2);
    }

    private void loadTextures()
    {
        _texture = new Texture(Gdx.files.internal("images/large.jpg"));
        _texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void show()
    {
        Gdx.app.log("World Screen", "show called");
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        _sprite.draw(_batch);
        _batch.end();

        controller.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void hide()
    {
        Gdx.app.log("World Screen", "hide called");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose()
    {
        Gdx.app.log("World Screen", "dispose called");
        Gdx.input.setInputProcessor(null);
    }

    // * InputProcessor methods ***************************//

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
