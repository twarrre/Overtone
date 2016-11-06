package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.ChordRatioRater;
import com.overtone.GeneticAlgorithm.Raters.ContinuousSilenceRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-11-05.
 */
public class ChordRatioRaterTest implements JMC
{
    public void Test()
    {
        ChordRatioRater csr = new ChordRatioRater();
        System.out.println("Chord Ratio Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        float value = csr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: No notes are Chords. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            Phrase chord = new Phrase();
            chord.addChord(GeneticAlgorithm.CHORDS[0], WHOLE_NOTE);
            p2.addPhrase(chord);
        }
        value = csr.Rate(new Organism(p2, 0));
        System.out.println("Test 2: All notes are chords. ");
        System.out.print("Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value ==  1.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if (i % 2 == 0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0], WHOLE_NOTE);
                p3.addPhrase(chord);
            }
            else
            {
                p3.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
            }
        }
        value = csr.Rate(new Organism(p3, 0));
        System.out.println("Test 3: Half notes are chords. ");
        System.out.print("Expected Result: " + 0.5f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value ==  0.5f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p4 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if (i == 0 || i == 4 || i == 7)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0], WHOLE_NOTE);
                p4.addPhrase(chord);
            }
            else
            {
                p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            }
        }
        value = csr.Rate(new Organism(p4, 0));
        System.out.println("Test 4: 30% are are chords. ");
        System.out.print("Expected Result: " + (3.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value ==  (3.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}

