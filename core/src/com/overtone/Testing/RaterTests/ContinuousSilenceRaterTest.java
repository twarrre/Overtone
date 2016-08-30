package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.ContinuousSilenceRater;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class ContinuousSilenceRaterTest implements JMC
{
    public static void Test()
    {
        ContinuousSilenceRater csr = new ContinuousSilenceRater();

        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }
        float value = csr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All are rest. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p2.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
            else if(i % 3 == 0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0], WHOLE_NOTE);
                p2.addPhrase(chord);
            }
            else
                p2.addPhrase(new Phrase(new Note(C3, QUARTER_NOTE)));
        }
        value = csr.Rate(new Organism(p2, 0));
        System.out.print("Test 2: Mix of notes and rests. ");
        System.out.println(value ==  1.0f - ((5.0f * QUARTER_NOTE) / ((8.0f * QUARTER_NOTE) + (2.0f * WHOLE_NOTE))) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
            p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        value = csr.Rate(new Organism(p3, 0));
        System.out.print("Test 3: All notes are the same. ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");
    }
}
