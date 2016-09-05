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
    private Part    _track;               // The track for this organism
    private float[] _rating;              // The fitness rating for this organism
    private float[] _quality;             // The quality of the rating
    private float   _rhythmProbability;
    private float   _pitchProbability;
    private float   _swapProbability;
    private float   _simplifyProbability;
    private float   _overallRating;

    /**
     * Constructor
     * @param p The track for this organism
     */
    public Organism(Part p, int currentIteration)
    {
        _track               = p;
        _rating              = new float[Overtone.NUM_RATERS];
        _quality             = new float[Overtone.NUM_RATERS];
        _rhythmProbability   = Utilities.Clamp(Overtone.RhythmMutatorValues[0] - (Overtone.RhythmMutatorValues[2] * currentIteration), Overtone.RhythmMutatorValues[1], Overtone.RhythmMutatorValues[0]);
        _pitchProbability    = Utilities.Clamp(Overtone.PitchMutatorValues[0] - (Overtone.PitchMutatorValues[2] * currentIteration), Overtone.PitchMutatorValues[1], Overtone.PitchMutatorValues[0]);
        _swapProbability     = Utilities.Clamp(Overtone.SwapMutatorValues[0] - (Overtone.SwapMutatorValues[2] * currentIteration), Overtone.SwapMutatorValues[1], Overtone.SwapMutatorValues[0]);
        _simplifyProbability = Utilities.Clamp(Overtone.SimplifyMutatorValues[0] - (Overtone.SimplifyMutatorValues[2] * currentIteration), Overtone.SimplifyMutatorValues[1], Overtone.SimplifyMutatorValues[0]);
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
    public float GetPitchProbability() {return _pitchProbability;}
    public float GetRhythmProbability() {return _rhythmProbability;}
    public float GetSimplifyProbability() {return _simplifyProbability;}
    public float GetSwapProbability() {return _swapProbability;}
    /** Sets overall rating */
    public void SetOverallRating(float r) {_overallRating = r;}
    /** Gets the average rating */
    public float GetOverallRating() {return _overallRating;}

}
