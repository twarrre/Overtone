package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Notes.Note;
import com.overtone.Screens.*;

import java.io.*;

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

	public static int[][] _scores;

	public static Note.DifficultyMultiplier _difficulty;

	@Override
	public void create ()
	{
		_difficulty = Note.DifficultyMultiplier.easy;

		_scores = new int[3][5];
		LoadHighScores();

		_currentScreen = new SplashScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//SongCompleteScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, Note.DifficultyMultiplier.easy, 39, 0, 0, 0, 0, 0);
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

	public static void SetScreen(Screens s, boolean completed, int score, int ... counters)
	{
		_currentScreen.hide();

		if (s == Screens.SongComplete)
			_currentScreen = new SongCompleteScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), completed, score, counters);

		_currentScreen.show();
	}

	public static void SetScreen(Screens s)
	{
		_currentScreen.hide();

		if(s == Screens.MainMenu)
			_currentScreen = new MainMenuScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		else if (s == Screens.DifficultySelect)
			_currentScreen = new DifficultySelectScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (s == Screens.Gameplay)
			_currentScreen = new GameplayScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

	public static void LoadHighScores()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("Storage\\HighScores.txt"));

			String line      = null;
			int diffCounter  = -1;
			int scoreCounter = 0;

			while ((line = reader.readLine())!= null)
			{
				if(line.compareTo("e") == 0  || line.compareTo("n") == 0  || line.compareTo("h") == 0 )
				{
					diffCounter++;
					scoreCounter = 0;
					continue;
				}

				_scores[diffCounter][scoreCounter] = Integer.parseInt(line);
				scoreCounter++;
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.out.print("Data Cannot be loaded at this time.");
		}
	}

	public static void ResetScores()
	{
		_scores = new int[3][5];

		try
		{
			File file = new File("Storage\\HighScores.txt");

			if (!file.exists())
				file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fw);
			String[] diff = {"e", "n", "h"};

			for(int i = 0; i < diff.length; i++)
			{
				writer.write(diff[i]);
				writer.newLine();
				for(int j = 0; j < 5; j++)
				{
					writer.write("" + 0);
					writer.newLine();
				}
			}

			writer.close();
		}
		catch(IOException x)
		{
			System.out.print("Data Cannot be reset at this time.");
		}
	}

	public static void WriteScores()
	{
		try
		{
			File file = new File("Storage\\HighScores.txt");

			if (!file.exists())
				file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fw);
			String[] diff = {"e", "n", "h"};

			for(int i = 0; i < diff.length; i++)
			{
				writer.write(diff[i]);
				writer.newLine();
				for(int j = 0; j < 5; j++)
				{
					writer.write("" + _scores[i][j]);
					writer.newLine();
				}
			}

			writer.close();
		}
		catch(IOException x)
		{
			System.out.print("Data Cannot be saved at this time.");
		}
	}

	public static void UpdateScore(int score, Note.DifficultyMultiplier difficulty)
	{
		boolean replacedScore = false;
		int replace = score;
		for(int i = 0; i < _scores[difficulty.ordinal()].length; i++)
		{
			if(replace > _scores[difficulty.ordinal()][i])
			{
				replacedScore = true;
				int temp = _scores[difficulty.ordinal()][i];
				_scores[difficulty.ordinal()][i] = replace;
				replace = temp;
			}
		}

		if(replacedScore)
			WriteScores();
	}
}
