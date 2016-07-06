package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.overtone.InputManager;
import com.overtone.Notes.Note;
import com.overtone.Notes.NoteRenderer;
import com.overtone.Quadtree;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import java.util.ArrayList;

/**
 * Screen used during gameplay
 * Created by trevor on 2016-05-01.
 */
public class GameplayScreen extends OvertoneScreen
{
    public static final float ERROR = 0.0045f;

    /**
     * Represents the four targets of the screen
     */
    public enum TargetZone
    {
        TopLeft     (Gdx.graphics.getWidth() * 0.12f, Gdx.graphics.getHeight() * 0.83f),
        TopRight    (Gdx.graphics.getWidth() * 0.88f, Gdx.graphics.getHeight() * 0.83f),
        BottomLeft  (Gdx.graphics.getWidth() * 0.12f, Gdx.graphics.getHeight() * 0.17f),
        BottomRight (Gdx.graphics.getWidth() * 0.88f, Gdx.graphics.getHeight() * 0.17f);

        public Vector2 value;
        TargetZone(float x, float y) { value = new Vector2(x, y);}

        // The size of the enum
        public static final int size = TargetZone.values().length;
    }

    /**
     * Represents the ratings of how well the player hit the button
     */
    public enum Rating
    {
        Perfect (7),
        Great   (5),
        Ok      (3),
        Bad     (1),
        Miss    (0),
        None    (0);

        public int score;
        Rating(int s) {score = s;}

        public String ToString()
        {
            switch(score)
            {
                case 7:
                    return "Perfect";
                case 5:
                    return "Great";
                case 3:
                    return "Okay";
                case 1:
                    return "Bad";
                case 0:
                    return "Miss";
                default:
                    return "";
            }
        }
    }

    // Objects
    private final NoteRenderer _renderer;
    private final InputManager _input;

    // Textures
    private final Texture _targetZone;
    private final Texture _progressBar;
    private final Texture _progress;
    private final Texture _arrow;
    private final Texture _d;
    private final Texture _e;
    private final Texture _i;
    private final Texture _k;

    // Data Structures
    private final Quadtree    _onScreenNotes;
    private final Queue<Note> _noteQueue;

    // Font
    private final BitmapFont  _font;
    private final GlyphLayout _glyphLayout;

    // Sound
    private final Sound _noteHit;

    // Variables
    private final float _totalTime;
    private final float _targetRadius;
    private int         _combo;
    private int         _score;
    private float       _elapsedTime;

    /**
     * Constructor
     * @param backgroundImagePath The path for the background image
     * @param screenWidth The width of the screen
     * @param screenHeight The height of the screen
     */
    public GameplayScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        // Initialize objects
        _input    = new InputManager();
        _renderer = new NoteRenderer();

        // Initialize font
        _font        = new BitmapFont();
        _glyphLayout = new GlyphLayout();

        // Load textures
        _targetZone  = new Texture(Gdx.files.internal("Textures\\targetzone.png"));
        _progressBar = new Texture(Gdx.files.internal("Textures\\progressbar.png"));
        _progress    = new Texture(Gdx.files.internal("Textures\\background1.jpg"));
        _arrow       = new Texture(Gdx.files.internal("Textures\\arrow.png"));
        _e           = new Texture(Gdx.files.internal("Textures\\e.png"));
        _d           = new Texture(Gdx.files.internal("Textures\\d.png"));
        _i           = new Texture(Gdx.files.internal("Textures\\i.png"));
        _k           = new Texture(Gdx.files.internal("Textures\\k.png"));

        // Load sounds
        _noteHit = Gdx.audio.newSound(Gdx.files.internal("Sounds\\note.wav"));

        // Initialize variables
        _targetRadius = _screenWidth * 0.05f / 2.0f;
        _totalTime    = 3.0f + (float)56 * 2.0f;
        _elapsedTime  = 0;
        _combo        = 0;
        _score        = 0;

