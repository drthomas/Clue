package com.me.clue.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.me.clue.model.World;


public class WorldController
{
    enum Keys
    {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        JUMP,
        FIRE,
        H01,
        H02
    }

    private World _world;

    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static
    {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
        keys.put(Keys.H01, false);
        keys.put(Keys.H02, false);
    }

    public WorldController(World world)
    {
        _world = world;
    }

    /************ Key Presses ****************/

    public void leftPressed()
    {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed()
    {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void upPressed()
    {
        keys.get(keys.put(Keys.UP, true));
    }

    public void downPressed()
    {
        keys.get(keys.put(Keys.DOWN, true));
    }

    public void jumpPressed()
    {
        keys.get(keys.put(Keys.JUMP, true));
    }

    public void firePressed()
    {
        keys.get(keys.put(Keys.FIRE, true));
    }

    public void h01Pressed()
    {
        keys.get(keys.put(Keys.H01, true));
    }

    public void h02Pressed()
    {
        keys.get(keys.put(Keys.H02, true));
    }

    /************ Key Releases ****************/

    public void leftReleased()
    {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased()
    {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void upReleased()
    {
        keys.get(keys.put(Keys.UP, false));
    }

    public void downReleased()
    {
        keys.get(keys.put(Keys.DOWN, false));
    }

    public void jumpReleased()
    {
        keys.get(keys.put(Keys.JUMP, false));
    }

    public void fireReleased()
    {
        keys.get(keys.put(Keys.FIRE, false));
    }

    public void h01Released()
    {
        keys.get(keys.put(Keys.H01, false));
    }

    public void h02Released()
    {
        keys.get(keys.put(Keys.H02, false));
    }


    /** Main update method **/
    public void update(OrthographicCamera camera)
    {
        processInput(camera);
    }

    private void processInput(OrthographicCamera camera)
    {
        if(keys.get(Keys.LEFT))
        {
            camera.translate(-16, 0);
        }
        if(keys.get(Keys.RIGHT))
        {
            camera.translate(16, 0);
        }
        if(keys.get(Keys.UP))
        {
            camera.translate(0, 16);
        }
        if(keys.get(Keys.DOWN))
        {
            camera.translate(0,-16);
        }
        if(keys.get(Keys.H01))
        {
            _world.getTiledMap().getLayers().get(0).setVisible(!_world.getTiledMap().getLayers().get(0).isVisible());
            h01Released();
        }
        if(keys.get(Keys.H02))
        {
            _world.getTiledMap().getLayers().get(1).setVisible(!_world.getTiledMap().getLayers().get(1).isVisible());
            h02Released();
        }
    }
}
