package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Keeps track of of the highest and lowest pitch to makes sure there is not too much variation but enough variation
 * Created by trevor on 2016-08-14.
 */
public class PitchRangeRater extends Rater
{
    public float Rate(Organism p)
    {
        int lowPitch = Integer.MAX_VALUE;
        int highPitch = 0;
        Part track = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            for(int j = 0; j < ph.length(); j++)
            {
                if(ph.getNote(j).isRest())
                    continue;

                int pitch = ph.getNote(j).getPitch();
                if(pitch > highPitch)
                    highPitch = pitch;
                else if(pitch < lowPitch)
                    lowPitch = pitch;
            }
        }

        return (float)lowPitch / (float)highPitch;
    }
}