        // Load notes
        _noteQueue = new Queue<Note>();
        for(int i = 0; i < 56; i++)
        {
            Note n = new Note(Note.NoteType.singleNote,
                    new Vector2(screenWidth / 2.0f, screenHeight / 2.0f),
                    TargetZone.values()[i % TargetZone.size].value,
                    new Vector2(screenWidth * 0.025f, screenWidth * 0.025f),
                    Note.DifficultyMultiplier.hard,
                    3.0f + (float)i * 2.0f,
                    _targetRadius,
                    i
            );
            _noteQueue.addLast(n);
        }
        _onScreenNotes = new Quadtree(new Rectangle(0, 0, screenWidth, screenHeight));
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
        _batch.draw(_progress,    _screenWidth * 0.23f,  _screenHeight * 0.955f, _screenWidth * 0.55f * songProgress , _screenHeight * 0.01f);
        _batch.draw(_arrow,       _screenWidth * 0.23f - (_arrow.getWidth() / 2.0f) + (_screenWidth * 0.55f * songProgress), _screenHeight * 0.94f, _arrow.getWidth(), _arrow.getHeight());

        // Draw the letter borders
        float letterWidth = _screenWidth * 0.2f;
        _batch.draw(_d, 0,                   0,                           letterWidth, letterWidth);
        _batch.draw(_k, _screenWidth * 0.8f, 0,                           letterWidth, letterWidth);
        _batch.draw(_i, _screenWidth * 0.8f, _screenHeight - letterWidth, letterWidth, letterWidth);
        _batch.draw(_e, 0,                   _screenHeight - letterWidth, letterWidth, letterWidth);

        // Draw the target zones
        for(int i = 0; i < TargetZone.size; i++)
        {
            Vector2 pos =  new Vector2(TargetZone.values()[i].value.x - (_screenWidth * 0.0225f) , TargetZone.values()[i].value.y - (_screenWidth * 0.0225f));
            _batch.draw(_targetZone,
                    pos.x,
                    pos.y,
                    _targetRadius * 2.0f,
                    _targetRadius * 2.0f
            );
        }

        // Draw the combo and score
        _glyphLayout.setText(_font,  "Combo: " + _combo + " Score: " + _score);
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.10f);

        _batch.end();

        _renderer.Draw(_onScreenNotes.GetAll());
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        if(_noteQueue.size > 0)
        {
            if(_noteQueue.first().GetTime() - _noteQueue.first().GetDifficulty().value <=  _elapsedTime + ERROR)
            {
                Note n = _noteQueue.removeFirst();
                n.SetVisibility(true);
                _onScreenNotes.Insert(n);
            }
        }

        if(_elapsedTime < _totalTime)
            _elapsedTime += deltaTime;

        // Update the note positions
        if(!_onScreenNotes.Update(deltaTime))
            _combo = 0;

        // Update the input
        _input.Update();
        CheckInput();
    }

    /**
     * Handles the input
     */
    private void CheckInput()
    {
        for(int i = 0; i < InputManager.KeyBinding.size; i++)
        {
            if(_input.ActionOccurred(InputManager.KeyBinding.values()[i], InputManager.ActionType.Pressed))
            {
                _noteHit.play();
                Rating rating = GetNoteRating(TargetZone.values()[i].value);

                // NEED TO FIX THIS
                if(rating == Rating.Perfect || rating == Rating.Great)
                    _combo++;
                else if(rating == Rating.Bad || rating == Rating.Miss)
                    _combo = 0;
                else if(rating == Rating.Ok || rating == Rating.None)
                    _combo += 0;

                _score += rating.score * _combo;
            }
        }
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
            return Rating.None;

        float minDistance = Float.MAX_VALUE;
        Note closestNote = null;

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
        if(minDistance <= _targetRadius * 0.15f)
            return Rating.Perfect;
        else if(minDistance <= _targetRadius * 0.55f && minDistance > _targetRadius * 0.15f)
            return Rating.Great;
        else if(minDistance <= _targetRadius  && minDistance > _targetRadius * 0.55f)
            return Rating.Ok;
        else if(minDistance < _targetRadius * 2.0f  && minDistance > _targetRadius)
            return Rating.Bad;
        else
            return Rating.Miss;
    }


    public void dispose ()
    {
        super.dispose();
        _targetZone.dispose();
        _progressBar.dispose();
        _progress.dispose();
        _arrow.dispose();
        _d.dispose();
        _e.dispose();
        _i.dispose();
        _k.dispose();
    }
}