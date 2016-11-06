package com.overtone.Testing.MutatorTests;

import com.overtone.GeneticAlgorithm.Mutators.*;
import com.overtone.GeneticAlgorithm.Organism;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.ArrayList;

/**
 * Created by trevor on 2016-11-05.
 */
public class AllMutatorsTest implements JMC
{
    public void Test()
    {
       ArrayList<Mutator> _mutators = new ArrayList<Mutator>();
        _mutators.add(new NotePitchMutator());
        _mutators.add(new SimplifyMutator());
        _mutators.add(new SwapMutator());
        _mutators.add(new RhythmMutator());
        _mutators.add(new DynamicMutator());

        Part p1 = new Part();
        for(int i = 0; i < 5; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
        }
        Organism o = new Organism(p1, 0);
        Organism mutation = new Organism(p1.copy(), 0);

        System.out.println("Mutation Test Started, 100% Probability");
        System.out.println("--------------------------------------------");
        for(int i = 0; i < _mutators.size(); i++)
        {
            if(_mutators.get(i) instanceof NotePitchMutator)
                System.out.println("Note Pitch Mutator Started...");
            else if(_mutators.get(i) instanceof RhythmMutator)
                System.out.println("Rhythm Mutator Started...");
            else if(_mutators.get(i) instanceof SimplifyMutator)
                System.out.println("Simplify Mutator Started...");
            else if(_mutators.get(i) instanceof SwapMutator)
                System.out.println("Swap Mutator Started...");
            else if(_mutators.get(i) instanceof DynamicMutator)
                System.out.println("Dynamic Mutator Started...");

            // Mutate the organism
            mutation.SetTrack(_mutators.get(i).Mutate(mutation.GetTrack(), 1.0f));

            if(_mutators.get(i) instanceof NotePitchMutator)
                System.out.println("Note Pitch Mutator Complete...");
            else if(_mutators.get(i) instanceof RhythmMutator)
                System.out.println("Rhythm Mutator Complete...");
            else if(_mutators.get(i) instanceof SimplifyMutator)
                System.out.println("Simplify Mutator Complete...");
            else if(_mutators.get(i) instanceof SwapMutator)
                System.out.println("Swap Mutator Complete...");
            else if(_mutators.get(i) instanceof DynamicMutator)
                System.out.println("Dynamic Mutator Complete...");
        }

        System.out.println("Sample: 100% (Pitch, Rhythm, Dynamic)");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Original                  Mutated");
        for (int j = 0; j < mutation.GetTrack().size(); ++j)
        {
            for(int k = 0; k < o.GetTrack().getPhrase(j).size(); ++k)
            {
                String p = o.GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? o.GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                System.out.print("P " +  p + ", ");
            }
            System.out.print("R " + o.GetTrack().getPhrase(j).getNote(o.GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
            System.out.print("D " + o.GetTrack().getPhrase(j).getNote(o.GetTrack().getPhrase(j).size() - 1).getDynamic());

            System.out.print("      ");

            for(int k = 0; k < mutation.GetTrack().getPhrase(j).size(); ++k)
            {
                String p = mutation.GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? mutation.GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                System.out.print("P " +  p + ", ");
            }
            System.out.print("R " + mutation.GetTrack().getPhrase(j).getNote(mutation.GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
            System.out.print("D " + mutation.GetTrack().getPhrase(j).getNote(mutation.GetTrack().getPhrase(j).size() - 1).getDynamic());
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------");


        Part p3 = new Part();
        for(int i = 0; i < 5; i++)
        {
            p3.addPhrase(new Phrase(new Note(C6, WHOLE_NOTE)));
        }
        Organism o3 = new Organism(p3, 0);
        Organism mutation3 = new Organism(p3.copy(), 0);

        System.out.println("Mutation Test Started, 50% Probability");
        System.out.println("--------------------------------------------");
        for(int i = 0; i < _mutators.size(); i++)
        {
            if(_mutators.get(i) instanceof NotePitchMutator)
                System.out.println("Note Pitch Mutator Started...");
            else if(_mutators.get(i) instanceof RhythmMutator)
                System.out.println("Rhythm Mutator Started...");
            else if(_mutators.get(i) instanceof SimplifyMutator)
                System.out.println("Simplify Mutator Started...");
            else if(_mutators.get(i) instanceof SwapMutator)
                System.out.println("Swap Mutator Started...");
            else if(_mutators.get(i) instanceof DynamicMutator)
                System.out.println("Dynamic Mutator Started...");

            // Mutate the organism
            mutation3.SetTrack(_mutators.get(i).Mutate(mutation3.GetTrack(), 0.5f));

            if(_mutators.get(i) instanceof NotePitchMutator)
                System.out.println("Note Pitch Mutator Complete...");
            else if(_mutators.get(i) instanceof RhythmMutator)
                System.out.println("Rhythm Mutator Complete...");
            else if(_mutators.get(i) instanceof SimplifyMutator)
                System.out.println("Simplify Mutator Complete...");
            else if(_mutators.get(i) instanceof SwapMutator)
                System.out.println("Swap Mutator Complete...");
            else if(_mutators.get(i) instanceof DynamicMutator)
                System.out.println("Dynamic Mutator Complete...");
        }

        System.out.println("Sample: 50% (Pitch, Rhythm, Dynamic)");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Original                  Mutated");
        for (int j = 0; j < mutation3.GetTrack().size(); ++j)
        {
            for(int k = 0; k < o3.GetTrack().getPhrase(j).size(); ++k)
            {
                String p = o3.GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? o3.GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                System.out.print("P " +  p + ", ");
            }
            System.out.print("R " + o3.GetTrack().getPhrase(j).getNote(o3.GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
            System.out.print("D " + o3.GetTrack().getPhrase(j).getNote(o3.GetTrack().getPhrase(j).size() - 1).getDynamic());

            System.out.print("      ");

            for(int k = 0; k < mutation3.GetTrack().getPhrase(j).size(); ++k)
            {
                String p = mutation3.GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? mutation3.GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                System.out.print("P " +  p + ", ");
            }
            System.out.print("R " + mutation3.GetTrack().getPhrase(j).getNote(mutation3.GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
            System.out.print("D " + mutation3.GetTrack().getPhrase(j).getNote(mutation3.GetTrack().getPhrase(j).size() - 1).getDynamic());
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------");






        Part p2 = new Part();
        for(int i = 0; i < 5; i++)
        {
            p2.addPhrase(new Phrase(new Note(C5, EIGHTH_NOTE)));
        }
        Organism o2 = new Organism(p2, 0);
        Organism mutation2 = new Organism(p2.copy(), 0);

        System.out.println("Mutation Test Started, 0% Probability");
        System.out.println("--------------------------------------------");
        for(int i = 0; i < _mutators.size(); i++)
        {
            if(_mutators.get(i) instanceof NotePitchMutator)
                System.out.println("Note Pitch Mutator Started...");
            else if(_mutators.get(i) instanceof RhythmMutator)
                System.out.println("Rhythm Mutator Started...");
            else if(_mutators.get(i) instanceof SimplifyMutator)
                System.out.println("Simplify Mutator Started...");
            else if(_mutators.get(i) instanceof SwapMutator)
                System.out.println("Swap Mutator Started...");
            else if(_mutators.get(i) instanceof DynamicMutator)
                System.out.println("Dynamic Mutator Started...");

            // Mutate the organism
            mutation2.SetTrack(_mutators.get(i).Mutate(mutation2.GetTrack(), 0.0f));

            if(_mutators.get(i) instanceof NotePitchMutator)
                System.out.println("Note Pitch Mutator Complete...");
            else if(_mutators.get(i) instanceof RhythmMutator)
                System.out.println("Rhythm Mutator Complete...");
            else if(_mutators.get(i) instanceof SimplifyMutator)
                System.out.println("Simplify Mutator Complete...");
            else if(_mutators.get(i) instanceof SwapMutator)
                System.out.println("Swap Mutator Complete...");
            else if(_mutators.get(i) instanceof DynamicMutator)
                System.out.println("Dynamic Mutator Complete...");
        }

        System.out.println("Sample: 0% (Pitch, Rhythm, Dynamic)");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Original                  Mutated");
        for (int j = 0; j < mutation2.GetTrack().size(); ++j)
        {
            for(int k = 0; k < o2.GetTrack().getPhrase(j).size(); ++k)
            {
                String p = o2.GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? o2.GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                System.out.print("P " +  p + ", ");
            }
            System.out.print("R " + o2.GetTrack().getPhrase(j).getNote(o2.GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
            System.out.print("D " + o2.GetTrack().getPhrase(j).getNote(o2.GetTrack().getPhrase(j).size() - 1).getDynamic());

            System.out.print("      ");

            for(int k = 0; k < mutation2.GetTrack().getPhrase(j).size(); ++k)
            {
                String p = mutation2.GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? mutation2.GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                System.out.print("P " +  p + ", ");
            }
            System.out.print("R " + mutation2.GetTrack().getPhrase(j).getNote(mutation2.GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
            System.out.print("D " + mutation2.GetTrack().getPhrase(j).getNote(mutation2.GetTrack().getPhrase(j).size() - 1).getDynamic());
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------");
    }
}
