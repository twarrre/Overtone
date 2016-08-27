package com.overtone.Testing.MutatorTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Mutators.RhythmMutator;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-27.
 */
public class RhythmMutatorTest implements JMC
{
    public static void Test()
    {
        RhythmMutator rm = new RhythmMutator();

        Part p = new Part();
        for(int i = 0; i < 10; i++)
            p.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));

        Part m1 = rm.Mutate(p.copy(), 1);
        Part m2 = rm.Mutate(p.copy(), 0.8f);
        Part m3 = rm.Mutate(p.copy(), 0.6f);
        Part m4 = rm.Mutate(p.copy(), 0.4f);
        Part m5 = rm.Mutate(p.copy(), 0.2f);
        Part m6 = rm.Mutate(p.copy(), 0.0f);

        System.out.println("Mutation 1, 100% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original         Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getRhythmValue() + "             " + m1.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();

        System.out.println("Mutation 2, 80% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getRhythmValue() + "             " + m2.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();

        System.out.println("Mutation 3, 60% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getRhythmValue() + "             " + m3.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();

        System.out.println("Mutation 4, 40% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getRhythmValue() + "             " + m4.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();

        System.out.println("Mutation 5, 20% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getRhythmValue() + "             " + m5.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();

        System.out.println("Mutation 6, 0% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getRhythmValue() + "             " + m6.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();

        Part pChord = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 ==  0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.chords[0], WHOLE_NOTE);
                pChord.addPhrase(chord);
            }
            else
            {
                pChord.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            }
        }

        Part mChord2 = rm.Mutate(pChord.copy(), 0.8f);
        Part mChord3 = rm.Mutate(pChord.copy(), 0.6f);
        Part mChord4 = rm.Mutate(pChord.copy(), 0.4f);

        System.out.println("Mutation Chord 1, 80% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < pChord.length(); i++)
        {
            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(pChord.getPhrase(i).getNote(j).getRhythmValue() + " ");
            }

            if(pChord.getPhrase(i).length() == 1)
                System.out.print("            ");
            else
                System.out.print("      ");

            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(mChord2.getPhrase(i).getNote(j).getRhythmValue() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 2, 60% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < pChord.length(); i++)
        {
            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(pChord.getPhrase(i).getNote(j).getRhythmValue() + " ");
            }

            if(pChord.getPhrase(i).length() == 1)
                System.out.print("            ");
            else
                System.out.print("      ");

            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(mChord3.getPhrase(i).getNote(j).getRhythmValue() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 3, 40% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < pChord.length(); i++)
        {
            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(pChord.getPhrase(i).getNote(j).getRhythmValue() + " ");
            }

            if(pChord.getPhrase(i).length() == 1)
                System.out.print("            ");
            else
                System.out.print("      ");

            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(mChord4.getPhrase(i).getNote(j).getRhythmValue() + " ");
            }
            System.out.println();
        }
        System.out.println();

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }

        Part mr1 = rm.Mutate(p3.copy(), 1);

        System.out.println("Mutation Rest 1, 100% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original         Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p3.getPhrase(i).getNote(0).getRhythmValue() + "             " + mr1.getPhrase(i).getNote(0).getRhythmValue());
        System.out.println();
    }
}
