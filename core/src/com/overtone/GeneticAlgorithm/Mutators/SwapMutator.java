package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.Utilities;
import jm.music.data.Part;
import java.util.Random;

/**
 * Mutator that swaps portions of the phrase and changes the order.
 * Created by trevor on 2016-08-14.
 */
public class SwapMutator extends Mutator
{
    public Part Mutate(Part p, float probability)
    {
        Random rand = new Random(System.nanoTime());
        int numSwaps = rand.nextInt(10) + 5;

        for(int k = 0; k < numSwaps; k++)
        {
            int ind = Utilities.GetRandom(0, 1, probability);
            if(ind == 0)
            {
                int swapLength = rand.nextInt((int) Math.floor((p.length() - 1.0f) / 2.0f));

                if (swapLength <= 0)
                    swapLength++;
                if (swapLength >= p.length() / 2)
                    swapLength--;

                int index1 = rand.nextInt(p.length() - (swapLength * 2));
                int index2 = rand.nextInt(((p.length() - swapLength) - (index1 + swapLength)) + 1) + (index1 + swapLength);

                Part swap1 = new Part();
                for (int i = index1; i < index1 + swapLength; i++)
                    swap1.addPhrase(p.getPhrase(i));

                Part swap2 = new Part();
                for (int i = index2; i < index2 + swapLength; i++)
                    swap2.addPhrase(p.getPhrase(i));


                Part swapped = new Part();
                for (int i = 0; i < p.length(); i++) {
                    if (i >= index1 && i < index1 + swap2.length()) {
                        swapped.addPhrase(swap2.getPhrase(i - index1));
                    } else if (i >= index2 && i < index2 + swap1.length()) {
                        swapped.addPhrase(swap1.getPhrase(i - index2));
                    } else {
                        swapped.addPhrase(p.getPhrase(i));
                    }
                }
                p = swapped;
            }
        }

        return p;
    }
}
