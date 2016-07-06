package com.overtone.Notes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.overtone.Quadtree;

import java.util.ArrayList;

/**
 * Handles rendering the notes so that only one instance of each note texture exists
 * Created by trevor on 2016-06-21.
 */
public class NoteRenderer
{
    private final SpriteBatch _spriteBatch;
    private final Texture[] _noteTextures;

    /**
     * Constructor
     */
    public NoteRenderer()
    {
        _spriteBatch = new SpriteBatch();

        _noteTextures = new Texture[3];
        _noteTextures[0] = new Texture(Gdx.files.internal("Textures\\Notes\\note.png"));
        _noteTextures[1] = new Texture(Gdx.files.internal("Textures\\Notes\\double_note.png"));
        _noteTextures[2] = new Texture(Gdx.files.internal("Textures\\Notes\\hold_note.png"));
    }

    /**
     * Draws all of the notes on screen
     * @param notes All of the notes on screen to be rendered
     */
    public void Draw(ArrayList<Note> notes)
    {
        _spriteBatch.begin();

        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).IsVisible())
            {
                _spriteBatch.draw(_noteTextures[notes.get(i).GetType().value],
                        notes.get(i).GetPosition().x,
                        notes.get(i).GetPosition().y,
                        notes.get(i).GetScale().x,
                        notes.get(i).GetScale().y
                );
            }
        }

        _spriteBatch.end();
    }
}
