package com.overtone.Testing.MutatorTests;

import com.overtone.GeneticAlgorithm.Mutators.DynamicMutator;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-27.
 */
public class DynamicMutatorTest implements JMC {
    public static void Test()
    {
        DynamicMutator rm = new DynamicMutator();

        Part p = new Part();
        for (int i = 0; i < 10; i++)
            p.addPhrase(new Phrase(new Note(C4, THIRTYSECOND_NOTE)));

        Part m1 = rm.Mutate(p.copy(), 1);
        Part m2 = rm.Mutate(p.copy(), 0.5f);
        Part m3 = rm.Mutate(p.copy(), 0.25f);
        Part m4 = rm.Mutate(p.copy(), 0.0f);
        //Part m5 = rm.Mutate(p.copy(), 0.2f);
        //Part m6 = rm.Mutate(p.copy(), 0.0f);

        System.out.println("Dynamic Mutation 1, 100% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original Dynamic         Mutated Dynamic");
        for (int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getDynamic() + "                        " + m1.getPhrase(i).getNote(0).getDynamic());
        System.out.println();

        System.out.println("Dynamic Mutation 2, 50% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original Dynamic         Mutated Dynamic");
        for (int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getDynamic() + "                        " + m2.getPhrase(i).getNote(0).getDynamic());
        System.out.println();

        System.out.println("Dynamic Mutation 3, 25% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original Dynamic         Mutated Dynamic");
        for (int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getDynamic() + "                        " + m3.getPhrase(i).getNote(0).getDynamic());
        System.out.println();

        System.out.println("Dynamic Mutation 4, 0% note mutation probability");
        System.out.println("--------------------------------------------");
        System.out.println("Original Dynamic         Mutated Dynamic");
        for (int i = 0; i < p.length(); i++)
            System.out.println(p.getPhrase(i).getNote(0).getDynamic() + "                        " + m4.getPhrase(i).getNote(0).getDynamic());
        System.out.println();
    }
}