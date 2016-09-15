package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Keeps track of of the highest and lowest pitch to makes sure there is not too much variation but enough variation
 * Rating is determined by lowest / highest
 * Created by trevor on 2016-08-14.
 */
public class PitchRangeRater extends Rater
{
    public float Rate(Organism p)
    {
        int lowPitch  = Integer.MAX_VALUE;
        int highPitch = Integer.MIN_VALUE;
        Part track    = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the track
            Phrase ph = track.getPhrase(i);
            for(int j = 0; j < ph.length(); j++)
            {
                // If the note is a rest, skip it
                if(ph.getNote(j).isRest())
                    continue;

                // Get the current pitch
                int pitch = ph.getNote(j).getPitch();

                // If the pitch is greater then the highest, store it
                if(pitch > highPitch)
                    highPitch = pitch;

                // If the pitch is lower than the lowest pitch, store it
                if(pitch < lowPitch)
                    lowPitch = pitch;
            }
        }

        // Return the rating
        if(lowPitch == Integer.MAX_VALUE || highPitch == Integer.MIN_VALUE)
            return 0;
        else
            return (float)lowPitch / (float)highPitch;
    }
}
