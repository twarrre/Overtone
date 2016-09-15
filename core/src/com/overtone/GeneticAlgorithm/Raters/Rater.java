package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;

/**
 * Base class for all raters
 * Created by trevor on 2016-08-01.
 */
public abstract class Rater
{
    /**
     * Rates an organism's phrase.
     * @param p The organism to rate
     * @return the rating of the phrase.
     */
    public abstract float Rate(Organism p);
}
