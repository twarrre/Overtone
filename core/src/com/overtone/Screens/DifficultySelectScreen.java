package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;

/**
 * Screen used form the difficulty select screen
 * Created by trevor on 2016-05-01.
 */
public class DifficultySelectScreen extends OvertoneScreen
{
    private final Stage  _stage;           // Stage to hold buttons and stuff
    private final Button _easyButton;      // The button for the easy difficulty
    private final Button _normalButton;    // The button for the normal difficulty
    private final Button _hardButton;      // The button for the hard difficultly
    private Button       _currentButton;   // Current difficulty button pressed
    private int          _difficultyIndex; // Which difficulty is selected right now

    /**
     * Constructor
     */
    public DifficultySelectScreen()
    {
        super();
        _stage           = new Stage();
        _difficultyIndex = 0;

        // Create the star button
        final Button startButton = CreateButton("START", "default", Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.075f), _stage);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.Gameplay);
        }});

        // Create the back button
        final Button backButton = CreateButton("BACK", "small", Overtone.ScreenWidth * 0.08f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.02f, Overtone.ScreenHeight * 0.92f), _stage);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.MainMenu);
        }});

        // Create the easy button
        _easyButton   = CreateButton("EASY", "group", Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.3f),_stage);
        _easyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(0, Overtone.Difficulty.Easy, _easyButton);
            }
        });

        // Create the normal button
        _normalButton = CreateButton("NORMAL", "group", Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.2075f, Overtone.ScreenHeight * 0.3f), _stage);
        _normalButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(1, Overtone.Difficulty.Normal, _normalButton);
            }
        });

        // Create the hard button
        _hardButton   = CreateButton("HARD", "group", Overtone.ScreenWidth * 0.1325f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.34f, Overtone.ScreenHeight * 0.3f), _stage);
        _hardButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(2, Overtone.Difficulty.Hard, _hardButton);
            }
        });

        _currentButton = _easyButton;
        _currentButton.setChecked(true);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _batch.begin();

        _glyphLayout.setText(_font24,  "High Score: " + Overtone.HighScores[_difficultyIndex][0]);
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.355f);

        _glyphLayout.setText(_font24,  "Rating: " +  Overtone.CrowdRatings[_difficultyIndex][0].toString());
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.43f);

        _glyphLayout.setText(_font36,  "Choose your Difficulty");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.92f);

        _batch.end();
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
        super.show();
        Gdx.input.setInputProcessor(_stage);
    }
    public void hide()
    {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
    public void dispose ()
    {
        super.dispose();
        _stage.dispose();
    }

    /**
     * Called when a difficulty button is pressed
     * @param idx The index of the difficulty
     * @param diff THe actual difficulty
     * @param currButton The button pressed
     */
    private void ButtonPress(int idx, Overtone.Difficulty diff, Button currButton)
    {
        _difficultyIndex    = idx;
        Overtone.Difficulty = diff;

        _currentButton.setChecked(false);
        _currentButton = currButton;
        currButton.setChecked(true);

        _buttonPress.play(Overtone.SFXVolume);
    }
}