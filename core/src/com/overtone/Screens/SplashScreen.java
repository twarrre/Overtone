package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.overtone.InputManager;
import com.overtone.Overtone;

/**
 * Screen used for the splash screen
 * Created by trevor on 2016-05-01.
 */
public class SplashScreen extends OvertoneScreen
{
    // Amount of time the splash screen stays on screen
    private static final float SPLASH_TIME = 3.0f;

    private final Texture      _splash;
    private final InputManager _input;
    private float              _elapsedTime;

    /**
     * Constructor
     * @param screenWidth The width of the screen
     * @param screenHeight The height of the screen
     */
    public SplashScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _splash      = new Texture("Textures//splash.jpg");
        _input       = new InputManager();
        _elapsedTime = 0;
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();
        _batch.draw(_splash, 0, 0, _screenWidth, _screenHeight);
        _batch.end();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        _input.Update();
        _elapsedTime += deltaTime;

        if (_elapsedTime >= SPLASH_TIME
                || _input.ActionOccurred(InputManager.KeyBinding.Enter, InputManager.ActionType.Pressed)
                || Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                || Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
            Overtone.SetScreen(Overtone.Screens.MainMenu);
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    public void show() {}

    public void hide() {}

    public void dispose ()
    {
        super.dispose();
        _splash.dispose();
    }
}