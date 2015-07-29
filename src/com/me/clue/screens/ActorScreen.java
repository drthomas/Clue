package com.me.clue.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.me.clue.actors.MyActor;


public class ActorScreen implements Screen
{
    private MyActor actor;

    private Game myGame;

    private Stage stage;


    public ActorScreen(Game g)
    {
        myGame = g;

        stage = new Stage();

        actor = new MyActor();

        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(300f, 0f);
        moveAction.setDuration(10f);
        actor.addAction(moveAction);

        stage.addActor(actor);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v)
    {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int i, int i1)
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
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
