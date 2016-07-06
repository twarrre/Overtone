package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Screens.*;

public class Overtone extends ApplicationAdapter
{
	// Stores the current screen that is on display
	public OvertoneScreen _currentScreen;

	// Stores an array of all of the screens in the game
	private Array<OvertoneScreen> _screens;

	@Override
	public void create ()
	{
		// Create all of the new screens
		_screens = new Array<OvertoneScreen>();
		_screens.add(new MainMenuScreen("Textures\\background1.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen("Textures\\background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen("Textures\\background3.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen("Textures\\background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_currentScreen = _screens.get(3);

		// Show the current one
		_currentScreen.show();
	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		Update(deltaTime);

		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_currentScreen.render(deltaTime);
	}

	public void Update(float deltaTime)
	{
		_currentScreen.update(deltaTime);
	}
}
