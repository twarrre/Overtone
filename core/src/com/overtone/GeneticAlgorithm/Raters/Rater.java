package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;

/**
 * Base class for all raters
 * Created by trevor on 2016-08-01.
 */
public abstract class Rater
{
    public abstract float Rate(Organism p);
}
