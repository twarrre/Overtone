package com.overtone.GeneticAlgorithm.Mutators;
import jm.music.data.Phrase;
import java.util.Random;

/**
 * Mutator that swaps portions of the phrase and changes the order.
 * Created by trevor on 2016-08-14.
 */
public class SwapMutator extends Mutator
{
    public Phrase Mutate(Phrase p, float probability)
    {
        Random rand = new Random(System.nanoTime());
        int numSwaps = rand.nextInt(10) + 5;

        for(int k = 0; k < numSwaps; k++)
        {
            int swapLength = rand.nextInt((int)Math.floor((p.length() - 1.0f) / 2.0f));

            if(swapLength <= 0)
                swapLength++;
            if(swapLength >= p.length() / 2)
                swapLength--;

            int index1 = rand.nextInt(p.length() - (swapLength * 2));
            int index2 = rand.nextInt(((p.length() - swapLength) - (index1 + swapLength)) + 1) + (index1 + swapLength);

            Phrase swap1 = new Phrase();
            for(int i = index1; i < index1 + swapLength; i++)
                swap1.addNote(p.getNote(i));

            Phrase swap2 = new Phrase();
            for(int i = index2; i < index2 + swapLength; i++)
                swap2.addNote(p.getNote(i));

            for(int i = 0, j = index1; i < swap2.length(); i++, j++)
                p.setNote(swap2.getNote(i), j);

            for(int i = 0, j = index2; i < swap1.length(); i++, j++)
                p.setNote(swap1.getNote(i), j);
        }

        return p;
    }
}
