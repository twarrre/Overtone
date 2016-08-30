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

    private Thread           _generatingThread; // Thread used to generate the notes
    private GeneticAlgorithm _genetic;          // Genetic algorithm object used to generate the notes
    private boolean          _completed;        // True if the thread has completed, false otherwise
    private float            _elapsedTime;      // Amount of time passed so far
    private Random           _random;           // Random number generator for string array
    private int              _stringIndex;      // Index for the loading strings
    private int              _timeInterval;     // Interval between switch loading sting
    private final Texture    _logoNoGlow;       // Texture of the logo on screen
    private final Texture    _glow;             // Texture of the logo on screen
    private float            _glowAlpha;        // The alpha of the glow for the logo
    private float            _glowDirection;    // The direction of the glow alpha

    private String[]         _loadingStrings = new String[] {
            "Tuning Instruments",
            "Replacing Strings",
            "Turing on Amp",
            "Practicing Riffs",
            "Rehearsing Songs",
            "Writing Music",
            "Warming Up Voice",
            "Practicing Scales",
            "Improvising Solos",
            "Replacing Reeds",
    };

    /**
     * Constructor
     */
    public LoadingScreen()
    {
        super();
        _completed       = false;
        _logoNoGlow      = new Texture(Gdx.files.internal("Textures\\logo_noglow.png"));
        _glow            = new Texture(Gdx.files.internal("Textures\\glow.png"));
        _elapsedTime     = 0.0f;
        _random          = new Random();
        _stringIndex     = 0;
        _timeInterval    = 1;
        _glowAlpha       = 0.0f;
        _glowDirection   = 0.01f;

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

        _batch.setColor(1.0f, 1.0f, 1.0f, _glowAlpha);
        _batch.draw(_glow, Overtone.ScreenWidth * 0.6f, 0.0f, Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.4f);
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        _batch.draw(_logoNoGlow, Overtone.ScreenWidth * 0.6f, 0.0f, Overtone.ScreenWidth * 0.4f, Overtone.ScreenHeight * 0.4f);

        _glyphLayout.setText(_font18,  _loadingStrings[_stringIndex] + "...");
        _font18.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.8f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.07f);

        _batch.end();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        _glowAlpha = Utilities.Clamp(_glowAlpha + _glowDirection, 0, 1);
        if(_glowAlpha >= 1.0f || _glowAlpha <= 0.0f)
            _glowDirection *= -1.0f;

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
        _logoNoGlow.dispose();
        _glow.dispose();
    }
}
