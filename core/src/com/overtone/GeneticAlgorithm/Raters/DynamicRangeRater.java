package com.overtone.GeneticAlgorithm.Raters;

import com.overtone.GeneticAlgorithm.Organism;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 * Created by trevor on 2016-09-05.
 */
public class DynamicRangeRater extends Rater
{
    public float Rate(Organism p)
    {
        int lowDynamic = Integer.MAX_VALUE;
        int highDynamic = Integer.MIN_VALUE;
        Part track = p.GetTrack();

        for(int i = 0; i < track.length(); i++)
        {
            Phrase ph = track.getPhrase(i);
            for(int j = 0; j < ph.length(); j++)
            {
                int dynamic = ph.getNote(j).getDynamic();

                if(dynamic > highDynamic)
                    highDynamic = dynamic;

                if(dynamic < lowDynamic)
                    lowDynamic = dynamic;
            }
        }

        if(lowDynamic == Integer.MAX_VALUE || highDynamic == Integer.MIN_VALUE)
            return 0;
        else
            return (float)lowDynamic / (float)highDynamic;
    }
}
