package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates based on how many rests there are in compared to the total number of notes
 * Created by trevor on 2016-09-15.
 */
public class RestRatioRater extends Rater
{
    public float Rate(Organism o)
    {
        Part part     = o.GetTrack();
        int numNotes  = 0;
        int numRests  = 0;

        for(int i = 0; i < part.length(); i++)
        {
            // Get the next phrase
            Phrase phrase = part.getPhrase(i);

            // If the note is a rest, increment
            if(phrase.getNote(0).isRest())
                numRests++;

            // Increment the number of notes
            numNotes++;
        }

        // Return the ratio
        if(numNotes == 0)
            return 0;
        else
            return (float)numRests / (float)numNotes;
    }
}
