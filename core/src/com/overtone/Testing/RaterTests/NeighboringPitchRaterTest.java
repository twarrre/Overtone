package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.NeighboringPitchRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class NeighboringPitchRaterTest implements JMC
{
    public static void Test()
    {
        NeighboringPitchRater npr = new NeighboringPitchRater();
        System.out.println("Neighboring Pitch Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        float value = npr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: All notes are the same. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
            p2.addPhrase(new Phrase(new Note(C1 + i, QUARTER_NOTE)));
        value = npr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes are unique going up. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(C8, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p3, 0));
        System.out.println("Test 3: Half are one note, and half are another note that is over an octave higher. ");
        System.out.print("Expected Result: " + 0.9f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.9f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p4 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p4.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p4, 0));
        System.out.println("Test 4: Two different notes at different intervals.  ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p5 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 3 == 0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
                p5.addPhrase(chord);
            }
            else if (i % 2 == 0)
                p5.addPhrase(new Phrase(new Note(C3, QUARTER_NOTE)));
            else
                p5.addPhrase(new Phrase(new Note(C7, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p5, 0));
        System.out.println("Test 5: Chords are mixed in and skipped. ");
        System.out.print("Expected Result: " + 0.5f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.5f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            Phrase chord = new Phrase();
            chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
            p6.addPhrase(chord);
        }
        value = npr.Rate(new Organism(p6, 0));
        System.out.println("Test 6: All are chords. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p7 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i == 5)
                p7.addPhrase(new Phrase(new Note(C9, QUARTER_NOTE)));
            else
                p7.addPhrase(new Phrase(new Note(C1, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p7, 0));
        System.out.println("Test 7: One very high note. ");
        System.out.print("Expected Result: " + 0.2f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.2f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p8 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p8.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p8, 0));
        System.out.println("Test 8: All are rest. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p9 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p9.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
            else if(i % 3 == 0)
                p9.addPhrase(new Phrase(new Note(C7, QUARTER_NOTE)));
            else
                p9.addPhrase(new Phrase(new Note(C3, QUARTER_NOTE)));
        }
        value = npr.Rate(new Organism(p9, 0));
        System.out.println("Test 9: Mix of notes and rests. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}
