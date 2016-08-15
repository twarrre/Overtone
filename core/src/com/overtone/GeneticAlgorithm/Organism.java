package com.overtone.GeneticAlgorithm;

import com.overtone.Overtone;
import jm.music.data.Phrase;

/**
 * Represents and organism in the genetic algorithm
 * Created by trevor on 2016-08-14.
 */
public class Organism
{
    private final Phrase _track;  // The track for this organism
    private float[]  _rating; // The fitness rating for this organism

    /**
     * Constructor
     * @param p The track for this organism
     */
    public Organism(Phrase p)
    {
        _track  = p;
        _rating = new float[Overtone.NUM_RATERS];
    }

    /** Sets the rating of this organism. */
    public void SetRating(float r, int i) {_rating[i] = r;}
    /** Gets the rating of this organism. */
    public float GetRating(int i) {return _rating[i];}
    /** Gets the track of this organism. */
    public Phrase GetTrack() {return _track;}
}
