package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-05.
 */
public class DynamicStabilityRater extends Rater
{
    public float Rate(Organism p)
    {
        int dynamicDirectionChanges = 0;
        int numNotes              = 0;
        int direction             = 0;
        int prevDynamic           = -1;

        Part track = p.GetTrack();
        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);

            if(ph.getNote(0).getDynamic() > prevDynamic && direction != 1 && prevDynamic != -1)
            {
                dynamicDirectionChanges++;
                direction = 1;

            }
            else if(ph.getNote(0).getDynamic() < prevDynamic && direction != -1 && prevDynamic != -1)
            {
                dynamicDirectionChanges++;
                direction = -1;
            }

            prevDynamic = ph.getNote(0).getDynamic();
            numNotes++;
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)dynamicDirectionChanges / (float)numNotes;
    }
}