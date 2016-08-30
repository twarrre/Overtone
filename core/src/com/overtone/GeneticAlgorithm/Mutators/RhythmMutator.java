package com.overtone.GeneticAlgorithm.Mutators;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.JMC;
import jm.music.data.Part;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by trevor on 2016-08-27.
 */
public class RhythmMutator extends Mutator implements JMC
{


    public Part Mutate(Part p, float probability)
    {
        Collections.sort(GeneticAlgorithm.RHYTHMS);

        for(int i = 0; i < p.length(); i++)
        {
            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                double currRhythm = p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).getRhythmValue();

                // Get a new rhythm value
                int index = BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), currRhythm);
                int indexToNewRhythm = Math.round(Utilities.Clamp(Utilities.GetRandomRangeNormalDistribution(index, 1.0f), 0 , GeneticAlgorithm.RHYTHMS.size() - 1));

                p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).setRhythmValue(GeneticAlgorithm.RHYTHMS.get(indexToNewRhythm));
            }
        }
        return p;
    }

    private static int BinarySearch(ArrayList<Double> list, int low, int high, double search)
    {
        int len = (high - low);
        int index = (high + low) / 2;

        if(len == 1 && list.get(index) == search)
            return index;
        else if(len == 1 && list.get(index) != search)
            return -1;

        if(search < list.get(index))
            return BinarySearch(list, low, index, search);
        else if(search > list.get(index))
            return BinarySearch(list, index, high, search);
        else
            return index;
    }
}
