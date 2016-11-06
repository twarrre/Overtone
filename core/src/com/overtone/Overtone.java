package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.overtone.Notes.OvertoneNote;
import com.overtone.Notes.Target;
import com.overtone.Screens.*;
import java.util.*;

import com.overtone.Testing.CrossoverTest;
import com.overtone.Testing.ElitismTest;
import com.overtone.Testing.MutatorTests.*;
import com.overtone.Testing.RaterTests.*;
import com.overtone.Testing.SelectionTest;
import jm.music.data.*;
import javax.sound.midi.Sequencer;
import jm.JMC;

/**
 * Manager for everything in the game, handles updating everything
 */
public class Overtone extends ApplicationAdapter implements JMC
{
	/** Maximum number of scores per difficulty. */
	public static final int NUM_SCORES = 10;
	/** The number of total raters in the genetic algorithm. */
	public static final int NUM_RATERS  = 18;

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
		Loading,
		Mutation
	}

	/**The available difficulties for the game.*/
	public enum Difficulty
	{
		Easy(3.0f),
		Normal(2.5f),
		Hard(2.0f);

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

			// Points given for each type of rating received
			score += 4 * counters[0];
			score += 3 * counters[1];
			score += 2 * counters[2];
			score += 0 * counters[3];
			score -= 1 * counters[4];

			float bestScore = totalNotes * 4;

			float scorePercentage = score / bestScore;
			if(scorePercentage >= 1.0f)
				return Perfection;
			else if(scorePercentage >= 0.85f && scorePercentage < 1.0f)
				return Brilliant;
			else if(scorePercentage >= 0.65f && scorePercentage < 0.85f)
				return Great;
			else if(scorePercentage >= 0.45f && scorePercentage < 0.65f)
				return Cleared;
			else if(scorePercentage < 0.45f)
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
	public static float                              ScreenWidth;           // The width of the screen
	public static float                              ScreenHeight;          // The height of the screen
	public static Vector2                            NoteScale;             // Scale of a note object
	public static Vector2                            NoteCenter;            // Initial position of a note object
	public static int[][]                            HighScores;            // Stores the high scores for each difficulty
    public static CrowdRating[][]                    CrowdRatings;          // Stores the associated crowd ratings for each high score
	public static Difficulty                         Difficulty;            // Stores the chosen difficulty of the game
	public static float                              MusicVolume;           // Stores the music volume for all music in the game
	public static float                              SFXVolume;             // Stores the sound effects volume for all sound effects in the game
	public static ArrayList<OvertoneNote>            NoteQueue;             // Storage for notes that are not on screen
	public static double                             TotalTime;             // The amount of time the song takes
	public static float[]                            BestRaterValues;       // The stored rater values
	public static float[]                            CurrentRaterValues;    // The currently generated rating values
	public static Target[]                           TargetZones;           // Array of targets that represent the four target zones
	public static boolean                            Regenerate;            // True if you want to regenerate the music or not
	public static Part                               GameMusic;             // Stores the jmusic generated notes from the genetic algorithm
	public static Sequencer                          BeatSequencer;         // Player for the beat in the background of the gameplay screen
	public static Sequencer                          MenuSequencer;         // Player for the menu music in on all screens that are not the gameplay screen or loading screen
	public static ArrayList<Pair<Sequencer, Double>> GameNoteSequencers;    // Stores sequencers to play each note generated in the algorithm
	public static ArrayList<Double>                  GameMusicStartTimes;   // The start time for each for each of the notes generated from the algorithm
	public static int                                NumberOfIterations;    // Represents the number of iterations the algorithm is going to go through
	public static int                                PopulationSize;        // The size of the population of tracks.
	public static int                                NumberOfElites;        // Number of elites to save
	public static float[]                            PitchMutatorValues;    // Storage of the starting point, minimum value and step value of the Pitch mutator
	public static float[]                            RhythmMutatorValues;   // Storage of the starting point, minimum value and step value of the Rhythm mutator
	public static float[]                            SimplifyMutatorValues; // Storage of the starting point, minimum value and step value of the Simplify mutator
	public static float[]                            SwapMutatorValues;     // Storage of the starting point, minimum value and step value of the Swap mutator
	public static float[]                            DynamicMutatorValues;  // Storage of the starting point, minimum value and step value of the Dynamic mutator
	private static OvertoneScreen                    _currentScreen;        // The current screen displayed on screen
	private SpriteBatch                              _batch;                // Sprite batch to draw to
	private Sprite                                   _farBackground;        // The star background for the whole app
	private Sprite                                   _closeBackground;      // The cloud background over-top of the star background (for depth)

	@Override
	public void create () {
		ScreenWidth = Gdx.graphics.getWidth();
		ScreenHeight = Gdx.graphics.getHeight();
		NoteScale = new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f);
		NoteCenter = new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f);
		Difficulty = Difficulty.Easy;
		HighScores = new int[Difficulty.values().length][NUM_SCORES];
		CrowdRatings = new CrowdRating[Difficulty.values().length][NUM_SCORES];
		MusicVolume = 1.0f;
		SFXVolume = 0.15f;
		BestRaterValues = new float[NUM_RATERS];
		CurrentRaterValues = new float[NUM_RATERS];
		NoteQueue = new ArrayList<OvertoneNote>();
		_currentScreen = new SplashScreen();
		_batch = new SpriteBatch();
		_farBackground = new Sprite(new Texture("Assets\\Textures\\space.jpg"));
		_closeBackground = new Sprite(new Texture("Assets\\Textures\\clouds.png"));
		TargetZones = new Target[4];
		TargetZones[0] = new Target(Overtone.TargetZone.TopLeft);
		TargetZones[1] = new Target(Overtone.TargetZone.TopRight);
		TargetZones[2] = new Target(Overtone.TargetZone.BottomLeft);
		TargetZones[3] = new Target(Overtone.TargetZone.BottomRight);
		Regenerate = true;
		NumberOfIterations = 500;
		PopulationSize = 25;
		NumberOfElites = 5;
		GameNoteSequencers = new ArrayList();
		GameMusicStartTimes = new ArrayList();
		PitchMutatorValues = new float[3];
		RhythmMutatorValues = new float[3];
		SimplifyMutatorValues = new float[3];
		SwapMutatorValues = new float[3];
		DynamicMutatorValues = new float[3];
		PitchMutatorValues[0] = 1.0f;
		PitchMutatorValues[1] = 0.01f;
		PitchMutatorValues[2] = 0.05f;
		RhythmMutatorValues[0] = 1.0f;
		RhythmMutatorValues[1] = 0.01f;
		RhythmMutatorValues[2] = 0.05f;
		SimplifyMutatorValues[0] = 1.0f;
		SimplifyMutatorValues[1] = 0.01f;
		SimplifyMutatorValues[2] = 0.05f;
		SwapMutatorValues[0] = 1.0f;
		SwapMutatorValues[1] = 0.01f;
		SwapMutatorValues[2] = 0.05f;
		DynamicMutatorValues[0] = 1.0f;
		DynamicMutatorValues[1] = 0.01f;
		DynamicMutatorValues[2] = 0.05f;

		_farBackground.setCenter(ScreenWidth / 2.0f, ScreenHeight / 2.0f);
		_closeBackground.setCenter(ScreenWidth / 2.0f, ScreenHeight / 2.0f);
		_currentScreen.show();

		Utilities.LoadHighScores();
		Utilities.LoadRaterValues();
		Utilities.LoadGenerationValues();
		Utilities.LoadMutationValues();
		Utilities.LoadMidiMusic(true);
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
	 * @param combo The combo achied by the player
	 * @param counters Counters for each type of rating
     */
	public static void SetScreen(Screens s, boolean completed, int score, int combo, int ... counters)
	{
		_currentScreen.hide();

		if(!MenuSequencer.isRunning())
			MenuSequencer.start();

		if (s == Screens.SongComplete)
			_currentScreen = new SongCompleteScreen(completed, score, combo, counters);

		_currentScreen.show();
	}

	/**
	 * Sets the screen to a new screen.
	 * @param s The type of the next screen.
     */
	public static void SetScreen(Screens s)
	{
		_currentScreen.hide();

		if((s != Screens.Gameplay && s != Screens.Loading) && !MenuSequencer.isRunning())
			MenuSequencer.start();
		else if((s == Screens.Gameplay || s == Screens.Loading) && MenuSequencer.isRunning())
			MenuSequencer.stop();

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
		else if(s == Screens.Mutation)
			_currentScreen = new MutationScreen();

		_currentScreen.show();
	}

	public void dispose()
	{
		_batch.dispose();

		// Dispose the beat sequencer
		if(BeatSequencer != null)
		{
			if(BeatSequencer.isRunning())
				BeatSequencer.stop();

			BeatSequencer.close();
		}

		// Dispose the menu sequencer
		if(MenuSequencer != null)
		{
			if(MenuSequencer.isRunning())
				MenuSequencer.stop();

			MenuSequencer.close();
		}

		// Dispose the note sequencers
		for(int i = 0; i <  Overtone.GameNoteSequencers.size(); i++)
		{
			if( Overtone.GameNoteSequencers.get(i).first.isRunning())
				Overtone.GameNoteSequencers.get(i).first.stop();
			if( Overtone.GameNoteSequencers.get(i).first.isOpen())
				Overtone.GameNoteSequencers.get(i).first.close();
		}

	}
}
