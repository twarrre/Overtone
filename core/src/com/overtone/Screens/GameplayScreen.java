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
 * Created by trevor on 2016-05-01.
 */
public class GameplayScreen extends OvertoneScreen
{
    public enum TargetZone
    {
        TopLeft(Gdx.graphics.getWidth() * 0.12f, Gdx.graphics.getHeight() * 0.83f),
        TopRight(Gdx.graphics.getWidth() * 0.88f, Gdx.graphics.getHeight() * 0.83f),
        BottomLeft(Gdx.graphics.getWidth() * 0.12f, Gdx.graphics.getHeight() * 0.17f),
        BottomRight(Gdx.graphics.getWidth() * 0.88f, Gdx.graphics.getHeight() * 0.17f);

        public Vector2 value;
        TargetZone(float x, float y) { value = new Vector2(x, y);}

        // The size of the enum
        public static final int size = TargetZone.values().length;
    }

    public enum Rating
    {
        Perfect(7),
        Great(5),
        Ok(3),
        Bad(1),
        Miss(0),
        None(-1);

        public int value;
        Rating(int val) {value = val;}

        public String ToString()
        {
            switch(value)
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

    public static final float ERROR = 0.0045f;

    private NoteRenderer _renderer;
    private Texture _targetZone;
    private Texture _d;
    private Texture _e;
    private Texture _i;
    private Texture _k;
    private InputManager _input;
    private Quadtree _onScreenNotes;
    private Queue<Note> _noteQueue;
    private float _elapsedTime;
    private final float _targetRadius;
    private int _combo;
    private int _score;
    private final BitmapFont _font;
    private final GlyphLayout _glyphLayout;
    private final Sound _noteHit;

    public GameplayScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        _targetRadius = _screenWidth * 0.05f / 2.0f;
        _input = new InputManager();
        _renderer = new NoteRenderer();
        _targetZone = new Texture(Gdx.files.internal("Textures\\targetzone.png"));
        _onScreenNotes = new Quadtree(new Rectangle(0, 0, screenWidth, screenHeight));

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

        _elapsedTime = 0;
        _combo = 0;
        _score = 0;
        _font = new BitmapFont();
        _glyphLayout = new GlyphLayout();

        _e = new Texture(Gdx.files.internal("Textures\\e.png"));
        _d = new Texture(Gdx.files.internal("Textures\\d.png"));
        _i = new Texture(Gdx.files.internal("Textures\\i.png"));
        _k = new Texture(Gdx.files.internal("Textures\\k.png"));

        _noteHit = Gdx.audio.newSound(Gdx.files.internal("Sounds\\note.wav"));
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _batch.draw(_d, 0, 0, _screenWidth * 0.2f, _screenWidth * 0.2f);
        _batch.draw(_k, _screenWidth * 0.8f, 0, _screenWidth * 0.2f, _screenWidth * 0.2f);
        _batch.draw(_i, _screenWidth * 0.8f, _screenHeight - _screenWidth * 0.2f, _screenWidth * 0.2f, _screenWidth * 0.2f);
        _batch.draw(_e, 0, _screenHeight - _screenWidth * 0.2f, _screenWidth * 0.2f, _screenWidth * 0.2f);

        for(int i = 0; i < TargetZone.size; i++)
        {
            Vector2 pos =  new Vector2(TargetZone.values()[i].value.x - ((_screenWidth * 0.045f) / 2.0f) , TargetZone.values()[i].value.y - ((_screenWidth * 0.045f) / 2.0f));
            _batch.draw(_targetZone,
                    pos.x,
                    pos.y,
                    _targetRadius * 2.0f,
                    _targetRadius * 2.0f
            );
        }

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

        _elapsedTime += deltaTime;

        // Update the note positions
        _onScreenNotes.Update(deltaTime);

        _input.Update();

        CheckInput();
    }

