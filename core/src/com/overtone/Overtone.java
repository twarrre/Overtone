package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Notes.Note;
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

	@Override
	public void create ()
	{
		_currentScreen = new SplashScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

	public static void SetScreen(Screens s, Note.DifficultyMultiplier diff, int score)
	{
		_currentScreen.hide();

		if (s == Screens.Gameplay)
			_currentScreen = new GameplayScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), diff, score);

		_currentScreen.show();
	}

	public static void SetScreen(Screens s, boolean completed, Note.DifficultyMultiplier diff, int score, int highscore)
	{
		_currentScreen.hide();

		if (s == Screens.SongComplete)
			_currentScreen = new SongCompleteScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), completed, diff, score, highscore);

		_currentScreen.show();
	}

	public static void SetScreen(Screens s)
	{
		_currentScreen.hide();

		if(s == Screens.MainMenu)
			_currentScreen = new MainMenuScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		else if (s == Screens.DifficultySelect)
			_currentScreen = new DifficultySelectScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		else if (s == Screens.Options)
			_currentScreen = new OptionsScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		else if (s == Screens.Help)
			_currentScreen = new HelpScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		else if (s == Screens.HighScore)
			_currentScreen = new HighScoreScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		else if (s == Screens.Splash)
			_currentScreen = new SplashScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		_currentScreen.show();
	}
}
