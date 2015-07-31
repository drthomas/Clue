package com.me.clue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.clue.screens.Home.HomeSplash;

import java.util.ArrayList;


public class Clue extends Game
{
	public HomeSplash _homeSplash;

	@Override
	public void create()
	{
		_homeSplash = new HomeSplash(this);
		Gdx.app.log("Home Splash", "App Created");
		setScreen(_homeSplash);
	}

	@Override
	public void resize(int i, int i1) {

	}

	public void render()
	{
		super.render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public void dispose()
	{
	}
}
