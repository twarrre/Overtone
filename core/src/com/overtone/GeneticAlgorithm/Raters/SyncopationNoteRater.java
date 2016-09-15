package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.JMC;
import jm.music.data.Part;

/**
 * Assumes 4/4 time.
 * Checks how many notes exceed a bar in that staff
 * Created by trevor on 2016-08-21.
 */
public class SyncopationNoteRater extends Rater implements JMC
{
    public float Rate(Organism p)
    {
        int syncopationCounter = 0;
        int numNotes           = 0;
        double duration        = 0;
        Part track             = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the note rhythm value
            double noteDur = track.getPhrase(i).getNote(track.getPhrase(i).length() - 1).getRhythmValue();

            // Check if the duration exceeds 4 (a full bar in the staff)
            duration += noteDur;
            if(duration == WHOLE_NOTE)
                duration = 0;
            if(duration > WHOLE_NOTE)
            {
                syncopationCounter++;
                duration = duration - WHOLE_NOTE;
            }

            // Increment number of notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)syncopationCounter / (float)numNotes;
    }
}
