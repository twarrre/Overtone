package com.overtone.Ratings;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by trevor on 2016-07-07.
 */
public class Rating
{
    public static final float ONSCREEN_TIME = 1.5f;

    /**
     * Represents the ratings of how well the player hit the button
     */
    public enum RatingValue
    {
        Perfect (700),
        Great   (500),
        Ok      (300),
        Bad     (100),
        Miss    (0),
        None    (0);

        public int score;
        RatingValue(int s) {score = s;}

        public String ToString()
        {
            switch(score)
            {
                case 700:
                    return "Perfect";
                case 500:
                    return "Great";
                case 300:
                    return "Okay";
                case 100:
                    return "Bad";
                case 0:
                    return "Miss";
                default:
                    return "";
            }
        }
    }

    private final RatingValue _rating;
    private float             _screenTime;
    private Vector2           _center;
    private boolean           _isVisible;

    public Rating(RatingValue rating, Vector2 center)
    {
        _rating     = rating;
        _center     = center;
        _screenTime = 0;
        _isVisible = true;
    }

    public void Update(float deltaTime)
    {
        if(_isVisible)
            _screenTime += deltaTime;

        if(_screenTime >= ONSCREEN_TIME)
            _isVisible = false;
    }

    public boolean IsVisible() {return _isVisible;}
    public Vector2 GetCenter() {return _center;}
    public RatingValue GetingRating() {return _rating;}
    public void SetVisibility(boolean v) {
        _isVisible = v;}

}
