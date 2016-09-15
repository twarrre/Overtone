package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.music.data.Part;
import java.util.Random;

/**
 * Mutator that swaps portions of the music
 * Created by trevor on 2016-08-14.
 */
public class SwapMutator extends Mutator
{
    // Create a new random number generator
    private Random _random;
    public SwapMutator()
    {
        _random = new Random();
    }

    public Part Mutate(Part p, float probability)
    {
        // Generate a number of swaps between 5 and 10
        int numSwaps = _random.nextInt(10) + 5;

        for(int k = 0; k < numSwaps; k++)
        {
            int ind = Utilities.GetRandom(0, 1, probability);
            if(ind == 0)
            {
                // Generate a number of values to swap
                int swapLength = _random.nextInt((int) Math.floor((p.length() - 1.0f) / 2.0f));

                if (swapLength <= 0)
                    swapLength++;
                if (swapLength >= p.length() / 2)
                    swapLength--;

                // Get the start point for each sections to swap
                int index1 = _random.nextInt(p.length() - (swapLength * 2));
                int index2 = _random.nextInt(((p.length() - swapLength) - (index1 + swapLength)) + 1) + (index1 + swapLength);

                // Create the first part to swap
                Part swap1 = new Part();
                for (int i = index1; i < index1 + swapLength; i++)
                    swap1.addPhrase(p.getPhrase(i).copy());

                // Create the second part to swap
                Part swap2 = new Part();
                for (int i = index2; i < index2 + swapLength; i++)
                    swap2.addPhrase(p.getPhrase(i).copy());

                // Swap the two parts
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

        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
