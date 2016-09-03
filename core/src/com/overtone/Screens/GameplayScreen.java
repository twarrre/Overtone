package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.InputManager;
import com.overtone.Notes.OvertoneNote;
import com.overtone.Notes.NoteRenderer;
import com.overtone.Notes.Target;
import com.overtone.Overtone;
import com.overtone.Quadtree;
import com.overtone.Ratings.Rating;
import com.overtone.Ratings.RatingRenderer;
import com.overtone.Utilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Screen used during gameplay
 * Created by trevor on 2016-05-01.
 */
public class GameplayScreen extends OvertoneScreen
{
    /** Maximum time error for putting notes on screen */
    public static final float ERROR            = 0.000045f;

    /** The delay for the pause resuming */
    public static final float RESUME_DELAY     = 3.0f;

    /** The delay for the completion or failure of the song */
    public static final float COMPLETION_DELAY = 5.0f;

    /** Delay for the start of the song*/
    public static final float START_DELAY = 5.0f;

    /** Timers that determine if you fail for each difficulty */
    public static final float[] FAILURE_TIMER  = { 12.0f, 11.0f, 10.0f };

    // Objects
    private final NoteRenderer                             _noteRenderer;       // Renders the notes on to the screen
    private final RatingRenderer                           _ratingRenderer;     // Renders ratings on screen
    private final InputManager                             _input;              // Manager input for the screen
    private final Stage                                    _stage;              // Stage to render buttons and stuff

    // Textures
    private final Texture                                  _targetZone;         // Texture for the target zones
    private final Texture                                  _targetZonePressed;  // Texture for when a target zone is pressed
    private final Texture                                  _progressBar;        // Texture for the progress bar
    private final Texture                                  _progress;           // Texture for the progress of the progress bar
    private final Texture                                  _progressArrow;      // Texture for arrow in the progress bar
    private final Texture                                  _d;                  // Texture for the bottom left target zone
    private final Texture                                  _e;                  // Texture for the top left target zone
    private final Texture                                  _i;                  // Texture for the top right target zone
    private final Texture                                  _k;                  // Texture for the bottom right target zone
    private final Texture                                  _background;         // Texture for background of the pause menu
    private final Texture                                  _perfection;         // Texture for Perfection crowd rating
    private final Texture                                  _brilliant;          // Texture for brilliant crowd rating
    private final Texture                                  _great;              // Texture for great crowd rating
    private final Texture                                  _cleared;            // Texture for cleared crowd rating
    private final Texture                                  _failure;            // Texture for failure crowd rating
    private final Texture                                  _losing;             // Texture for the losing animation
    private final Sprite                                   _ship;               // Texture for the ship in the center of the screen
    private Texture                                        _currentCrowdRating; // Texture for the current crowd ratings on screen

    // Data Structures
    private Quadtree                                       _onScreenNotes;      // Stores notes that are on screen
    private ArrayList<Rating>                              _onScreenRatings;    // Stores ratings that are on screen
    private HashMap<OvertoneNote, InputManager.KeyBinding> _holdNotesOnScreen;  // List of current hold notes on screen

    // Sound
    private final Sound[]                                   _noteSFX;            // Stores the note sound effects for good, bad, and none
    private final Sound                                     _noteShot;           // Sound effect for when the ship shoots a note
    private final Sound                                     _success;            // Sound effect for when you successfully complete a song
    private final Sound                                     _fail;               // Sound effect for when you fail a song

    // Variables
    private final boolean[]                                 _targetZonesPressed; // Boolean to say if the particular target zone has been pressed
    private final Button                                    _resumeButton;       // Resume Button for the pause menu
    private final Button                                    _retryButton;        // Retry button for the pause menu
    private final Button                                    _quitButton;         // Main menu button for the pause menu
    private final Button                                    _difficultyButton;   // Difficulty select button for the pause menu
    private final Button                                    _musicNext;          // Music volume up button for pause menu
    private final Button                                    _sfxNext;            // SFX volume up button for pause menu
    private final Button                                    _musicBack;          // Music volume down button for pause menu
    private final Button                                    _sfxBack;            // SFX volume down button for pause menu
    private final Button                                    _pauseButton;        // Pause button to bring up pause menu
    private final Vector2                                   _ratingScale;        // Scale for a rating to show up on screen
    private Vector2                                         _shipDirection;      // The direction that the sound is pointing
    private boolean                                         _paused;             // True if game is paused, false otherwise
    private boolean                                         _resuming;           // True if in resuming state, false otherwise
    private boolean                                         _songComplete;       // True if the song is complete, false otherwise
    private float                                           _elapsedTime;        // Amount of time the song has been playing
    private float                                           _resumeTimer;        // Amount of time that has passed in the resume state
    private float                                           _prevResumeTimer;    // Amount of time that has passed in the resume state, last frame
    private float                                           _completionTimer;    // Amount of time in the completion state
    private float                                           _failureTimer;       // Amount of time spent in the fail state
    private int                                             _perfectCounter;     // The number of perfect notes
    private int                                             _greatCounter;       // The number of great notes
    private int                                             _goodCounter;        // The number of good notes
    private int                                             _badCounter;         // The number of bad notes
    private int                                             _missCounter;        // The number of missed notes
    private int                                             _combo;              // The current combo
    private int                                             _score;              // The current score
    private boolean                                         _musicPlaying;       // True if the music is playing, false otherwise

