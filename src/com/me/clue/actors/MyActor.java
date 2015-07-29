package com.me.clue.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class MyActor extends Actor
{
    Texture texture = new Texture(Gdx.files.internal("images/0001.png"));
    public boolean started = false;

    public MyActor()
    {
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((MyActor)event.getTarget()).started = true;
                return true;
            }
        });
    }


    @Override
    public void draw(Batch batch, float alpha)
    {
        batch.draw(texture, this.getX(), this.getY());
    }
}

