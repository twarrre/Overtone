package com.overtone.GeneticAlgorithm.Mutators;

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
    private static final ArrayList<Double> rhythms = new ArrayList<Double>()
    {{
        add(THIRTYSECOND_NOTE);
        add(DOTTED_SIXTEENTH_NOTE);
        add(SIXTEENTH_NOTE);
        add(DOUBLE_DOTTED_EIGHTH_NOTE);
        add(DOTTED_EIGHTH_NOTE);
        add(EIGHTH_NOTE);
        add(DOUBLE_DOTTED_QUARTER_NOTE);
        add(DOTTED_QUARTER_NOTE);
        add(QUARTER_NOTE);
        add(HALF_NOTE);
        add(DOUBLE_DOTTED_HALF_NOTE);
        add(DOTTED_HALF_NOTE);
        add(WHOLE_NOTE);
    }};

    public Part Mutate(Part p, float probability)
    {
        Collections.sort(rhythms);

        for(int i = 0; i < p.length(); i++)
        {
            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                double currRhythm = p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).getRhythmValue();

                // Get a new rhythm value
                int index = BinarySearch(rhythms, 0, rhythms.size(), currRhythm);
                int indexToNewRhythm = Math.round(Utilities.Clamp(Utilities.GetRandomRangeNormalDistribution(index, 1.0f), 0 , rhythms.size() - 1));

                p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).setRhythmValue(rhythms.get(indexToNewRhythm));
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
