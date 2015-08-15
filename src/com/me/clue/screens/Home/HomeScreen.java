package com.me.clue.screens.Home;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.me.clue.Clue;
import com.me.clue.model.Home;
import com.me.clue.screens.World.WorldScreen;
import com.me.clue.view.HomeRenderer;

public class HomeScreen implements Screen
{
    private Home                    _home;
    private HomeRenderer            _renderer;
    private WorldScreen             _worldScreen;

    private Clue _game;
    private Stage _stage;

    public HomeScreen(Clue game)
    {
        Gdx.app.log("Home Screen", "constructor called");
        _game = game;

        initialize();
    }

    private void initialize()
    {
        _stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        _home = new Home(_stage);
        _renderer = new HomeRenderer(_home, false);
        _worldScreen = new WorldScreen(_game);
    }

    private void changeScreen()
    {
        _worldScreen.setSelectedPlayers(_home.getSelectedPlayers());
        _game.setScreen(_worldScreen);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        _home.update();
        _renderer.render();

        if(_home.getCreateButton().isPressed())
        {
            if (_home.getPlayerCount() > 2)
            {
                Gdx.app.log("Home Screen", "Create, " + _home.getPlayerCount() + " players.");

                changeScreen();
            }
            _home.getCreateButton().setPressed(false);
        }
    }

    @Override
    public void resize(int width, int height)
    {
        _renderer.setSize(width, height);
        _stage.getViewport().update(width, height);

        _renderer.update();
    }

    @Override
    public void show()
    {
        Gdx.app.log("Home Screen", "show called");
        Gdx.input.setInputProcessor(_stage);
    }

    @Override
    public void hide()
    {
        Gdx.app.log("Home Screen", "hide called");
        _home.getCreateButton().setPressed(false);
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
        Gdx.app.log("Home Screen", "dispose called");

        Gdx.input.setInputProcessor(null);
    }
}
