package com.overtone.GeneticAlgorithm.Mutators;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.JMC;
import jm.music.data.Part;

/**
 * Mutates the dynamic of the the note.
 * Created by trevor on 2016-09-05.
 */
public class DynamicMutator extends Mutator implements JMC
{
    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            // Skip rests
            if(p.getPhrase(i).getNote(0).isRest())
                continue;

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                for(int j = 0; j < p.getPhrase(i).length(); j++)
                {
                    // Get the old dynamic
                    int oldDynamic = p.getPhrase(i).getNote(j).getDynamic();
                    // Get a new dynamic normally distributed around the old dynamic
                    int newDynamic = Utilities.GetRandomRangeNormalDistribution(oldDynamic, 5.0f, GeneticAlgorithm.HIGH_DYNAMIC, GeneticAlgorithm.LOW_DYNAMIC, false);
                    p.getPhrase(i).getNote(j).setDynamic(newDynamic);
                }
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
