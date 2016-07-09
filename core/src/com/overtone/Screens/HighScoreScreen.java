package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.overtone.Overtone;

/**
 * Screen used for high scores
 * Created by trevor on 2016-05-01.
 */
public class HighScoreScreen extends OvertoneScreen
{
    private final Stage _stage;

    public HighScoreScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();

        final TextButton backButton = CreateTextButton("BACK", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2175f, _screenHeight * 0.075f), _stage);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        final TextButton startButton = CreateTextButton("RESET", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.5475f, _screenHeight * 0.075f), _stage);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Scores Reset");
            }
        });
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
        _stage.act(deltaTime);
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
        _stage.getViewport().update(width, height, true);
    }

    public void show()
    {
        Gdx.input.setInputProcessor(_stage);
    }

    public void hide() {Gdx.input.setInputProcessor(null);}

    public void dispose ()
    {
        super.dispose();
        _stage.dispose();
    }
}