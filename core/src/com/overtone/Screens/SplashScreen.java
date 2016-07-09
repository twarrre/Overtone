package com.overtone.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.overtone.InputManager;
import com.overtone.Overtone;

/**
 * Screen used for the song complete screen
 * Created by trevor on 2016-05-01.
 */
public class SplashScreen extends OvertoneScreen
{
    private static final float SPLASH_TIME = 3000;
    private float _startTime;
    private final Texture _splash;
    private final InputManager _input;

    public SplashScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
        _splash = new Texture("Textures//splash.jpg");

        _input = new InputManager();
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

        if (TimeUtils.millis() > (_startTime + SPLASH_TIME))
            Overtone.SetScreen(Overtone.Screens.MainMenu);

        if(_input.ActionOccurred(InputManager.KeyBinding.Enter, InputManager.ActionType.Pressed))
            Overtone.SetScreen(Overtone.Screens.MainMenu);
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    public void show()
    {
        _startTime = TimeUtils.millis();
    }

    public void hide() {}

    public void dispose ()
    {
        super.dispose();
        _splash.dispose();
    }
}