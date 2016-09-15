package com.overtone.GeneticAlgorithm.Mutators;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.JMC;
import jm.music.data.Part;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Mutates the rhy
 * Created by trevor on 2016-08-27.
 */
public class RhythmMutator extends Mutator implements JMC
{
    // Chords cannot be larger than a double dotted quarter note because it cannot be simultaneously be a hold note
    // Therefore we search though the list of valid rhythms and find the index of the availiable note
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
                // Store the current rhythm of the note
                double currRhythm = p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).getRhythmValue();

                // Find the index of the current rhythm in the array of valid rhythm values
                int index = BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), currRhythm);

                // Determine the upper bound index of the rhythm array (all are available for normal notes, about half are available for chords.)
                int range = p.getPhrase(i).length() > 1 ? _indexForLargestRhythmChord : GeneticAlgorithm.RHYTHMS.size() - 1;

                // Use normal distribution to get the index to a new rhythm, clamped between 0 and the range value
                int indexToNewRhythm = Utilities.GetRandomRangeNormalDistribution(index, 2, range, 0.0f, true);

                // Set the rhythm value
                p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).setRhythmValue(GeneticAlgorithm.RHYTHMS.get(indexToNewRhythm));
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }

    /**
     * Binary search to search though the array of rhythm values
     * @param list The list to search through
     * @param low The low value
     * @param high The high value
     * @param search The value needed to be found
     * @return the index to the found rhythm
     */
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
