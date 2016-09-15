package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates the phrase based on the pitch direction to prevent drastic changes in pitch.
 * Created by trevor on 2016-08-14.
 */
public class PitchDirectionRater extends Rater
{
    public float Rate(Organism p)
    {
        int pitchesHigherThanBefore = 0;
        int numNotes                = 0;
        int prevPitch               = -1;
        Part track                  = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the phrase
            Phrase ph = track.getPhrase(i);

            // If it is a chord  or rest skip it
            if(ph.length() > 1 || ph.getNote(0).isRest())
                continue;

            // if the note was higher then before, increment
            if(ph.getNote(0).getPitch() > prevPitch && prevPitch != -1)
                pitchesHigherThanBefore++;

            // Save the previous pitch
            prevPitch = ph.getNote(0).getPitch();

            // Increment the number if notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)pitchesHigherThanBefore / (float)numNotes;
    }
}
