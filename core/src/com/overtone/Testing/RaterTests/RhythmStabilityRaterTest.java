package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.DynamicStabilityRater;
import com.overtone.GeneticAlgorithm.Raters.RhythmStabilityRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import org.omg.CORBA.WrongTransactionHolder;

/**
 * Created by trevor on 2016-11-05.
 */
public class RhythmStabilityRaterTest implements JMC
{
    public static void Test()
    {
        RhythmStabilityRater dsr = new RhythmStabilityRater();
        System.out.println("Rhythm Stability Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        }
        float value = dsr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: All notes are the same. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p2.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE + i)));
        }
        value = dsr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes are unique going up. ");
        System.out.print("Expected Result: " + 0.1f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.1f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p22 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p22.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE - i)));
        }
        value = dsr.Rate(new Organism(p22, 0));
        System.out.println("Test 3: All notes are unique going down. ");
        System.out.print("Expected Result: " + 0.1f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.1f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {

            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(C4, HALF_NOTE_TRIPLET)));
        }
        value = dsr.Rate(new Organism(p3, 0));
        System.out.println("Test 4: Half are one note, and half are another note. ");
        System.out.print("Expected Result: " + 0.9f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.9f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p7 = new Part();
        for(int i = 0; i < 10; i++)
        {

            if (i < 5)
                p7.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p7.addPhrase(new Phrase(new Note(C4, WHOLE_NOTE)));
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
