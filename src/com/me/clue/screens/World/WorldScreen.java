package com.me.clue.screens.World;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.me.clue.Clue;
import com.me.clue.controller.WorldController;
import com.me.clue.model.BCharacter;
import com.me.clue.model.SelectedCharacter;
import com.me.clue.model.World;
import com.me.clue.view.WorldRenderer;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class WorldScreen implements Screen, GestureListener
{
    private static final float CAMERA_WIDTH = 800f;
    private static final float CAMERA_HEIGHT = 480f;

    private World           _world;
    private WorldRenderer   _renderer;
    private WorldController _controller;
    private Stage           _stage;
    private Clue            _game;

    private ArrayList<SelectedCharacter> _selectedPlayers = new  ArrayList<SelectedCharacter>() { };
    private ArrayList<BCharacter> _playerList = new ArrayList<BCharacter>() { };

    public void setSelectedPlayer( ArrayList<SelectedCharacter> list) { _selectedPlayers = list; }

    public WorldScreen(Clue game)
    {
        Gdx.app.log("World Screen", "constructor called");
        _game = game; // ** get Game parameter **//

        initialize();
    }

    private void initialize()
    {
        _stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        _world = new World(_stage);
        _renderer = new WorldRenderer(_world, true);
        _controller = new WorldController(_world);
    }

    @Override
    public void show()
    {
        Gdx.app.log("World Screen", "show called");
        Gdx.input.setInputProcessor(new GestureDetector(this));

        _world.setSelectedPlayers(_selectedPlayers);


        _world.start();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _controller.update(delta);
        _renderer.render();
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

        _renderer.getCamera().translate(-deltaX, deltaY);

        _renderer.getCamera().position.x =
                MathUtils.clamp(_renderer.getCamera().position.x,
                -(_renderer.getSprite().getWidth() - Gdx.graphics.getWidth()) / 2,
                (_renderer.getSprite().getWidth() - Gdx.graphics.getWidth()) / 2);

        _renderer.getCamera().position.y =
                MathUtils.clamp(_renderer.getCamera().position.y,
                -(_renderer.getSprite().getHeight() - Gdx.graphics.getHeight()) / 2,
                (_renderer.getSprite().getHeight() - Gdx.graphics.getHeight()) / 2);

        _renderer.getCamera().update();

        _world.update(x, y, deltaX, deltaY);

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
