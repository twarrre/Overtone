package com.overtone.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Base class for all screens in Overtone.
 * Stores some variables that will be used by all screens
 * Created by trevor on 2016-06-30.
 */
public class OvertoneScreen implements OvertoneScreenInterface
{
    // The background image for the screen
    protected final Texture _backgroundImage;
    protected final SpriteBatch _batch;
    protected final BitmapFont _font;
    protected final GlyphLayout _glyphLayout;
    protected float _screenWidth;
    protected float _screenHeight;


    /**
     * Constructor
     * @param backgroundImagePath The path the the background image
     * @param screenWidth The screen width
     * @param screenHeight The screen height
     */
    public OvertoneScreen(String backgroundImagePath, float screenWidth, float screenHeight)
    {
        _screenWidth  = screenWidth;
        _screenHeight = screenHeight;

        _batch           = new SpriteBatch();
        _backgroundImage = new Texture(backgroundImagePath);

        _font        = new BitmapFont();
        _glyphLayout = new GlyphLayout();
    }

    public void show ()
    {

    }

    public void render (float deltaTime)
    {
        // Draws the background image
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
        _batch.dispose();
    }
}
