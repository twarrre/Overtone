package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.overtone.Notes.Note;
import com.overtone.Notes.Target;
import com.overtone.Screens.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manager for everything in the game, handles updating everything
 */
public class Overtone extends ApplicationAdapter
{
	/**Maximum number of scores that are saved for each difficulty*/
	public static final int NUM_SCORES = 10;

	public static final int NUM_RATERS  = 4;

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
		Splash,
		Loading
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
			score -= 0.2f * ((float)counters[4] / totalNotes); // negative points for a miss

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
	public static float           ScreenWidth;        // The width of the screen;
	public static float           ScreenHeight;       // The height of the screen;
	public static int[][]         HighScores;         // Stores the high scores for each difficulty
    public static CrowdRating[][] CrowdRatings;       // Stores the associated crowd ratings for each high score
	public static Difficulty      Difficulty;         // Stores the chosen difficulty of the game
	public static float           MusicVolume;        // Stores the music volume for all music in the game
	public static float           SFXVolume;          // Stores the sound effects volume for all sound effects in the game
	public static ArrayList<Note> NoteQueue;          // Storage for notes that are not on screen
	public static ArrayList<Note> BackupQueue;        // Backup for the whole notes in the song
	public static float           TotalTime;          // The amount of time the song takes
	public static float[]         BestRaterValues;    // The stored rater values
	public static float[]         CurrentRaterValues; // The currently generated rating values
	public static Target[]        TargetZones;        // Array of targets that represent the four target zones
	public static boolean         Regenerate;         // True if you want to regenerate the music or not
	private static OvertoneScreen _currentScreen;     // The current screen displayed on screen
	private SpriteBatch           _batch;             // Sprite batch to draw to
	private Sprite                _farBackground;     // The star background for the whole app
	private Sprite                _closeBackground;   // The cloud background over-top of the star background (for depth)

	@Override
	public void create ()
	{
		ScreenWidth        = Gdx.graphics.getWidth();
		ScreenHeight       = Gdx.graphics.getHeight();
		Difficulty         = Difficulty.Easy;
		HighScores         = new int[Difficulty.values().length][NUM_SCORES];
        CrowdRatings       = new CrowdRating[Difficulty.values().length][NUM_SCORES];
		MusicVolume        = 1.0f;
		SFXVolume          = 1.0f;
		BestRaterValues    = new float[NUM_RATERS];
		CurrentRaterValues = new float[NUM_RATERS];
		_currentScreen     = new SplashScreen();
		_batch             = new SpriteBatch();
		_farBackground     = new Sprite(new Texture("Textures\\space.jpg"));
		_closeBackground   = new Sprite(new Texture("Textures\\clouds.png"));
		TargetZones        = new Target[4];
		TargetZones[0]     = new Target(Overtone.TargetZone.TopLeft);
		TargetZones[1]     = new Target(Overtone.TargetZone.TopRight);
		TargetZones[2]     = new Target(Overtone.TargetZone.BottomLeft);
		TargetZones[3]     = new Target(Overtone.TargetZone.BottomRight);
		Regenerate         = true;

		_farBackground.setCenter(ScreenWidth / 2.0f, ScreenHeight / 2.0f);
		_closeBackground.setCenter(ScreenWidth / 2.0f, ScreenHeight / 2.0f);
		_currentScreen.show();
		LoadHighScores();
		LoadVolume();
		LoadRaterValues();
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
		else if (s == Screens.Loading)
			_currentScreen = new LoadingScreen();

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

	/**
	 * Load the rater values from a file
	 */
	public static void LoadRaterValues()
	{
		try
		{
			// Open the file
			BufferedReader reader = new BufferedReader(new FileReader("Storage\\GeneticRaters.txt"));

			String line  = null;
			int    index = 0;

			while ((line = reader.readLine())!= null && index < NUM_RATERS) // While there is a lind to read
			{
				BestRaterValues[index] = Float.parseFloat(line);
				index++;
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.out.print("Data cannot be loaded at this time.");
		}
	}

	/**
	 * Write the rater values to a file
	 */
	public static void WriteRaterValues()
	{
		try
		{
			File file = new File("Storage\\GeneticRaters.txt");

			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fileWriter);

			for(int i = 0; i < BestRaterValues.length; i++)
			{
				writer.write(BestRaterValues[i] + "");
				writer.newLine();
			}

			writer.close();
		}
		catch(IOException x)
		{
			System.out.print("Data cannot be saved at this time.");
		}
	}

	/**
	 * Average the rater scores if the user says that they like the song
	 */
	public static void AverageRaterValues()
	{
		for(int i = 0; i < NUM_RATERS; i++)
			BestRaterValues[i] = (BestRaterValues[i] + CurrentRaterValues[i]) / 2.0f;
	}

	/**
	 * Resorts the notes and saves a backup of them.
	 * @param notes The notes to sort and create a backup from
     */
	public static void SortNotes(ArrayList<Note> notes)
	{
		// Sort notes based on the time they appear on screen
		Collections.sort(notes);

		Overtone.NoteQueue   = new ArrayList<Note>();
		Overtone.BackupQueue = new ArrayList<Note>();

		for(int i = 0; i < notes.size(); i++)
		{
			Overtone.NoteQueue.add(new Note(notes.get(i)));
			Overtone.BackupQueue.add(new Note(notes.get(i)));
		}

		for(int i = 0; i < Overtone.NoteQueue.size(); i++)
		{
			if((Overtone.NoteQueue.get(i).GetType() == Note.NoteType.Hold || Overtone.NoteQueue.get(i).GetType() == Note.NoteType.Double) && Overtone.NoteQueue.get(i).GetOtherNote() == null )
			{
				int index = BinarySearch(Overtone.NoteQueue, Overtone.NoteQueue.get(i).GetOtherNoteTime(), 0, Overtone.NoteQueue.size() - 1);
				Overtone.NoteQueue.get(i).SetOtherNote(Overtone.NoteQueue.get(index));
				Overtone.NoteQueue.get(index).SetOtherNote(Overtone.NoteQueue.get(i));

				Overtone.BackupQueue.get(i).SetOtherNote(Overtone.BackupQueue.get(index));
				Overtone.BackupQueue.get(index).SetOtherNote(Overtone.BackupQueue.get(i));
			}
		}
	}

	/**
	 * Binary search to search for the coresponding note
	 * @param n The array to search
	 * @param search The value to seach for
	 * @param low low index of the array
	 * @param high high index of the array
     * @return the index of the found note, -1 otherwise
     */
	private static int BinarySearch(ArrayList<Note> n, float search, int low, int high)
	{
		int len = (high - low);
		int index = (high + low) / 2;

		if(len == 1 && n.get(index).GetTime() == search)
			return index;
		else if(len == 1 && n.get(index).GetTime() != search)
			return -1;

		if(search < n.get(index).GetTime())
			return BinarySearch(n, search, low, index);
		else if(search > n.get(index).GetTime())
			return BinarySearch(n, search, index, high);
		else
			return index;
	}
}
