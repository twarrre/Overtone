package com.overtone.GeneticAlgorithm.Raters;
import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

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
        Part part = o.GetTrack();

        for(int i = 0; i < part.length(); i++)
        {
            Phrase phrase = part.getPhrase(i);
            for(int j = 0; j < phrase.length(); j++)
            {
                int pitch = phrase.getNote(i).getPitch();
                noteCounter.put(pitch, noteCounter.get(pitch) + 1);
            }
        }

        return 0;
    }
}
