package com.overtone.GeneticAlgorithm.Mutators;

import com.overtone.GeneticAlgorithm.Organism;

/**
 * Base class for all mutators
 * Created by trevor on 2016-08-12.
 */
public abstract class Mutator
{
    public abstract Organism Mutate(Organism p);
}
