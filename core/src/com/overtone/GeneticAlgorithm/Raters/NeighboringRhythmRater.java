package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;

import java.util.ArrayList;

/**
 * Created by trevor on 2016-09-05.
 */
public class NeighboringRhythmRater extends Rater
{
    public static int ACCEPTABLE_RANGE = 3;

    public float Rate(Organism p)
    {
        int numRhythmsNotes = 0;
        int numNotes = 0;
        Part track = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            if(i == track.length() - 1)
            {
                numNotes++;
                continue;
            }

            int currentRhythm = BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), track.getPhrase(i).getNote(track.getPhrase(i).length() - 1).getRhythmValue());
            int nextRhythm =  BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), track.getPhrase(i + 1).getNote(track.getPhrase(i + 1).length() - 1).getRhythmValue());

            if((nextRhythm > (currentRhythm + ACCEPTABLE_RANGE) || nextRhythm < (currentRhythm - ACCEPTABLE_RANGE)))
                numRhythmsNotes++;

            numNotes++;
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)numRhythmsNotes / (float)numNotes;
    }

    private static int BinarySearch(ArrayList<Double> list, int low, int high, double search)
    {
        int len = (high - low);
        int index = (high + low) / 2;

        if(len == 1 && list.get(index) == search)
            return index;
        else if(len == 1 && list.get(index) != search)
            return -1;

        if(search < list.get(index))
            return BinarySearch(list, low, index, search);
        else if(search > list.get(index))
            return BinarySearch(list, index, high, search);
        else
            return index;
    }
}
