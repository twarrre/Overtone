package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;

/**
 * Screen used for the main menu
 * Created by trevor on 2016-05-01.
 */
public class MainMenuScreen extends OvertoneScreen
{
    private final Stage _stage;

    public MainMenuScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();

        final TextButton playButton = CreateTextButton("PLAY", "default", _screenWidth * 0.85f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.21f), _stage);
        playButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {Overtone.SetScreen(Overtone.Screens.DifficultySelect);}
        });

        final TextButton highScoreButton = CreateTextButton("HIGH SCORES", "default", _screenWidth * 0.18f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f), _stage);
        highScoreButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {Overtone.SetScreen(Overtone.Screens.HighScore);}});

        final TextButton optionsButton = CreateTextButton("OPTIONS", "default", _screenWidth * 0.18f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2983f, _screenHeight * 0.075f), _stage);
        optionsButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {Overtone.SetScreen(Overtone.Screens.Options);}
        });

        final TextButton helpButton = CreateTextButton("HELP", "default", _screenWidth * 0.18f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.5217f, _screenHeight * 0.075f), _stage);
        helpButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {Overtone.SetScreen(Overtone.Screens.Help);
            }
        });

        final TextButton quitButton = CreateTextButton("QUIT", "default", _screenWidth * 0.18f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.745f, _screenHeight * 0.075f), _stage);
        quitButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Gdx.app.exit();
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

        _glyphLayout.setText(_font,"Version 1.0");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.035f - (_glyphLayout.width / 2.0f), _screenHeight * 0.035f - (_glyphLayout.height / 2.0f));
        _batch.end();

        _font.getData().scale(1);
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
