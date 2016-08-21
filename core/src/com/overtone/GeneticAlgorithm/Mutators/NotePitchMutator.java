package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.Utilities;
import jm.music.data.Part;
import jm.JMC;

/**
 * Mutates the passed in phrase by changing the note's pitch slightly.
 * Created by trevor on 2016-08-14.
 */
public class NotePitchMutator extends Mutator implements JMC
{
    public static final int OCTAVE = 13;

    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // TODO: Fix for chords
                int pitch = p.getPhrase(i).getNote(0).getPitch();
                int newPitch = Utilities.GetRandomRangeNormalDistribution(pitch, OCTAVE / 2.0f);
                p.getPhrase(i).getNote(0).setPitch(newPitch);
            }
        }
        return p;
    }
}
