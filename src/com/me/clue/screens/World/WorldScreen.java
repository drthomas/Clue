package com.me.clue.screens.World;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.me.clue.Clue;
import com.me.clue.controller.WorldController;
import com.me.clue.model.BCharacter;
import com.me.clue.model.World;
import com.me.clue.view.WorldRenderer;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class WorldScreen implements Screen, GestureListener
{
    private static final float CAMERA_WIDTH = 1280f;
    private static final float CAMERA_HEIGHT = 720f;

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

        _camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        _camera.update();

        _batch = new SpriteBatch();

        _sprite = new Sprite(_texture);
        _sprite.setOrigin(0, 0);
        _sprite.setPosition(-_sprite.getWidth() / 2, -_sprite.getHeight() / 2);

        //_stage.addActor(_sprite);
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
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

    @Override
    public boolean touchDown(float v, float v1, int i, int i1)
    {
        return false;
    }

    @Override
    public boolean tap(float v, float v1, int i, int i1)
    {
        Gdx.app.log("World Screen:", "tap Called");
        return false;
    }

    @Override
    public boolean longPress(float v, float v1)
    {
        Gdx.app.log("World Screen:", "longPress Called");
        return false;
    }

    @Override
    public boolean fling(float v, float v1, int i)
    {
        Gdx.app.log("World Screen:", "fling Called");
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        Gdx.app.log("World Screen:", "pan Called");


        _camera.translate(-deltaX, deltaY);
        _camera.position.x = MathUtils.clamp(_camera.position.x, -(_sprite.getWidth() - Gdx.graphics.getWidth()) / 2,
                (_sprite.getWidth() - Gdx.graphics.getWidth()) / 2);
        _camera.position.y = MathUtils.clamp(_camera.position.y, -(_sprite.getHeight() - Gdx.graphics.getHeight()) / 2,
                (_sprite.getHeight() - Gdx.graphics.getHeight()) / 2);
        _camera.update();

        return false;
    }

    @Override
    public boolean panStop(float v, float v1, int i, int i1)
    {
        Gdx.app.log("World Screen:", "panStop Called");
        return false;
    }

    @Override
    public boolean zoom(float v, float v1)
    {
        Gdx.app.log("World Screen:", "zoom Called");
        return false;
    }

    @Override
    public boolean pinch(Vector2 vector2, Vector2 vector21, Vector2 vector22, Vector2 vector23)
    {
        Gdx.app.log("World Screen:", "pinch Called");
        return false;
    }
}
