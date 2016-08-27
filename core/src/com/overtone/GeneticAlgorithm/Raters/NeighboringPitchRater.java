package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Note;
import jm.music.data.Part;

/**
 * Rates the phrase based on how the pitches flow between each other.
 * Created by trevor on 2016-08-14.
 */
public class NeighboringPitchRater extends Rater
{
    public static int OCTAVE = 13;

    public float Rate(Organism p)
    {
        int numCrazyNotes = 0;
        int numNotes = 0;
        Part track = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            if(track.getPhrase(i).length() > 1 || track.getPhrase(i).getNote(0).isRest())
                continue;

            if(i == track.length() - 1)
            {
                numNotes++;
                continue;
            }

            //skip chords and rests
            if(track.getPhrase(i + 1).length() < 2 && !track.getPhrase(i + 1).getNote(0).isRest())
            {
                if((track.getPhrase(i + 1).getNote(0).getPitch() > (track.getPhrase(i).getNote(0).getPitch() + OCTAVE)
                        || track.getPhrase(i + 1).getNote(0).getPitch() < (track.getPhrase(i).getNote(0).getPitch() - OCTAVE)))
                    numCrazyNotes++;
            }

            numNotes++;
        }

        if(numNotes == 0)
            return 0;
        else
            return (float)numCrazyNotes / (float)numNotes;
    }
}
