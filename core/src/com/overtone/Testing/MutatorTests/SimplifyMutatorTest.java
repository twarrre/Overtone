package com.overtone.Testing.MutatorTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Mutators.SimplifyMutator;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class SimplifyMutatorTest implements JMC
{
    public static void Test()
    {
        SimplifyMutator sm = new SimplifyMutator();

        Part p = new Part();
        for(int i = 0; i < 10; i++)
            p.addPhrase(new Phrase(new Note(C4 + i, QUARTER_NOTE)));

        Part m1 = sm.Mutate(p.copy(), 1);
        Part m2 = sm.Mutate(p.copy(), 0.8f);
        Part m3 = sm.Mutate(p.copy(), 0.6f);
        Part m4 = sm.Mutate(p.copy(), 0.4f);
        Part m5 = sm.Mutate(p.copy(), 0.2f);
        Part m6 = sm.Mutate(p.copy(), 0.0f);

        System.out.println("Mutation 1, 100% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original         Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m1.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        System.out.println("Mutation 2, 80% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m2.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        System.out.println("Mutation 3, 60% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m3.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        System.out.println("Mutation 4, 40% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m4.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        System.out.println("Mutation 5, 20% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m5.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        System.out.println("Mutation 6, 0% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m6.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        Part pChord = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 ==  0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.chords[0], QUARTER_NOTE);
                pChord.addPhrase(chord);
            }
            else
            {
                pChord.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            }
        }

        Part mChord2 = sm.Mutate(pChord.copy(), 0.8f);
        Part mChord3 = sm.Mutate(pChord.copy(), 0.6f);
        Part mChord4 = sm.Mutate(pChord.copy(), 0.4f);

        System.out.println("Mutation Chord 1, 80% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < pChord.length(); i++)
        {
            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(pChord.getPhrase(i).getNote(j).getPitch() + " ");
            }

            if(pChord.getPhrase(i).length() == 1)
                System.out.print("            ");
            else
                System.out.print("      ");

            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(mChord2.getPhrase(i).getNote(j).getPitch() + " ");
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
                System.out.print(pChord.getPhrase(i).getNote(j).getPitch() + " ");
            }

            if(pChord.getPhrase(i).length() == 1)
                System.out.print("            ");
            else
                System.out.print("      ");

            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(mChord3.getPhrase(i).getNote(j).getPitch() + " ");
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
                System.out.print(pChord.getPhrase(i).getNote(j).getPitch() + " ");
            }

            if(pChord.getPhrase(i).length() == 1)
                System.out.print("            ");
            else
                System.out.print("      ");

            for(int j = 0; j < pChord.getPhrase(i).length(); j++)
            {
                System.out.print(mChord4.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
