package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.overtone.Notes.Note;
import com.overtone.Notes.NoteRenderer;

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

    private ArrayList<Note> _notes;
    private NoteRenderer _renderer;

    public GameplayScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        _renderer = new NoteRenderer();

        _notes = new ArrayList<Note>();
        for(int i = 0; i < TargetZone.size; i++)
        {
            _notes.add(new Note(Note.NoteType.singleNote,
                    new Vector2(screenWidth / 2.0f, screenHeight / 2.0f),
                    TargetZone.values()[i].value,
                    new Vector2(Gdx.graphics.getWidth() * 0.025f, Gdx.graphics.getWidth() * 0.025f),
                    Note.DifficultyMultiplier.easy
            ));

            _notes.get(i).SetVisibility(true);
        }
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();
        _renderer.Draw(_notes);
        _batch.end();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        for(int i = 0; i < _notes.size(); i++)
        {
            if(_notes.get(i).IsVisible())
                _notes.get(i).Update(deltaTime);
        }
    }

    public void dispose ()
    {
        super.dispose();
    }
}