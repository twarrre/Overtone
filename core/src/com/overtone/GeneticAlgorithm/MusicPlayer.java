package com.overtone.GeneticAlgorithm;

import jm.audio.Instrument;
import jm.music.data.Score;
import jm.util.Play;

/**
 * Created by trevor on 2016-08-03.
 */
public class MusicPlayer implements Runnable
{
    private Score _music;
    private Instrument[] _inst;

    public MusicPlayer(Score s, Instrument[] i)
    {

        _music = s;
        _inst = i;
    }

    public void run()
    {
        StartMusicPlayer();
    }

    public synchronized void StopMusicPlayer()
    {
        Play.stopAudio();
    }

    public void StartMusicPlayer()
    {
        Play.audio(_music, _inst);
    }

    public void PauseMusicPlayer()
    {
        Play.pauseAudio();
    }

    public void UnPauseMusicPlayer()
    {
        Play.unPauseAudio();
    }
}
