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

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // Get how much to change the pitch by
                int pitchDelta = Utilities.GetRandomRangeNormalDistribution(0, OCTAVE / 2.0f);
                for(int j = 0; j < p.getPhrase(i).length(); j++)
                {
                    // Change all of the notes in the phrase by that pitch delta
                    int pitch = p.getPhrase(i).getNote(j).getPitch();
                    p.getPhrase(i).getNote(j).setPitch((int)Utilities.Clamp(pitch + pitchDelta, CN1, G9));
                }
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
