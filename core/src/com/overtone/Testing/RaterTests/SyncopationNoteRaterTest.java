package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.SyncopationNoteRater;
import jm.JMC;
import jm.constants.Durations;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class SyncopationNoteRaterTest implements JMC
{
    public static void Test()
    {
        SyncopationNoteRater snr = new SyncopationNoteRater();
        System.out.println("Syncopation Note Rater Test");
        System.out.println("------------------------------------------------");

        Part p1 = new Part();
        for(int i = 0; i < 12; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        float value = snr.Rate(new Organism(p1, 0));
        System.out.println("Test 1: No Syncopation Notes. ");
        System.out.print("Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p3 = new Part();
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, HALF_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, HALF_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        value = snr.Rate(new Organism(p3, 0));
        System.out.println("Test 2: Two Syncopation Notes");
        System.out.print("Expected Result: " + (2.0f / 10.f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (2.0f / 10.f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p4 = new Part();
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, WHOLE_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, WHOLE_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));

        value = snr.Rate(new Organism(p4, 0));
        System.out.println("Test 3: Two Syncopation Notes In Different Spots. ");
        System.out.print("Expected Result: " + (2.0f / 6.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (2.0f / 6.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p5 = new Part();

        Phrase chord = new Phrase();
        chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
        Phrase chord2 = new Phrase();
        chord2.addChord(GeneticAlgorithm.CHORDS[0], HALF_NOTE);
        Phrase chord3 = new Phrase();
        chord3.addChord(GeneticAlgorithm.CHORDS[0], WHOLE_NOTE);

        p5.addPhrase(chord);
        p5.addPhrase(chord);
        p5.addPhrase(chord);
        p5.addPhrase(chord2);
        p5.addPhrase(chord);
        p5.addPhrase(chord3);
        p5.addPhrase(chord2);

        value = snr.Rate(new Organism(p5, 0));
        System.out.println("Test 4: Two Syncopation Chords ");
        System.out.print("Expected Result: " + (2.0f / 7.0f)  + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (2.0f / 7.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");

        Part p6 = new Part();
        p6.addPhrase(new Phrase(new Note(REST, DOTTED_HALF_NOTE)));
        p6.addPhrase(new Phrase(new Note(REST, WHOLE_NOTE)));
        p6.addPhrase(new Phrase(new Note(REST, WHOLE_NOTE)));
        p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));

        value = snr.Rate(new Organism(p6, 0));
        System.out.println("Test 5: Test with rests.  ");
        System.out.print("Expected Result: " + 0.5f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.5f ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
    }
}
