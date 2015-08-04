package com.me.clue.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.clue.Clue;
import com.me.clue.controller.WorldController;
import com.me.clue.model.GridComponent;
import com.me.clue.model.World;

import java.io.FileNotFoundException;
import java.io.IOException;


public class WorldRenderer
{
    private static final float CAMERA_WIDTH = 800f;
    private static final float CAMERA_HEIGHT = 400f;

    private World               _world;

    ShapeRenderer debugRenderer = new ShapeRenderer();

    private OrthographicCamera  _camera;

    private boolean _debug = false;
    private int _width;
    private int _height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis



    /**Debug Fields**/
    private GridComponent[][] _componenentMatrix;

    public OrthographicCamera getCamera() { return _camera; }

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

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        _camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);

        _camera.setToOrtho(false, w, h);

        _camera.update();
    }

    public void render()
    {
        _camera.update();

        _world.draw(_camera);

        if (_debug)
            drawDebug();
    }

    private void drawDebug()
    {
        _componenentMatrix = _world.getComponentMatrix();

        debugRenderer.setProjectionMatrix(_camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        //Render matrix
        for(GridComponent[] c : _componenentMatrix)
        {
            for(GridComponent component : c)
            {
                Rectangle rect = component.getBounds();
                float x1 = component.getPosition().x;
                float y1 = component.getPosition().y;
                debugRenderer.setColor(new Color(0, 1, 0, 1));
                debugRenderer.rect(x1, y1, rect.width, rect.height);
            }
        }

        debugRenderer.end();
    }
}
