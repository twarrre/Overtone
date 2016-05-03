package com.overtone.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by trevor on 2016-05-01.
 */
public class SongCompleteScreen implements Screen
{
    private Texture _backgroundImage;
    private SpriteBatch _batch;
    private int _screenWidth;
    private int _screenHeight;

    public SongCompleteScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        _screenWidth = screenWidth;
        _screenHeight = screenHeight;

        _batch = new SpriteBatch();
        _backgroundImage = new Texture(backgroundImagePath);
    }

    // Called when the screen is made the main one
    public void show ()
    {

    }

    public void render (float deltaTime)
    {
        _batch.begin();
        _batch.draw(_backgroundImage, 0, 0, _screenWidth, _screenHeight);
        _batch.end();
    }

    public void resize (int width, int height)
    {
        _screenWidth = width;
        _screenHeight = height;
    }

    public void pause ()
    {

    }

    public void resume ()
    {

    }

    public void hide ()
    {

    }

    public void dispose ()
    {
        _batch.dispose();
        _backgroundImage.dispose();
    }
}