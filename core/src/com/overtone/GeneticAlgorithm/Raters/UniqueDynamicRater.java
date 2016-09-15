package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;
import java.util.HashMap;

/**
 * Rater that checks how many unique dynamic values there are in the piece of music.
 * Returns number of unique dynamics / the total number of notes
 * Created by trevor on 2016-09-05.
 */
public class UniqueDynamicRater extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Integer, Integer> dynamicCounter = new HashMap();
        Part part                                = o.GetTrack();
        int numNotes                             = 0;

        for(int i = 0; i < part.length(); i++)
        {
            // Get the current phrase
            Phrase phrase = part.getPhrase(i);
            for(int j = 0; j < phrase.length(); j++)
            {
                // If the note is a rest, skip it
                if(phrase.getNote(j).isRest())
                    continue;

                // Get the dynamic from the note in the phrase
                int dynamic = phrase.getNote(j).getDynamic();

                // Check if the dynamic has been put in the map already
                if(dynamicCounter.containsKey(dynamic))
                    dynamicCounter.put(dynamic, dynamicCounter.get(dynamic) + 1);
                else
                    dynamicCounter.put(dynamic, 1);

                // Increment the number of notes
                numNotes++;
            }
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)dynamicCounter.size() / (float)numNotes;
    }
}
