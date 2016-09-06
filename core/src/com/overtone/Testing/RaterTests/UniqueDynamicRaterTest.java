package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.UniqueDynamicRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-06.
 */
public class UniqueDynamicRaterTest implements JMC
{
    public static void Test()
    {
        UniqueDynamicRater unr = new UniqueDynamicRater();

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p1.getPhrase(i).getNote(0).setDynamic(87);
        }
        float value = unr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All notes are the same. ");
        System.out.println(value == (1.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p2.addPhrase(new Phrase(new Note(C4 + i, QUARTER_NOTE)));
            p2.getPhrase(i).getNote(0).setDynamic(87 + i);
        }
        value = unr.Rate(new Organism(p2, 0));
        System.out.print("Test 2: All notes are unique. ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            if(i % 2 == 0)
                p3.getPhrase(i).getNote(0).setDynamic(81);
            else
                p3.getPhrase(i).getNote(0).setDynamic(100);
        }
        value = unr.Rate(new Organism(p3, 0));
        System.out.print("Test 3: Half are one note, and half are another note. ");
        System.out.println(value == (2.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = unr.Rate(new Organism(p6, 0));
        System.out.print("Test 4: All are rest. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
    }
}