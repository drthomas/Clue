package com.me.clue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.clue.screens.Home.HomeSplash;


public class Clue extends Game
{
	public HomeSplash _homeSplash;

	private int rendCount;
	private long startTime;
	private long endTime;

	@Override
	public void create()
	{
		_homeSplash = new HomeSplash(this);
		Gdx.app.log("Home Splash", "App Created");
		startTime = TimeUtils.millis();
		setScreen(_homeSplash);
	}

	@Override
	public void resize(int i, int i1) {

	}

	public void render()
	{
		super.render();
		rendCount++;
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public void dispose()
	{
		Gdx.app.log("Home Splash", "App rendered " + rendCount + " times");
		Gdx.app.log("Home Splash", "App Ended");
		endTime = TimeUtils.millis();
		Gdx.app.log("Home Splash", "App running for " + (endTime - startTime) / 1000 + " seconds");
	}
}
