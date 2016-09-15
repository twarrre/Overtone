package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Note;
import jm.music.data.Part;

/**
 * Rates the phrase based on how the pitches flow between each other.
 * Checks if the notes around each other are significantly higher or lower than each other
 * Created by trevor on 2016-08-14.
 */
public class NeighboringPitchRater extends Rater
{
    public float Rate(Organism p)
    {
        int numCrazyNotes = 0;
        int numNotes      = 0;
        Part track        = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Skip chords and rests
            if(track.getPhrase(i).length() > 1 || track.getPhrase(i).getNote(0).isRest())
                continue;

            // You are finished if you reach the last note
            if(i == track.length() - 1)
            {
                numNotes++;
                continue;
            }

            //skip chords and rests
            if(track.getPhrase(i + 1).length() < 2 && !track.getPhrase(i + 1).getNote(0).isRest())
            {
                // Check the note ahead of you and check if the pitch is greater or less than and octave compared to the current one
                if((track.getPhrase(i + 1).getNote(0).getPitch() > (track.getPhrase(i).getNote(0).getPitch() + GeneticAlgorithm.OCTAVE)
                        || track.getPhrase(i + 1).getNote(0).getPitch() < (track.getPhrase(i).getNote(0).getPitch() - GeneticAlgorithm.OCTAVE)))
                    numCrazyNotes++;
            }

            // Increment the number of notes
            numNotes++;
        }

        // Return the ratio
        if(numNotes == 0)
            return 0;
        else
            return (float)numCrazyNotes / (float)numNotes;
    }
}
