package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.overtone.InputManager;
import com.overtone.Overtone;

/**
 * Screen used for the splash screen
 * Created by trevor on 2016-05-01.
 */
public class SplashScreen extends OvertoneScreen
{
    /**The amount of time the screen stays on screen*/
    private static final float SPLASH_TIME = 3.0f;

    private final InputManager _inputManager;       // Manager for input
    private float              _elapsedTime; // amount of time the screen has been rendered

    /**
     * Constructor
     */
    public SplashScreen()
    {
        super();
        _inputManager = new InputManager();
        _elapsedTime  = 0;
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

        _inputManager.Update();
        _elapsedTime += deltaTime;

        if (_elapsedTime >= SPLASH_TIME
                || Gdx.input.isButtonPressed(Input.Keys.ENTER)
                || Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                || Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
            Overtone.SetScreen(Overtone.Screens.MainMenu);
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
    }
    public void show() {super.show();}
    public void hide() {super.hide();}
    public void dispose ()
    {
        super.dispose();
    }
}