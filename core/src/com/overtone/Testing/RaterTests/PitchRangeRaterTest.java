package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.PitchRangeRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class PitchRangeRaterTest implements JMC
{
    public static void Test()
    {
        PitchRangeRater pr = new PitchRangeRater();
        System.out.println("Pitch Range Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        float value = pr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: All notes are the same. ");
        System.out.print("Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
            p2.addPhrase(new Phrase(new Note(C4 + i, QUARTER_NOTE)));
        value = pr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes are unique. ");
        System.out.print("Expected Result: " + (60.0f / 69.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (60.0f / 69.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C1, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(C7, QUARTER_NOTE)));
        }
        value = pr.Rate(new Organism(p3, 0));
        System.out.println("Test 3: Half are one note, and half are another note. ");
        System.out.print("Expected Result: " + ((float)C1 / (float)C7) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == ((float)C1 / (float)C7) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p4 = new Part();
        for(int i = 0; i < 10; i++)
        {
            Phrase chord = new Phrase();
            chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
            p4.addPhrase(chord);
        }
        value = pr.Rate(new Organism(p4, 0));
        System.out.println("Test 4: All the same chord. ");
        System.out.print("Expected Result: " + ((float)C3 / (float)G3) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == ((float)C3 / (float)G3) ? "Expected result. OK" : "Not expected result. FAIL");
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
                p5.addPhrase(new Phrase(new Note(C1, QUARTER_NOTE)));
        }
        value = pr.Rate(new Organism(p5, 0));
        System.out.println("Test 5: Half one note, and half are a chord. ");
        System.out.print("Expected Result: " + ((float)C1 / (float)G3) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == ((float)C1 / (float)G3) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = pr.Rate(new Organism(p6, 0));
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
        value = pr.Rate(new Organism(p7, 0));
        System.out.println("Test 7: Mix of notes and rests. ");
        System.out.print("Expected Result: " + ((float)C3 / (float)C5) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (float)C3 / (float)C5 ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}
