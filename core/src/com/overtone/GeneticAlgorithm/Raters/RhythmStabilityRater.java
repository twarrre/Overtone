package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates based on how many times the direction of the rhythms changes
 * Rated by the number of direction changes / number of notes
 * Created by trevor on 2016-09-15.
 */
public class RhythmStabilityRater extends Rater
{
    public float Rate(Organism p)
    {
        int rhythmDirectionChanges = 0;
        int numNotes                = 0;
        int direction               = 0;
        double prevRhythm           = -1;
        Part track                  = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the track
            Phrase ph = track.getPhrase(i);

            // Check the if the direction changed and update the direction
            if(ph.getNote(ph.length() - 1).getRhythmValue() > prevRhythm && direction != 1 && prevRhythm != -1)
            {
                rhythmDirectionChanges++;
                direction = 1;

            }
            else if(ph.getNote(ph.length() - 1).getRhythmValue() < prevRhythm && direction != -1 && prevRhythm != -1)
            {
                rhythmDirectionChanges++;
                direction = -1;
            }

            // Store the previous dynamic
            prevRhythm = ph.getNote(ph.length() - 1).getRhythmValue();

            // Increment the number of notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)rhythmDirectionChanges / (float)numNotes;
    }
}
