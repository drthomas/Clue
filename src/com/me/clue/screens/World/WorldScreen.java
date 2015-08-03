package com.me.clue.screens.World;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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


public class WorldScreen implements Screen, GestureListener, InputProcessor
{

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

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);

       Gdx.input.setInputProcessor(im);

        _world.setSelectedPlayers(_selectedPlayers);


        _world.start();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _controller.update(_renderer.getCamera());
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
    public boolean touchDown(float screenX, float screenY, int pointer, int button)
    {
        Gdx.app.log("World Screen", "GestureListener touchDown called, finger: " + Integer.toString(button));

        //Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        //Vector3 position = _renderer.getCamera().unproject(clickCoordinates);
        //_world.getSprite().setPosition(position.x, position.y);

        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        Gdx.app.log("World Screen", "GestureListener tap called");
        return true;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        Gdx.app.log("World Screen", "GestureListener longPress called");
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        Gdx.app.log("World Screen", "GestureListener fling called, velocity: " + Float.toString(velocityX) +
                ", " + Float.toString(velocityY));
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        Gdx.app.log("World Screen", "GestureListener pan called, delta: " + Float.toString(deltaX) +
                ", " + Float.toString(deltaY));

        Vector3 touchPos = new Vector3(x, y, 0);//Position the screen is touched

        _renderer.getCamera().unproject(touchPos);

        _renderer.getCamera().translate(-deltaX, deltaY);

        //_world.getSprite().setPosition(touchPos.x - _world.getSprite().getWidth() / 2,
        //        touchPos.y - _world.getSprite().getHeight() / 2);

        return true;
    }

    @Override
    public boolean panStop(float v, float v1, int i, int i1)
    {
        Gdx.app.log("World Screen", "GestureListener panStop called");
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        Gdx.app.log("World Screen", "GestureListener zoom called, initial distance: " + Float.toString(initialDistance) +
            " Distance: " + Float.toString(distance));
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2)
    {
        Gdx.app.log("World Screen", "GestureListener pinch called");
        return true;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        Gdx.app.log("World Screen", "InputProcessor keyDown called: " + keycode);

        if (keycode == Input.Keys.LEFT)
            _controller.leftPressed();
        if (keycode == Input.Keys.RIGHT)
            _controller.rightPressed();
        if (keycode == Input.Keys.UP)
            _controller.upPressed();
        if (keycode == Input.Keys.DOWN)
            _controller.downPressed();
        if (keycode == Input.Keys.Z)
            _controller.jumpPressed();
        if (keycode == Input.Keys.X)
            _controller.firePressed();
        if (keycode == Input.Keys.NUM_1)
            _controller.h01Pressed();
        if (keycode == Input.Keys.NUM_2)
            _controller.h02Pressed();

        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        Gdx.app.log("World Screen", "InputProcessor keyUp called: " + keycode);

        if (keycode == Input.Keys.LEFT)
            _controller.leftReleased();
        if (keycode == Input.Keys.RIGHT)
            _controller.rightReleased();
        if (keycode == Input.Keys.UP)
            _controller.upReleased();
        if (keycode == Input.Keys.DOWN)
            _controller.downReleased();
        if (keycode == Input.Keys.Z)
            _controller.jumpReleased();
        if (keycode == Input.Keys.X)
            _controller.fireReleased();
        if (keycode == Input.Keys.NUM_1)
            _controller.h01Released();
        if (keycode == Input.Keys.NUM_2)
            _controller.h02Released();

        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        Gdx.app.log("World Screen", "InputProcessor keyTyped called");
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.log("World Screen", "InputProcessor touchDown called");
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.log("World Screen", "InputProcessor touchUp called");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        Gdx.app.log("World Screen", "InputProcessor touchDragged called");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        Gdx.app.log("World Screen", "InputProcessor mouseMoved called");
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        Gdx.app.log("World Screen", "InputProcessor scrolled called");
        return false;
    }
}
