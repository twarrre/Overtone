package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.HashMap;

/**
 * Created by trevor on 2016-08-21.
 */
public class UniqueRhythmValues extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Double, Integer> rhythmCounter = new HashMap();
        Part part = o.GetTrack();

        for(int i = 0; i < part.length(); i++)
        {
            Phrase phrase = part.getPhrase(i);
            for(int j = 0; j < phrase.length(); j++)
            {
                double rhythm = phrase.getNote(j).getRhythmValue();
                rhythmCounter.put(rhythm, rhythmCounter.get(rhythm) + 1);
            }
        }

        return (float)rhythmCounter.size() / (float)part.length();
    }
}
