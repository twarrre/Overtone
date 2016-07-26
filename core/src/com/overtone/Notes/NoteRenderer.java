package com.overtone.Notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 * Handles rendering the notes so that only one instance of each note texture exists
 * Created by trevor on 2016-06-21.
 */
public class NoteRenderer
{
    private final Texture[] _noteTextures;                  // Stores all of the textures for note objects
    private final Texture   _doubleNoteConnectorHorizontal; // Stores the horizontal connector of a double note
    private final Texture   _doubleNoteConnectorVertical;   // Stores the vertical connector of a double note
    private final Texture   _holdNoteConnector;             // Stores the connector of a hold note

    /**
     * Constructor
     */
    public NoteRenderer()
    {
        // Initialize all of the textures
        _noteTextures                  = new Texture[3];
        _noteTextures[0]               = new Texture(Gdx.files.internal("Textures\\Notes\\note.png"));
        _noteTextures[1]               = new Texture(Gdx.files.internal("Textures\\Notes\\double_note.png"));
        _noteTextures[2]               = new Texture(Gdx.files.internal("Textures\\Notes\\hold_note.png"));
        _doubleNoteConnectorHorizontal = new Texture(Gdx.files.internal("Textures\\Notes\\double_note_connector_horizontal.png"));
        _doubleNoteConnectorVertical   = new Texture(Gdx.files.internal("Textures\\Notes\\double_note_connector_vertical.png"));
        _holdNoteConnector             = new Texture(Gdx.files.internal("Textures\\Notes\\hold_note_connector.png"));
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
                if(CheckDoubleNoteConditions(n) && !n.IsConnectorRendered())       // if this note is a double note, draw the connector
                    DrawDoubleNoteConnector(n, batch);
                else if(CheckHoldNoteConditions(n) && !n.IsConnectorRendered())    // if this note is a hold note, draw the connector
                    DrawHoldNoteConnector(n, batch);

                batch.draw(_noteTextures[n.GetType().ordinal()], n.GetPosition().x, n.GetPosition().y, n.GetScale().x, n.GetScale().y); // Draw this note
            }
        }
    }

    /**
     * Draws the connector for a double note
     * @param n The note to draw a connector for
     * @param batch The sprite batch to draw to.
     */
    private void DrawDoubleNoteConnector(Note n, SpriteBatch batch)
    {
        if(!n.IsVisible() || !n.GetOtherNote().IsVisible())
            return;

        Vector2 notePos  = n.GetPosition();
        Vector2 otherPos = n.GetOtherNote().GetPosition();

        if(notePos.y == otherPos.y) // If the double notes are at the same y level, therefore a horizontal connector must be drawn
        {
            float xPos = notePos.x < otherPos.x ? notePos.x : otherPos.x; // Determine which end to start at ( the one closest to 0, so we are always drawing from left to right )
            batch.draw(_doubleNoteConnectorHorizontal, xPos + (n.GetScale().x / 2.0f), notePos.y, notePos.dst(otherPos), n.GetScale().y);
        }
        else // Else they have different x values, therefore a vertical connector myst be drawn
        {
            float yPos = notePos.y < otherPos.y ? notePos.y : otherPos.y; // Determine which end to start at ( the one closest to 0, so we are always drawing from bottom to top )
            batch.draw(_doubleNoteConnectorVertical, notePos.x, yPos + (n.GetScale().y / 2.0f), n.GetScale().x, notePos.dst(otherPos));
        }

        // Set connector rendered to true
        n.GetOtherNote().SetConnectorRendered(true);
        n.SetConnectorRendered(true);
    }

    /**
     * Draws the connector for a hold note
     * @param n The note to draw the connector to
     * @param batch The sprite batch to draw to.
     */
    private void DrawHoldNoteConnector(Note n, SpriteBatch batch)
    {
        Vector2 noteCenter      = n.GetCenter();
        Vector2 otherCenter     = n.GetOtherNote().GetCenter();
        Vector2 dir             = new Vector2(otherCenter.x - noteCenter.x, otherCenter.y - noteCenter.y).nor(); // Direction from the first note to the other note
        Vector2 pos             = new Vector2(noteCenter.x, noteCenter.y);                                       // The starting position for the connector
        Vector2 dirFromPos      = new Vector2(otherCenter.x - pos.x , otherCenter.y - pos.y);                    // Direction from the drawing position to the other note
        Vector2 connectorScale = new Vector2( n.GetScale().x * 0.5f,  n.GetScale().y * 0.5f);                    // Set the connector to be half the size of a note.

        while(dirFromPos.dot(dir) >= 0) // While the direction from pos to other note is pointed in the same direction as dir (as in, while pos has not passed the other note)
        {
            batch.draw(_holdNoteConnector, pos.x - ( connectorScale.x / 2.0f), pos.y - (connectorScale.y / 2.0f), connectorScale.x, connectorScale.y );
            pos.add(( connectorScale.x / 4.0f) * dir.x, ( connectorScale.y / 4.0f) * dir.y); // Move the connector position down the direction 1/4 the size of a connector
            dirFromPos  = new Vector2(otherCenter.x - pos.x , otherCenter.y - pos.y);        // Update the direction from position to the other note
        }
        // Set connector rendered to true
        n.GetOtherNote().SetConnectorRendered(true);
        n.SetConnectorRendered(true);
    }

    /**
     * Disposes of the textures when this object is destroyed
     */
    public void dispose()
    {
        _noteTextures[0].dispose();
        _noteTextures[1].dispose();
        _noteTextures[2].dispose();
        _doubleNoteConnectorHorizontal.dispose();
        _doubleNoteConnectorVertical.dispose();
        _holdNoteConnector.dispose();
    }

    /**
     * Checks to see if a double note connector needs to be drawn
     * @param n the double note to be checked
     * @return True if a connector is to be drawn, false otherwise
     */
    private boolean CheckDoubleNoteConditions(Note n)
    {
        return (n.GetType() == Note.NoteType.Double && n.GetOtherNote().IsVisible() && !n.IsConnectorRendered());
    }

    /**
     * Checks to see if a hold note connector needs to be drawn.
     * @param n the note to be checked
     * @return True is a hold note connector is drawn, false otherwise
     */
    private boolean CheckHoldNoteConditions(Note n)
    {
        return (n.GetType() == Note.NoteType.Hold && !n.IsConnectorRendered());
    }
}
