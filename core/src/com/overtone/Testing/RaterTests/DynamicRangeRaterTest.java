package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.DynamicRangeRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-06.
 */
public class DynamicRangeRaterTest implements JMC
{
    public static void Test()
    {
        DynamicRangeRater pr = new DynamicRangeRater();

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p1.getPhrase(i).getNote(0).setDynamic(85);
        }

        float value = pr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All dynamics are the same. ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p2.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p2.getPhrase(i).getNote(0).setDynamic(85 + i);
        }

        value = pr.Rate(new Organism(p2, 0));
        System.out.print("Test 2: All notes are unique. ");
        System.out.println(value == (85.0f / 94.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p3.addPhrase(new Phrase(new Note(C7, QUARTER_NOTE)));
            if(i % 2 == 0)
                p3.getPhrase(i).getNote(0).setDynamic(85);
            else
                p3.getPhrase(i).getNote(0).setDynamic(75);
        }
        value = pr.Rate(new Organism(p3, 0));
        System.out.print("Test 3: Half are one note, and half are another note. ");
        System.out.println(value == (75.0f / 85.0f) ? "Expected result. OK" : "Not expected result. FAIL");


        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = pr.Rate(new Organism(p6, 0));
        System.out.print("Test 4: All are rest. ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

    }
}
