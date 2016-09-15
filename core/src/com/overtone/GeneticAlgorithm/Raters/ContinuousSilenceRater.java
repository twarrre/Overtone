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
        float totalLength        = 0;
        Part track               = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the track
            Phrase ph = track.getPhrase(i);

            // Get The duration of the note
            if(ph.length() > 1)
                totalLength += ph.getNote(ph.length() - 1).getDuration();
            else
                totalLength += ph.getNote(0).getDuration();

            // It the note was a rest, save it's duration
            if(ph.getNote(0).isRest())
                totalSilenceLength += ph.getNote(0).getDuration();
        }

        // Return the rating value
        return 1.0f - (totalSilenceLength / totalLength);
    }
}
