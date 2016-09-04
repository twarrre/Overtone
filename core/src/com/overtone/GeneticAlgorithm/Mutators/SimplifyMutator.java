package com.overtone.GeneticAlgorithm.Mutators;
import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Utilities;
import jm.music.data.Part;
import jm.music.data.Phrase;

import java.util.Random;

/**
 * Mutates the phrase so that the note's pitch will match the note before it or after it.
 * Created by trevor on 2016-08-14.
 */
public class SimplifyMutator extends Mutator
{
    private Random _random;
    public SimplifyMutator()
    {
        _random = new Random();
    }

    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            if(p.getPhrase(i).getNote(0).isRest())
                continue;

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // Randomly choose the note ahead of it, or behind it
                int leftOrRight = Utilities.GetRandom(-1, 1, 0.5f);

                int phraseToCopy = i + leftOrRight;
                if(i == 0 && leftOrRight == -1)
                    phraseToCopy = p.length() - 1;
                else if(i == p.length() - 1 && leftOrRight == 1)
                    phraseToCopy = 0;

                // Check if both sides are rests
                if(p.getPhrase(phraseToCopy).getNote(0).isRest())
                {
                    phraseToCopy = i + (leftOrRight * -1);

                    if(i == 0 && (leftOrRight * -1) == -1)
                        phraseToCopy = p.length() - 1;
                    else if(i == p.length() - 1 && (leftOrRight * -1) == 1)
                        phraseToCopy = 0;

                    if(p.getPhrase(phraseToCopy).getNote(0).isRest())
                        continue;
                }

                // Get the pitch of a random note in the randomly selected phrase
                Phrase copyPhrase = p.getPhrase(phraseToCopy);
                int newPitch = copyPhrase.getNote(_random.nextInt(copyPhrase.length())).getPitch();

                // Get a random note in the phrase to be mutated
                int noteToChangePitch = _random.nextInt(p.getPhrase(i).length());

                // Store the pitch that the not was previously
                int changedPitch = p.getPhrase(i).getNote(noteToChangePitch).getPitch();

                // Calculate the difference in the pitch from the changed one
                int pitchDifference = newPitch - changedPitch;

                // Change the pitch to the new one
                p.getPhrase(i).getNote(noteToChangePitch).setPitch(newPitch);

                // If the phrase was a cord, update the other notes by the difference in change
                for(int j = 0; j < p.getPhrase(i).length(); j++)
                {
                    if(j == noteToChangePitch)
                        continue;
                    else
                    {
                        int currentPitch =  p.getPhrase(i).getNote(j).getPitch();
                        p.getPhrase(i).getNote(j).setPitch(currentPitch + pitchDifference);
                    }

                }
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
