package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.DirectionStabilityRater;
import com.overtone.GeneticAlgorithm.Raters.DynamicStabilityRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-06.
 */
public class DynamicStabilityRaterTest implements JMC
{
    public static void Test()
    {
        DynamicStabilityRater dsr = new DynamicStabilityRater();
        System.out.println("Dynamic Stability Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p1.getPhrase(i).getNote(0).setDynamic(56);
        }
        float value = dsr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: All notes are the same. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p2.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p2.getPhrase(i).getNote(0).setDynamic(56 + i);
        }
        value = dsr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes are unique going up. ");
        System.out.print("Expected Result: " + 0.1f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.1f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p22 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p22.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p22.getPhrase(i).getNote(0).setDynamic(56 - i);
        }
        value = dsr.Rate(new Organism(p22, 0));
        System.out.println("Test 3: All notes are unique going down. ");
        System.out.print("Expected Result: " + 0.1f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.1f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            if(i % 2 == 0)
                p3.getPhrase(i).getNote(0).setDynamic(87);
            else
                p3.getPhrase(i).getNote(0).setDynamic(43);
        }
        value = dsr.Rate(new Organism(p3, 0));
        System.out.println("Test 4: Half are one note, and half are another note. ");
        System.out.print("Expected Result: " + 0.9f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.9f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p7 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p7.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            if (i < 5)
                p7.getPhrase(i).getNote(0).setDynamic(82);
            else
                p7.getPhrase(i).getNote(0).setDynamic(120);
        }
        value = dsr.Rate(new Organism(p7, 0));
        System.out.println("Test 5: Half one note, half another note. ");
        System.out.print("Expected Result: " + 0.1f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.1f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p8 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p8.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = dsr.Rate(new Organism(p8, 0));
        System.out.println("Test 6: All are rest. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}
