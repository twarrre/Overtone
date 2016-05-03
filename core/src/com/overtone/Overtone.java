package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Screens.DifficultySelectScreen;
import com.overtone.Screens.GameplayScreen;
import com.overtone.Screens.MainMenuScreen;
import com.overtone.Screens.SongCompleteScreen;

public class Overtone extends ApplicationAdapter
{
	public Screen currentScreen;
	private Array<Screen> _screens;

	@Override
	public void create ()
	{
		_screens = new Array<Screen>();
		_screens.add(new MainMenuScreen("background1.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen("background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen("background3.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen("background4.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		currentScreen = _screens.get(0);
		currentScreen.show();
	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
		{
			currentScreen.hide();
			currentScreen = _screens.get(0);
			currentScreen.show();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
		{
			currentScreen.hide();
			currentScreen = _screens.get(1);
			currentScreen.show();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_3))
		{
			currentScreen.hide();
			currentScreen = _screens.get(2);
			currentScreen.show();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_4))
		{
			currentScreen.hide();
			currentScreen = _screens.get(3);
			currentScreen.show();
		}


		currentScreen.render(deltaTime);
	}
}
