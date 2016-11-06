package com.overtone.Testing;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.Overtone;
import jm.JMC;
import jm.music.data.Rest;

/**
 * Created by trevor on 2016-11-05.
 */
public class InitializationTest implements JMC
{
    public void Test()
    {
        GeneticAlgorithm ga = new GeneticAlgorithm(true);
        Organism[] population = ga.Initialization();

        System.out.println("Initialization Test Started");
        System.out.println("Population Size: " + Overtone.PopulationSize);
        System.out.println("-------------------------------------------------------------------------------");
        /*System.out.println("P1               P2               P3                P4               P5");
        System.out.println("-------------------------------------------------------------------------------");*/
        for(int i = 0; i < population.length; ++i)
        {
            System.out.println("Sample: " + i + " (Pitch, Rhythm, Dynamic)");
            System.out.println("-------------------------------------------------------------------------------");
            for (int j = 0; j < population[i].GetTrack().size(); ++j)
            {
                for(int k = 0; k < population[i].GetTrack().getPhrase(j).size(); ++k)
                {
                    String p = population[i].GetTrack().getPhrase(j).getNote(k).getPitch() != REST ? population[i].GetTrack().getPhrase(j).getNote(k).getPitch() + "" : "rest";
                    System.out.print("P " +  p + ", ");
                }
                System.out.print("R " + population[i].GetTrack().getPhrase(j).getNote(population[i].GetTrack().getPhrase(j).size() - 1).getRhythmValue() + ", ");
                System.out.print("D " + population[i].GetTrack().getPhrase(j).getNote(population[i].GetTrack().getPhrase(j).size() - 1).getDynamic());
                System.out.println();
            }
            System.out.println("-------------------------------------------------------------------------------");
        }
    }
}
