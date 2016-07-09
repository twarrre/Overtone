package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.overtone.Overtone;

/**
 * Screen used form the difficulty select screen
 * Created by trevor on 2016-05-01.
 */
public class DifficultySelectScreen extends OvertoneScreen
{
    public DifficultySelectScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
       super(backgroundImagePath, screenWidth, screenHeight);

        final TextButton startButton = CreateTextButton("START", "default", _screenWidth * 0.3975f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f));
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.Gameplay);
            }
        });

        final TextButton backButton = CreateTextButton("BACK", "default", _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f));
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
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