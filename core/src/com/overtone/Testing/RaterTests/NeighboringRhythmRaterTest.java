package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.NeighboringRhythmRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-06.
 */
public class NeighboringRhythmRaterTest implements JMC
{
    public static void Test()
    {
        NeighboringRhythmRater npr = new NeighboringRhythmRater();

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        }
        float value = npr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All rhythms are the same. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p2.addPhrase(new Phrase(new Note(C1, GeneticAlgorithm.RHYTHMS.get(i))));
        }
        value = npr.Rate(new Organism(p2, 0));
        System.out.print("Test 2: All notes are unique going up. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, GeneticAlgorithm.RHYTHMS.get(0))));
            else
                p3.addPhrase(new Phrase(new Note(C4, GeneticAlgorithm.RHYTHMS.get(10))));
        }
        value = npr.Rate(new Organism(p3, 0));
        System.out.print("Test 3: Half are one note, and half are another note that is over an octave higher. ");
        System.out.println(value == 0.9f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p7 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i == 5)
                p7.addPhrase(new Phrase(new Note(C9,  GeneticAlgorithm.RHYTHMS.get(1))));
            else
                p7.addPhrase(new Phrase(new Note(C9,  GeneticAlgorithm.RHYTHMS.get(10))));
        }
        value = npr.Rate(new Organism(p7, 0));
        System.out.print("Test 5: One very high note. ");
        System.out.println(value == 0.2f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p8 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p8.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p8, 0));
        System.out.print("Test 8: All are rest. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
    }
}