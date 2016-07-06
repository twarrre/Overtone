package com.overtone.Notes;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a note object in the game
 * Created by trevor on 2016-06-21.
 */
public class Note
{
    /**
     * All of the different types of notes
     */
    public enum  NoteType
    {
        singleNote(0),
        doubleNote(1),
        holdNote(2);

        public int value;
        NoteType(int value) { this.value = value; }
    }

    /**
     * The difficulty multiplier for each difficulty.
     */
    public enum DifficultyMultiplier
    {
        easy(5),
        medium(4),
        hard(3);

        public float value;
        DifficultyMultiplier(float value) { this.value = value; }
    }

    // Stores the type of note that this is
    private final NoteType _type;

    // The target of where this note is heading
    private final Vector2 _target;

    // The speed that the note travels at
    private final float _speed;

    // The difficulty of the note
    private final DifficultyMultiplier _difficulty;

    // The direction that the note is heading
    private final Vector2 _direction;

    // Time that the note must be at the target zone at
    private final float _timer;

    // Radius of the target zone
    private final float _targetRadius;

    // Unique identifier of the note
    private final int _id;

    // True if visible on screen, false otherwise
    private boolean _isVisible;

    // The scale of the note
    private Vector2 _scale;

    // The note's center point in the world
    private Vector2 _center;

    /**
     * Constructor
     * @param type The type of note
     * @param center The center point
     * @param target The target of where to the note is going
     * @param scale The scale of the note
     * @param diff the difficulty of the note
     * @param timer The time signature of the note
     * @param targetRadius The radius of the target zone
     * @param id The unique identifer of the note
     */
    public Note(NoteType type, Vector2 center, Vector2 target, Vector2 scale, DifficultyMultiplier diff, float timer, float targetRadius, int id)
    {
        // Calculate the direction the note is heading
        _direction = new Vector2(target.x - center.x, target.y - center.y).nor();

        // Shift the center into the proper quad based on the direction it is going
        _center       = new Vector2(center.x + _direction.x, center.y + _direction.y);

        // Calculate the speed of the note based on difficulty and distance to the target
        _speed     = ((float)Math.sqrt(Math.pow((target.x - _center.x), 2) + Math.pow((target.y - _center.y), 2))) / diff.value;

        _type         = type;
        _target       = target;
        _isVisible    = false;
        _difficulty   = diff;
        _scale        = scale;
        _timer        = timer;
        _targetRadius = targetRadius;
        _id           = id;
    }

    /**
     * Checks if the note has passed the target position
     * @return returns true if hit has passed the target position, false otherwise
     */
    private boolean IsPassedTarget()
    {
        // Create a vector where the head is the target and the tail is the center of the note
        Vector2 noteDir = new Vector2(_target.x - _center.x, _target.y - _center.y);

        // If the dot product is negative we know that the note has passed the target
        if(noteDir.dot(_direction) < 0 && _center.dst(_target) > _targetRadius * 2.1)
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
        if(!IsPassedTarget())
            _center.add(new Vector2(_direction.x * _speed * deltaTime, _direction.y * _speed * deltaTime));
        else
            _isVisible = false;
    }

    /**
     * @return The bottom left corner position of the note
     */
    public Vector2 GetPosition() {return new Vector2(_center.x - (_scale.x / 2.0f), _center.y - (_scale.y / 2.0f));}

    /**
     * @return The center position of the note
     */
    public Vector2 GetCenter() {return _center;}

    /**
     * @return The position of the note's target
     */
    public Vector2 GetTarget() {return _target;}

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
     * @return The difficulty of the note
     */
    public DifficultyMultiplier GetDifficulty() {return _difficulty;}

    /**
     * @return The identifier of the note
     */
    public int GetId() {return _id;}

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
}
