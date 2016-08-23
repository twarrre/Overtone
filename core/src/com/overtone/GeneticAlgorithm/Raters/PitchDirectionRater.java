package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Rates the phrase based on the pitch direction to prevent drastic changes in pitch.
 * Created by trevor on 2016-08-14.
 */
public class PitchDirectionRater extends Rater
{
    public float Rate(Organism p)
    {
        int pitchesHigherThanBefore = 0;
        int numNotes                = 0;
        int prevPitch               = -1;

        Part track = p.GetTrack();
        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            if(ph.length() > 1)
                continue;

            if(ph.getNote(0).getPitch() > prevPitch && prevPitch != -1)
                pitchesHigherThanBefore++;

            prevPitch = ph.getNote(0).getPitch();
            numNotes++;
        }

        return (float)pitchesHigherThanBefore / (float)numNotes;
    }
}
