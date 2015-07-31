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


public class WorldRenderer
{
    private static final float CAMERA_WIDTH = 800f;
    private static final float CAMERA_HEIGHT = 400f;

    private World               _world;

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Textures **/

    private Stage               _stage;
    private Clue                _game;
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
        loadTextures();

        _camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        _camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        _camera.update();

        _batch = new SpriteBatch();

        _sprite = new Sprite(_texture);
        _sprite.setOrigin(0, 0);
        _sprite.setPosition(-_sprite.getWidth() / 2, -_sprite.getHeight() / 2);    }

    private void loadTextures()
    {
        _texture = new Texture(Gdx.files.internal("images/large.jpg"));
        _texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void render()
    {
        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        _sprite.draw(_batch);
        _batch.end();

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
