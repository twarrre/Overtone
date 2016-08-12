package com.overtone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles input for the Game.
 *
 * Created by trevor on 2016-05-01.
 */
public class InputManager
{
    /**Represents all of the different types of inputs taken from the keyboard*/
    public enum ActionType
    {
        Pressed,
        Held,
        Released,
        Up
    }

    /**Stores all of the different key codes that are being tracked in the game*/
    public enum KeyBinding
    {
        TopLeft     (Input.Keys.E),
        TopRight    (Input.Keys.I),
        BottomLeft  (Input.Keys.D),
        BottomRight (Input.Keys.K),
        Pause       (Input.Keys.ESCAPE),
        Enter       (Input.Keys.ENTER);

        KeyBinding(int key) { this.keyCode = key; }
        public int keyCode; // The code for the enum value
    }

    private Map<Integer, Boolean> _prevKeyboardState;    // Stores the state of the keyboard from the previous frame
    private Map<Integer, Boolean> _currentKeyBoardState; // Stores the current keyboard state for this frame.

    /**
     * Constructor
     */
    public InputManager()
    {
        // Initialize the keyboard states to false
        _currentKeyBoardState = new HashMap<>();
        _prevKeyboardState    = new HashMap<>();
        KeyBinding[] bindings = KeyBinding.values();

        for(int i = 0; i < KeyBinding.values().length; ++i)
        {
            _currentKeyBoardState.put(bindings[i].keyCode, false);
            _prevKeyboardState.put(bindings[i].keyCode, false);
        }
    }

    /**
     * Called every frame. Keeps track of the state of all keyboard keys being tracked.
     */
    public void Update()
    {
        for(Integer key : _currentKeyBoardState.keySet())
        {
            _prevKeyboardState.put(key, _currentKeyBoardState.get(key));
            _currentKeyBoardState.put(key, Gdx.input.isKeyPressed(key));
        }
    }

    /**
     * Called to check if a particular keyboard action occurred and calls the appropriate method to handle the action.
     * @param key - the key for which we are checking if an action occurred
     * @param action - the action that we are checking to see if occurred
     * @return true if the action occurred, false otherwise
     */
    public boolean ActionOccurred(KeyBinding key, ActionType action)
    {
        switch(action)
        {
            case Pressed:
                return KeyPressed(key.keyCode);
            case Held:
                return KeyHeld(key.keyCode);
            case Released:
                return KeyReleased(key.keyCode);
            case Up:
                return KeyUp(key.keyCode);
            default:
                return false;
        }
    }

    /**
     * Checks to see if a key was pressed.
     * @param key the key that we want to see if it was pressed
     * @return true if the key was pressed, false otherwise
     */
    private boolean KeyPressed(int key)
    {
        if(_prevKeyboardState.containsKey(key) && _currentKeyBoardState.containsKey(key))
            return !_prevKeyboardState.get(key) && _currentKeyBoardState.get(key);
        else
            return false;
    }

    /**
     * Checks to see if a key was held.
     * @param key the key that we want to see if it was held
     * @return true if the key was held, false otherwise
     */
    private boolean KeyHeld(int key)
    {
        if(_prevKeyboardState.containsKey(key) && _currentKeyBoardState.containsKey(key))
            return _currentKeyBoardState.get(key) && _prevKeyboardState.get(key);
        else
            return false;
    }

    /**
     * Checks to see if a key was released.
     * @param key the key that we want to see if it was released
     * @return true if the key was released, false otherwise
     */
    private boolean KeyReleased(int key)
    {
        if(_prevKeyboardState.containsKey(key) && _currentKeyBoardState.containsKey(key))
            return (!_currentKeyBoardState.get(key)) && _prevKeyboardState.get(key);
        else
            return false;
    }

    /**
     * Checks to see if a key is up.
     * @param key the key that we want to see if it is up
     * @return true if the key is up, false otherwise
     */
    private boolean KeyUp(int key)
    {
        if(_prevKeyboardState.containsKey(key) && _currentKeyBoardState.containsKey(key))
            return !_currentKeyBoardState.get(key);
        else
            return false;
    }

    public void dispose()
    {
        _prevKeyboardState.clear();
        _currentKeyBoardState.clear();
    }
}
