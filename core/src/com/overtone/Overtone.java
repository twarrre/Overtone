package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.overtone.Screens.*;

import java.io.*;

public class Overtone extends ApplicationAdapter
{

	/**
	 * Enum for the different types of screens
	 */
	public enum Screens
	{
		MainMenu,
		DifficultySelect,
		SongComplete,
		Gameplay,
		Options,
		Help,
		HighScore,
		Splash
	}

	/**
	 * The available difficulties for the game.
	 */
	public enum Difficulty
	{
		Easy(5),
		Normal(4),
		Hard(3);

		public float Multiplier;
		Difficulty(float multiplier) { this.Multiplier = multiplier; }
	}

	/**
	 * Enum for the different ratings for the score
	 */
	public enum CrowdRating
	{
		Perfection,
		Brilliant,
		Great,
		Cleared,
		Failure,
		None;

		public static CrowdRating GetRating(int ... counters)
		{
			float score = 0;

			int totalNotes = 0;
			for(int i = 0; i < counters.length; i++)
				totalNotes += counters[i];

			score += 1.0f * ((float)counters[0] / totalNotes);
			score += 0.5f * ((float)counters[1] / totalNotes);
			score += 0.3f * ((float)counters[2] / totalNotes);
			score += 0.1f * ((float)counters[3] / totalNotes);
			score += 0.0f * ((float)counters[4] / totalNotes);

			if(score >= 1.0f)
				return Perfection;
			else if(score >= 0.86f && score < 1.0f)
				return Brilliant;
			else if(score >= 0.66f && score <= 0.85f)
				return Great;
			else if(score >= 0.5f && score < 0.65f)
				return Cleared;
			else if(score < 0.5f)
				return Failure;
			else
				return None;
		}

		public static CrowdRating GetRating(String rating)
		{
			if(rating.compareTo("Perfection") == 0)
				return Perfection;
			else if(rating.compareTo("Brilliant") == 0)
				return Brilliant;
			else if(rating.compareTo("Great") == 0)
				return Great;
			else if(rating.compareTo("Cleared") == 0)
				return Cleared;
			else if(rating.compareTo("Failure") == 0)
				return Failure;
			else
				return None;
		}

		public String toString()
		{
			switch(this.ordinal())
			{
				case 0:
					return "Perfection";
				case 1:
					return "Brilliant";
				case 2:
					return "Great";
				case 3:
					return "Cleared";
				case 4:
					return "Failure";
				case 5:
					return "---";
				default:
					return "---";
			}
		}
	}

	/**
	 * Represents the four targets of the screen
	 */
	public enum TargetZone
	{
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight;
	}

	// Variables
	private static OvertoneScreen _currentScreen;
	public static int[][]         HighScores;
    public static CrowdRating[][] CrowdRatings;
	public static Difficulty      Difficulty;

	@Override
	public void create ()
	{
		Difficulty     = Difficulty.Easy;
		HighScores     = new int[3][5];
        CrowdRatings   = new CrowdRating[3][5];
		_currentScreen = new SplashScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		_currentScreen.show();
		LoadHighScores();
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

	/**
	 * Called every frame. Updates the current screen.
	 * @param deltaTime Time since last frame.
     */
	public void Update(float deltaTime)
	{
		_currentScreen.update(deltaTime);
	}

	/**
	 * Sets the screen to a new screen. Called from Gameplay screen to Song Completion screen.
	 * @param s The type of the next screen
	 * @param completed True if the song was successfully completed, false otherwise
	 * @param score The score that the player achieved
	 * @param counters Counters for each type of rating
     */
	public static void SetScreen(Screens s, boolean completed, int score, int ... counters)
	{
		_currentScreen.hide();

		if (s == Screens.SongComplete)
			_currentScreen = new SongCompleteScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), completed, score, counters);

		_currentScreen.show();
	}

	/**
	 * Sets the screen to a new screen.
	 * @param s The type of the next screen.
     */
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

	/**
	 * Loads high scores from a file.
	 */
	public static void LoadHighScores()
	{
		try
		{
			// Open the file
			BufferedReader reader = new BufferedReader(new FileReader("Storage\\HighScores.txt"));

			String line      = null;
			int diffCounter  = -1;
			int scoreCounter = 0;

			while ((line = reader.readLine())!= null)
			{
                String[] tokens = line.split(" ");

				if(tokens[0].compareTo("e") == 0  || tokens[0].compareTo("n") == 0  || tokens[0].compareTo("h") == 0 )
				{
					diffCounter++;
					scoreCounter = 0;
					continue;
				}

				HighScores[diffCounter][scoreCounter]   = Integer.parseInt(tokens[0]);
                CrowdRatings[diffCounter][scoreCounter] = CrowdRating.GetRating("" + tokens[1]);
				scoreCounter++;
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.out.print("Data Cannot be loaded at this time.");
		}
	}

	/**
	 * Writes the scores to a file
	 * @param reset true if you want to reset the high scores, false otherwise
     */
	public static void WriteScores(boolean reset)
	{

		if(reset)
		{
			HighScores   = new int[3][5];
			CrowdRatings = new CrowdRating[3][5];
		}

		try
		{
			File file = new File("Storage\\HighScores.txt");

			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fileWriter);
			String[] diff         = {"e", "n", "h"};

			for(int i = 0; i < diff.length; i++)
			{
				writer.write(diff[i]);
				writer.newLine();
				for(int j = 0; j < 5; j++)
				{
					if(reset)
					{
						CrowdRatings[i][j] = CrowdRating.None;
						HighScores[i][j]   = 0;
						writer.write(0 + " ---");
					}
					else
						writer.write(HighScores[i][j] + " " + CrowdRatings[i][j]);

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

	/**
	 * Checks if the passed in score is better than any of the stored scores and updates the scores.
	 * @param score The scores to be checked
	 * @param rating The rating of the score
	 * @param difficulty The difficultly of the song
     */
	public static void UpdateScore(int score, CrowdRating rating, Difficulty difficulty)
	{
		boolean replaced            = false;
		int scoreToReplace          = score;
		CrowdRating ratingToReplace = rating;

		for(int i = 0; i < HighScores[difficulty.ordinal()].length; i++)
		{
			if(scoreToReplace > HighScores[difficulty.ordinal()][i])
			{
				replaced = true;

				// Replace the score
				int tempScore                           = HighScores[difficulty.ordinal()][i];
				HighScores[difficulty.ordinal()][i]     = scoreToReplace;
				scoreToReplace                          = tempScore;

				// Replace the rating
				CrowdRating tempRating                  = CrowdRatings[Difficulty.ordinal()][i];
                CrowdRatings[Difficulty.ordinal()][i]   = ratingToReplace;
				ratingToReplace                         = tempRating;
			}
		}

		// If any scores were changed, write the scores to the file
		if(replaced)
			WriteScores(false);
	}
}
