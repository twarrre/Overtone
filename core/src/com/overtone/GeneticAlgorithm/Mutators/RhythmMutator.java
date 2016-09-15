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
    // Therefore we search though the list of valid rhythms and find the index of the available note
    private int _indexForLargestRhythmChord;
    public RhythmMutator()
    {
        Collections.sort(GeneticAlgorithm.RHYTHMS);
        _indexForLargestRhythmChord =  GeneticAlgorithm.RHYTHMS.indexOf(DOUBLE_DOTTED_QUARTER_NOTE);
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
                int index = GeneticAlgorithm.RHYTHMS.indexOf(currRhythm);

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
}
