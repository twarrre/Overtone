package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.RhythmRangeRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-06.
 */
public class RhythmRangeRaterTest implements JMC
{
    public static void Test()
    {
        RhythmRangeRater pr = new RhythmRangeRater();

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, GeneticAlgorithm.RHYTHMS.get(1))));
        }
        float value = pr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All rhythm are the same. ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p2.addPhrase(new Phrase(new Note(C4, GeneticAlgorithm.RHYTHMS.get(i))));
        }
        value = pr.Rate(new Organism(p2, 0));
        System.out.print("Test 2: All notes are unique. ");
        float test = (float)(GeneticAlgorithm.RHYTHMS.get(0) / GeneticAlgorithm.RHYTHMS.get(9));
        System.out.println((value == test) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C1, GeneticAlgorithm.RHYTHMS.get(0))));
            else
                p3.addPhrase(new Phrase(new Note(C7, GeneticAlgorithm.RHYTHMS.get(2))));
        }
        value = pr.Rate(new Organism(p3, 0));
        System.out.print("Test 3: Half are one note, and half are another note. ");
        System.out.println(value == (GeneticAlgorithm.RHYTHMS.get(0) / GeneticAlgorithm.RHYTHMS.get(2)) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = pr.Rate(new Organism(p6, 0));
        System.out.print("Test 6: All are rest. ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");
    }
}
