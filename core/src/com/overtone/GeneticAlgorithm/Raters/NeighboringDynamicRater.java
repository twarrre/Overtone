package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;

/**
 * Created by trevor on 2016-09-05.
 */
public class NeighboringDynamicRater extends Rater
{
    public static int ACCEPTABLE_RANGE = 15;

    public float Rate(Organism p)
    {
        int numDynamicsNotes = 0;
        int numNotes = 0;
        Part track = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            if(track.getPhrase(i).getNote(0).isRest())
                continue;

            if(i == track.length() - 1)
            {
                numNotes++;
                continue;
            }

            //skip rests
            if(!track.getPhrase(i + 1).getNote(0).isRest())
            {
                if((track.getPhrase(i + 1).getNote(0).getDynamic() > (track.getPhrase(i).getNote(0).getDynamic() + ACCEPTABLE_RANGE)
                        || track.getPhrase(i + 1).getNote(0).getDynamic() < (track.getPhrase(i).getNote(0).getDynamic() - ACCEPTABLE_RANGE)))
                    numDynamicsNotes++;
            }

            numNotes++;
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)numDynamicsNotes / (float)numNotes;
    }
}
