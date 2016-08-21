package com.overtone.GeneticAlgorithm.Mutators;

import jm.music.data.Part;

/**
 * Base class for all mutators
 * Created by trevor on 2016-08-12.
 */
public abstract class Mutator
{
    public abstract Part Mutate(Part p, float probability);
}
