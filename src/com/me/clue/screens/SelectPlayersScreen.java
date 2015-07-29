package com.me.clue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.me.clue.model.World;
import com.me.clue.view.WorldRenderer;


/**
 * Created by David on 7/22/2015.
 */
public class SelectPlayersScreen implements Screen, InputProcessor
{
    private int _counter = 0;

    private World _world;
    private WorldRenderer _renderer;

    /*private Checkbox _ckbGreen = new Checkbox();
    private Checkbox _ckbMustard = new Checkbox();
    private Checkbox _ckbPeacock = new Checkbox();
    private Checkbox _ckbPlum = new Checkbox();
    private Checkbox _ckbScarlet = new Checkbox();
    private Checkbox _ckbWhite = new Checkbox();*/


    public SelectPlayersScreen(World world, WorldRenderer renderer)
    {
        _world = world;
        _renderer = renderer;
    }

    @Override
    public void render(float v)
    {

    }

    @Override
    public void resize(int i, int i1)
    {

    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    public boolean keyDown(int i)
    {
        return false;
    }

    @Override
    public boolean keyUp(int i)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char c)
    {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3)
    {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }


    @Override
    public boolean scrolled(int i)
    {
        return false;
    }
}
