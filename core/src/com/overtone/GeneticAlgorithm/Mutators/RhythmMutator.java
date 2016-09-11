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
    private int _indexForLargestRhythmChord;
    public RhythmMutator()
    {
        Collections.sort(GeneticAlgorithm.RHYTHMS);
        _indexForLargestRhythmChord = BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), DOUBLE_DOTTED_QUARTER_NOTE);
    }

    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                double currRhythm = p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).getRhythmValue();

                //Find the index of the current rhythm
                int index = BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), currRhythm);

                int range = p.getPhrase(i).length() > 1 ? _indexForLargestRhythmChord : GeneticAlgorithm.RHYTHMS.size() - 1;

                int indexToNewRhythm = Utilities.GetRandomRangeNormalDistribution(index, 2, range, 0.0f, true);

                p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).setRhythmValue(GeneticAlgorithm.RHYTHMS.get(indexToNewRhythm));
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
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
