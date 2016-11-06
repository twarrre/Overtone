package com.overtone.Testing;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.Overtone;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by trevor on 2016-11-05.
 */
public class ElitismTest implements JMC
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
        Organism[] elite  = ga.Elitism(o);

        System.out.println("Elitism Test Sample Population");
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < population.size(); ++i)
        {
            System.out.println("Sample: " + i + ", Overall Rating: " + population.get(i).GetOverallRating());
        }
        System.out.println("Elitism Test Sample Population Complete");
        System.out.println("---------------------------------------------------------------");

        System.out.println("Elitism Test Elites From Sample Population, Number of Elites: " + Overtone.NumberOfElites);
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < elite.length; ++i)
        {
            System.out.println("Elite: " + i + ", Overall Rating: " + elite[i].GetOverallRating());
        }
        System.out.println("Elitism Test Elites From Sample Population");
        System.out.println("---------------------------------------------------------------");
    }
}
