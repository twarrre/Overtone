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
        Perfect (7),
        Great   (5),
        Ok      (3),
        Bad     (1),
        Miss    (0),
        None    (0);

        public int score;
        RatingValue(int s) {score = s;}

        public String ToString()
        {
            switch(score)
            {
                case 7:
                    return "Perfect";
                case 5:
                    return "Great";
                case 3:
                    return "Okay";
                case 1:
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
    private boolean           _isVisisble;

    public Rating(RatingValue rating, Vector2 center)
    {
        _rating     = rating;
        _center     = center;
        _screenTime = 0;
        _isVisisble = true;
    }

    public void Update(float deltaTime)
    {
        if(_isVisisble)
            _screenTime += deltaTime;

        if(_screenTime >= ONSCREEN_TIME)
            _isVisisble = false;
    }

    public boolean IsVisible() {return _isVisisble;}
    public Vector2 GetCenter() {return _center;}
    public RatingValue GetingRating() {return _rating;}
    public void SetVisibility(boolean v) {_isVisisble = v;}

}
