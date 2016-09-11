package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
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
            if(p.getPhrase(i).getNote(0).isRest())
                continue;

            if(p.getPhrase(i).length() > 1)
                continue;

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // Get how much to change the pitch by
                int pitchDelta = Utilities.GetRandomRangeNormalDistribution(0, OCTAVE / 2.0f, OCTAVE, -OCTAVE, false);
                int pitch = p.getPhrase(i).getNote(0).getPitch();
                p.getPhrase(i).getNote(0).setPitch((int)Utilities.Clamp(pitch + pitchDelta, GeneticAlgorithm.LOW_PITCH, GeneticAlgorithm.HIGH_PITCH));
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
