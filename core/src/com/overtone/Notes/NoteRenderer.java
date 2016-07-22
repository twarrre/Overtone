package com.overtone.Notes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Handles rendering the notes so that only one instance of each note texture exists
 * Created by trevor on 2016-06-21.
 */
public class NoteRenderer
{
    // Stores all of the textures for note objects
    private final Texture[] _noteTextures;

    private final Texture _doubleNoteConnectorHorizontal;
    private final Texture _doubleNoteConnectorVertical;

    /**
     * Constructor
     */
    public NoteRenderer()
    {
        _noteTextures                  = new Texture[3];
        _noteTextures[0]               = new Texture(Gdx.files.internal("Textures\\Notes\\note.png"));
        _noteTextures[1]               = new Texture(Gdx.files.internal("Textures\\Notes\\double_note.png"));
        _noteTextures[2]               = new Texture(Gdx.files.internal("Textures\\Notes\\hold_note.png"));
        _doubleNoteConnectorHorizontal = new Texture(Gdx.files.internal("Textures\\Notes\\double_note_connector_horizontal.png"));
        _doubleNoteConnectorVertical   = new Texture(Gdx.files.internal("Textures\\Notes\\double_note_connector_vertical.png"));
    }

    /**
     * Draws all of the notes on screen
     * @param notes All of the notes on screen to be rendered
     * @param batch The sprite batch to draw to.
     */
    public void Draw(ArrayList<Note> notes, SpriteBatch batch)
    {
        for(Note n : notes)
        {
            if(n.IsVisible())
            {
                if(n.GetType() == Note.NoteType.Double && !n.IsRendered() && !n.GetOtherNote().IsRendered() && n.GetOtherNote().IsVisible())
                    DrawDoubleNote(n, batch);
                else if(n.GetType() == Note.NoteType.Hold)
                    DrawHoldNote(n, batch);

                batch.draw(_noteTextures[n.GetType().ordinal()], n.GetPosition().x, n.GetPosition().y, n.GetScale().x, n.GetScale().y);
                n.SetRendered(true);
            }
        }
    }

    private void DrawDoubleNote(Note n, SpriteBatch batch)
    {
        if(n.GetPosition().y == n.GetOtherNote().GetPosition().y)
        {
            float xPos = n.GetPosition().x < n.GetOtherNote().GetPosition().x ? n.GetPosition().x : n.GetOtherNote().GetPosition().x;
            batch.draw(_doubleNoteConnectorHorizontal, xPos + (n.GetScale().x / 2.0f), n.GetPosition().y, n.GetPosition().dst(n.GetOtherNote().GetPosition()), n.GetScale().y);
        }
        else
        {
            float yPos = n.GetPosition().y < n.GetOtherNote().GetPosition().y ? n.GetPosition().y : n.GetOtherNote().GetPosition().y;
            batch.draw(_doubleNoteConnectorVertical, n.GetPosition().x, yPos + (n.GetScale().y / 2.0f), n.GetScale().x, n.GetPosition().dst(n.GetOtherNote().GetPosition()));
        }
        n.GetOtherNote().SetRendered(true);
    }

    private void DrawHoldNote(Note n, SpriteBatch batch)
    {

    }
}
