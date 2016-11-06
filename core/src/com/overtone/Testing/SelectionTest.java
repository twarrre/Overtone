package com.overtone.Testing;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.Pair;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by trevor on 2016-11-05.
 */
public class SelectionTest implements JMC
{
    public void Test()
    {
        Random r = new Random(System.nanoTime());
        ArrayList<Organism> population = new ArrayList<Organism>();
        for(int i = 0; i < 10; ++i)
        {
            Part p1 = new Part();
            for(int j = 0; j < 10; j++)
                p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE, 4)));
            Organism o1 = new Organism(p1, 5);
            o1.SetOverallRating(r.nextFloat());
            population.add(o1);
        }

        Collections.shuffle(population, r);
        Organism[] o = new Organism[population.size()];
        o = population.toArray(o);

        GeneticAlgorithm ga = new GeneticAlgorithm(true);

        float[] probabilities = new float[o.length];
        for(int i = 0; i < o.length; i++)
            probabilities[i] = o[i].GetOverallRating();

        ArrayList<Integer> values = new ArrayList<>();

        for (int i = 0; i < 25; ++i)
        {
            // Find the index of two parents in the parents array to crossover
            int p1 = ga.RouletteSelection(probabilities);
            values.add(p1);
        }

        Arrays.sort(probabilities);
        System.out.println("Population Probabilities (Has Been sorted)");
        System.out.println("----------------------------------");
        for(int i = 0; i < probabilities.length; i++)
        {
            System.out.println(probabilities[i]);
        }
        System.out.println();

        Collections.sort(values);
        System.out.println("Selected Probabilities (Has Been Sorted)");
        System.out.println("----------------------------------");
        for(int i = 0; i < values.size(); ++i)
        {
            System.out.println(probabilities[values.get(i)]);
        }
    }
}
