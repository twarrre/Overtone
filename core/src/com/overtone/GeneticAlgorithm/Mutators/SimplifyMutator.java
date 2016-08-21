package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.Utilities;
import jm.music.data.Part;

/**
 * Mutates the phrase so that the note's pitch will match the note before it or after it.
 * Created by trevor on 2016-08-14.
 */
public class SimplifyMutator extends Mutator
{
    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // TODO: Fix for chords
                // Randomly choose the note ahead of it, or behind it
                int leftOrRight = Utilities.GetRandom(-1, 1, 0.5f);

                int noteToCopy = i + leftOrRight;
                if(i == 0 && leftOrRight == -1)
                    noteToCopy = p.length() - 1;
                else if(i == p.length() - 1 && leftOrRight == 1)
                    noteToCopy = 0;

                p.getPhrase(i).getNote(0).setPitch(p.getPhrase(noteToCopy).getNote(0).getPitch());
            }
        }
        return p;
    }
}
