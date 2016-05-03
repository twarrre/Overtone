package com.overtone;

import com.badlogic.gdx.Gdx;

/**
 * Created by trevor on 2016-05-01.
 */
public class InputManager
{

    public enum ActionType
    {
        Pressed,
        Held,
        Released,
        Down,
        Up
    }

    public InputManager()
    {

    }

    public boolean ActionOccurred(String actionName, ActionType action)
    {
        switch(action)
        {
            case Pressed:
                return KeyPressed();
            case Held:
                return KeyHeld();
            case Released:
                return KeyReleased();
            case Down:
                return KeyDown();
            case Up:
                return KeyUp();
            default:
                return false;
        }
    }

    private boolean KeyPressed()
    {
        return false;
    }

    private boolean KeyHeld()
    {
        return false;
    }

    private boolean KeyDown()
    {
        return false;
    }

    private boolean KeyReleased()
    {
        return false;
    }

    private boolean KeyUp()
    {
        return false;
    }
}
