package com.overtone.Ratings;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a texture on screen to tell the player how well they hit the note compared to the target zone.
 * Created by trevor on 2016-07-07.
 */
public class Rating
{
    /** How long to keep the rating on screen*/
    public static final float ONSCREEN_TIME = 0.75f;

    /**Represents the ratings of how well the player hit the button*/
    public enum RatingType
    {
        Perfect (700, 1, 0),
        Great   (500, 1, 0),
        Ok      (300, 0, 0),
        Bad     (100, -1, 1),
        Miss    (0, -1, 1),
        None    (0, 0, 2);

        public int Score; // The score associated with each rating
        public int ComboMultiplier;
        public int SoundIndex;
        RatingType(int score, int combo, int sound) {Score = score; ComboMultiplier = combo; SoundIndex = sound;}
    }

    private final RatingType _rating;      // The type of the rating
    private float            _screenTime;  // How long the rating has been on screen
    private Vector2          _center;      // The center point of the rating
    private boolean          _isVisible;   // True if it is visible on screen, false otherwise
    private Vector2          _scale;       // The scale of the rating

    /**
     * Constructor
     * @param rating The type of the rating this is
     * @param center The position of the rating based on the center
     */
    public Rating(RatingType rating, Vector2 center, Vector2 scale)
    {
        _rating      = rating;
        _center      = center;
        _screenTime  = 0;
        _isVisible   = true;
        _scale       = scale;
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
    /**
     * @return True if visible on screen, false otherwise
     */
    public boolean IsVisible() {return _isVisible;}

    /**
     * @return The center point of the rating
     */
    public Vector2 GetCenter() {return _center;}

    /**
     * @return The position of the bottom left conner of the rating, for drawing on screen
     */
    public Vector2 GetDrawingPosition() {return new Vector2(_center.x - (_scale.x / 2.0f), _center.y - (_scale.y / 2.0f));}

    /**
     * @return the scale of the rating
     */
    public Vector2 GetScale() {return _scale;}

    /**
     * @return The type of this rating
     */
    public RatingType GetRating() {return _rating;}

    /**
     * Sets the visibility of the rating
     * @param v Trus if visible on screen, false otherwise
     */
    public void SetVisibility(boolean v) {_isVisible = v;}
}
