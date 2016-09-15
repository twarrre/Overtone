package com.overtone.GeneticAlgorithm.Raters;
import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;
import java.util.HashMap;

/**
 * Rater that checks how many unique note values there are in the piece of music.
 * Returns number of unique notes / the total number of notes
 * Created by trevor on 2016-08-14.
 */
public class UniqueNoteRater extends Rater
{
    public float Rate(Organism o)
    {
        HashMap<Integer, Integer> noteCounter = new HashMap();
        Part part                             = o.GetTrack();
        int numNotes                          = 0;

        for(int i = 0; i < part.length(); i++)
        {
            // Get the phrase
            Phrase phrase = part.getPhrase(i);
            for(int j = 0; j < phrase.length(); j++)
            {
                // Skip rests
                if(phrase.getNote(j).isRest())
                    continue;

                // Grab the pitch of each of the notes in the phrases
                int pitch = phrase.getNote(j).getPitch();

                // Check if the pitch has already stored in the map
                if(noteCounter.containsKey(pitch))
                    noteCounter.put(pitch, noteCounter.get(pitch) + 1);
                else
                    noteCounter.put(pitch, 1);

                // Increment the number of notes
                numNotes++;
            }
        }

        // Return the value
        if(numNotes == 0)
            return 0;
        else
            return (float)noteCounter.size() / (float)numNotes;
    }
}
