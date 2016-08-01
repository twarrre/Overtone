package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.overtone.Screens.*;
import java.io.*;

/**
 * Manager for everything in the game, handles updating everything
 */
public class Overtone extends ApplicationAdapter
{
	/**Maximum number of scores that are saved for each difficulty*/
	public static final int NUM_SCORES = 10;

	/**Enum for the different types of screens*/
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

	/**The available difficulties for the game.*/
	public enum Difficulty
	{
		Easy(6),
		Normal(4),
		Hard(2);

		public float Multiplier; // The amount of time the note must be on screen
		Difficulty(float multiplier) { this.Multiplier = multiplier; }
	}

	/**Enum for the different ratings for the score*/
	public enum CrowdRating
	{
		Perfection,
		Brilliant,
		Great,
		Cleared,
		Failure,
		None;

		/**
		 * Determines the rating based on counters collected in game
		 * @param counters counters collected in game, used to determine rating
         * @return a rating based on the counters passed in
         */
		public static CrowdRating GetRating(int ... counters)
		{
			float score = 0;

			int totalNotes = 0;
			for(int i = 0; i < counters.length; i++)
				totalNotes += counters[i];

			score += 1.0f * ((float)counters[0] / totalNotes); // 1 point for each perfect
			score += 0.7f * ((float)counters[1] / totalNotes); // seventh of a point for each great
			score += 0.5f * ((float)counters[2] / totalNotes); // fourth of a point for each okay
			score += 0.1f * ((float)counters[3] / totalNotes); // a tenth of a point for each bad
			score -= 0.1f * ((float)counters[4] / totalNotes); // no points for each miss

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

		/**
		 * Returns a rating that corresponds to the string passed in, used for reading in scores / ratings from a file
		 * @param rating The string of the rating read in from a file
         * @return The rating corresponding to the string passed in
         */
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

	/**Represents the four targets of the screen*/
	public enum TargetZone
	{
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight
	}

	// Variables
	public static float           ScreenWidth;      // The width of the screen;
	public static float           ScreenHeight;     // The height of the screen;
	public static int[][]         HighScores;       // Stores the high scores for each difficulty
    public static CrowdRating[][] CrowdRatings;     // Stores the associated crowd ratings for each high score
	public static Difficulty      Difficulty;       // Stores the chosen difficulty of the game
	public static float           MusicVolume;      // Stores the music volume for all music in the game
	public static float           SFXVolume;        // Stores the sound effects volume for all sound effects in the game
	private static OvertoneScreen _currentScreen;   // The current screen displayed on screen
	private SpriteBatch           _batch;           // Sprite batch to draw to
	private Sprite                _farBackground;   // The star background for the whole app
	private Sprite                _closeBackground; // The cloud background over-top of the star background (for depth)

	@Override
	public void create ()
	{
		ScreenWidth      = Gdx.graphics.getWidth();
		ScreenHeight     = Gdx.graphics.getHeight();
		Difficulty       = Difficulty.Easy;
		HighScores       = new int[Difficulty.values().length][NUM_SCORES];
        CrowdRatings     = new CrowdRating[Difficulty.values().length][NUM_SCORES];
		MusicVolume      = 1.0f;
		SFXVolume        = 1.0f;
		_currentScreen   = new SplashScreen();
		_batch           = new SpriteBatch();
		_farBackground   = new Sprite(new Texture("Textures\\space.jpg"));
		_closeBackground = new Sprite(new Texture("Textures\\clouds.png"));

		_farBackground.setCenter(ScreenWidth / 2.0f, ScreenHeight / 2.0f);
		_closeBackground.setCenter(ScreenWidth / 2.0f, ScreenHeight / 2.0f);
		_currentScreen.show();
		LoadHighScores();
		LoadVolume();
	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		Update(deltaTime);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render the background
		_batch.begin();
		_farBackground.draw(_batch);
		_closeBackground.draw(_batch);
		_batch.end();

		// Render the current screen
		_currentScreen.render(deltaTime);
	}

	/**
	 * Called every frame. Updates the current screen.
	 * @param deltaTime Time since last frame.
     */
	public void Update(float deltaTime)
	{
		// Update the rotation of the background
		_farBackground.rotate(1.0f * deltaTime);
		_closeBackground.rotate(2.0f * deltaTime);

		// Update the current screen
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
			_currentScreen = new SongCompleteScreen(completed, score, counters);

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
			_currentScreen = new MainMenuScreen();
		else if (s == Screens.DifficultySelect)
			_currentScreen = new DifficultySelectScreen();
		else if (s == Screens.Gameplay)
			_currentScreen = new GameplayScreen();
		else if (s == Screens.Options)
			_currentScreen = new OptionsScreen();
		else if (s == Screens.Help)
			_currentScreen = new HelpScreen();
		else if (s == Screens.HighScore)
			_currentScreen = new HighScoreScreen();
		else if (s == Screens.Splash)
			_currentScreen = new SplashScreen();

		_currentScreen.show();
	}

	/**
	 * Loads high scores & crowd ratings from a file.
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

			while ((line = reader.readLine())!= null) // While there is a lind to read
			{
                String[] tokens = line.split(" "); // Tokenize it into "score" and "rating"

				if(tokens[0].compareTo("e") == 0  || tokens[0].compareTo("n") == 0  || tokens[0].compareTo("h") == 0 ) // Check the line is one of the difficulty markers
				{
					// Move onto the next difficulty (next dimension in the array)
					diffCounter++;
					scoreCounter = 0;
					continue;
				}

				// Store the high score and the associated crowd rating
				HighScores[diffCounter][scoreCounter]   = Integer.parseInt(tokens[0]);
                CrowdRatings[diffCounter][scoreCounter] = CrowdRating.GetRating("" + tokens[1]);
				scoreCounter++;
			}
			reader.close();
		}
		catch (IOException e)
		{
			System.out.print("Data cannot be loaded at this time.");
		}
	}

	/**
	 * Writes the scores and crowd ratings to a file
	 * @param reset true if you want to reset the high scores, false otherwise
     */
	public static void WriteScores(boolean reset)
	{
		if(reset)
		{
			HighScores   = new int[Difficulty.values().length][NUM_SCORES];
			CrowdRatings = new CrowdRating[Difficulty.values().length][NUM_SCORES];
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
				for(int j = 0; j < NUM_SCORES; j++)
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
			System.out.print("Data cannot be saved at this time.");
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

	/**
	 * Loads the music and sound effects volumes from a file
	 */
	public static void LoadVolume()
	{
		try
		{
			// Open the file
			BufferedReader reader = new BufferedReader(new FileReader("Storage\\Volume.txt"));

			String line      = null;
			int counter      = 0;

			// Read two lines from the file and store in the appropriate variables
			while ((line = reader.readLine())!= null)
			{
				if(counter == 0)
					MusicVolume = Float.parseFloat(line);
				else if(counter == 1)
					SFXVolume = Float.parseFloat(line);
				else
					break;
				counter++;
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.out.print("Volume data cannot be loaded at this time.");
		}
	}

	/**
	 * Writes the new volume values to a file
	 */
	public static void WriteVolume()
	{
		try
		{
			File file = new File("Storage\\Volume.txt");

			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fileWriter);

			writer.write(MusicVolume + "");
			writer.newLine();
			writer.write(SFXVolume + "");

			writer.close();
		}
		catch(IOException x)
		{
			System.out.print("Volume data cannot be saved at this time.");
		}
	}
}
