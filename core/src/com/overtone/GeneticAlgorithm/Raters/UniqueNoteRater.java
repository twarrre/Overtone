package com.overtone.GeneticAlgorithm.Raters;
import com.overtone.GeneticAlgorithm.Organism;

import java.util.HashMap;

/**
 * Measures the number of unique notes in the phrase
 * Created by trevor on 2016-08-14.
 */
public class UniqueNoteRater extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Integer, Integer> noteCounter = new HashMap();
        int length = o.GetTrack().length();

        for(int i = 0; i < length; i++)
        {
            int pitch = o.GetTrack().getNote(i).getPitch();
            noteCounter.put(pitch, noteCounter.get(pitch) + 1);
        }

        return 0;
    }
}
