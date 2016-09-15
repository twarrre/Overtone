package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates the range of the lowest and the highest dynamic value in the phrase.
 * Returns the lowest value / highest value.
 * Created by trevor on 2016-09-05.
 */
public class DynamicRangeRater extends Rater
{
    public float Rate(Organism p)
    {
        int lowDynamic  = Integer.MAX_VALUE;
        int highDynamic = Integer.MIN_VALUE;
        Part track      = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the track
            Phrase ph = track.getPhrase(i);
            for(int j = 0; j < ph.length(); j++)
            {
                // Get the dynamic of the note
                int dynamic = ph.getNote(j).getDynamic();

                // If the dynamic is higher than the highest stored, then update it
                if(dynamic > highDynamic)
                    highDynamic = dynamic;

                // If the dynamic is lower than the lowest stored, then update it.
                if(dynamic < lowDynamic)
                    lowDynamic = dynamic;
            }
        }

        // Return the rater
        if(lowDynamic == Integer.MAX_VALUE || highDynamic == Integer.MIN_VALUE)
            return 0;
        else
            return (float)lowDynamic / (float)highDynamic;
    }
}
