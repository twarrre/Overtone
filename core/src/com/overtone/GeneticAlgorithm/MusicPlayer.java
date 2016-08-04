package com.overtone.GeneticAlgorithm;

import com.overtone.Overtone;
import jm.util.Play;

/**
 * Created by trevor on 2016-08-03.
 */
public class MusicPlayer implements Runnable
{
    private volatile boolean Stopped;

    public MusicPlayer()
    {
        Stopped = false;
    }

    public void run()
    {
        StartMusicPlayer();
        while (!Stopped)
        {
            if (Stopped)
                Play.stopMidi();
            Stopped = Play.cycleIsPlaying();
        }
    }

    public void StopMusicPlayer()
    {
        Stopped = true;
    }

    public void StartMusicPlayer()
    {
        Stopped = false;
        Play.midi(Overtone.Music);
    }
}
