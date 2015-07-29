package com.me.clue.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.clue.model.Home;


public class HomeRenderer
{
    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;

    private Home _home;
    private OrthographicCamera _cam;

    /** for debug rendering **/
    ShapeRenderer _debugRenderer = new ShapeRenderer();

    /** Textures **/

    private SpriteBatch _spriteBatch;
    private boolean _debug = false;
    private int _width;
    private int _height;
    private float _ppuX;	// pixels per unit on the X axis
    private float _ppuY;	// pixels per unit on the Y axis

    public void setSize (int w, int h)
    {
        _width = w;
        _height = h;
        _ppuX = (float)_width / CAMERA_WIDTH;
        _ppuY = (float)_height / CAMERA_HEIGHT;
    }

    public HomeRenderer(Home home, boolean debug)
    {
        _home = home;
        _cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        _cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        _cam.update();
        _debug = debug;
        _spriteBatch = new SpriteBatch();
    }

    public void render()
    {
        _spriteBatch.begin();
        drawBlocks();
        drawBob();
        _home.draw();
        _spriteBatch.end();
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
        _debugRenderer.setProjectionMatrix(_cam.combined);
        _debugRenderer.begin(ShapeRenderer.ShapeType.Line);


        _debugRenderer.end();
    }
}
