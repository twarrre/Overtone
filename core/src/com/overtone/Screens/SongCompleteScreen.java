package com.overtone.Screens;

import com.badlogic.gdx.Gdx;

/**
 * Screen used for the song complete screen
 * Created by trevor on 2016-05-01.
 */
public class SongCompleteScreen extends OvertoneScreen
{
    public SongCompleteScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    public void show()
    {
        Gdx.input.setInputProcessor(_stage);
    }

    public void dispose ()
    {
        super.dispose();
    }
}