package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.InputManager;
import com.overtone.Notes.Note;
import com.overtone.Notes.NoteRenderer;
import com.overtone.Notes.Target;
import com.overtone.Overtone;
import com.overtone.Quadtree;
import com.overtone.Ratings.Rating;
import com.overtone.Ratings.RatingRenderer;

import java.util.ArrayList;

/**
 * Screen used during gameplay
 * Created by trevor on 2016-05-01.
 */
public class GameplayScreen extends OvertoneScreen
{
    public static final float ERROR           = 0.000045f;
    public static final float PAUSE_DELAY     = 3.0f;
    public static final float DONE_DELAY      = 3.0f;
    public static final float[] FAILURE_TIMER = {10.0f, 8.0f, 5.0f};

    // Objects
    private final NoteRenderer    _noteRenderer;
    private final InputManager    _input;
    private final RatingRenderer  _ratingRenderer;
    private final Stage           _stage;

    // Textures
    private final Texture _targetZone;
    private final Texture _targetZonePressed;
    private final Texture _progressBar;
    private final Texture _progress;
    private final Texture _progressArrow;
    private final Texture _d;
    private final Texture _e;
    private final Texture _i;
    private final Texture _k;
    private final Texture _background;
    private final Texture _perfection;
    private final Texture _brilliant;
    private final Texture _great;
    private final Texture _cleared;
    private final Texture _failure;
    private final Texture _losing;
    private final Sprite  _ship;
    private Texture       _currentCrowdRating;

    // Data Structures
    private final Quadtree          _onScreenNotes;
    private final ArrayList<Note>   _noteQueue;
    private final ArrayList<Rating> _onScreenRatings;

    // Sound
    private final Sound _noteHit;
    private final Sound _noteShot;

    // Variables
    private int                       _combo;
    private int                       _score;
    private boolean                   _paused;
    private boolean                   _resumeDelay;
    private boolean                   _songDone;
    private final float               _totalTime;
    private float                     _elapsedTime;
    private float                     _resumeTimer;
    private float                     _prevResumeTimer;
    private float                     _doneTimer;
    private float                     _failureTimer;

    private int                       _perfectCounter;
    private int                       _greatCounter;
    private int                       _goodCounter;
    private int                       _badCounter;
    private int                       _missCounter;

    private Vector2                   _shipDirection;

    private final Target[] _targetZones;
    private final boolean[] _targetZonesPressed;

    private final TextButton          _resumeButton;
    private final TextButton          _retryButton;
    private final TextButton          _quitButton;

    /**
     * Constructor
     * @param screenWidth The width of the screen
     * @param screenHeight The height of the screen
     */
    public GameplayScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

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
        _ship.setCenter(_screenWidth * 0.5f, _screenHeight * 0.5f);
        _ship.setScale(0.75f);
        _shipDirection = new Vector2(0, 0);
        _ship.setRotation(0);

        // Load sounds
        _noteHit  = Gdx.audio.newSound(Gdx.files.internal("Sounds\\note.wav"));
        _noteShot = Gdx.audio.newSound(Gdx.files.internal("Sounds\\laser.wav"));

        // Initialize variables
        _targetZones = new Target[4];
        _targetZones[0] = new Target(Overtone.TargetZone.TopLeft);
        _targetZones[1] = new Target(Overtone.TargetZone.TopRight);
        _targetZones[2] = new Target(Overtone.TargetZone.BottomLeft);
        _targetZones[3] = new Target(Overtone.TargetZone.BottomRight);
        _targetZonesPressed = new boolean[4];
        _targetZonesPressed[0] = false;
        _targetZonesPressed[1] = false;
        _targetZonesPressed[2] = false;
        _targetZonesPressed[3] = false;
        _totalTime      = 3.0f + (float)27 * 2.0f;
        _elapsedTime    = 0;
        _combo          = 0;
        _score          = 0;
        _paused         = false;
        _resumeDelay    = false;
        _resumeTimer    = 0;
        _prevResumeTimer    = 0;
        _songDone       = false;
        _doneTimer      = 0.0f;
        _failureTimer   = 0.0f;
        _perfectCounter = 0;
        _greatCounter   = 0;
        _goodCounter    = 0;
        _badCounter     = 0;
        _missCounter    = 0;

