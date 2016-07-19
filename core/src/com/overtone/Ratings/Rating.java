package com.overtone.Ratings;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a texture on screen to tell the player how well they hit the note compared to the target zone.
 * Created by trevor on 2016-07-07.
 */
public class Rating
{
    /** How long to keep the rating on screen*/
    public static final float ONSCREEN_TIME = 1.5f;

    /**
     * Represents the ratings of how well the player hit the button
     */
    public enum RatingType
    {
        Perfect (700),
        Great   (500),
        Ok      (300),
        Bad     (100),
        Miss    (0),
        None    (0);

        public int Score;
        RatingType(int score) {Score = score;}
    }

    private final RatingType _rating;
    private float            _screenTime;
    private Vector2          _center;
    private boolean          _isVisible;

    /**
     * Constructor
     * @param rating The type of the rating this is
     * @param center The position of the rating based on the center
     */
    public Rating(RatingType rating, Vector2 center)
    {
        _rating     = rating;
        _center     = center;
        _screenTime = 0;
        _isVisible  = true;
    }

    /**
     * Updates the note on every frame
     * @param deltaTime the time since the last frame
     */
    public void Update(float deltaTime)
    {
        if(_isVisible)
            _screenTime += deltaTime;

        if(_screenTime >= ONSCREEN_TIME)
            _isVisible = false;
    }

    public boolean IsVisible() {return _isVisible;}
    public Vector2 GetCenter() {return _center;}
    public RatingType GetRating() {return _rating;}
    public void SetVisibility(boolean v) {_isVisible = v;}

}
