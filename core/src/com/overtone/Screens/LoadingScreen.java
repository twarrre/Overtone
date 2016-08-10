package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.overtone.GeneticAlgorithm.GeneticAlgorithm;
import com.overtone.Overtone;
import com.overtone.Utilities;

import java.util.Random;

/**
 * Loading screen for generating the notes of the game
 * Created by trevor on 2016-08-01.
 */
public class LoadingScreen extends OvertoneScreen
{
    /** If there is no generation, delay for shitz.*/
    public static float LOADING_TIMER = 3.0f;

    private final Texture    _progressBar;      // Texture for the progress bar
    private final Texture    _progress;         // Texture for the progress of the progress bar
    private Thread           _generatingThread; // Thread used to generate the notes
    private GeneticAlgorithm _genetic;          // Genetic algorithm object used to generate the notes
    private boolean          _completed;        // True if the thread has completed, false otherwise
    private float            _elapsedTime;      // Amount of time passed so far
    private Random           _random;           // Random number generator for string array
    private int              _stringIndex;      // Index for the loading strings
    private int              _timeInterval;     // Interval between switch loading sting

    private String[]         _loadingStrings = new String[] {
            "Tuning Instruments",
            "Replacing Strings",
            "Turing on Amp",
            "Practicing Riffs",
            "Rehearsing Songs",
            "Writing Music",
            "Warming Up Voice",
            "Practicing Scales"
    };

    /**
     * Constructor
     */
    public LoadingScreen()
    {
        super();
        _completed       = false;
        _progressBar     = new Texture(Gdx.files.internal("Textures\\progressbar.png"));
        _progress        = new Texture(Gdx.files.internal("Textures\\red.png"));
        _elapsedTime     = 0.0f;
        _random          = new Random();
        _stringIndex     = 0;
        _timeInterval    = 1;

        if(Overtone.Regenerate)
        {
            _genetic          = new GeneticAlgorithm();
            _generatingThread = new Thread(_genetic);
            _generatingThread.start();
        }
        else
        {
            Utilities.SortNotes(Overtone.BackupQueue);
        }
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();
        _glyphLayout.setText(_font36,  "Loading");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.85f);

        _glyphLayout.setText(_font30,  _loadingStrings[_stringIndex] + "...");
        _font30.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.25f);

        _batch.draw(_progressBar, Overtone.ScreenWidth * 0.125f, Overtone.ScreenHeight * 0.07f, Overtone.ScreenWidth * 0.75f, Overtone.ScreenHeight * 0.1f);
        _batch.draw(_progress, Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.08f, Overtone.ScreenWidth * 0.735f * (Overtone.Regenerate ? _genetic.GetPercentComplete() : _elapsedTime / LOADING_TIMER), Overtone.ScreenHeight * 0.08f);
        _batch.end();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        if(_completed)
            Overtone.SetScreen(Overtone.Screens.Gameplay);

        if(Overtone.Regenerate && _genetic.IsCompleted())
        {
            try
            {
                _generatingThread.join();
            }
            catch (InterruptedException e)
            {
                System.out.println("Thread Interrupted.");
            }
            _completed = true;
        }
        else
        {
            _elapsedTime += deltaTime;

            if(_elapsedTime > _timeInterval)
            {
                _stringIndex =  _random.nextInt(_loadingStrings.length);
                _timeInterval++;
            }
            
            if(_elapsedTime >= LOADING_TIMER)
            {
                _completed = true;
                _elapsedTime = LOADING_TIMER;
            }

        }
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
    }
    public void show() {super.show();}
    public void hide() {super.hide();}
    public void dispose ()
    {
        super.dispose();
        _progressBar.dispose();
        _progress.dispose();
    }
}
