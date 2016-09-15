package com.overtone.GeneticAlgorithm.Mutators;

import jm.music.data.Part;

/**
 * Base class for all mutators
 * Created by trevor on 2016-08-12.
 */
public abstract class Mutator
{
    /**
     * Mutates a part.
     * @param p The part to be mutated
     * @param probability The probability that a mutation will occur
     * @return The new mutated part
     */
    public abstract Part Mutate(Part p, float probability);
}
