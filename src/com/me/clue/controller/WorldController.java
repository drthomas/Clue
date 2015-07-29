package com.me.clue.controller;

import java.util.HashMap;
import java.util.Map;

import com.me.clue.model.World;


public class WorldController
{
    enum Keys
    {
        LEFT,
        RIGHT,
        JUMP,
        FIRE
    }

    private World world;

    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static
    {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    }

    public WorldController(World world)
    {
        this.world = world;
        //this.bob = world.getBob();
    }

    /************ Key presses and touches *************/

    public void leftPressed()
    {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed()
    {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void jumpPressed()
    {
        keys.get(keys.put(Keys.JUMP, true));
    }

    public void firePressed()
    {
        keys.get(keys.put(Keys.FIRE, true));
    }

    public void leftReleased()
    {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased()
    {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void jumpReleased()
    {
        keys.get(keys.put(Keys.JUMP, false));
    }

    public void fireReleased()
    {
        keys.get(keys.put(Keys.FIRE, false));
    }


    /** Main update method **/
    public void update(float delta)
    {
        processInput();
        //bob.update(delta);
    }

    /** Change Bob's state and parameters based on input controls **/
    private void processInput()
    {
        if(keys.get(Keys.LEFT))
        {
            //left pressed
            //bob.setFacingLeft(true);
            //bob.setState(State.WALKING);
            //bob.getVelocity().x = -Bob.SPEED;
        }
        if(keys.get(Keys.RIGHT))
        {
            //right pressed
            //bob.setFacingLeft(false);
            //bob.setState(State.WALKING);
            //bob.getVelocity().x = Bob.SPEED;
        }
        if((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT))
                || (!keys.get(Keys.LEFT) && ! keys.get(Keys.RIGHT)))
        {
            //bob.setState(State.IDLE);
            //bob.getAcceleration().x = 0;
            //bob.getVelocity().x = 0;
        }
    }
}
