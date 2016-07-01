package com.overtone.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by trevor on 2016-06-30.
 */
public class OvertoneScreen implements OvertoneScreenInterface
{
    protected Texture _backgroundImage;
    protected SpriteBatch _batch;
    protected float _screenWidth;
    protected float _screenHeight;

    public OvertoneScreen(String backgroundImagePath, float screenWidth, float screenHeight)
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

    public void update(float deltaTime)
    {

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
