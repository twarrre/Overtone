package com.overtone.Notes;

import com.badlogic.gdx.math.Vector2;
import com.overtone.Overtone;

/**
 * Represents a note object in the game
 * Created by trevor on 2016-06-21.
 */
public class Note implements Comparable<Note>
{
    /**
     * All of the different types of notes
     */
    public enum  NoteType
    {
        Single,
        Double,
        Hold
    }

    // Stores the type of note that this is
    private final NoteType _type;

    // The target of where this note is heading
    private final Target _target;

    // The direction that the note is heading
    private final Vector2 _direction;

    // Time that the note must be at the target zone at
    private final float _timer;

    // The speed that the note travels at
    private final float _speed;

    // True if visible on screen, false otherwise
    private boolean _isVisible;

    // The scale of the note
    private Vector2 _scale;

    // The note's center point in the world
    private Vector2 _center;

    private Note _partnerNote;

    private boolean _connectorRendered;

    /**
     * Constructor
     * @param type The type of note
     * @param center The center point
     * @param target The target of where to the note is going
     * @param scale The scale of the note
     * @param timer The time signature of the note
     */
    public Note(NoteType type, Vector2 scale, Vector2 center, Target target, float timer)
    {
        // Calculate the direction the note is heading
        _direction = new Vector2(target.Position.x - center.x, target.Position.y - center.y).nor();

        // Shift the center into the proper quad based on the direction it is going
        _center = new Vector2(center.x + _direction.x, center.y + _direction.y);

        // Calculate the speed of the note based on difficulty and distance to the target
        _speed                = ((float)Math.sqrt(Math.pow((target.Position.x - _center.x), 2) + Math.pow((target.Position.y - _center.y), 2))) / Overtone.Difficulty.Multiplier;

        _target               = target;
        _timer                = timer;
        _type                 = type;
        _isVisible            = false;
        _scale                = scale;
        _partnerNote          = null;
        _connectorRendered    = false;
    }

    /**
     * Checks if the note has passed the target position
     * @return returns true if hit has passed the target position, false otherwise
     */
    private boolean IsPassedTarget(Vector2 pos, Target target, Vector2 dir)
    {
        // Create a vector where the head is the target and the tail is the center of the note
        Vector2 noteDir = new Vector2(target.Position.x - pos.x, target.Position.y - pos.y);

        // If the dot product is negative we know that the note has passed the target
        if(noteDir.dot(dir) < 0 && pos.dst(target.Position) > Target.Radius * 0.75f)
            return true;

       return false;
    }

    /**
     * Updates the note every frame
     * @param deltaTime The time since the last frame
     */
    public void Update(float deltaTime)
    {
        // Keep moving if the note has not passed the target, otherwise it is now invisible
        if(!IsPassedTarget(_center, _target, _direction))
            _center.add(new Vector2(_direction.x * _speed * deltaTime, _direction.y * _speed * deltaTime));
        else
            _isVisible = false;

        _connectorRendered = false;
    }

    /**
     * @return The bottom left corner position of the note
     */
    public Vector2 GetPosition()
    {
        return new Vector2(_center.x - (_scale.x / 2.0f), _center.y - (_scale.y / 2.0f));
    }

    /**
     * @return The center position of the note
     */
    public Vector2 GetCenter() {return _center;}

    /**
     * @return The position of the note's target
     */
    public Target GetTarget() {return _target;}

    /**
     * @return The type of note this one is
     */
    public NoteType GetType() {return _type;}

    /**
     * @return The scale of the note
     */
    public Vector2 GetScale() {return _scale;}

    /**
     * @return The time signature of the note
     */
    public float GetTime() {return _timer;}

    /**
     * @return The direction the note is heading
     */
    public Vector2 GetDirection() {return _direction;}

    /**
     * @return Trye if the note is visiblle on screen, false otherwise
     */
    public boolean IsVisible() {return _isVisible;}

    /**
     * Sets the visibility of the note
     * @param b true if visible, false otherwise
     */
    public void SetVisibility(boolean b) {_isVisible = b;}

    /**
     * Sets the scale of the note
     * @param x The x scale
     * @param y The y scale
     */
    public void SetScale(float x, float y) {_scale = new Vector2(x, y);}

    public void SetOtherNote(Note n){_partnerNote = n;}

    public Note GetOtherNote() {return _partnerNote;}

    public void SetConnectorRendered(boolean b){_connectorRendered = b;}

    public boolean IsConnectorRendered() {return _connectorRendered;}

    public int compareTo(Note o)
    {
        if(_timer < o.GetTime())
            return -1;
        else if (_timer == o.GetTime())
            return 0;
        else
            return 1;
    }
}