    float CheckNotes(Vector2 target)
    {
        ArrayList<Note> notes = _onScreenNotes.Get(target);

        if(notes.isEmpty())
            return Rating.None.value;

        float minDistance = Float.MAX_VALUE;
        Note closestNote = null;

        for(Note n : notes)
        {
            float distance = Vector2.dst(target.x, target.y, n.GetCenter().x, n.GetCenter().y);
            if(distance < minDistance)
            {
                minDistance = distance;
                closestNote = n;
            }
        }

        _onScreenNotes.Remove(closestNote);

        if(minDistance <= _targetRadius * 0.15f)
            return Rating.Perfect.value;
        else if(minDistance <= _targetRadius * 0.55f && minDistance > _targetRadius * 0.15f)
            return Rating.Great.value;
        else if(minDistance <= _targetRadius  && minDistance > _targetRadius * 0.55f)
            return Rating.Ok.value;
        else if(minDistance <= _targetRadius * 2.0f  && minDistance > _targetRadius)
            return Rating.Bad.value;
        else
            return Rating.Miss.value;
    }


    private void CheckInput()
    {
        // Check for input
        if(_input.ActionOccurred(InputManager.KeyBinding.BottomLeft, InputManager.ActionType.Pressed))
        {
            _noteHit.play();

            float rating = CheckNotes(TargetZone.BottomLeft.value);

            if(rating == Rating.Perfect.value)
            {
                _combo++;
                _score += Rating.Perfect.value * _combo;
            }
            else if(rating == Rating.Great.value)
            {
                _combo++;
                _score += Rating.Great.value * _combo;
            }
            else if(rating == Rating.Ok.value)
            {
                _combo = 0;
                _score += Rating.Ok.value;
            }
            else if(rating == Rating.Bad.value)
            {
                _combo = 0;
                _score += Rating.Bad.value;
            }
            else if(rating == Rating.Miss.value)
            {
                _combo = 0;
                _score += Rating.Miss.value;
            }
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.BottomRight, InputManager.ActionType.Pressed))
        {
            _noteHit.play();

            float rating = CheckNotes(TargetZone.BottomRight.value);

            if(rating == Rating.Perfect.value)
            {
                _combo++;
                _score += Rating.Perfect.value * _combo;
            }
            else if(rating == Rating.Great.value)
            {
                _combo++;
                _score += Rating.Great.value * _combo;
            }
            else if(rating == Rating.Ok.value)
            {
                _combo = 0;
                _score += Rating.Ok.value;
            }
            else if(rating == Rating.Bad.value)
            {
                _combo = 0;
                _score += Rating.Bad.value;
            }
            else if(rating == Rating.Miss.value)
            {
                _combo = 0;
                _score += Rating.Miss.value;
            }
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.TopRight, InputManager.ActionType.Pressed))
        {
            _noteHit.play();

            float rating = CheckNotes(TargetZone.TopRight.value);

            if(rating == Rating.Perfect.value)
            {
                _combo++;
                _score += Rating.Perfect.value * _combo;
            }
            else if(rating == Rating.Great.value)
            {
                _combo++;
                _score += Rating.Great.value * _combo;
            }
            else if(rating == Rating.Ok.value)
            {
                _combo = 0;
                _score += Rating.Ok.value;
            }
            else if(rating == Rating.Bad.value)
            {
                _combo = 0;
                _score += Rating.Bad.value;
            }
            else if(rating == Rating.Miss.value)
            {
                _combo = 0;
                _score += Rating.Miss.value;
            }
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.TopLeft, InputManager.ActionType.Pressed))
        {
            _noteHit.play();

            float rating = CheckNotes(TargetZone.TopLeft.value);

            if(rating == Rating.Perfect.value)
            {
                _combo++;
                _score += Rating.Perfect.value * _combo;
            }
            else if(rating == Rating.Great.value)
            {
                _combo++;
                _score += Rating.Great.value * _combo;
            }
            else if(rating == Rating.Ok.value)
            {
                _combo = 0;
                _score += Rating.Ok.value;
            }
            else if(rating == Rating.Bad.value)
            {
                _combo = 0;
                _score += Rating.Bad.value;
            }
            else if(rating == Rating.Miss.value)
            {
                _combo = 0;
                _score += Rating.Miss.value;
            }
        }
    }

    public void dispose ()
    {
        super.dispose();
        _targetZone.dispose();
    }
}