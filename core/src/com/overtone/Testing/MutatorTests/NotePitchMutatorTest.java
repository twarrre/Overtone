package com.overtone.Testing.MutatorTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Mutators.NotePitchMutator;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class NotePitchMutatorTest implements JMC
{
    public static void Test()
    {
        NotePitchMutator npm = new NotePitchMutator();

        Part p = new Part();
        for(int i = 0; i < 10; i++)
            p.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));

        Part m1 = npm.Mutate(p.copy(), 1);
        Part m2 = npm.Mutate(p.copy(), 0.8f);
        Part m3 = npm.Mutate(p.copy(), 0.5f);
        Part m4 = npm.Mutate(p.copy(), 0.25f);
        Part m5 = npm.Mutate(p.copy(), 0.2f);
        Part m6 = npm.Mutate(p.copy(), 0.0f);

        System.out.println("Note Pitch Mutation 1, 100% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original         Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m1.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        /*System.out.println("Mutation 2, 80% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m2.getPhrase(i).getNote(0).getPitch());
        System.out.println();*/

        System.out.println("Note Pitch Mutation 2, 50% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m3.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        System.out.println("Note Pitch Mutation 3, 25% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m4.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        /*System.out.println("Mutation 5, 20% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m5.getPhrase(i).getNote(0).getPitch());
        System.out.println();*/

        System.out.println("Note Pitch Mutation 4, 0% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original        Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getPitch() + "             " + m6.getPhrase(i).getNote(0).getPitch());
        System.out.println();

        /*Part pChord = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 ==  0)
            {
                Phrase chord = new Phrase();
                chord.addChord(GeneticAlgorithm.CHORDS[0], QUARTER_NOTE);
                pChord.addPhrase(chord);
            }
            else
            {
                pChord.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            }
        }

        Part mChord2 = npm.Mutate(pChord.copy(), 0.8f);
        Part mChord3 = npm.Mutate(pChord.copy(), 0.6f);
        Part mChord4 = npm.Mutate(pChord.copy(), 0.4f);

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

        Part p3 = new Part();
        for(int i = 0; i < 10; i++)
        {
            if(i % 2 == 0)
                p3.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            else
                p3.addPhrase(new Phrase(new Note(REST, QUARTER_NOTE)));
        }

        Part mr1 = npm.Mutate(p3.copy(), 1);

        System.out.println("Mutation Rest 1, 100% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original         Mutated");
        for(int i = 0; i < p.length(); i++)
            System.out.println(p3.getPhrase(i).getNote(0).getPitch() + "             " + mr1.getPhrase(i).getNote(0).getPitch());
        System.out.println();*/
    }
}
