package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Checks how often the direction of pitches changes in the piece of music
 * Rated by the number of direction changes / number of notes
 * Created by trevor on 2016-08-21.
 */
public class DirectionStabilityRater extends Rater
{
    public float Rate(Organism p)
    {
        int pitchDirectionChanges = 0;
        int numNotes              = 0;
        int direction             = 0;
        int prevPitch             = -1;
        Part track                = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            // Get the track
            Phrase ph = track.getPhrase(i);

            // Skip chords
            if(ph.length() > 1 || ph.getNote(0).isRest())
                continue;

            // If the direction changes increment and change the direction
            if(ph.getNote(0).getPitch() > prevPitch && direction != 1 && prevPitch != -1)
            {
                pitchDirectionChanges++;
                direction = 1;

            }
            else if(ph.getNote(0).getPitch() < prevPitch && direction != -1 && prevPitch != -1)
            {
                pitchDirectionChanges++;
                direction = -1;
            }

            // Store the previous pitch
            prevPitch = ph.getNote(0).getPitch();

            // Increment the number of notes
            numNotes++;
        }

        // Return the rating
        if(numNotes == 0)
            return 0;
        else
            return (float)pitchDirectionChanges / (float)numNotes;
    }
}
