package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.PitchDirectionRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class PitchDirectionRaterTest implements JMC
{
    public static void Test()
    {
        PitchDirectionRater pdr = new PitchDirectionRater();

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        float value = pdr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All notes are the same. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
            p2.addPhrase(new Phrase(new Note(C4 + i, QUARTER_NOTE)));
        value = pdr.Rate(new Organism(p2, 0));
        System.out.print("Test 2: All notes are unique. ");
        System.out.println(value == (9.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
        }
        value = pdr.Rate(new Organism(p3, 0));
        System.out.print("Test 3: Half are one note, and half are another note. ");
        System.out.println(value == 0.5f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p4 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 3 == 0)
                p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p4.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
        }
        value = pdr.Rate(new Organism(p4, 0));
        System.out.print("Test 4: Two different notes at different intervals.  ");
        System.out.println(value == (3.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p5 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 3 == 0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.chords[0], QUARTER_NOTE);
                p5.addPhrase(chord);
            }
            else if (i % 2 == 0)
                p5.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p5.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
        }
        value = pdr.Rate(new Organism(p5, 0));
        System.out.print("Test 5: Chords are mixed in and skipped. ");
        System.out.println(value == (1.0f / 6.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p6 = new Part();
        for(int i = 0; i < 10; i++)
        {
            Phrase chord = new Phrase();
            chord.addChord(GeneticAlgorithm.chords[0], QUARTER_NOTE);
            p6.addPhrase(chord);
        }
        value = pdr.Rate(new Organism(p6, 0));
        System.out.print("Test 6: All are chords. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p7 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p7.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        value = pdr.Rate(new Organism(p7, 0));
        System.out.print("Test 7: All are rest. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p8 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p8.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
            else if(i % 3 == 0)
                p8.addPhrase(new Phrase(new Note(C5, QUARTER_NOTE)));
            else
                p8.addPhrase(new Phrase(new Note(C3, QUARTER_NOTE)));
        }
        value = pdr.Rate(new Organism(p8, 0));
        System.out.print("Test 8: Mix of notes and rests. ");
        System.out.println(value == 2.0f / 5.0f ? "Expected result. OK" : "Not expected result. FAIL");
    }
}
