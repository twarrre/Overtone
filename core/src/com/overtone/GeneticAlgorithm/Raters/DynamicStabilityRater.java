package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates based on how many times the direction of the dynamic changes
 * Rated by the number of direction changes / number of notes
 * Created by trevor on 2016-09-05.
 */
public class DynamicStabilityRater extends Rater
{
    public float Rate(Organism p)
    {
        int dynamicDirectionChanges = 0;
        int numNotes                = 0;
        int direction               = 0;
        int prevDynamic             = -1;
        Part track                  = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the track
            Phrase ph = track.getPhrase(i);

            // Check the if the direction changed and update the direction
            if(ph.getNote(0).getDynamic() > prevDynamic && direction != 1 && prevDynamic != -1)
            {
                dynamicDirectionChanges++;
                direction = 1;

            }
            else if(ph.getNote(0).getDynamic() < prevDynamic && direction != -1 && prevDynamic != -1)
            {
                dynamicDirectionChanges++;
                direction = -1;
            }

            // Store the previous dynamic
            prevDynamic = ph.getNote(0).getDynamic();

            // Increment the number of notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)dynamicDirectionChanges / (float)numNotes;
    }
}