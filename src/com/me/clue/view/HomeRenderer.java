package com.me.clue.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.clue.model.Home;


public class HomeRenderer
{
    private static final float CAMERA_WIDTH = 800f;
    private static final float CAMERA_HEIGHT = 480f;

    private float scale = CAMERA_WIDTH / Gdx.graphics.getWidth();

    private Home _home;
    private OrthographicCamera _camera;
    private OrthographicCamera _secondaryCamera;


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
        _camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        _camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        _camera.update();

        Gdx.app.log("Home Renderer", "Scale: " + scale);

        _secondaryCamera = new OrthographicCamera(Gdx.graphics.getWidth() * scale,
                                                    Gdx.graphics.getHeight() * scale);
        _secondaryCamera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        _secondaryCamera.update();

        _debug = debug;
        _spriteBatch = new SpriteBatch();
    }

    public void render()
    {
        _spriteBatch.begin();
        _home.draw(_secondaryCamera);
        _spriteBatch.end();

        if (_debug)
            drawDebug();
    }

    private void drawDebug()
    {
        _debugRenderer.setProjectionMatrix(_camera.combined);
        _debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        _debugRenderer.end();
    }

    public void update()
    {
        scale = CAMERA_WIDTH / Gdx.graphics.getWidth();

        _secondaryCamera.translate(Gdx.graphics.getWidth() * scale,
                                    Gdx.graphics.getHeight() * scale);
        _secondaryCamera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        _secondaryCamera.update();
    }
}
