package com.overtone.Testing;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.JMC;

/**
 * Created by trevor on 2016-11-05.
 */
public class CrossoverTest implements JMC
{
    public void Test()
    {
        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE, 4)));
        Organism o1 = new Organism(p1, 5);

        Part p2 = new Part();
        for(int i = 0; i < 10; i++)
            p2.addPhrase(new Phrase(new Note(C8, HALF_NOTE, 5)));
        Organism o2 = new Organism(p2, 5);

        GeneticAlgorithm ga = new GeneticAlgorithm(true);
        Organism[] children = ga.Crossover(o1, o2);

        System.out.println("Crossover Test Started (Displaying Note Pitches)");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Parent 1        Parent 2        Child 1         Child 2");
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < 10; ++i)
        {
            System.out.println(o1.GetTrack().getPhrase(i).getNote(0).getPitch() + "             " + o2.GetTrack().getPhrase(i).getNote(0).getPitch() + "               " + children[0].GetTrack().getPhrase(i).getNote(0).getPitch()+ "             " + children[1].GetTrack().getPhrase(i).getNote(0).getPitch());
        }
        System.out.println("Crossover Pitch Test Completed");
        System.out.println("---------------------------------------------------------------");

        System.out.println("Crossover Test Started (Displaying Note Dynamics)");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Parent 1        Parent 2        Child 1         Child 2");
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < 10; ++i)
        {
            System.out.println(o1.GetTrack().getPhrase(i).getNote(0).getDynamic() + "             " + o2.GetTrack().getPhrase(i).getNote(0).getDynamic() + "               " + children[0].GetTrack().getPhrase(i).getNote(0).getDynamic()+ "             " + children[1].GetTrack().getPhrase(i).getNote(0).getDynamic());
        }
        System.out.println("Crossover Dynamics Test Completed");
        System.out.println("---------------------------------------------------------------");

        System.out.println("Crossover Test Started (Displaying Note Rhythm Values)");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Parent 1        Parent 2        Child 1         Child 2");
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < 10; ++i)
        {
            System.out.println(o1.GetTrack().getPhrase(i).getNote(0).getRhythmValue() + "             " + o2.GetTrack().getPhrase(i).getNote(0).getRhythmValue() + "               " + children[0].GetTrack().getPhrase(i).getNote(0).getRhythmValue()+ "             " + children[1].GetTrack().getPhrase(i).getNote(0).getRhythmValue());
        }
        System.out.println("Crossover Rhythm Test Completed");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Crossover Test Completed");
    }
}
