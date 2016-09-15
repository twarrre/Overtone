package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import java.util.ArrayList;

/**
 * Rates the phrase based on how the rhythm values flow between each other.
 * Checks if the notes around each other are significantly higher or lower than each other
 * Created by trevor on 2016-09-05.
 */
public class NeighboringRhythmRater extends Rater
{
    /** The acceptable range for rhythm values for the rating. */
    private static int ACCEPTABLE_RANGE = 3;

    public float Rate(Organism p)
    {
        int numRhythmsNotes = 0;
        int numNotes        = 0;
        Part track          = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // If you reach the last note, you are done
            if(i == track.length() - 1)
            {
                numNotes++;
                continue;
            }

            // Find the index (from the rhythm array) of the current rhythm value
            int currentRhythm = BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), track.getPhrase(i).getNote(track.getPhrase(i).length() - 1).getRhythmValue());

            // Get the index of the next rhythm value
            int nextRhythm =  BinarySearch(GeneticAlgorithm.RHYTHMS, 0, GeneticAlgorithm.RHYTHMS.size(), track.getPhrase(i + 1).getNote(track.getPhrase(i + 1).length() - 1).getRhythmValue());

            // If the next rhythm is note within the range of acceptance
            if((nextRhythm > (currentRhythm + ACCEPTABLE_RANGE) || nextRhythm < (currentRhythm - ACCEPTABLE_RANGE)))
                numRhythmsNotes++;

            // Increment the number of notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)numRhythmsNotes / (float)numNotes;
    }

    /**
     * Implementation of binary search to find the index of a rhythm value
     * @param list The list of values to search
     * @param low The low index of the searchable area in the array
     * @param high The high index of the searchable area in the array
     * @param search The number to search for
     * @return The index of the found value.
     */
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
