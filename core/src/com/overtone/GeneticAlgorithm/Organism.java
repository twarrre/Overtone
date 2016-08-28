package com.overtone.GeneticAlgorithm;

import com.overtone.Overtone;
import com.overtone.Utilities;
import jm.music.data.Part;

/**
 * Represents and organism in the genetic algorithm
 * Created by trevor on 2016-08-14.
 */
public class Organism
{
    /** Amount do drop probability of mutation after each generation */
    public static final float MUTATION_STEP = 0.0005f;

    /** The starting probability for how a note can mutate */
    public static final float STARTING_PROBABILITY = 0.8f;

    private Part    _track;               // The track for this organism
    private float[] _rating;              // The fitness rating for this organism
    private float[] _quality;             // The quality of the rating
    private float   _mutationProbability; // The probability that this track will contain some mutation
    private float   _overallRating;

    /**
     * Constructor
     * @param p The track for this organism
     */
    public Organism(Part p, float mutation)
    {
        _track               = p;
        _rating              = new float[Overtone.NUM_RATERS];
        _quality             = new float[Overtone.NUM_RATERS];
        _mutationProbability = Utilities.Clamp(mutation, 0.001f, 1.0f);
        _overallRating       = 0;
    }

    /** Sets the rating of this organism. */
    public void SetRating(float r, int i) {_rating[i] = r;}
    /** Gets the rating of this organism. */
    public float GetRating(int i) {return _rating[i];}
    /** Sets the quality of this organism. */
    public void SetQuality(float r, int i) {_quality[i] = r;}
    /** Gets the quality of this organism. */
    public float GetQuality(int i) {return _quality[i];}
    /** Gets the track of this organism. */
    public Part GetTrack() {return _track;}
    /** Sets the track of this organism. */
    public void SetTrack(Part p) {_track = p;}
    /** Gets the mutation probability */
    public float GetProbability() {return _mutationProbability;}
    /** Sets overall rating */
    public void SetOverallRating(float r) {_overallRating = r;}
    /** Gets the average rating */
    public float GetOverallRating() {return _overallRating;}

}