    /**
     * Constructor
     */
    public GameplayScreen()
    {
        super();

        // Initialize objects
        _input          = new InputManager();
        _noteRenderer   = new NoteRenderer();
        _ratingRenderer = new RatingRenderer();
        _stage          = new Stage();

        // Load textures
        _targetZone         = new Texture(Gdx.files.internal("Textures\\targetzone.png"));
        _targetZonePressed  = new Texture(Gdx.files.internal("Textures\\targetzonepressed.png"));
        _progressBar        = new Texture(Gdx.files.internal("Textures\\progressbar.png"));
        _progress           = new Texture(Gdx.files.internal("Textures\\red.png"));
        _progressArrow      = new Texture(Gdx.files.internal("Textures\\arrow.png"));
        _e                  = new Texture(Gdx.files.internal("Textures\\e.png"));
        _d                  = new Texture(Gdx.files.internal("Textures\\d.png"));
        _i                  = new Texture(Gdx.files.internal("Textures\\i.png"));
        _k                  = new Texture(Gdx.files.internal("Textures\\k.png"));
        _background         = new Texture(Gdx.files.internal("Textures\\background.png"));
        _perfection         = new Texture(Gdx.files.internal("Textures\\perfection.png"));
        _brilliant          = new Texture(Gdx.files.internal("Textures\\brilliant.png"));
        _great              = new Texture(Gdx.files.internal("Textures\\great.png"));
        _cleared            = new Texture(Gdx.files.internal("Textures\\cleared.png"));
        _failure            = new Texture(Gdx.files.internal("Textures\\failure.png"));
        _losing             = new Texture(Gdx.files.internal("Textures\\losing.png"));
        _currentCrowdRating = _cleared;
        _ship               = new Sprite(new Texture(Gdx.files.internal("Textures\\ship.png")));
        _ship.setCenter(Overtone.ScreenWidth * 0.5f, Overtone.ScreenHeight * 0.5f);
        _ship.setScale(0.75f);
        _shipDirection = new Vector2(0, 0);
        _ship.setRotation(0);

        // Load sounds
        _noteSFX    = new Sound[3];
        _noteSFX[0] = Gdx.audio.newSound(Gdx.files.internal("Sounds\\note_good.wav"));
        _noteSFX[1] = Gdx.audio.newSound(Gdx.files.internal("Sounds\\note_bad.wav"));
        _noteSFX[2] = Gdx.audio.newSound(Gdx.files.internal("Sounds\\note_none.wav"));
        _noteShot   = Gdx.audio.newSound(Gdx.files.internal("Sounds\\laser.wav"));
        _success    = Gdx.audio.newSound(Gdx.files.internal("Sounds\\complete.wav"));
        _fail       = Gdx.audio.newSound(Gdx.files.internal("Sounds\\fail.wav"));

        // Initialize variables
        _targetZonesPressed    = new boolean[4];
        _targetZonesPressed[0] = false;
        _targetZonesPressed[1] = false;
        _targetZonesPressed[2] = false;
        _targetZonesPressed[3] = false;
        _elapsedTime           = 0.0f;
        _resumeTimer           = 0.0f;
        _prevResumeTimer       = 0.0f;
        _completionTimer       = 0.0f;
        _failureTimer          = 0.0f;
        _songComplete          = false;
        _paused                = false;
        _resuming              = false;
        _perfectCounter        = 0;
        _greatCounter          = 0;
        _goodCounter           = 0;
        _badCounter            = 0;
        _missCounter           = 0;
        _combo                 = 0;
        _score                 = 0;
        _ratingScale           = new Vector2(Overtone.ScreenWidth * 0.1f, Overtone.ScreenHeight * 0.09f);
        _holdNotesOnScreen     = new HashMap<>();
        _onScreenNotes         = new Quadtree(new Rectangle(0, 0, Overtone.ScreenWidth, Overtone.ScreenHeight));
        _onScreenRatings       = new ArrayList<>();
        _musicPlaying          = false;

        // Create the resume button on the paused menu
        _resumeButton = CreateButton("RESUME", "small", Overtone.ScreenWidth * 0.2f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.725f), _stage);
        _resumeButton.setDisabled(true);
        _resumeButton.setVisible(false);
        _resumeButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ResumeButtonPressed();
            }
        });

        // Create the retry button on the paused menu
        _retryButton = CreateButton("RETRY", "small", Overtone.ScreenWidth * 0.2f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.625f), _stage);
        _retryButton.setDisabled(true);
        _retryButton.setVisible(false);
        _retryButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                PausedMenuButtonPressed(Overtone.Screens.Loading);
                Overtone.Regenerate = false;
            }});

        // Create the change difficulty button on the paused menu
        _difficultyButton = CreateButton("Difficulty", "small", Overtone.ScreenWidth * 0.2f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.525f), _stage);
        _difficultyButton.setDisabled(true);
        _difficultyButton.setVisible(false);
        _difficultyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                PausedMenuButtonPressed(Overtone.Screens.DifficultySelect);
                Overtone.Regenerate = false;
            }});

        // Create the main menu button on the paused menu
        _quitButton = CreateButton("MAIN MENU", "small", Overtone.ScreenWidth * 0.2f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.425f), _stage);
        _quitButton.setDisabled(true);
        _quitButton.setVisible(false);
        _quitButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                PausedMenuButtonPressed(Overtone.Screens.MainMenu);}
        });

        // Create next button for music volume
        _musicNext = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.02f, Overtone.ScreenWidth * 0.02f, new Vector2(Overtone.ScreenWidth * 0.58f, Overtone.ScreenHeight * 0.325f), _stage);
        _musicNext.setDisabled(true);
        _musicNext.setVisible(false);
        _musicNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            VolumeButtonPressed(false, 1);
        }});

        // Create back button for music volume
        _musicBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.02f, Overtone.ScreenWidth * 0.02f, new Vector2(Overtone.ScreenWidth * 0.55f,  Overtone.ScreenHeight * 0.325f), _stage);
        _musicBack.setDisabled(true);
        _musicBack.setVisible(false);
        _musicBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            VolumeButtonPressed(false, -1);
        }});

        // Create the next button for sfx
        _sfxNext = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.02f, Overtone.ScreenWidth * 0.02f, new Vector2(Overtone.ScreenWidth * 0.58f,  Overtone.ScreenHeight * 0.225f), _stage);
        _sfxNext.setDisabled(true);
        _sfxNext.setVisible(false);
        _sfxNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            VolumeButtonPressed(true, 1);
        }});

        // Create the back button for sfx
        _sfxBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.02f, Overtone.ScreenWidth * 0.02f,  new Vector2(Overtone.ScreenWidth * 0.55f, Overtone.ScreenHeight * 0.225f), _stage);
        _sfxBack.setDisabled(true);
        _sfxBack.setVisible(false);
        _sfxBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            VolumeButtonPressed(true, -1);
        }});

        // Create the pause button
        _pauseButton = CreateButton("Pause", "small", Overtone.ScreenWidth * 0.08f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.46f, Overtone.ScreenHeight * 0.87f), _stage);
        _pauseButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
              PausedButtonPressed();
            }});

        Utilities.LoadMidiMusic(false);
    }

    /**
     * Renders the gameplay screen
     * @param deltaTime The time since the last frame
     */
    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _batch.begin();

        // Draw progress bar
        float songProgress = _elapsedTime / Overtone.TotalTime;
        _batch.draw(_progressBar, Overtone.ScreenWidth * 0.225f, Overtone.ScreenHeight * 0.95f, Overtone.ScreenWidth * 0.55f, Overtone.ScreenHeight * 0.03f);
        _batch.draw(_progress, Overtone.ScreenWidth * 0.23f, Overtone.ScreenHeight * 0.955f, Overtone.ScreenWidth * 0.54f * songProgress , Overtone.ScreenHeight * 0.02f);
        _batch.draw(_progressArrow, Overtone.ScreenWidth * 0.23f - (_progressArrow.getWidth() / 2.0f) + (Overtone.ScreenWidth * 0.54f * songProgress), Overtone.ScreenHeight * 0.94f, _progressArrow.getWidth(), _progressArrow.getHeight());

        // Draw the letter borders
        float letterWidth = Overtone.ScreenWidth * 0.2f;
        _batch.draw(_d, 0, 0,letterWidth, letterWidth);
        _batch.draw(_k, Overtone.ScreenWidth * 0.8f, 0, letterWidth, letterWidth);
        _batch.draw(_i, Overtone.ScreenWidth * 0.8f, Overtone.ScreenHeight - letterWidth, letterWidth, letterWidth);
        _batch.draw(_e, 0, Overtone.ScreenHeight - letterWidth, letterWidth, letterWidth);

        // Draw the target zones
        for(int i = 0; i < Overtone.TargetZones.length; i++)
            _batch.draw(_targetZonesPressed[i] ? _targetZonePressed : _targetZone,  Overtone.TargetZones[i].GetDrawingPosition().x,  Overtone.TargetZones[i].GetDrawingPosition().y, Target.Diameter, Target.Diameter);

        // Draw the combo
        _glyphLayout.setText(_font18,  "Combo: " + _combo);
        _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.7f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.05f);

        // Draw the score
        _glyphLayout.setText(_font18, "Score: " + _score);
        _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.3f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.05f);

        // Draw the high score
        _glyphLayout.setText(_font18, "High Score: " + Overtone.HighScores[Overtone.Difficulty.ordinal()][0]);
        _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.775f - _glyphLayout.width, Overtone.ScreenHeight * 0.92f);

        // Draw the difficulty
        _glyphLayout.setText(_font18, "Difficulty: " + Overtone.Difficulty.toString());
        _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.225f, Overtone.ScreenHeight * 0.92f);

        // render the notes and ratings
        _noteRenderer.Draw(_onScreenNotes.GetAll(), _holdNotesOnScreen.keySet(), _batch);
        _ratingRenderer.Draw(_onScreenRatings, _batch);

        // Render the ship
        _ship.setRotation(0);
        _ship.rotate((float)Math.toDegrees(Math.atan2(_shipDirection.y, _shipDirection.x )));
        _ship.draw(_batch);

        // Draw the crowd
        float crowdRatingScale = Overtone.ScreenWidth * 0.04f;
        for(int i = 0; i <  5; i++)
            _batch.draw(_currentCrowdRating, Overtone.ScreenWidth * 0.40f + (crowdRatingScale * (float)i), Overtone.ScreenHeight * 0.02f, crowdRatingScale, crowdRatingScale);

        // Draw the failure effect
        _batch.setColor(1.0f, 1.0f, 1.0f, (_failureTimer / FAILURE_TIMER[Overtone.Difficulty.ordinal()]));
        _batch.draw(_losing, 0, 0, Overtone.ScreenWidth, Overtone.ScreenHeight);
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Draw the pause menu
        if(_paused)
        {
            _batch.draw(_background, Overtone.ScreenWidth * 0.375f, 0, Overtone.ScreenWidth * 0.25f, Overtone.ScreenHeight);
            _glyphLayout.setText(_font36,  "Paused");
            _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.92f);

            _glyphLayout.setText(_font18, "Music: " + (int)(Overtone.MusicVolume * 100.0f) + "%");
            _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.325f + _glyphLayout.height);

            _glyphLayout.setText(_font18, "SFX: " + (int)(Overtone.SFXVolume * 100.0f) + "%");
            _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.225f + _glyphLayout.height);
        }

        // Draw the resume countdown
        if(_resuming)
        {
            _batch.draw(_background, Overtone.ScreenWidth * 0.375f, 0, Overtone.ScreenWidth * 0.25f, Overtone.ScreenHeight);
            _glyphLayout.setText(_font18,  "Game will resume in");
            _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.85f);

            _glyphLayout.setText(_font36, (3 - (int)_resumeTimer) + "");
            _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.5f);
        }

        // Draw the song complete screen
        if(_songComplete)
        {
            _batch.draw(_background, 0, 0, Overtone.ScreenWidth, Overtone.ScreenHeight);
            _glyphLayout.setText(_font36, _elapsedTime <  Overtone.TotalTime ? "Game Over..." : "Finished!!");
            _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.5f);
        }

        _batch.end();
        _stage.draw();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
        _stage.act(deltaTime);

        // Do nothing if paused
        if(_paused)
            return;

        // If resuming update timers
        if(_resuming)
        {
            UpdateResumingState(deltaTime);
            return;
        }

        // If song has finished delay a bit
        if(_songComplete)
        {
            _pauseButton.setDisabled(true);
            _pauseButton.setVisible(false);

            _completionTimer += deltaTime;
            if(_completionTimer > COMPLETION_DELAY)
                Overtone.SetScreen(Overtone.Screens.SongComplete, _elapsedTime <  Overtone.TotalTime ? false : true, _score, _perfectCounter, _greatCounter, _goodCounter, _badCounter, _missCounter);
            return;
        }

        if(_elapsedTime < Overtone.TotalTime && !_songComplete)
            _elapsedTime += deltaTime;

        if(_elapsedTime >= START_DELAY && !_musicPlaying)
        {
            Overtone.GameplaySequencer.start();
            _musicPlaying = true;
        }

        if(_elapsedTime >=  Overtone.TotalTime && !_songComplete)
        {
            PlaySongCompletionSFX(true);
            _songComplete = true;
            if(Overtone.GameplaySequencer.isRunning())
                Overtone.GameplaySequencer.stop();
        }

        // Move notes from the note queue to the quadtree if they are ready to be displayed on screen
        if(!Overtone.NoteQueue.isEmpty())
        {
            // Get notes that's time has come to put on screen
            ArrayList<OvertoneNote> forRemoval = new ArrayList<OvertoneNote>();
            for(int i = 0; i <  Overtone.TargetZones.length; i++)
            {
                if(i < Overtone.NoteQueue.size())
                {
                    if(Overtone.NoteQueue.get(i).GetTime() - Overtone.Difficulty.Multiplier <=  _elapsedTime + ERROR)
                    {
                        OvertoneNote n = Overtone.NoteQueue.get(i);
                        n.SetVisibility(true);
                        _onScreenNotes.Insert(n);
                        forRemoval.add(Overtone.NoteQueue.get(i));
                        _noteShot.play(Overtone.SFXVolume);
                    }
                }
            }
            DetermineShipDirection(forRemoval);
            Overtone.NoteQueue.removeAll(forRemoval);
        }

        // Update the note positions
        ArrayList<OvertoneNote> removedNotes = _onScreenNotes.Update(deltaTime);
        if(!removedNotes.isEmpty())
        {
            // All of the notes that have passed their targets
            for(OvertoneNote n : removedNotes)
            {
                _missCounter++;
                _onScreenRatings.add(new Rating(Rating.RatingType.Miss, n.GetPosition(), _ratingScale));
                n.SetVisibility(false);
                if(n.GetType() == OvertoneNote.NoteType.Hold) // if a miss was a hold note then remove the other one
                {
                    _missCounter++; // add a miss for the other note
                    n.GetOtherNote().SetVisibility(false);
                    if(!_onScreenNotes.Remove(n.GetOtherNote()))// if it is note in the quadtree the remove it from the note que
                        Overtone.NoteQueue.remove(n.GetOtherNote());

                    _holdNotesOnScreen.remove(n);
                }
            }
            _combo = 0;
        }

        // Update on screen ratings
        ArrayList<Rating> done = new ArrayList<Rating>();
        for(Rating r : _onScreenRatings)
        {
            r.Update(deltaTime);
            if(!r.IsVisible())
                done.add(r);
        }
        _onScreenRatings.removeAll(done);

        _input.Update();
        CheckInput();
        UpdateCrowdRating(deltaTime);
    }

    /**
     * Handles the input
     */
    private void CheckInput()
    {
        // Handle all hold notes
        Iterator<OvertoneNote> it = _holdNotesOnScreen.keySet().iterator();
        while(it.hasNext())
        {
            OvertoneNote currentNote = it.next();
            InputManager.KeyBinding currentKey = _holdNotesOnScreen.get(currentNote);

            // If they are no longer holding the note
            if(!_input.ActionOccurred(currentKey, InputManager.ActionType.Held))
            {
                // Get its rating
                Rating rating = GetNoteRating(currentNote.GetTarget().Position, currentNote.GetOtherNote());
                HandleRating(rating);

                currentNote.GetOtherNote().SetVisibility(false);
                if(!_onScreenNotes.Remove(currentNote.GetOtherNote()))// if it is note in the quadtree the remove it from the note que
                    Overtone.NoteQueue.remove(currentNote.GetOtherNote());

                _holdNotesOnScreen.remove(currentNote);
            }
        }

        // Check each input related to each target zone
        for(int i = 0; i < Overtone.TargetZones.length; i++)
        {
            _targetZonesPressed[i] = false;
            if(_input.ActionOccurred(InputManager.KeyBinding.values()[i], InputManager.ActionType.Pressed))
            {
                OvertoneNote close = GetClosestNote( Overtone.TargetZones[i].Position, InputManager.KeyBinding.values()[i]);
                Rating rating = GetNoteRating( Overtone.TargetZones[i].Position, close);

                // If the player missed the first hold note, then remove the other one.
                if(rating.GetRating() == Rating.RatingType.Miss && close.GetType() == OvertoneNote.NoteType.Hold)
                {
                    _missCounter++; // add a miss for the other note
                    close.GetOtherNote().SetVisibility(false);
                    if(!_onScreenNotes.Remove(close.GetOtherNote()))// if it is note in the quadtree the remove it from the note que
                        Overtone.NoteQueue.remove(close.GetOtherNote());

                    _holdNotesOnScreen.remove(close);
                }

                _targetZonesPressed[i] = true;
               HandleRating(rating);
            }
        }
    }

    /**
     * Handles the rating in specific ways
     * @param rating The rating to handle
     */
    private void HandleRating(Rating rating)
    {
        if(rating.GetRating().ComboMultiplier == -1)
            _combo = 0;
        else
            _combo += rating.GetRating().ComboMultiplier;

        _noteSFX[rating.GetRating().SoundIndex].play(Overtone.SFXVolume);
        _score += rating.GetRating().Score * _combo;

        if(rating.GetRating() != Rating.RatingType.None)
            _onScreenRatings.add(rating);
    }

    /**
     * Returns the closest on screen note to the passed in target
     * @param target The target to find the closest note to
     * @param key The key binding for this target
     * @return The closest on screen note to the target
     */
    private OvertoneNote GetClosestNote(Vector2 target, InputManager.KeyBinding key)
    {
        ArrayList<OvertoneNote> notes = _onScreenNotes.Get(target);

        if(notes.isEmpty())
            return null;

        float minDistance = Float.MAX_VALUE;
        OvertoneNote closestNote  = null;

        // Find the closest note and store it
        for(OvertoneNote n : notes)
        {
            float distance = Vector2.dst(target.x, target.y, n.GetCenter().x, n.GetCenter().y);
            if(distance < minDistance)
            {
                minDistance = distance;
                closestNote = n;
            }
        }

        // Remove it from the quadtree
        _onScreenNotes.Remove(closestNote);
        if(closestNote.GetType() == OvertoneNote.NoteType.Hold)
            _holdNotesOnScreen.put(closestNote, key);

        return closestNote;
    }

    /**
     * Returns the rating of the note based on the distance from the target zone
     * @param target The target the note is heading to
     * @param closestNote The note that was closest to it
     * @return A rating based on how close the note was to the target
     */
    private Rating GetNoteRating(Vector2 target, OvertoneNote closestNote)
    {
        if(closestNote == null)
            return new Rating(Rating.RatingType.None, new Vector2(), _ratingScale);

        float minDistance = Vector2.dst(target.x, target.y, closestNote.GetCenter().x, closestNote.GetCenter().y);

        // Return a rating based on how close it was to the target
        if(minDistance <= Target.Diameter * 0.15f)
        {
            _perfectCounter++;
            return new Rating(Rating.RatingType.Perfect, closestNote.GetCenter(), _ratingScale);
        }
        else if(minDistance <= Target.Diameter * 0.55f && minDistance > Target.Diameter * 0.15f)
        {
            _greatCounter++;
            return new Rating(Rating.RatingType.Great, closestNote.GetCenter(), _ratingScale);
        }
        else if(minDistance <= Target.Diameter && minDistance > Target.Diameter * 0.55f)
        {
            _goodCounter++;
            return new Rating(Rating.RatingType.Ok, closestNote.GetCenter(), _ratingScale);
        }
        else if(minDistance < Target.Diameter * 2.0f  && minDistance > Target.Diameter)
        {
            _badCounter++;
            return new Rating(Rating.RatingType.Bad, closestNote.GetCenter(), _ratingScale);
        }
        else
        {
            _missCounter++;
            return new Rating(Rating.RatingType.Miss, closestNote.GetCenter(), _ratingScale);
        }
    }

    /**
     * Updates the crowd on screen to different ratings based on the score so far
     * @param deltaTime The time since the last frame
     */
    private void UpdateCrowdRating(float deltaTime)
    {
        Overtone.CrowdRating rating = Overtone.CrowdRating.GetRating(_perfectCounter, _greatCounter, _goodCounter, _badCounter, _missCounter);

        // If you are in failure state, update the timer
        if(rating == Overtone.CrowdRating.Failure)
            _failureTimer += deltaTime;
        else
            _failureTimer = 0.0f;

        // If the timer reaches the maximum time in fail state, then the song is over
        if(_failureTimer > FAILURE_TIMER[Overtone.Difficulty.ordinal()] && !_songComplete)
        {
            PlaySongCompletionSFX(false);
            _songComplete = true;
            if( Overtone.GameplaySequencer.isRunning())
                Overtone.GameplaySequencer.stop();
        }

        // Update the crowd, to reflect the rating
        switch(rating.ordinal())
        {
            case 0:
                _currentCrowdRating = _perfection;
                break;
            case 1:
                _currentCrowdRating = _brilliant;
                break;
            case 2:
                _currentCrowdRating = _great;
                break;
            case 3:
                _currentCrowdRating = _cleared;
                break;
            case 4:
                _currentCrowdRating = _failure;
                 break;
            default:
                _currentCrowdRating = _cleared;
                break;
        }
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        _stage.getViewport().update(width, height, true);
    }
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(_stage);
    }
    public void hide()
    {
        super.hide();
        Gdx.input.setInputProcessor(null);
        if(Overtone.GameplaySequencer.isRunning())
            Overtone.GameplaySequencer.stop();
    }
    public void dispose ()
    {
        super.dispose();
        _noteRenderer.dispose();
        _ratingRenderer.dispose();
        _input.dispose();
        _stage.dispose();
        _targetZone.dispose();
        _targetZonePressed.dispose();
        _progressBar.dispose();
        _progress.dispose();
        _progressArrow.dispose();
        _d.dispose();
        _e.dispose();
        _i.dispose();
        _k.dispose();
        _background.dispose();
        _perfection.dispose();
        _brilliant.dispose();
        _great.dispose();
        _cleared.dispose();
        _failure.dispose();
        _losing.dispose();
        _currentCrowdRating.dispose();
        _noteShot.dispose();
        _success.dispose();
        _fail.dispose();

        for(int i = 0; i < _noteSFX.length; i++)
            _noteSFX[1].dispose();

        if(Overtone.GameplaySequencer.isRunning())
            Overtone.GameplaySequencer.stop();

        Overtone.GameplaySequencer.close();
    }

    /**
     * Called when the song is complete, plays the fail or success sound effect
     * @param completed True if the song was completed successfully, false otherwise
     */
    private void PlaySongCompletionSFX(boolean completed)
    {
        if(completed)
            _success.play(Overtone.SFXVolume);
        else
            _fail.play(Overtone.SFXVolume);
    }

    /**
     * Called when a menu button in the pause menu is clicked
     * @param screen The next screen to transition to
     */
    private void PausedMenuButtonPressed(Overtone.Screens screen)
    {
        Utilities.WriteVolume();
        _buttonPress.play(Overtone.SFXVolume);
        Overtone.SetScreen(screen);
    }

    /**
     * Called when a volume button is pressed from the pause menu
     * @param sfx True if the sfx volume button was pressed, false means the music volume button was pressed
     * @param up 1 if volume is going up, -1 if going down
     */
    private void VolumeButtonPressed(boolean sfx, int up)
    {
        _buttonPress.play(Overtone.SFXVolume);

        if(sfx)
            Overtone.SFXVolume += (0.01f * up);
        else
            Overtone.MusicVolume += (0.01f * up);

        if(Overtone.SFXVolume > 1.0f)
            Overtone.SFXVolume = 1.0f;
        if(Overtone.SFXVolume < 0.0f)
            Overtone.SFXVolume = 0.0f;
    }

    /**
     * Called when the resume button from the pause menu is pressed
     */
    public void ResumeButtonPressed()
    {
        _paused   = false;
        _resuming = true;
        _resumeButton.setDisabled(true);
        _retryButton.setDisabled(true);
        _quitButton.setDisabled(true);
        _difficultyButton.setDisabled(true);
        _musicBack.setDisabled(true);
        _musicNext.setDisabled(true);
        _sfxNext.setDisabled(true);
        _sfxBack.setDisabled(true);
        _resumeButton.setVisible(false);
        _retryButton.setVisible(false);
        _quitButton.setVisible(false);
        _difficultyButton.setVisible(false);
        _musicBack.setVisible(false);
        _musicNext.setVisible(false);
        _sfxNext.setVisible(false);
        _sfxBack.setVisible(false);
        Utilities.WriteVolume();
    }

    /**
     * Called when the paused button is pressed
     */
    public void PausedButtonPressed()
    {
        _paused = true;
        _buttonPress.play(Overtone.SFXVolume);
        _resumeButton.setDisabled(false);
        _retryButton.setDisabled(false);
        _quitButton.setDisabled(false);
        _difficultyButton.setDisabled(false);
        _musicBack.setDisabled(false);
        _musicNext.setDisabled(false);
        _sfxNext.setDisabled(false);
        _sfxBack.setDisabled(false);
        _resumeButton.setVisible(true);
        _retryButton.setVisible(true);
        _quitButton.setVisible(true);
        _difficultyButton.setVisible(true);
        _musicBack.setVisible(true);
        _musicNext.setVisible(true);
        _sfxNext.setVisible(true);
        _sfxBack.setVisible(true);
        _pauseButton.setDisabled(true);
        _pauseButton.setVisible(false);
        if(Overtone.GameplaySequencer.isRunning())
            Overtone.GameplaySequencer.stop();
    }

    /**
     * Called when in the resuming state to update things
     * @param deltaTime the time since last frame
     */
    private void UpdateResumingState(float deltaTime)
    {
        _prevResumeTimer = _resumeTimer;
        _resumeTimer     += deltaTime;

        // Play countdown SFX on 3 2 1 0
        if(_prevResumeTimer == 0 && _resumeTimer >= 0 || _prevResumeTimer < 1 && _resumeTimer >= 1 || _prevResumeTimer < 2 && _resumeTimer >= 2 || _prevResumeTimer < 3 && _resumeTimer >= 3)
            _countdown.play(Overtone.SFXVolume);

        // Once the resume delay is done
        if(_resumeTimer > RESUME_DELAY)
        {
            _paused          = false;
            _resuming        = false;
            _resumeTimer     = 0;
            _prevResumeTimer = 0;
            _pauseButton.setDisabled(false);
            _pauseButton.setVisible(true);
            Overtone.GameplaySequencer.start();
        }
    }

    /**
     * Determines which direction the ship points in based on the notes added to the screen
     * @param notes notes added to the screen
     */
    private void DetermineShipDirection(ArrayList<OvertoneNote> notes)
    {
        // If there is more then one note on screen at the same time, else there is only one on screen at a time
        if(notes.size() > 1)
        {
            // If they ys are equal then it is a horizontal double note, else it is a vertical double note
            if(notes.get(0).GetCenter().y == notes.get(1).GetCenter().y)
            {
                // Find the half way point between the horizontal double note
                float half = Math.abs(notes.get(0).GetCenter().x - notes.get(1).GetCenter().x) / 2.0f;

                // Calculate the leftmost of the two note (this is the starting point
                float x = notes.get(0).GetCenter().x > notes.get(1).GetCenter().x ? notes.get(0).GetCenter().x : notes.get(1).GetCenter().x;

                // Calculate the direction
                _shipDirection = new Vector2(((x - half) - Overtone.ScreenWidth * 0.5f), (notes.get(0).GetCenter().y - Overtone.ScreenHeight * 0.5f));
            }
            else
            {
                // Calculate the half way point in the vertical direction
                float half = Math.abs(notes.get(0).GetCenter().y - notes.get(1).GetCenter().y) / 2.0f;

                // Find the lowest of the two points
                float y = notes.get(0).GetCenter().y > notes.get(1).GetCenter().y ? notes.get(0).GetCenter().y : notes.get(1).GetCenter().y;

                // Calculate the direction
                _shipDirection = new Vector2((notes.get(0).GetCenter().x - Overtone.ScreenWidth * 0.5f), (y - half) - Overtone.ScreenHeight * 0.5f);
            }
        }
        else if (notes.size() == 1)
        {
            // Calculate the direction
            _shipDirection = new Vector2(notes.get(0).GetCenter().x - (Overtone.ScreenWidth * 0.5f), notes.get(0).GetCenter().y - (Overtone.ScreenHeight * 0.5f));
        }
    }
}