package com.overtone.Testing.RaterTests;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.GeneticAlgorithm.Raters.*;
import com.overtone.Overtone;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.ArrayList;

/**
 * Created by trevor on 2016-11-05.
 */
public class AllRatersTest implements JMC
{
    public void Test()
    {
        float runningSum = 0;
        Part p1 = new Part();
        for(int i = 0; i < 10; i++)
        {
            p1.addPhrase(new Phrase(new Note(C4, QUARTER_NOTE)));
            p1.getPhrase(i).getNote(0).setDynamic(56);
        }

        System.out.println("Overall Rating Test Started, Stored Best Values = 0.5");
        System.out.println("--------------------------------------------");

        ChordRatioRater csr = new ChordRatioRater();
        float value = csr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Chord Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        ContinuousSilenceRater csr2 = new ContinuousSilenceRater();
        value = csr2.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Continuous Silence Rater Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        DirectionStabilityRater dsr = new DirectionStabilityRater();
        value = dsr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Direction Stability Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        DynamicRangeRater pr = new DynamicRangeRater();
        value = pr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Dynamic Range Rater Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        DynamicStabilityRater dsr3 = new DynamicStabilityRater();
        value = dsr3.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Dynamic Stability Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        EqualConsecutiveNoteRater ecnr = new EqualConsecutiveNoteRater();
        value = ecnr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Equal Consecutive Note Rater Expected Result: " + 0.9f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.9f ? "Expected result. OK" : "Not expected result. FAIL");

        NeighboringDynamicRater npr = new NeighboringDynamicRater();
        value = npr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Neighboring Dynamic Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        NeighboringPitchRater npr2 = new NeighboringPitchRater();
        value = npr2.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Neighboring Pitch Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        NeighboringRhythmRater npr3 = new NeighboringRhythmRater();
        value = npr3.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Neighboring Rhythm Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        PitchDirectionRater pdr = new PitchDirectionRater();
        value = pdr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Pitch Direction Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        PitchRangeRater pr2 = new PitchRangeRater();
        value = pr2.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Pitch Range Rater Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        RestRatioRater csr3 = new RestRatioRater();
        value = csr3.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Rest Ratio Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        RhythmRangeRater pr3 = new RhythmRangeRater();
        value = pr3.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Rhythm Range Expected Result: " + 1.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 1.0f ? "Expected result. OK" : "Not expected result. FAIL");

        RhythmStabilityRater dsr2 = new RhythmStabilityRater();
        value = dsr2.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Rhythm Stability Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        SyncopationNoteRater snr = new SyncopationNoteRater();
        value = snr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Syncopation Note Rater Expected Result: " + 0.0f + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == 0.0f ? "Expected result. OK" : "Not expected result. FAIL");

        UniqueDynamicRater unr = new UniqueDynamicRater();
        value = unr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Unique Dynamic Expected Result: " + (1.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (1.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        UniqueNoteRater unr2 = new UniqueNoteRater();
        value = unr2.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Unique Note Pitch Rater Expected Result: " + (1.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (1.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");

        UniqueRhythmValuesRater urr = new UniqueRhythmValuesRater();
        value = urr.Rate(new Organism(p1, 0));
        runningSum += (0.5f - value);
        System.out.print("Unique Rhythm Rater Expected Result: " + (1.0f / 10.0f) + ".... Calculated Result: " + value + " .... ");
        System.out.println(value == (1.0f / 10.0f) ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
        System.out.println("Calculating Overall Rating for Sample");
        System.out.println("--------------------------------------------");
        runningSum /= (float)Overtone.NUM_RATERS;
        System.out.print("Overall Rating Expected Result: " + (double)Math.round((3.8f / 18f) * 10000d) / 10000d  + ".... Calculated Result: " + (double)Math.round(runningSum * 10000d) / 10000d  + " .... ");
        System.out.println(((double)Math.round(runningSum * 10000d) / 10000d) == ((double)Math.round((3.8f / 18f) * 10000d) / 10000d)  ? "Expected result. OK" : "Not expected result. FAIL");
        System.out.println("------------------------------------------------");
        System.out.println("Overall Rating Test Complete");
        System.out.println("--------------------------------------------");

    }
}
