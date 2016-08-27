package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.HashMap;

/**
 * Created by trevor on 2016-08-21.
 */
public class UniqueRhythmValuesRater extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Double, Integer> rhythmCounter = new HashMap();
        Part part = o.GetTrack();
        int numNotes = 0;

        for(int i = 0; i < part.length(); i++)
        {
            Phrase phrase = part.getPhrase(i);
            double rhythm = phrase.getNote(phrase.length() - 1).getRhythmValue();

            if(rhythmCounter.containsKey(rhythm))
                rhythmCounter.put(rhythm, rhythmCounter.get(rhythm) + 1);
            else
                rhythmCounter.put(rhythm, 1);

            numNotes++;
        }

        return (float)rhythmCounter.size() / (float)numNotes;
    }
}
