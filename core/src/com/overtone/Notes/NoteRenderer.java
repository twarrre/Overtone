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

    private final Texture _doubleNoteConnector;

    /**
     * Constructor
     */
    public NoteRenderer()
    {
        _noteTextures        = new Texture[3];
        _noteTextures[0]     = new Texture(Gdx.files.internal("Textures\\Notes\\note.png"));
        _noteTextures[1]     = new Texture(Gdx.files.internal("Textures\\Notes\\double_note.png"));
        _noteTextures[2]     = new Texture(Gdx.files.internal("Textures\\Notes\\hold_note.png"));
        _doubleNoteConnector = new Texture(Gdx.files.internal("Textures\\Notes\\double_note_connector.png"));
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
            if(n.IsVisible() && !n.IsRendered())
            {
                if(n.GetType() == Note.NoteType.Double && n.GetOtherNote().IsVisible())
                {
                    float xPos = n.GetPosition().x < n.GetOtherNote().GetPosition().x ? n.GetPosition().x : n.GetOtherNote().GetPosition().x;
                    batch.draw(_doubleNoteConnector, xPos + (n.GetScale().x / 2.0f), n.GetPosition().y, n.GetPosition().dst(n.GetOtherNote().GetPosition()), n.GetScale().y);

                    batch.draw(_noteTextures[n.GetType().ordinal()],
                            n.GetOtherNote().GetPosition().x,
                            n.GetOtherNote().GetPosition().y,
                            n.GetOtherNote().GetScale().x,
                            n.GetOtherNote().GetScale().y
                    );

                    n.GetOtherNote().SetRendered(true);
                }

                batch.draw(_noteTextures[n.GetType().ordinal()],
                        n.GetPosition().x,
                        n.GetPosition().y,
                        n.GetScale().x,
                        n.GetScale().y
                );
                n.SetRendered(true);
            }
        }
    }
}
