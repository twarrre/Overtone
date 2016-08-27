package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Check how much rests there are in the music.
 * Created by trevor on 2016-08-21.
 */
public class ContinuousSilenceRater extends Rater
{
    public float Rate(Organism p)
    {
        float totalSilenceLength = 0;
        float totalLength = 0;

        Part track = p.GetTrack();
        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            if(ph.length() > 1)
                totalLength += ph.getNote(ph.length() - 1).getDuration();
            else
                totalLength += ph.getNote(0).getDuration();

            if(ph.getNote(0).isRest())
                totalSilenceLength += ph.getNote(0).getDuration();
        }

        return 1.0f - (totalSilenceLength / totalLength);
    }
}
