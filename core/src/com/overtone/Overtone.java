package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.overtone.Notes.OvertoneNote;
import com.overtone.Notes.Target;
import com.overtone.Screens.*;
import java.util.ArrayList;
import jm.audio.Instrument;
import jm.music.data.*;
import javax.sound.midi.Sequencer;
import jm.JMC;

/**
 * Manager for everything in the game, handles updating everything
 */
public class Overtone extends ApplicationAdapter implements JMC
{
	/**Maximum number of scores that are saved for each difficulty*/
	public static final int NUM_SCORES = 10;

	public static final int NUM_RATERS  = 5;

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
	public static float                   ScreenWidth;        // The width of the screen;
	public static float                   ScreenHeight;       // The height of the screen;
	public static int[][]                 HighScores;         // Stores the high scores for each difficulty
    public static CrowdRating[][]         CrowdRatings;       // Stores the associated crowd ratings for each high score
	public static Difficulty              Difficulty;         // Stores the chosen difficulty of the game
	public static float                   MusicVolume;        // Stores the music volume for all music in the game
	public static float                   SFXVolume;          // Stores the sound effects volume for all sound effects in the game
	public static ArrayList<OvertoneNote> NoteQueue;          // Storage for notes that are not on screen
	public static ArrayList<OvertoneNote> BackupQueue;        // Backup for the whole notes in the song
	public static float                   TotalTime;          // The amount of time the song takes
	public static float[]                 BestRaterValues;    // The stored rater values
	public static float[]                 CurrentRaterValues; // The currently generated rating values
	public static Target[]                TargetZones;        // Array of targets that represent the four target zones
	public static boolean                 Regenerate;         // True if you want to regenerate the music or not
	public static Score                   GameMusic;          // Music for the game
	public static Instrument[]            GameInstruments;    // Instruments for the game music
	public static Sequencer               GameplaySequencer;  // Plays the midi sound
	public static Sequencer               MenuSequencer;      // Plays the midi sound
	private static OvertoneScreen         _currentScreen;     // The current screen displayed on screen
	private SpriteBatch                   _batch;             // Sprite batch to draw to
	private Sprite                        _farBackground;     // The star background for the whole app
	private Sprite                        _closeBackground;   // The cloud background over-top of the star background (for depth)


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
		NoteQueue          = new ArrayList<>();
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
		Utilities.LoadHighScores();
		Utilities.LoadVolume();
		Utilities.LoadRaterValues();
		Utilities.LoadMidiMusic(true);

		// Debug track
		/*Phrase t = new Phrase();
		t.addNote(new Note(C4, QUARTER_NOTE));
		t.addNote(new Note(C4, QUARTER_NOTE));
		t.addNote(new Note(C4, QUARTER_NOTE));
		t.addNote(new Note(C6, QUARTER_NOTE));
		t.addNote(new Note(C6, QUARTER_NOTE));
		t.addNote(new Note(C6, QUARTER_NOTE));
		t.addNote(new Note(C5, QUARTER_NOTE));
		t.addNote(new Note(C5, QUARTER_NOTE));
		t.addNote(new Note(C5, QUARTER_NOTE));
		t.addNote(new Note(C7, QUARTER_NOTE));
		t.addNote(new Note(C7, QUARTER_NOTE));
		t.addNote(new Note(C7, QUARTER_NOTE));

		Mutator m = new SwapMutator();
		t = m.Mutate(t, 0.95f);*/
		return;
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

		if(!MenuSequencer.isRunning())
			MenuSequencer.start();

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

		_currentScreen.show();
	}

	public void dispose()
	{
		_batch.dispose();

		// Dispose the gameplay sequencer
		if(GameplaySequencer != null)
		{
			if(GameplaySequencer.isRunning())
				GameplaySequencer.stop();

			GameplaySequencer.close();
		}

		// Dispose the menu sequencer
		if(MenuSequencer != null)
		{
			if(MenuSequencer.isRunning())
				MenuSequencer.stop();

			MenuSequencer.close();
		}
	}
}
