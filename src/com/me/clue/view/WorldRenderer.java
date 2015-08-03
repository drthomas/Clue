package com.me.clue.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.clue.Clue;
import com.me.clue.controller.WorldController;
import com.me.clue.model.World;

import java.io.FileNotFoundException;
import java.io.IOException;


public class WorldRenderer
{
    private static final float CAMERA_WIDTH = 800f;
    private static final float CAMERA_HEIGHT = 400f;

    private World               _world;

    ShapeRenderer debugRenderer = new ShapeRenderer();

    private Texture             _texture;
    private Sprite              _sprite;
    private SpriteBatch         _batch;
    private OrthographicCamera  _camera;

    private boolean _debug = false;
    private int _width;
    private int _height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis

    public OrthographicCamera getCamera() { return _camera; }
    public Sprite getSprite() { return _sprite; }

    public void setSize (int w, int h)
    {
        _width = w;
        _height = h;
        ppuX = (float)_width / CAMERA_WIDTH;
        ppuY = (float)_height / CAMERA_HEIGHT;
    }

    public WorldRenderer(World world, boolean debug)
    {
        _world = world;
        _debug = debug;

        initialize();
    }

    private void initialize()
    {
        _camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        _camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        _camera.update();
    }

    public void render()
    {
        _world.draw();

        if (_debug)
            drawDebug();
    }

    private void drawBlocks()
    {

    }

    private void drawBob()
    {

    }

    private void drawDebug()
    {
        debugRenderer.setProjectionMatrix(_camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);


        debugRenderer.end();
    }
}
