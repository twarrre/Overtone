package com.overtone.Screens;

import com.badlogic.gdx.Screen;

/**
 * New Screen interface that extends the libgdx one.
 * Used to provide an Update method
 * Created by trevor on 2016-06-30.
 */
public interface OvertoneScreenInterface extends Screen
{
    /**
     * Called when the screen becomes the one to show on the screen
     */
    void show ();

    /**
     * Renders the graphics obejcts
     * @param deltaTime the time since the last frame
     */
    void render (float deltaTime);

    /**
     * Used to update game obejcts, logic, etc
     * @param deltaTime The time since the last frame
     */
    void update (float deltaTime);

    /**
     * Handles resizing the window
     * @param width The new width of the window
     * @param height The new height of the windows
     */
    void resize (int width, int height);

    /**
     * Handles when the screen is paused
     */
    void pause ();

    /**
     * Handles when the screen is resumed
     */
    void resume ();

    /**
     * Handles when the screen is hidden
     */
    void hide ();

    /**
     * Handles the destruction of the screen
     */
    void dispose ();
}
