package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Notes.Note;
import com.overtone.Overtone;
import java.io.*;


/**
 * Screen used form the difficulty select screen
 * Created by trevor on 2016-05-01.
 */
public class DifficultySelectScreen extends OvertoneScreen
{
    private final Stage _stage;
    private int _difficultyIndex;
    private final TextButton _easyButton;
    private final TextButton _normalButton;
    private final TextButton _hardButton;
    private TextButton _currentButton;

    public DifficultySelectScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();
        _difficultyIndex = 0;

        final TextButton startButton = CreateTextButton("START", "default", _screenWidth * 0.85f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f),_stage);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.Gameplay);
            }
        });

        final TextButton backButton = CreateTextButton("BACK", "default", _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f), _stage);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        _easyButton   = CreateTextButton("EASY", "group", _screenWidth * 0.1325f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.3f),_stage);
        _easyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone._difficulty = Note.DifficultyMultiplier.easy;
                _difficultyIndex = 0;
                _currentButton.setChecked(false);
                _easyButton.setChecked(true);
                _currentButton = _easyButton;
            }
        });

        _normalButton = CreateTextButton("NORMAL", "group", _screenWidth * 0.1325f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.2075f, _screenHeight * 0.3f), _stage);
        _normalButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone._difficulty = Note.DifficultyMultiplier.normal;
                _difficultyIndex = 1;
                _currentButton.setChecked(false);
                _normalButton.setChecked(true);
                _currentButton = _normalButton;
            }
        });

        _hardButton   = CreateTextButton("HARD", "group", _screenWidth * 0.1325f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.34f, _screenHeight * 0.3f), _stage);
        _hardButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone._difficulty = Note.DifficultyMultiplier.hard;
                _difficultyIndex = 2;
                _currentButton.setChecked(false);
                _hardButton.setChecked(true);
                _currentButton = _hardButton;
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

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font,  "High Score: " + Overtone._scores[_difficultyIndex][0]);
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.355f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font,  "Rating: Brilliant");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.43f);

        _glyphLayout.reset();
        _font.getData().setScale(3);
        _glyphLayout.setText(_font,  "Choose your Difficulty");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.9f);

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