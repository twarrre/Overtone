package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.UniqueNoteRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class UniqueNoteRaterTest implements JMC
{
    public static void Test()
    {
        UniqueNoteRater unr = new UniqueNoteRater();
        System.out.println("Unique Note Pitch Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        float value = unr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: All notes are the same. ");
        System.out.print("Expected Result: " + (1.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (1.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
            p2.addPhrase(new Phrase(new Note(C4 + i, QUARTER_NOTE)));
        value = unr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes are unique. ");
        System.out.print("Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
        }
        value = unr.Rate(new Organism(p3, 0));
        System.out.println("Test 3: Half are one note, and half are another note. ");
        System.out.print("Expected Result: " + (2.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (2.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p4 = new Part();
        for(int i = 0; i < 10; i++)
        {
            Phrase chord = new Phrase();
            chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
            p4.addPhrase(chord);
        }
        value = unr.Rate(new Organism(p4, 0));
        System.out.println("Test 4: All the same chord. ");
        System.out.print("Expected Result: " + (3.0f / 30.0f)  + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (3.0f / 30.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p5 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
                p5.addPhrase(chord);
            }
            else
                p5.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
        }
        value = unr.Rate(new Organism(p5, 0));
        System.out.println("Test 5: Half one note, and half are a chord. ");
        System.out.print("Expected Result: " + (4.0f / 20.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (4.0f / 20.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = unr.Rate(new Organism(p6, 0));
        System.out.println("Test 6: All are rest. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p7 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p7.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
            else if(i % 3 == 0)
                p7.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
            else
                p7.addPhrase(new Phrase(new Note(C3, QUARTER_NOTE)));
        }
        value = unr.Rate(new Organism(p7, 0));
        System.out.println("Test 7: Mix of notes and rests. ");
        System.out.print("Expected Result: " + (2.0f / 5.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (2.0f / 5.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}
