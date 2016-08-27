package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-21.
 */
public class DirectionStabilityRater extends Rater
{
    public float Rate(Organism p)
    {
        int pitchDirectionChanges = 0;
        int numNotes              = 0;
        int direction             = 0;
        int prevPitch             = -1;

        Part track = p.GetTrack();
        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            if(ph.length() > 1)
                continue;

            if(ph.getNote(0).isRest())
                continue;

            if(ph.getNote(0).getPitch() > prevPitch && direction != 1 && prevPitch != -1)
            {
                pitchDirectionChanges++;
                direction = 1;

            }
            else if(ph.getNote(0).getPitch() < prevPitch && direction != -1 && prevPitch != -1)
            {
                pitchDirectionChanges++;
                direction = -1;
            }

            prevPitch = ph.getNote(0).getPitch();
            numNotes++;
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)pitchDirectionChanges / (float)numNotes;
    }
}
