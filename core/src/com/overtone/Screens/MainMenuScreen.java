package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.overtone.Overtone;

/**
 * Screen used for the main menu
 * Created by trevor on 2016-05-01.
 */
public class MainMenuScreen extends OvertoneScreen
{
    public MainMenuScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        final TextButton playButton = CreateTextButton("PLAY", "default", _screenWidth * 0.85f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.21f));
        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.DifficultySelect);
            }
        });

        final TextButton highScoreButton = CreateTextButton("HIGH SCORES", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f));
        highScoreButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.HighScore);
            }
        });

        final TextButton optionsButton = CreateTextButton("OPTIONS", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.375f, _screenHeight * 0.075f));
        optionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.Options);
            }
        });

        final TextButton helpButton = CreateTextButton("HELP", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.675f, _screenHeight * 0.075f));
        helpButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.Help);
            }
        });
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.reset();
        _font.getData().setScale(1);
        _glyphLayout.setText(_font,"Trevor Ware");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.965f - (_glyphLayout.width / 2.0f), _screenHeight * 0.035f - (_glyphLayout.height / 2.0f));

        _glyphLayout.reset();
        _font.getData().setScale(1);
        _glyphLayout.setText(_font,"Version 1.0");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.035f - (_glyphLayout.width / 2.0f), _screenHeight * 0.035f - (_glyphLayout.height / 2.0f));

        _batch.end();

        _font.getData().scale(1);
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
