package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;
import java.util.HashMap;

/**
 * Rater that checks how many unique note values there are in the piece of music.
 * Returns number of unique rhythms / the total number of notes
 * Created by trevor on 2016-08-21.
 */
public class UniqueRhythmValuesRater extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Double, Integer> rhythmCounter = new HashMap();
        Part part                              = o.GetTrack();
        int numNotes                           = 0;

        for(int i = 0; i < part.length(); i++)
        {
            // Get the phrase
            Phrase phrase = part.getPhrase(i);
            double rhythm = phrase.getNote(phrase.length() - 1).getRhythmValue();

            // Check if it is already in the map
            if(rhythmCounter.containsKey(rhythm))
                rhythmCounter.put(rhythm, rhythmCounter.get(rhythm) + 1);
            else
                rhythmCounter.put(rhythm, 1);

            // Increment the number of notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)rhythmCounter.size() / (float)numNotes;
    }
}
