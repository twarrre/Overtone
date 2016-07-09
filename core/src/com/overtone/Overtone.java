package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Screens.*;

public class Overtone extends ApplicationAdapter
{

	public enum Screens
	{
		MainMenu,
		DifficultySelect,
		SongComplete,
		Gameplay,
		Options,
		Help,
		HighScore,
		Splash;
	}

	// Stores the current screen that is on display
	public static OvertoneScreen _currentScreen;

	// Stores an array of all of the screens in the game
	private static Array<OvertoneScreen> _screens;

	@Override
	public void create ()
	{
		// Create all of the new screens
		_screens = new Array<OvertoneScreen>();
		_screens.add(new MainMenuScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new OptionsScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new HelpScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new HighScoreScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SplashScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_currentScreen = _screens.get(7);

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

	public static void SetScreen(Screens s)
	{
		_currentScreen.hide();
		_currentScreen = _screens.get(s.ordinal());
		_currentScreen.show();
	}

	public static void ResetScreen(Screens s)
	{
		if(s == Screens.MainMenu)
		{
			_screens.set(s.ordinal(), new MainMenuScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.DifficultySelect)
		{
			_screens.set(s.ordinal(), new DifficultySelectScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.SongComplete)
		{
			_screens.set(s.ordinal(), new SongCompleteScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.Gameplay)
		{
			_screens.set(s.ordinal(), new GameplayScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.Options)
		{
			_screens.set(s.ordinal(), new OptionsScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.Help)
		{
			_screens.set(s.ordinal(), new OptionsScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.HighScore)
		{
			_screens.set(s.ordinal(), new HighScoreScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
		else if (s == Screens.Splash)
		{
			_screens.set(s.ordinal(), new SplashScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		}
	}
}
