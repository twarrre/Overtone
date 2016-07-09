package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.overtone.Overtone;

/**
 * Screen used for options
 * Created by trevor on 2016-05-01.
 */
public class OptionsScreen extends OvertoneScreen
{
    public OptionsScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        final TextButton backButton = CreateTextButton("CONFIRM", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2175f, _screenHeight * 0.075f));
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        final TextButton startButton = CreateTextButton("RESET", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.5475f, _screenHeight * 0.075f));
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Options Reset");
            }
        });
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