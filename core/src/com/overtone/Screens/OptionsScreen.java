package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;

/**
 * Screen used for options
 * Created by trevor on 2016-05-01.
 */
public class OptionsScreen extends OvertoneScreen
{
    private final Stage _stage;

    public OptionsScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();

        final TextButton backButton = CreateTextButton("Back", "default", _screenWidth * 0.58f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2175f, _screenHeight * 0.075f), _stage);
        backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _buttonPress.play(Overtone.SFXVolume);
                Overtone.WriteVolume();
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        final ImageButton musicNext = CreateImageButton("nextButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.7725f, _screenHeight * 0.7f - _screenWidth * 0.025f), _stage);
        musicNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.MusicVolume += 0.01f;
            if(Overtone.MusicVolume > 1.0f)
                Overtone.MusicVolume = 1.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final ImageButton musicBack = CreateImageButton("backButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.6f, _screenHeight * 0.7f - _screenWidth * 0.025f), _stage);
        musicBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.MusicVolume -= 0.01f;
            if(Overtone.MusicVolume < 0.0f)
                Overtone.MusicVolume = 0.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final ImageButton sfxNext = CreateImageButton("nextButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.7725f, _screenHeight * 0.58f - _screenWidth * 0.025f), _stage);
        sfxNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.SFXVolume += 0.01f;
            if(Overtone.SFXVolume > 1.0f)
                Overtone.SFXVolume = 1.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final ImageButton sfxBack = CreateImageButton("backButton",_screenWidth * 0.025f, _screenWidth * 0.025f,  new Vector2(_screenWidth * 0.6f, _screenHeight * 0.58f - _screenWidth * 0.025f), _stage);
        sfxBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.SFXVolume -= 0.01f;
            if(Overtone.SFXVolume < 0.0f)
                Overtone.SFXVolume = 0.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final TextButton clearDataButton = CreateTextButton("Clear Data", "default", _screenWidth * 0.2f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.6f, _screenHeight * 0.42f), _stage);
        clearDataButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _buttonPress.play(Overtone.SFXVolume);
                System.out.println("Data has been Cleared.");
            }
        });

        final TextButton clearScoresButton = CreateTextButton("Clear Scores", "default", _screenWidth * 0.2f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.6f, _screenHeight * 0.30f), _stage);
        clearScoresButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _buttonPress.play(Overtone.SFXVolume);
            }
        });
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();

        _batch.begin();

        _glyphLayout.setText(_font36, "Options");
        _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.92f);

        _glyphLayout.setText(_font24, "Music Volume:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.7f);

        _glyphLayout.setText(_font24, (int)(Overtone.MusicVolume * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.7f - (_glyphLayout.width / 2.0f), _screenHeight * 0.7f);

        _glyphLayout.setText(_font24, "SFX Volume:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.58f);

        _glyphLayout.setText(_font24, (int)(Overtone.SFXVolume * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.7f - (_glyphLayout.width / 2.0f), _screenHeight * 0.58f);

        _glyphLayout.setText(_font24, "Saved Data:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.46f);

        _glyphLayout.setText(_font24, "High Scores:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.34f);

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