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

    private final InputManager _input;
    private float              _elapsedTime;

    /**
     * Constructor
     */
    public SplashScreen()
    {
        super();

        _input       = new InputManager();
        _elapsedTime = 0;
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.setText(_font36, "Trevor Ware");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.6f - (_glyphLayout.height / 2.0f));

        _glyphLayout.setText(_font24, "A00844405");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.1f - (_glyphLayout.height / 2.0f));

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
    }
}