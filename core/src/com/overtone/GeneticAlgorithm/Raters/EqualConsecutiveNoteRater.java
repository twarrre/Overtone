package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-08-21.
 */
public class EqualConsecutiveNoteRater extends Rater
{
    public float Rate(Organism p)
    {
        int consecutiveNote = 0;
        int numNotes = 1;

        Part track = p.GetTrack();
        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            Phrase phPrev;

            if(ph.length() > 1)
                continue;
            else if(ph.getNote(0).isRest())
                continue;
            else if(i == 0)
                continue;
            else if(track.getPhrase(i - 1).length() > 1)
                continue;
            else
                phPrev = track.getPhrase(i - 1);

            if(ph.getNote(0).getPitch() == phPrev.getNote(0).getPitch())
                consecutiveNote++;

            numNotes++;
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)consecutiveNote / (float)numNotes;
    }
}
