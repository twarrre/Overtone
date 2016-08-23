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
        int i = 0;

        while(track.getPhrase(i).length() > 1)
            i++;
        Note prev = track.getPhrase(i).getNote(0);

        for(int j = i; j < track.length(); j++)
        {
            if(track.getPhrase(i).length() > 1)
                continue;

            if(track.getPhrase(j).getNote(0).getPitch() > prev.getPitch() + OCTAVE || track.getPhrase(j).getNote(0).getPitch() > prev.getPitch() - OCTAVE)
                numCrazyNotes++;

            prev = track.getPhrase(j).getNote(0);
            numNotes++;
        }

        return (float)numCrazyNotes / (float)numNotes;
    }
}
