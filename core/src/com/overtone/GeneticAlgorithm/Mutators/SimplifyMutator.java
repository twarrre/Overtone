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
    // Generate random number
    private Random _random;
    public SimplifyMutator()
    {
        _random = new Random();
    }

    public Part Mutate(Part p, float probability)
    {
        for(int i = 0; i < p.length(); i++)
        {
            // Skip rests
            if(p.getPhrase(i).getNote(0).isRest())
                continue;

            // Skip chords
            if(p.getPhrase(i).length() > 1)
                continue;

            // Random to decide if the note will be mutated or not
            int random = Utilities.GetRandom(0, 1, probability);
            if(random == 0)
            {
                // Randomly choose the note ahead of it, or behind it
                int leftOrRight = Utilities.GetRandom(-1, 1, 0.5f);

                // Make sure that the next note does not go out of bounds and wraps
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

                    // Skip rests
                    if(p.getPhrase(phraseToCopy).getNote(0).isRest())
                        continue;
                }

                // Get the pitch & dynamic of a random note in the randomly selected phrase
                Phrase copyPhrase = p.getPhrase(phraseToCopy);
                int newPitch = copyPhrase.getNote(_random.nextInt(copyPhrase.length())).getPitch();
                int newDynamic = copyPhrase.getNote(_random.nextInt(copyPhrase.length())).getDynamic();

                // Get a random note in the phrase to be mutated
                int noteToChangePitch = _random.nextInt(p.getPhrase(i).length());

                // Change the pitch & dynamic to the new one
                p.getPhrase(i).getNote(noteToChangePitch).setPitch(Math.round(Utilities.Clamp(newPitch, GeneticAlgorithm.LOW_PITCH, GeneticAlgorithm.HIGH_PITCH)));
                p.getPhrase(i).getNote(noteToChangePitch).setDynamic(Math.round(Utilities.Clamp(newDynamic, GeneticAlgorithm.LOW_DYNAMIC, GeneticAlgorithm.HIGH_DYNAMIC)));
            }
        }
        return GeneticAlgorithm.CorrectStartTime(GeneticAlgorithm.CorrectDuration(p));
    }
}
