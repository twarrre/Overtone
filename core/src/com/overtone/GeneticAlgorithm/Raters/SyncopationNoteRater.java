package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.JMC;
import jm.music.data.Part;

/**
 * Assumes 4/4 time
 * Created by trevor on 2016-08-21.
 */
public class SyncopationNoteRater extends Rater implements JMC
{
    public float Rate(Organism p)
    {
        int syncopationCounter = 0;
        int numNotes = 0;
        double duration = 0;

        Part track = p.GetTrack();
        for(int i = 0; i < track.length(); i++)
        {
            double noteDur;
            if(track.getPhrase(i).length() > 1)
                noteDur = track.getPhrase(i).getNote(track.getPhrase(i).length() - 1).getDuration();
            else
                noteDur = track.getPhrase(i).getNote(0).getDuration();

            duration += noteDur;
            if(duration > WHOLE_NOTE)
            {
                syncopationCounter++;
                duration = duration - WHOLE_NOTE;
            }
            numNotes++;
        }

        return (float)syncopationCounter / (float)numNotes;
    }
}
