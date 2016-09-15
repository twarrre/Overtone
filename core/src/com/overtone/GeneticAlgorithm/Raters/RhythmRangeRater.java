package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates the range of the lowest and the highest rhythm value in the phrase.
 * Returns the lowest value / highest value.
 * Created by trevor on 2016-09-05.
 */
public class RhythmRangeRater extends Rater
{
    public float Rate(Organism p)
    {
        double lowRhythm  = Double.MAX_VALUE;
        double highRhythm = Double.MIN_VALUE;
        Part track        = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the phrase
            Phrase ph = track.getPhrase(i);
            for(int j = 0; j < ph.length(); j++)
            {
                // Get each rhythm value in the phrase
                double rhythm = ph.getNote(j).getRhythmValue();

                // If it is greater then the highest rhythm then update it
                if(rhythm > highRhythm)
                    highRhythm = rhythm;

                // If it is less then the lowest rhythm value then update it
                if(rhythm < lowRhythm)
                    lowRhythm = rhythm;
            }
        }

        // Return the low rhythm value / high rhythm value
        if(lowRhythm == Double.MAX_VALUE || highRhythm == Double.MIN_VALUE)
            return 0;
        else
            return (float)lowRhythm / (float)highRhythm;
    }
}
