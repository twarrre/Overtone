package com.overtone.GeneticAlgorithm.Mutators;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.JMC;
import jm.music.data.Part;

/**
 * Created by trevor on 2016-09-05.
 */
public class DynamicMutator extends Mutator implements JMC
{
    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            if(p.getPhrase(i).getNote(0).isRest())
                continue;

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                for(int j = 0; j < p.getPhrase(i).length(); j++)
                {
                    int oldDynamic = p.getPhrase(i).getNote(j).getDynamic();
                    int newDynamic = Utilities.GetRandomRangeNormalDistribution(oldDynamic, 5.0f);
                    p.getPhrase(i).getNote(j).setDynamic((int)Utilities.Clamp(newDynamic, GeneticAlgorithm.LOW_DYNAMIC, GeneticAlgorithm.HIGH_DYNAMIC));
                }
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
