package com.overtone.GeneticAlgorithm.Mutators;
import jm.music.data.Phrase;

/**
 * Mutates the phrase so that the note's pitch will match the note before it.
 * Created by trevor on 2016-08-14.
 */
public class SimplifyMutator extends Mutator
{
    public Phrase Mutate(Phrase p)
    {
        return p;
    }
}
