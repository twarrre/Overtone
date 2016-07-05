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
        easy(5),
        medium(4),
        hard(3);

        public float value;
        DifficultyMultiplier(float value) { this.value = value; }
    }

    public Note(NoteType type, Vector2 center, Vector2 target, Vector2 scale, DifficultyMultiplier diff, float timer, float targetRadius, int id)
    {
        _direction = new Vector2(target.x - center.x, target.y - center.y).nor();

        _type = type;
        _center = new Vector2(center.x + _direction.x, center.y + _direction.y);
        _target = target;
        _isVisible = false;
        _difficulty = diff;
        _scale = scale;
        _timer = timer;
        _targetRadius = targetRadius;
        _id = id;

        _speed = ((float)Math.sqrt(Math.pow((_target.x - _center.x), 2) + Math.pow((_target.y - _center.y), 2))) / _difficulty.value;
    }

    private NoteType _type;
    private Vector2 _target;
    private boolean _isVisible;
    private float _speed;
    private DifficultyMultiplier _difficulty;
    private Vector2 _scale;
    private Vector2 _center;
    private Vector2 _direction;
    private float _timer;
    private float _targetRadius;
    private int _id;

    private boolean IsPassedTarget()
    {
        Vector2 noteDir = new Vector2(_target.x - _center.x, _target.y - _center.y);

        if(noteDir.dot(_direction) < 0 && _center.dst(_target) > _targetRadius * 2.1)
            return true;

       return false;
    }
    public void Update(float deltaTime)
    {
        if(!IsPassedTarget()) //_isVisible && ((float)Math.sqrt(Math.pow((_target.x - _center.x), 2) + Math.pow((_target.y - _center.y), 2))) > 0.5
        {
            _center.add(new Vector2(_direction.x * _speed * deltaTime, _direction.y * _speed * deltaTime));
        }
        else
        {
            _isVisible = false;
        }
    }

    public Vector2 GetPosition() {return new Vector2(_center.x - (_scale.x / 2.0f), _center.y - (_scale.y / 2.0f));}
    public Vector2 GetCenter() {return _center;}
    public Vector2 GetTarget() {return _target;}
    public boolean IsVisible() {return _isVisible;}
    public NoteType GetType() {return _type;}
    public Vector2 GetScale() {return _scale;}
    public float GetTime() {return _timer;}
    public DifficultyMultiplier GetDifficulty() {return _difficulty;}
    public int GetId() {return _id;}

    public void SetVisibility(boolean b) {_isVisible = b;}
}