        _resumeButton = CreateTextButton("RESUME", "default", _screenWidth * 0.5f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.25f, _screenHeight * 0.475f), _stage);
        _resumeButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _resumeDelay = true;
                _resumeButton.setDisabled(true);
                _retryButton.setDisabled(true);
                _quitButton.setDisabled(true);
                _resumeButton.setVisible(false);
                _retryButton.setVisible(false);
                _quitButton.setVisible(false);
            }
        });
        _resumeButton.setDisabled(true);
        _resumeButton.setVisible(false);

        _retryButton = CreateTextButton("RETRY", "default", _screenWidth * 0.5f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.25f, _screenHeight * 0.275f), _stage);
        _retryButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {_buttonPress.play(); Overtone.SetScreen(Overtone.Screens.Gameplay);}
        });
        _retryButton.setDisabled(true);
        _retryButton.setVisible(false);

        _quitButton = CreateTextButton("MAIN MENU", "default", _screenWidth * 0.5f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.25f, _screenHeight * 0.075f), _stage);
        _quitButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {_buttonPress.play(); Overtone.SetScreen(Overtone.Screens.MainMenu);}
        });
        _quitButton.setDisabled(true);
        _quitButton.setVisible(false);

        _noteQueue = new ArrayList<Note>();

        Note d1 = new Note(Note.NoteType.Double,
                 new Vector2(_screenWidth * 0.025f, _screenWidth * 0.025f),
                 new Vector2(_screenWidth / 2.0f, _screenHeight / 2.0f),
                 _targetZones[0],
                 3.0f + (float)0 * 2.0f);

        Note d2 = new Note(Note.NoteType.Double,
                new Vector2(_screenWidth * 0.025f, _screenWidth * 0.025f),
                new Vector2(_screenWidth / 2.0f, _screenHeight / 2.0f),
                _targetZones[1],
                3.0f + (float)0 * 2.0f);

        d1.SetOtherNote(d2);
        d2.SetOtherNote(d1);

        _noteQueue.add(d1);
        _noteQueue.add(d2);

        Note d3 = new Note(Note.NoteType.Double,
                new Vector2(_screenWidth * 0.025f, _screenWidth * 0.025f),
                new Vector2(_screenWidth / 2.0f, _screenHeight / 2.0f),
                _targetZones[0],
                3.0f + (float)1 * 2.0f);

        Note d4 = new Note(Note.NoteType.Double,
                new Vector2(_screenWidth * 0.025f, _screenWidth * 0.025f),
                new Vector2(_screenWidth / 2.0f, _screenHeight / 2.0f),
                _targetZones[2],
                3.0f + (float)1 * 2.0f);

        d3.SetOtherNote(d4);
        d4.SetOtherNote(d3);

        _noteQueue.add(d3);
        _noteQueue.add(d4);


        // Load notes
        for(int i = 2; i < 27; i++)
        {
           // Note(NoteType type, Vector2 scale, Vector2[] center, Target[] target, float[] timer)
            Note n = new Note(Note.NoteType.Single,
                     new Vector2(_screenWidth * 0.025f, _screenWidth * 0.025f),
                     new Vector2(_screenWidth / 2.0f, _screenHeight / 2.0f),
                    _targetZones[i %_targetZones.length],
                     3.0f + (float)i * 2.0f
            );
            _noteQueue.add(n);
        }

        _onScreenNotes   = new Quadtree(new Rectangle(0, 0, screenWidth, screenHeight));
        _onScreenRatings = new ArrayList<Rating>();
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
        float songProgress = _elapsedTime / _totalTime;
        _batch.draw(_progressBar, _screenWidth * 0.225f, _screenHeight * 0.95f,  _screenWidth * 0.55f,                 _screenHeight * 0.02f);
        _batch.draw(_progress,    _screenWidth * 0.23f,  _screenHeight * 0.955f, _screenWidth * 0.54f * songProgress , _screenHeight * 0.01f);
        _batch.draw(_progressArrow,       _screenWidth * 0.23f - (_progressArrow.getWidth() / 2.0f) + (_screenWidth * 0.54f * songProgress), _screenHeight * 0.94f, _progressArrow.getWidth(), _progressArrow.getHeight());

        // Draw the letter borders
        float letterWidth = _screenWidth * 0.2f;
        _batch.draw(_d, 0,                   0,                           letterWidth, letterWidth);
        _batch.draw(_k, _screenWidth * 0.8f, 0,                           letterWidth, letterWidth);
        _batch.draw(_i, _screenWidth * 0.8f, _screenHeight - letterWidth, letterWidth, letterWidth);
        _batch.draw(_e, 0,                   _screenHeight - letterWidth, letterWidth, letterWidth);

        // Draw the target zones
        for(int i = 0; i < _targetZones.length; i++)
        {
            _batch.draw(_targetZonesPressed[i] ? _targetZonePressed : _targetZone, _targetZones[i].GetDrawingPosition().x, _targetZones[i].GetDrawingPosition().y, Target.Radius, Target.Radius);
        }

        // Draw the combo and score
        _glyphLayout.setText(_font18,  "Combo: " + _combo);
        _font18.draw(_batch, _glyphLayout, _screenWidth * 0.7f - (_glyphLayout.width / 2.0f), _screenHeight * 0.05f);

        _glyphLayout.setText(_font18, "Score: " + _score);
        _font18.draw(_batch, _glyphLayout, _screenWidth * 0.3f - (_glyphLayout.width / 2.0f), _screenHeight * 0.05f);

        _glyphLayout.setText(_font18, "High Score: " + Overtone.HighScores[Overtone.Difficulty.ordinal()][0]);
        _font18.draw(_batch, _glyphLayout, _screenWidth * 0.775f - _glyphLayout.width, _screenHeight * 0.92f);

        _glyphLayout.setText(_font18, "Difficulty: " + Overtone.Difficulty.toString());
        _font18.draw(_batch, _glyphLayout, _screenWidth * 0.225f, _screenHeight * 0.92f);

        _noteRenderer.Draw(_onScreenNotes.GetAll(), _batch);
        _ratingRenderer.Draw(_onScreenRatings, _batch);

        _ship.setRotation(0);
        _ship.rotate((float)Math.toDegrees(Math.atan2(_shipDirection.y, _shipDirection.x )));
        _ship.draw(_batch);

        for(int i = 0; i <  5; i++)
        {
            _batch.draw(_currentCrowdRating, _screenWidth * 0.40f + (_screenWidth * 0.04f * (float)i), _screenHeight * 0.02f, _screenWidth * 0.04f, _screenWidth * 0.04f);
        }

        _batch.setColor(1.0f, 1.0f, 1.0f, (_failureTimer / FAILURE_TIMER[Overtone.Difficulty.ordinal()]));
        _batch.draw(_losing, 0, 0, _screenWidth, _screenHeight);
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        if(_paused && !_resumeDelay)
        {
            _batch.draw(_background, 0, 0, _screenWidth, _screenHeight);
            _glyphLayout.setText(_font36,  "Paused");
            _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.85f);
        }

        if(_resumeDelay)
        {
            _batch.draw(_background, 0, 0, _screenWidth, _screenHeight);
            _glyphLayout.setText(_font36,  "Game will resume in");
            _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.85f);

            _glyphLayout.setText(_font36, (3 - (int)_resumeTimer) + "");
            _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.5f);
        }

        if(_songDone)
        {
            _batch.draw(_background, 0, 0, _screenWidth, _screenHeight);
            _glyphLayout.setText(_font36, _elapsedTime < _totalTime ? "Game Over..." : "Finished!!");
            _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.5f);
        }

        _batch.end();

        if(_paused && !_resumeDelay)
        {
            _stage.draw();
        }
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
        _stage.act(deltaTime);
        _input.Update();

        if(_paused)
        {
            CheckInputPaused();
            if(_resumeDelay)
            {
                _prevResumeTimer = _resumeTimer;
                _resumeTimer += deltaTime;

                if(_prevResumeTimer == 0 && _resumeTimer >= 0 || _prevResumeTimer < 1 && _resumeTimer >= 1 || _prevResumeTimer < 2 && _resumeTimer >= 2 || _prevResumeTimer < 3 && _resumeTimer >= 3)
                    _countdown.play();

                if(_resumeTimer > PAUSE_DELAY)
                {
                    _paused          = false;
                    _resumeDelay     = false;
                    _resumeTimer     = 0;
                    _prevResumeTimer = 0;
                }
            }
            return;
        }

        if(!_noteQueue.isEmpty())
        {
            ArrayList<Note> forRemoval = new ArrayList<Note>();
            for(int i = 0; i < _targetZones.length; i++)
            {
                if(i < _noteQueue.size())
                {
                    if(_noteQueue.get(i).GetTime() - Overtone.Difficulty.Multiplier <=  _elapsedTime + ERROR)
                    {
                        Note n = _noteQueue.get(i);
                        n.SetVisibility(true);
                        _onScreenNotes.Insert(n);
                        forRemoval.add(_noteQueue.get(i));
                        _noteShot.play();
                    }
                }
            }

            if(forRemoval.size() > 1)
            {
                if(forRemoval.get(0).GetCenter().y == forRemoval.get(1).GetCenter().y)
                {
                    float amount = Math.abs(forRemoval.get(0).GetCenter().x - forRemoval.get(1).GetCenter().x) / 2.0f;
                    float x = forRemoval.get(0).GetCenter().x > forRemoval.get(1).GetCenter().x ? forRemoval.get(0).GetCenter().x : forRemoval.get(1).GetCenter().x;
                    _shipDirection = new Vector2(((x - amount) - _screenWidth * 0.5f), (forRemoval.get(0).GetCenter().y - _screenHeight * 0.5f));
                }
                else
                {
                    float amount = Math.abs(forRemoval.get(0).GetCenter().y - forRemoval.get(1).GetCenter().y) / 2.0f;
                    float y = forRemoval.get(0).GetCenter().y > forRemoval.get(1).GetCenter().y ? forRemoval.get(0).GetCenter().y : forRemoval.get(1).GetCenter().y;
                    _shipDirection = new Vector2((forRemoval.get(0).GetCenter().x - _screenWidth * 0.5f), (y - amount) - _screenHeight * 0.5f);
                }
            }
            else if (!forRemoval.isEmpty())
                _shipDirection = new Vector2(forRemoval.get(0).GetCenter().x - (_screenWidth * 0.5f), forRemoval.get(0).GetCenter().y - (_screenHeight * 0.5f));
            _noteQueue.removeAll(forRemoval);
        }

        if(_elapsedTime < _totalTime && !_songDone)
            _elapsedTime += deltaTime;

        if(_elapsedTime >= _totalTime)
            _songDone = true;

        if(_songDone)
        {
            _doneTimer += deltaTime;
            if(_doneTimer > DONE_DELAY)
                Overtone.SetScreen(Overtone.Screens.SongComplete, _elapsedTime < _totalTime ? false : true, _score, _perfectCounter, _greatCounter, _goodCounter, _badCounter, _missCounter);
        }
        else
        {
            // Update the note positions
            ArrayList<Vector2> removedNotes = _onScreenNotes.Update(deltaTime);
            if(!removedNotes.isEmpty())
            {
                for(Vector2 v : removedNotes)
                {
                    _missCounter++;
                    _onScreenRatings.add(new Rating(Rating.RatingType.Miss, v));
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

            CheckInput();
            UpdateCrowdRating(deltaTime);
        }
    }

    /**
     * Handles the input when not paused
     */
    private void CheckInput()
    {
        for(int i = 0; i <_targetZones.length; i++)
        {
            _targetZonesPressed[i] = false;
            if(_input.ActionOccurred(InputManager.KeyBinding.values()[i], InputManager.ActionType.Pressed))
            {
                _noteHit.play();
                Rating rating = GetNoteRating(_targetZones[i].Position);
                _targetZonesPressed[i] = true;

                if(rating.GetRating() == Rating.RatingType.Perfect || rating.GetRating() == Rating.RatingType.Great)
                    _combo++;
                else if(rating.GetRating() == Rating.RatingType.Bad || rating.GetRating() == Rating.RatingType.Miss)
                    _combo = 0;
                else if(rating.GetRating() == Rating.RatingType.Ok || rating.GetRating() == Rating.RatingType.None)
                    _combo += 0;

                _score += rating.GetRating().Score * _combo;

                if(rating.GetRating() != Rating.RatingType.None)
                    _onScreenRatings.add(rating);
            }
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.Pause, InputManager.ActionType.Pressed) && !_songDone)
        {
            _paused = !_paused;

            if(_paused)
            {
                _buttonPress.play();
                _resumeButton.setDisabled(false);
                _retryButton.setDisabled(false);
                _quitButton.setDisabled(false);
                _resumeButton.setVisible(true);
                _retryButton.setVisible(true);
                _quitButton.setVisible(true);
            }
        }
    }

    private void CheckInputPaused()
    {
        if(_input.ActionOccurred(InputManager.KeyBinding.Pause, InputManager.ActionType.Pressed))
            _resumeDelay = true;
    }

    /**
     * Finds the closest note for the particular target and rates how close it was
     * @param target The target position
     * @return A rating as to how close the note was to the target
     */
    Rating GetNoteRating(Vector2 target)
    {
        ArrayList<Note> notes = _onScreenNotes.Get(target);

        if(notes.isEmpty())
            return new Rating(Rating.RatingType.None, target);

        float minDistance = Float.MAX_VALUE;
        Note closestNote  = null;

        // Find the closest note and store it
        for(Note n : notes)
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

        // Return a rating based on how close it was to the target
        if(minDistance <= Target.Radius * 0.15f)
        {
            _perfectCounter++;
            return new Rating(Rating.RatingType.Perfect, closestNote.GetCenter());
        }
        else if(minDistance <= Target.Radius * 0.55f && minDistance > Target.Radius * 0.15f)
        {
            _greatCounter++;
            return new Rating(Rating.RatingType.Great, closestNote.GetCenter());
        }
        else if(minDistance <= Target.Radius  && minDistance > Target.Radius * 0.55f)
        {
            _goodCounter++;
            return new Rating(Rating.RatingType.Ok, target);
        }
        else if(minDistance < Target.Radius * 2.0f  && minDistance > Target.Radius)
        {
            _badCounter++;
            return new Rating(Rating.RatingType.Bad, closestNote.GetCenter());
        }
        else
        {
            _missCounter++;
            return new Rating(Rating.RatingType.Miss, closestNote.GetCenter());
        }

    }

    /**
     * Updates the crowd on screen to different ratings based on the score so far
     * @param deltaTime The time since the last frame
     */
    private void UpdateCrowdRating(float deltaTime)
    {
        Overtone.CrowdRating rating = Overtone.CrowdRating.GetRating(_perfectCounter, _greatCounter, _goodCounter, _badCounter, _missCounter);

        if(rating == Overtone.CrowdRating.Failure)
            _failureTimer += deltaTime;
        else
            _failureTimer = 0.0f;

        if(_failureTimer > FAILURE_TIMER[Overtone.Difficulty.ordinal()])
            _songDone = true;

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

    public void resize(int width, int height)
    {
        super.resize(width, height);
        _stage.getViewport().update(width, height, true);
    }

    public void show()
    {
        Gdx.input.setInputProcessor(_stage);
    }

    public void hide() {Gdx.input.setInputProcessor(null);}

    public void dispose ()
    {
        super.dispose();
        _targetZone.dispose();
        _progressBar.dispose();
        _progress.dispose();
        _progressArrow.dispose();
        _d.dispose();
        _e.dispose();
        _i.dispose();
        _k.dispose();
        _stage.dispose();
        _background.dispose();
        _perfection.dispose();
        _brilliant.dispose();
        _great.dispose();
        _cleared.dispose();
        _failure.dispose();
        _losing.dispose();
    }
}