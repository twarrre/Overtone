package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.overtone.InputManager;
import com.overtone.Notes.Note;
import com.overtone.Notes.NoteRenderer;
import com.overtone.Quadtree;

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

    public static final float ERROR = 0.0045f;

    private NoteRenderer _renderer;
    private Texture _targetZone;
    private InputManager _input;
    private Quadtree _onScreenNotes;
    private Queue<Note> _noteQueue;
    private float elaspedTime;

    public GameplayScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        _input = new InputManager();
        _renderer = new NoteRenderer();
        _targetZone = new Texture(Gdx.files.internal("targetzone.png"));
        _onScreenNotes = new Quadtree(new Rectangle(0, 0, screenWidth, screenHeight));

        _noteQueue = new Queue<Note>();
        for(int i = 0; i < 12; i++)
        {
            Note n = new Note(Note.NoteType.singleNote,
                              new Vector2(screenWidth / 2.0f, screenHeight / 2.0f),
                              TargetZone.values()[i % TargetZone.size].value,
                              new Vector2(screenWidth * 0.025f, screenWidth * 0.025f),
                              Note.DifficultyMultiplier.easy,
                              i * 3.0f
                     );
            _noteQueue.addLast(n);
        }

        elaspedTime = 0;
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        if(_noteQueue.size > 0)
        {
            if(Math.abs(elaspedTime - _noteQueue.first().GetTime()) <= _noteQueue.first().GetDifficulty().value + ERROR)
            {
                Note n = _noteQueue.removeFirst();
                n.SetVisibility(true);
                _onScreenNotes.Insert(n);
            }
        }

        elaspedTime += deltaTime;

        for(int i = 0; i < TargetZone.size; i++)
        {
            Vector2 pos =  new Vector2(TargetZone.values()[i].value.x - ((_screenWidth * 0.045f) / 2.0f) , TargetZone.values()[i].value.y - ((_screenWidth * 0.045f) / 2.0f));
            _batch.draw(_targetZone,
                    pos.x,
                    pos.y,
                    _screenWidth * 0.045f,
                    _screenWidth * 0.045f
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
            return;
        }

        if(_input.ActionOccurred(InputManager.KeyBinding.BottomRight, InputManager.ActionType.Pressed))
        {

        }

        if(_input.ActionOccurred(InputManager.KeyBinding.TopRight, InputManager.ActionType.Pressed))
        {

        }

        if(_input.ActionOccurred(InputManager.KeyBinding.TopLeft, InputManager.ActionType.Pressed))
        {

        }
    }

    public void dispose ()
    {
        super.dispose();
        _targetZone.dispose();
    }
}