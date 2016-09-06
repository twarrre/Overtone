package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-05.
 */
public class RhythmRangeRater extends Rater
{
    public float Rate(Organism p)
    {
        double lowRhythm = Double.MAX_VALUE;
        double highRhythm = Double.MIN_VALUE;
        Part track = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            for(int j = 0; j < ph.length(); j++)
            {
                double rhythm = ph.getNote(j).getRhythmValue();

                if(rhythm > highRhythm)
                    highRhythm = rhythm;

                if(rhythm < lowRhythm)
                    lowRhythm = rhythm;
            }
        }

        if(lowRhythm == Double.MAX_VALUE || highRhythm == Double.MIN_VALUE)
            return 0;
        else
            return (float)lowRhythm / (float)highRhythm;
    }
}
