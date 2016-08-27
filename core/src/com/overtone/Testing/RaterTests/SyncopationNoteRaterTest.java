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

        Part p1 = new Part();
        for(int i = 0; i < 12; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        float value = snr.Rate(new Organism(p1, 0));
        System.out.print("Test 1: All notes are the same. ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

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
        System.out.print("Test 2: Half are one note, and half are another note. ");
        System.out.println(value == 2.0f / 10.f ? "Expected result. OK" : "Not expected result. FAIL");

        Part p4 = new Part();
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, WHOLE_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, WHOLE_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        p4.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));

        value = snr.Rate(new Organism(p4, 0));
        System.out.print("Test 3: Two different notes at different intervals.  ");
        System.out.println(value == (2.0f / 6.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p5 = new Part();

        Phrase chord = new Phrase();
        chord.addChord(GeneticAlgorithm.chords[0], QUARTER_NOTE);
        Phrase chord2 = new Phrase();
        chord2.addChord(GeneticAlgorithm.chords[0], HALF_NOTE);
        Phrase chord3 = new Phrase();
        chord3.addChord(GeneticAlgorithm.chords[0], WHOLE_NOTE);

        p5.addPhrase(chord);
        p5.addPhrase(chord);
        p5.addPhrase(chord);
        p5.addPhrase(chord2);
        p5.addPhrase(chord);
        p5.addPhrase(chord3);
        p5.addPhrase(chord2);

        value = snr.Rate(new Organism(p5, 0));
        System.out.print("Test 4: Test with chords instead of notes.  ");
        System.out.println(value == (2.0f / 7.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        Part p6 = new Part();
        p6.addPhrase(new Phrase(new Note(REST, DOTTED_HALF_NOTE)));
        p6.addPhrase(new Phrase(new Note(REST, WHOLE_NOTE)));
        p6.addPhrase(new Phrase(new Note(REST, WHOLE_NOTE)));
        p6.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));

        value = snr.Rate(new Organism(p6, 0));
        System.out.print("Test 5: Test with rests.  ");
        System.out.println(value == 0.5f ? "Expected result. OK" : "Not expected result. FAIL");
    }
}
