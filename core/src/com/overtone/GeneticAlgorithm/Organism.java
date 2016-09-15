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
    private float[] _quality;             // The quality of the rating based on this organism and the best rater values
    private float   _rhythmProbability;   // Probability to mutate the track in the rhythm mutator
    private float   _pitchProbability;    // Probability to mutate the track in the pitch mutator
    private float   _swapProbability;     // Probability to mutate the track in the swap mutator
    private float   _simplifyProbability; // Probability to mutate the track in the simplify mutator
    private float   _dynamicProbability;  // Probability to mutate the track in the dynamic mutator
    private float   _overallRating;       // The overall rating of the organism

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
        _dynamicProbability  = Utilities.Clamp(Overtone.DynamicMutatorValues[0] - (Overtone.DynamicMutatorValues[2] * currentIteration), Overtone.DynamicMutatorValues[1], Overtone.DynamicMutatorValues[0]);
        _overallRating       = 0;
    }

    public Organism(Organism o)
    {
        _track               = o.GetTrack().copy();
        _quality             = new float[Overtone.NUM_RATERS];
        _rating              = new float[Overtone.NUM_RATERS];

        for(int i = 0; i < Overtone.NUM_RATERS; i++)
        {
            _rating[i]  = o.GetRating(i);
            _quality[i] = o.GetQuality(i);
        }

        _rhythmProbability   = o.GetRhythmProbability();
        _pitchProbability    = o.GetPitchProbability();
        _swapProbability     = o.GetSwapProbability();
        _simplifyProbability = o.GetSimplifyProbability();
        _dynamicProbability  = o.GetDynamicProbability();
        _overallRating       = o.GetOverallRating();
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
    /** Gets the mutation probability of pitch mutation */
    public float GetPitchProbability() {return _pitchProbability;}
    /** Gets the mutation probability of rhythm mutation */
    public float GetRhythmProbability() {return _rhythmProbability;}
    /** Gets the mutation probability of simplify mutation */
    public float GetSimplifyProbability() {return _simplifyProbability;}
    /** Gets the mutation probability of swap mutation */
    public float GetSwapProbability() {return _swapProbability;}
    /** Gets the mutation probability of dynamic mutation */
    public float GetDynamicProbability() {return _dynamicProbability;}
    /** Sets overall rating */
    public void SetOverallRating(float r) {_overallRating = r;}
    /** Gets the average rating */
    public float GetOverallRating() {return _overallRating;}
}
