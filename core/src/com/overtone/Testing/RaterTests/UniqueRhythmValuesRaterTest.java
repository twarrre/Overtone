package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.UniqueNoteRater;
import com.overtone.GeneticAlgorithm.Raters.UniqueRhythmValuesRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class UniqueRhythmValuesRaterTest implements JMC
{
    public static void Test()
    {
        double[] rhythms = new double[] {QUARTER_NOTE, WHOLE_NOTE, HALF_NOTE, EIGHTH_NOTE, SIXTEENTH_NOTE, DOTTED_HALF_NOTE, DOTTED_QUARTER_NOTE, DOTTED_EIGHTH_NOTE, THIRTYSECOND_NOTE, THIRTYSECOND_NOTE_TRIPLET};
        UniqueRhythmValuesRater urr = new UniqueRhythmValuesRater();
        System.out.println("Unique Rhythm Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
            p1.addPhrase(new Phrase(new Note(C4, rhythms[0])));
        float value = urr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: All rhythms are the same. ");
        System.out.print("Expected Result: " + (1.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (1.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
            p2.addPhrase(new Phrase(new Note(C4,  rhythms[i])));
        value = urr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes rhythms are unique. ");
        System.out.print("Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4,  rhythms[0])));
            else
                p3.addPhrase(new Phrase(new Note(C5,  rhythms[1])));
        }
        value = urr.Rate(new Organism(p3, 0));
        System.out.println("Test 3: Half are one note, and half are another note. ");
        System.out.print("Expected Result: " + (2.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (2.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p4 = new Part();
        for(int i = 0; i < 10; i++)
        {
            Phrase chord = new Phrase();
            chord.addChord(GeneticAlgorithm.CHORDS[0],  rhythms[0]);
            p4.addPhrase(chord);
        }
        value = urr.Rate(new Organism(p4, 0));
        System.out.println("Test 4: All the same chord. ");
        System.out.print("Expected Result: " + (3.0f / 30.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (3.0f / 30.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p5 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0],  rhythms[0]);
                p5.addPhrase(chord);
            }
            else
                p5.addPhrase(new Phrase(new Note(C5,  rhythms[1])));
        }
        value = urr.Rate(new Organism(p5, 0));
        System.out.println("Test 5: Half one note, and half are a chord. ");
        System.out.print("Expected Result: " + (4.0f / 20.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (4.0f / 20.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}
