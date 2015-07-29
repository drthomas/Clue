package com.me.clue.screens.Home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.clue.Clue;


public class HomeSplash implements Screen, InputProcessor
{
    private HomeScreen              _homeScreen;

    private SpriteBatch             _batch;
    private Clue                    _game;
    private Texture                 _texture;
    private Sprite                  _sprite;
    private OrthographicCamera      _camera;

    private int _width, _height;

    public HomeSplash(Clue game)
    {
        Gdx.app.log("Home Splash Screen", "constructor called");
        _game = game; // ** get Game parameter **//

        initialize();
    }

    private void initialize()
    {
        _camera = new OrthographicCamera(1280, 720);
        _batch = new SpriteBatch();

        _texture = new Texture(Gdx.files.internal("images/splash.png"));

        _sprite = new Sprite(_texture);
        _sprite.setOrigin(0, 0);
        _sprite.setPosition(-_sprite.getWidth() / 2, -_sprite.getHeight() / 2);

        _homeScreen = new HomeScreen(_game);
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
        _game.setScreen(_homeScreen);
        return true;
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

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        _sprite.draw(_batch);
        _batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        _width = width;
        _height = height;
    }

    @Override
    public void show()
    {
        Gdx.app.log("Home Splash Screen", "show called");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide()
    {
        Gdx.app.log("Home Splash Screen", "hide called");
        Gdx.input.setInputProcessor(null);
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
        _texture.dispose();
        _batch.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
