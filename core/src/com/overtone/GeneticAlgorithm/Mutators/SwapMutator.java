package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.Utilities;
import jm.music.data.Phrase;

/**
 * Mutator that swaps portions of the phrase and changes the order.
 * Created by trevor on 2016-08-14.
 */
public class SwapMutator extends Mutator
{
    public Phrase Mutate(Phrase p, float probability)
    {
        Phrase mutation = new Phrase();
        for(int i = 0; i < p.length(); i++)
        {
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {

            }
        }
        return p;
    }
}
