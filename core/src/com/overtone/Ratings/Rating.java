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

    public enum ScoreRating
    {
        Perfection,
        Brilliant,
        Great,
        Cleared,
        Failure,
        None;

        public static ScoreRating GetRating(int ... counters)
        {
            float score = 0;

            int totalNotes = 0;
            for(int i = 0; i < counters.length; i++)
                totalNotes += counters[i];

            score += 1.0f * ((float)counters[0] / totalNotes);
            score += 0.5f * ((float)counters[1] / totalNotes);
            score += 0.3f * ((float)counters[2] / totalNotes);
            score += 0.1f * ((float)counters[3] / totalNotes);
            score += 0.0f * ((float)counters[4] / totalNotes);

            if(score >= 1.0f)
                return Perfection;
            else if(score >= 0.86f && score < 1.0f)
                return Brilliant;
            else if(score >= 0.66f && score <= 0.85f)
                return Great;
            else if(score >= 0.5f && score < 0.65f)
                return Cleared;
            else if(score < 0.5f)
                return Failure;
            else
                return None;
        }

        public static ScoreRating GetRating(String rating)
        {
            if(rating.compareTo("Perfection") == 0)
                return Perfection;
            else if(rating.compareTo("Brilliant") == 0)
                return Brilliant;
            else if(rating.compareTo("Great") == 0)
                return Great;
            else if(rating.compareTo("Cleared") == 0)
                return Cleared;
            else if(rating.compareTo("Failure") == 0)
                return Failure;
            else
                return None;
        }


        public String toString()
        {
            switch(this.ordinal())
            {
                case 0:
                    return "Perfection";
                case 1:
                    return "Brilliant";
                case 2:
                    return "Great";
                case 3:
                    return "Cleared";
                case 4:
                    return "Failure";
                case 5:
                    return "---";
                default:
                    return "---";
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
