package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;

/**
 * Rates the phrase based on how the dynamics flow between each other.
 * Checks if the notes around each other are significantly higher or lower than each other
 * Created by trevor on 2016-08-14.
 * Created by trevor on 2016-09-05.
 */
public class NeighboringDynamicRater extends Rater
{
    public static int ACCEPTABLE_RANGE = 15;

    public float Rate(Organism p)
    {
        int numDynamicsNotes = 0;
        int numNotes         = 0;
        Part track           = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // If you have reached the end of the phrase you are done
            if(i == track.length() - 1)
            {
                numNotes++;
                continue;
            }

            // Check if the dynamic in front of the current one is outside of the acceptable range
            if((track.getPhrase(i + 1).getNote(0).getDynamic() > (track.getPhrase(i).getNote(0).getDynamic() + ACCEPTABLE_RANGE)
                    || track.getPhrase(i + 1).getNote(0).getDynamic() < (track.getPhrase(i).getNote(0).getDynamic() - ACCEPTABLE_RANGE)))
                numDynamicsNotes++;

            // Increment the number of notes
            numNotes++;
        }

        // Return rating
        if(numNotes == 0)
            return 0;
        else
            return (float)numDynamicsNotes / (float)numNotes;
    }
}
