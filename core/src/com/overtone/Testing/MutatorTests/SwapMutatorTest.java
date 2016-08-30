package com.overtone.Testing.MutatorTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Mutators.SwapMutator;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-26.
 */
public class SwapMutatorTest implements JMC
{
    public static void Test()
    {
        SwapMutator sm = new SwapMutator();

        Part pChord = new Part();
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

        Part mChord1 = sm.Mutate(pChord.copy(), 1.0f);
        Part mChord2 = sm.Mutate(pChord.copy(), 0.8f);
        Part mChord3 = sm.Mutate(pChord.copy(), 0.6f);
        Part mChord4 = sm.Mutate(pChord.copy(), 0.4f);
        Part mChord5 = sm.Mutate(pChord.copy(), 0.2f);
        Part mChord6 = sm.Mutate(pChord.copy(), 0.0f);

        System.out.println("Mutation Chord 1, 100% note mutation probability");
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

            for(int j = 0; j < mChord1.getPhrase(i).length(); j++)
            {
                System.out.print(mChord1.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 2, 80% note mutation probability");
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

            for(int j = 0; j < mChord2.getPhrase(i).length(); j++)
            {
                System.out.print(mChord2.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 3, 60% note mutation probability");
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

            for(int j = 0; j < mChord3.getPhrase(i).length(); j++)
            {
                System.out.print(mChord3.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 4, 40% note mutation probability");
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

            for(int j = 0; j < mChord4.getPhrase(i).length(); j++)
            {
                System.out.print(mChord4.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 5, 20% note mutation probability");
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

            for(int j = 0; j < mChord5.getPhrase(i).length(); j++)
            {
                System.out.print(mChord5.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Mutation Chord 6, 0% note mutation probability");
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

            for(int j = 0; j < mChord6.getPhrase(i).length(); j++)
            {
                System.out.print(mChord6.getPhrase(i).getNote(j).getPitch() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
