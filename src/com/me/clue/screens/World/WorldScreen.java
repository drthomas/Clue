package com.me.clue.screens.World;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.me.clue.Clue;
import com.me.clue.Enums;
import com.me.clue.controller.WorldController;
import com.me.clue.huds.WorldHUD;
import com.me.clue.model.BCharacter;
import com.me.clue.model.GridComponent;
import com.me.clue.model.PlayerCharacter;
import com.me.clue.model.Room;
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
    private WorldHUD        _hud;

    private Stage           _stage;
    private Clue            _game;

    private float initialScale = 1.0f;
    private boolean _panning = false;
    private boolean _dragging = false;

    private ArrayList<SelectedCharacter> _selectedPlayers = new  ArrayList<SelectedCharacter>() { };

    public void setSelectedPlayers(ArrayList<SelectedCharacter> list) { _selectedPlayers = list; }

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
        _renderer = new WorldRenderer(_world, false);
        _controller = new WorldController(_world);
        _hud = new WorldHUD(_world);
    }

    @Override
    public void show()
    {
        Gdx.app.log("World Screen", "show called");

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(_world.getStage());
        im.addProcessor(gd);
        im.addProcessor(this);
        Gdx.input.setInputProcessor(im);

        _renderer.getCamera().position.x = 0;
        _renderer.getCamera().position.y = 0;

        _world.setSelectedPlayers(_selectedPlayers);

        _world.createWorldStage();
        _world.createHUDStage(_hud);
        _world.start();
        _renderer.resetPosition();

        _world.run();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _world.update();
        _hud.update();
        if(_hud.nextPlayerPressed())
        {
            _renderer.moveCameraToPlayer(_world.getCurrentPlayer());
        }

        _controller.update(_renderer.getCamera());
        _renderer.render();
    }

    @Override
    public void resize(int width, int height)
    {
        _renderer.resize(width, height);
    }

    @Override
    public void hide()
    {
        Gdx.app.log("World Screen", "hide called");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause()
    {
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
        //Gdx.app.log("World Screen", "GestureListener touchDown called");


        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        //Gdx.app.log("World Screen", "GestureListener tap called");

        Vector3 touchPos = new Vector3(x, y, 0);
        _renderer.getCamera().unproject(touchPos);

        GridComponent touched = null;

        for (GridComponent[] c : _world.getComponentMatrix().getMatrix())
        {
            for (GridComponent component : c)
            {
                if (component.getPosition().x > touchPos.x &&
                        component.getPosition().x <= touchPos.x + component.getWidth() &&
                        component.getPosition().y > touchPos.y &&
                        component.getPosition().y <= touchPos.y + component.getHeight())
                {
                    touched = component;
                    break;
                }
            }
        }

        if (touched != null && _world.getCurrentPlayer().getValidMoves().contains(touched))
        {
            _world.getCurrentPlayer().setCurrentNode(touched);
            _world.getCurrentPlayer().setStart(touched.getContentCode() == Enums.GridContent.Start);
            _world.getCurrentPlayer().setInRoom(touched.getContentCode() == Enums.GridContent.Room);
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        //Gdx.app.log("World Screen", "GestureListener longPress called");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        //Gdx.app.log("World Screen", "GestureListener fling called, velocity: " + Float.toString(velocityX) +
        //        ", " + Float.toString(velocityY));
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        //Gdx.app.log("World Screen", "GestureListener pan called, delta: " + Float.toString(deltaX) +
        //        ", " + Float.toString(deltaY));

        //Position the screen is touched
        Vector3 touchPos = new Vector3(x, y, 0);
        _renderer.getCamera().unproject(touchPos);

        if(_panning)
        {
            //Pan Camera
            float dx = (deltaX / Gdx.graphics.getWidth())
                    * WorldRenderer.CAMERA_WIDTH * _renderer.getCamera().zoom;
            float dy = (deltaY / Gdx.graphics.getHeight())
                    * WorldRenderer.CAMERA_HEIGHT * _renderer.getCamera().zoom;

            _renderer.getCamera().translate(-dx, dy);
        }

        if(_dragging)
        {
            GridComponent draggedComponent = null;

            for (GridComponent[] c : _world.getComponentMatrix().getMatrix())
            {
                for (GridComponent component : c)
                {
                    if (component.getPosition().x > touchPos.x &&
                            component.getPosition().x <= touchPos.x + component.getWidth() &&
                            component.getPosition().y > touchPos.y &&
                            component.getPosition().y <= touchPos.y + component.getHeight())
                    {
                        draggedComponent = component;
                        break;
                    }
                }
            }

            if (draggedComponent != null)
            {
                if(_world.getCurrentPlayer().getValidMoves().contains(draggedComponent))
                    {
                        _world.getCurrentPlayer().setCurrentNode(draggedComponent);
                        _world.getCurrentPlayer().setStart(draggedComponent.getContentCode() == Enums.GridContent.Start);
                        _world.getCurrentPlayer().setInRoom(draggedComponent.getContentCode() == Enums.GridContent.Room);
                    }
            }

            if(_world.getCurrentPlayer().inRoom())
            {
                for(Room room : _world.getWorldCreator().getRooms())
                {

                }
            }
        }
        return true;
    }

    @Override
    public boolean panStop(float v, float v1, int i, int i1)
    {
        //Gdx.app.log("World Screen", "GestureListener panStop called");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        /*Gdx.app.log("World Screen", "GestureListener zoom called, initial distance: " +
                Float.toString(initialDistance) +
                ", Distance: " + Float.toString(distance) +
                ", Current Zoom Level: " + _renderer.getCamera().zoom);*/

        float ratio = (initialDistance - distance);

        _renderer.getCamera().zoom = MathUtils.clamp((initialScale * ratio) / 100, 0.5f, 3f);

        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2)
    {
        //Gdx.app.log("World Screen", "GestureListener pinch called");

        return true;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        //Gdx.app.log("World Screen", "InputProcessor keyDown called: " + keycode);

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
        //Gdx.app.log("World Screen", "InputProcessor keyUp called: " + keycode);

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
        //Gdx.app.log("World Screen", "InputProcessor keyTyped called");
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.log("World Screen", "InputProcessor touchDown called");

        /*Gdx.app.log("World Screen", "Screen: (" + Gdx.graphics.getWidth() +
                ", " +  Gdx.graphics.getHeight() + ")");*/

        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        _renderer.getCamera().unproject(touchPos);

        initialScale = _renderer.getCamera().zoom;

        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = _renderer.getCamera().unproject(clickCoordinates);

        GridComponent clickedComponent = null;

        for(GridComponent[] c : _world.getComponentMatrix().getMatrix())
        {
            for(GridComponent component : c)
            {
                if(component.getPosition().x > position.x &&
                        component.getPosition().x <= position.x + component.getWidth() &&
                        component.getPosition().y > position.y &&
                        component.getPosition().y <= position.y + component.getHeight())
                {
                    clickedComponent = component;
                    break;
                }
            }
        }

        _panning = !(clickedComponent == _world.getCurrentPlayer().getCurrentNode());
        _dragging = clickedComponent == _world.getCurrentPlayer().getCurrentNode();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.log("World Screen", "InputProcessor touchUp called");

        _panning = false;
        _dragging = false;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        //Gdx.app.log("World Screen", "InputProcessor touchDragged called: " +
        //            screenX + ", " + screenY + ")");

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        //Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
        //Vector3 position = _renderer.getCamera().unproject(mouseCoordinates);

        //Gdx.app.log("World Screen", "Mouse Location: " + position);

        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        if(amount > 0 && _renderer.getCamera().zoom < 3f)
        {
            _renderer.zoomIn();
        }

        if(amount < 0 && _renderer.getCamera().zoom > 0.5f)
        {
            _renderer.zoomOut();
        }
        return false;
    }
}
