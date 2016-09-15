package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.music.data.Part;
import jm.JMC;

/**
 * Mutates the pitch of notes slightly
 * Created by trevor on 2016-08-14.
 */
public class NotePitchMutator extends Mutator implements JMC
{
    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            // Skip rests because they do not have pitches
            if(p.getPhrase(i).getNote(0).isRest())
                continue;

            // Skip chords
            if(p.getPhrase(i).length() > 1)
                continue;

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // Get how much to change the pitch by with in an octave in either direction
                int pitchDelta = Utilities.GetRandomRangeNormalDistribution(0, GeneticAlgorithm.OCTAVE / 2.0f, GeneticAlgorithm.OCTAVE, -GeneticAlgorithm.OCTAVE, false);
                int pitch = p.getPhrase(i).getNote(0).getPitch();
                p.getPhrase(i).getNote(0).setPitch((int)Utilities.Clamp(pitch + pitchDelta, GeneticAlgorithm.LOW_PITCH, GeneticAlgorithm.HIGH_PITCH));
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
