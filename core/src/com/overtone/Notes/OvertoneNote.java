package com.overtone.Notes;

import com.badlogic.gdx.math.Vector2;
import com.overtone.Overtone;

/**
 * Represents a note object in the game
 * Created by trevor on 2016-06-21.
 */
public class OvertoneNote implements Comparable<OvertoneNote>
{
    /**All of the different types of notes*/
    public enum  NoteType
    {
        Single, // One single note
        Double, // Two notes at the same time
        Hold    // Note must be held for a certain amount of time
    }

    private final NoteType _type;              // Stores the type of note that this one is
    private final Target   _target;            // The target point of where this note is heading
    private final Vector2  _direction;         // The direction that the note is heading in
    private final float    _time;              // Time that the note must be at the target zone at
    private final float    _speed;             // The speed that the note travels at
    private final Vector2  _scale;             // The scale of the note
    private Vector2        _center;            // The note's center point in the world
    private boolean        _isVisible;         // True if visible on screen, false otherwise
    private boolean        _connectorRendered; // True if the note is double or hold and it's connector has been rendered on screen
    private OvertoneNote   _partnerNote;       // Stores a reference to the partner note if it is a hold or double note, null otherwise
    private float          _otherNoteTime;     // Time that the note must be at the target zone at
    private boolean        _completed;

    /**
     * Constructor
     * @param type The type of note
     * @param target The target of where to the note is going
     * @param timer The time signature of the note
     */
    public OvertoneNote(NoteType type, Target target, float timer)
    {
        float distance     = target.Position.dst(Overtone.NoteCenter);                                                   // Find the distance from the target to the center of the note

        _direction         = new Vector2(target.Position.x - Overtone.NoteCenter.x, target.Position.y - Overtone.NoteCenter.y).nor(); // Creates a vector point in the direction of the target zone
        _center            = new Vector2(Overtone.NoteCenter.x + _direction.x, Overtone.NoteCenter.y + _direction.y);                 // Shift the center into the proper quad based on the direction it is going
        _speed             = distance / Overtone.Difficulty.Multiplier;                                     // Calculate the speed of note (dist / time)
        _target            = target;
        _time              = timer;
        _type              = type;
        _scale             = Overtone.NoteScale;
        _partnerNote       = null;
        _otherNoteTime     = 0;
        _connectorRendered = false;
        _isVisible         = false;
        _completed         = false;
    }

    /**
     * Copy Constructor
     * @param n
     */
    public OvertoneNote(OvertoneNote n)
    {
        float distance     = n.GetTarget().Position.dst(n.GetCenter());                                                                 // Find the distance from the target to the center of the note

        _direction         = new Vector2(n.GetTarget().Position.x - n.GetCenter().x, n.GetTarget().Position.y - n.GetCenter().y).nor(); // Creates a vector point in the direction of the target zone
        _center            = new Vector2(n.GetCenter().x + _direction.x, n.GetCenter().y + _direction.y);                               // Shift the center into the proper quad based on the direction it is going
        _speed             = distance / Overtone.Difficulty.Multiplier;                                                                 // Calculate the speed of note (dist / time)
        _target            = n.GetTarget();
        _time              = n.GetTime();
        _type              = n.GetType();
        _scale             = n.GetScale();
        _partnerNote       = null;
        _otherNoteTime     = (n.GetType() == NoteType.Hold || n.GetType() == NoteType.Double) ? n.GetOtherNote().GetTime() : 0.0f;
        _connectorRendered = false;
        _isVisible         = false;
        _completed         = false;
    }

    /**
     * Checks if the note has passed the target position
     * @return returns true if the note has passed the target position, false otherwise
     */
    private boolean IsPassedTarget(Vector2 pos, Target target, Vector2 dir)
    {
        // Create a vector pointing to the target from the note position
        Vector2 noteDir = new Vector2(target.Position.x - pos.x, target.Position.y - pos.y);

        // If the dot product is negative we know that the note has passed the target
        if(noteDir.dot(dir) < 0
                && pos.dst(target.Position) > Target.Diameter * 0.75f) // and if it has passed an acceptable radius of the target zone
            return true;

       return false;
    }

    /**
     * Updates the note every frame
     * @param deltaTime The time since the last frame
     */
    public void Update(float deltaTime)
    {
        // if the target has not passed the target, move it in the direction, else make it not visible anymore
        if(!IsPassedTarget(_center, _target, _direction))
            _center.add(new Vector2(_direction.x * _speed * deltaTime, _direction.y * _speed * deltaTime));
        else
            _isVisible = false;

        _connectorRendered = false; // The connector has not been rendered this frame
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
     * @return The target for the note
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
    public float GetTime() {return _time;}

    /**
     * @return The direction the note is heading
     */
    public Vector2 GetDirection() {return _direction;}

    /**
     * @return True if the note is visible on screen, false otherwise
     */
    public boolean IsVisible() {return _isVisible;}

    /**
     * Sets the visibility of the note
     * @param b true if visible, false otherwise
     */
    public void SetVisibility(boolean b) {_isVisible = b;}

    /**
     * Sets the partner note if the note is a double, or hold
     * @param n the other note in the pair
     */
    public void SetOtherNote(OvertoneNote n)
    {
        if(_type == NoteType.Double || _type == NoteType.Hold)
            _partnerNote = n;
    }

    /**
     * Sets the time stamp of the other note
     * @param t the time stamp of the other note.
     */
    public void SetOtherNoteTime(float t)
    {
        if(_type == NoteType.Double || _type == NoteType.Hold)
            _otherNoteTime = t;
    }

    /**
     * @return The partner note of this note, null if a single note
     */
    public OvertoneNote GetOtherNote() {return _partnerNote;}

    /**
     * @return the time signature of the other note.
     */
    public float GetOtherNoteTime() {return _otherNoteTime;}

    /**
     * Sets if the connector has been rendered or not
     * @param b True if the connector has been rendered, false otherwise
     */
    public void SetConnectorRendered(boolean b)
    {
        if(_type == NoteType.Double || _type == NoteType.Hold)
            _connectorRendered = b;
    }

    /**
     * @return True if the connector has been rendered, false otherwise
     */
    public boolean IsConnectorRendered() {return _connectorRendered;}

    /**
     * Compares the notes based on their time signature
     * @param o The other note to compare to
     * @return -1 if this note is less than o, 0 if they are equal, 1 if this note is greater than o
     */
    public int compareTo(OvertoneNote o)
    {
        if(_time < o.GetTime())
            return -1;
        else if (_time == o.GetTime())
            return 0;
        else
            return 1;
    }

    public void NoteCompleted(boolean val) {_completed = val; }

    public boolean IsCompleted(){return _completed;}
}
