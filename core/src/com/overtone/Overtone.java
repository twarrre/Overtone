package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Screens.*;

public class Overtone extends ApplicationAdapter
{
	public OvertoneScreen _currentScreen;

	private Array<OvertoneScreen> _screens;
	private InputManager _input;


	@Override
	public void create ()
	{
		_input = new InputManager();

		_screens = new Array<OvertoneScreen>();
		_screens.add(new MainMenuScreen("background1.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen("background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen("background3.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen("background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_currentScreen = _screens.get(3);

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
		_input.Update();
		_currentScreen.update(deltaTime);
	}
}
