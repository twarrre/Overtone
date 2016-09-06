package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.HashMap;

/**
 * Created by trevor on 2016-09-05.
 */
public class UniqueDynamicRater extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Integer, Integer> dynamicCounter = new HashMap();
        Part part = o.GetTrack();
        int numNotes = 0;

        for(int i = 0; i < part.length(); i++)
        {
            Phrase phrase = part.getPhrase(i);
            for(int j = 0; j < phrase.length(); j++)
            {
                if(phrase.getNote(j).isRest())
                    continue;

                int dynamic = phrase.getNote(j).getDynamic();

                if(dynamicCounter.containsKey(dynamic))
                    dynamicCounter.put(dynamic, dynamicCounter.get(dynamic) + 1);
                else
                    dynamicCounter.put(dynamic, 1);

                numNotes++;
            }
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)dynamicCounter.size() / (float)numNotes;
    }
}
