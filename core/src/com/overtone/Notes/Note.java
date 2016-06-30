package com.overtone.Notes;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a note object in the game
 * Created by trevor on 2016-06-21.
 */
public class Note
{
    public enum  NoteType
    {
        singleNote(0),
        doubleNote(1),
        holdNote(2);

        public int value;
        NoteType(int value) { this.value = value; }
    }

    public enum DifficultyMultiplier
    {
        easy(4),
        medium(3),
        hard(2);

        public float value;
        DifficultyMultiplier(float value) { this.value = value; }
    }

    public Note(NoteType type, Vector2 position, Vector2 target, DifficultyMultiplier diff)
    {
        _type = type;
        _position = position;
        _target = target;
        _isVisible = false;
        _difficulty = diff;

        _speed = ((float)Math.sqrt(Math.pow((_target.x - _position.x), 2) + Math.pow((_target.y - _position.y), 2))) / _difficulty.value;
    }

    private NoteType _type;
    private Vector2 _position;
    private Vector2 _target;
    private boolean _isVisible;
    private float _speed;
    private DifficultyMultiplier _difficulty;

    public void Update(float deltaTime)
    {
        if(_isVisible)
        {
            _position.add(_target.nor().scl(_speed * deltaTime));
        }
    }

    public Vector2 GetPosition() {return _position;}
    public Vector2 GetTarget() {return _target;}
    public boolean IsVisible() {return _isVisible;}
    public NoteType GetType() {return _type;}

    public void SetVisiblilty(boolean b) {_isVisible = b;}
}
