package com.overtone.Screens;

import com.badlogic.gdx.Screen;

/**
 * Created by trevor on 2016-06-30.
 */
public interface OvertoneScreenInterface extends Screen
{
    // Called when the screen is made the main one
    void show ();

    void render (float deltaTime);

    void update (float deltaTime);

    void resize (int width, int height);

    void pause ();

    void resume ();

    void hide ();

    void dispose ();
}
