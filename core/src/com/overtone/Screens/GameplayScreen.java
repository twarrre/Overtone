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
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

/**
 * Created by trevor on 2016-05-01.
 */
public class GameplayScreen extends OvertoneScreen
{
    public enum TargetZone
    {
        TopLeft(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.85f),
        TopRight(Gdx.graphics.getWidth() * 0.85f, Gdx.graphics.getHeight() * 0.85f),
        BottomLeft(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.15f),
        BottomRight(Gdx.graphics.getWidth() * 0.85f, Gdx.graphics.getHeight() * 0.15f);

        public Vector2 value;
        TargetZone(float x, float y) { value = new Vector2(x, y);}

        // The size of the enum
        public static final int size = TargetZone.values().length;
    }

    public enum Rating
    {
        Perfect(5),
        Great(3),
        Ok(1),
        Bad(0),
        None(-1);

        public int value;
        Rating(int val) {value = val;}
    }

    public static final float ERROR = 0.0045f;

    private NoteRenderer _renderer;
    private Texture _targetZone;
    private InputManager _input;
    private Quadtree _onScreenNotes;
    private Queue<Note> _noteQueue;
    private float elapsedTime;
    private final float _targetRadius;

    public GameplayScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        _targetRadius = _screenWidth * 0.045f;
        _input = new InputManager();
        _renderer = new NoteRenderer();
        _targetZone = new Texture(Gdx.files.internal("targetzone.png"));
        _onScreenNotes = new Quadtree(new Rectangle(0, 0, screenWidth, screenHeight));

        _noteQueue = new Queue<Note>();
        for(int i = 0; i < 4; i++)
        {
            Note n = new Note(Note.NoteType.singleNote,
                              new Vector2(screenWidth / 2.0f, screenHeight / 2.0f),
                              TargetZone.BottomLeft.value,//TargetZone.values()[i % TargetZone.size].value,
                              new Vector2(screenWidth * 0.025f, screenWidth * 0.025f),
                              Note.DifficultyMultiplier.easy,
                              5.0f + (float)i * 5.0f,
                              _targetRadius,
                              i
                     );
            _noteQueue.addLast(n);
        }

        elapsedTime = 0;
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        if(_noteQueue.size > 0)
        {
            if(_noteQueue.first().GetTime() - _noteQueue.first().GetDifficulty().value <=  elapsedTime + ERROR)
            {
                Note n = _noteQueue.removeFirst();
                n.SetVisibility(true);
                _onScreenNotes.Insert(n);
            }
        }

        elapsedTime += deltaTime;

        for(int i = 0; i < TargetZone.size; i++)
        {
            Vector2 pos =  new Vector2(TargetZone.values()[i].value.x - ((_screenWidth * 0.045f) / 2.0f) , TargetZone.values()[i].value.y - ((_screenWidth * 0.045f) / 2.0f));
            _batch.draw(_targetZone,
                    pos.x,
                    pos.y,
                    _targetRadius,
                    _targetRadius
            );
        }

        _batch.end();

        _renderer.Draw(_onScreenNotes.GetAll());
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        _input.Update();

        // Update the note positions
        _onScreenNotes.Update(deltaTime);

        // Check for input
        if(_input.ActionOccurred(InputManager.KeyBinding.BottomLeft, InputManager.ActionType.Pressed))
        {
            float rating = CheckNotes(TargetZone.BottomLeft.value);

            if(rating == Rating.Perfect.value)
                System.out.println("perfect");
            else if(rating == Rating.Great.value)
                System.out.println("great");
            else if(rating == Rating.Ok.value)
                System.out.println("ok");
            else if(rating == Rating.Bad.value)
                System.out.println("bad");
            else
                System.out.println("nothing");
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.BottomRight, InputManager.ActionType.Pressed))
        {
            CheckNotes(TargetZone.BottomRight.value);
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.TopRight, InputManager.ActionType.Pressed))
        {
            CheckNotes(TargetZone.TopRight.value);
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.TopLeft, InputManager.ActionType.Pressed))
        {
            CheckNotes(TargetZone.TopLeft.value);
        }
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
            float distance = Vector2.dst(target.x, target.y, n.GetPosition().x, n.GetPosition().y);
            if(distance < minDistance)
            {
                minDistance = distance;
                closestNote = n;
            }
        }

        _onScreenNotes.Remove(closestNote);

        if(minDistance <= _targetRadius * 0.10f)
            return Rating.Perfect.value;
        else if(minDistance <= _targetRadius * 0.35f && minDistance > _targetRadius * 0.10f)
            return Rating.Great.value;
        else if(minDistance <= _targetRadius && minDistance > _targetRadius * 0.35)
            return Rating.Ok.value;
        else
            return Rating.Bad.value;
    }

    public void dispose ()
    {
        super.dispose();
        _targetZone.dispose();
    }
}