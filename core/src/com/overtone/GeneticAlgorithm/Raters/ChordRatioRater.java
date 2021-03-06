package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates based on how many chords there are in compared to the total number of notes
 * Created by trevor on 2016-09-09.
 */
public class ChordRatioRater extends Rater
{
    public float Rate(Organism o)
    {
        Part part     = o.GetTrack();
        int numNotes  = 0;
        int numChords = 0;

        for(int i = 0; i < part.length(); i++)
        {
            // Get the next phrase
            Phrase phrase = part.getPhrase(i);

            // If the note is a chord, increment
            if(phrase.length() > 1)
                numChords++;

            // Increment the number of notes
            numNotes++;
        }

        // Return the ratio
        if(numNotes == 0)
            return 0;
        else
            return (float)numChords / (float)numNotes;
    }
}
