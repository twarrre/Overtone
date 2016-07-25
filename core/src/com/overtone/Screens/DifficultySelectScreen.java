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
 * Screen used form the difficulty select screen
 * Created by trevor on 2016-05-01.
 */
public class DifficultySelectScreen extends OvertoneScreen
{
    private final Stage      _stage;
    private final TextButton _easyButton;
    private final TextButton _normalButton;
    private final TextButton _hardButton;

    private TextButton       _currentButton;
    private int              _difficultyIndex;

    public DifficultySelectScreen()
    {
        super();
        _stage           = new Stage();
        _difficultyIndex = 0;

        final TextButton startButton = CreateTextButton("START", "default", Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.075f),_stage);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {_buttonPress.play(Overtone.SFXVolume); Overtone.SetScreen(Overtone.Screens.Gameplay);}
        });

        final TextButton backButton = CreateTextButton("BACK", "default", Overtone.ScreenWidth * 0.11f, Overtone.ScreenHeight * 0.08f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.845f), _stage);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {_buttonPress.play(Overtone.SFXVolume); Overtone.SetScreen(Overtone.Screens.MainMenu);}});

        _easyButton   = CreateTextButton("EASY", "group", Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.3f),_stage);
        _easyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone.Difficulty = Overtone.Difficulty.Easy;
                _difficultyIndex = 0;
                _currentButton.setChecked(false);
                _easyButton.setChecked(true);
                _currentButton = _easyButton;
                _buttonPress.play(Overtone.SFXVolume);
            }
        });

        _normalButton = CreateTextButton("NORMAL", "group", Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.2075f, Overtone.ScreenHeight * 0.3f), _stage);
        _normalButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone.Difficulty = Overtone.Difficulty.Normal;
                _difficultyIndex = 1;
                _currentButton.setChecked(false);
                _normalButton.setChecked(true);
                _currentButton = _normalButton;
                _buttonPress.play(Overtone.SFXVolume);
            }
        });

        _hardButton   = CreateTextButton("HARD", "group", Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.34f, Overtone.ScreenHeight * 0.3f), _stage);
        _hardButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone.Difficulty = Overtone.Difficulty.Hard;
                _difficultyIndex = 2;
                _currentButton.setChecked(false);
                _hardButton.setChecked(true);
                _currentButton = _hardButton;
                _buttonPress.play(Overtone.SFXVolume);
            }
        });

        _currentButton = _easyButton;
        _currentButton.setChecked(true);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();

        _batch.begin();

        _glyphLayout.setText(_font24,  "High Score: " + Overtone.HighScores[_difficultyIndex][0]);
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.355f);

        _glyphLayout.setText(_font24,  "Rating: " +  Overtone.CrowdRatings[_difficultyIndex][0].toString());
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.43f);

        _glyphLayout.setText(_font36,  "Choose your Difficulty");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.92f);
        _batch.end();
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