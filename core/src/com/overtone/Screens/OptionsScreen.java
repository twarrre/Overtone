package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private boolean _showConfirmationScreen;
    private final Image       _background;
    private final ImageButton _yesButton;
    private final ImageButton _noButton;
    private  final TextButton _backButton;
    private boolean _dataCleared;

    public OptionsScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();
        _showConfirmationScreen = false;
        _dataCleared = true;

        _backButton = CreateTextButton("back", "default",  _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f), _stage);
        _backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_backButton.isDisabled())
                    return;
                _buttonPress.play(Overtone.SFXVolume);
                Overtone.WriteVolume();
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        final ImageButton musicNext = CreateImageButton("nextButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.7725f, _screenHeight * 0.65f - _screenWidth * 0.025f), _stage);
        musicNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.MusicVolume += 0.01f;
            if(Overtone.MusicVolume > 1.0f)
                Overtone.MusicVolume = 1.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final ImageButton musicBack = CreateImageButton("backButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.6f, _screenHeight * 0.65f - _screenWidth * 0.025f), _stage);
        musicBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.MusicVolume -= 0.01f;
            if(Overtone.MusicVolume < 0.0f)
                Overtone.MusicVolume = 0.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final ImageButton sfxNext = CreateImageButton("nextButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.7725f, _screenHeight * 0.5f - _screenWidth * 0.025f), _stage);
        sfxNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.SFXVolume += 0.01f;
            if(Overtone.SFXVolume > 1.0f)
                Overtone.SFXVolume = 1.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final ImageButton sfxBack = CreateImageButton("backButton",_screenWidth * 0.025f, _screenWidth * 0.025f,  new Vector2(_screenWidth * 0.6f, _screenHeight * 0.5f - _screenWidth * 0.025f), _stage);
        sfxBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.SFXVolume -= 0.01f;
            if(Overtone.SFXVolume < 0.0f)
                Overtone.SFXVolume = 0.0f;
            _buttonPress.play(Overtone.SFXVolume);
        }});

        final TextButton clearDataButton = CreateTextButton("Clear Data", "default", _screenWidth * 0.2f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.6f, _screenHeight * 0.31f), _stage);
        clearDataButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _backButton.setDisabled(true);
                _buttonPress.play(Overtone.SFXVolume);
                _background.setVisible(true);
                _yesButton.setVisible(true);
                _noButton.setVisible(true);
                _yesButton.setDisabled(false);
                _noButton.setDisabled(false);
                _showConfirmationScreen = true;
                _warning.play(Overtone.SFXVolume);
                _dataCleared = true;
            }
        });

        final TextButton clearScoresButton = CreateTextButton("Clear Scores", "default", _screenWidth * 0.2f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.6f, _screenHeight * 0.16f), _stage);
        clearScoresButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _backButton.setDisabled(true);
                _buttonPress.play(Overtone.SFXVolume);
                _background.setVisible(true);
                _yesButton.setVisible(true);
                _noButton.setVisible(true);
                _yesButton.setDisabled(false);
                _noButton.setDisabled(false);
                _showConfirmationScreen = true;
                _warning.play(Overtone.SFXVolume);
                _dataCleared = false;
            }
        });

        _background = new Image(new Texture(Gdx.files.internal("Textures\\background.png")));
        _background.setWidth(_screenWidth * 0.85f);
        _background.setHeight(_screenHeight * 0.75f);
        _background.setPosition(screenWidth * 0.075f, _screenHeight * 0.125f);
        _stage.addActor(_background);
        _background.setVisible(false);

        _yesButton = CreateImageButton("yesButtons",_screenWidth * 0.1f, _screenWidth * 0.1f, new Vector2(_screenWidth * 0.375f, _screenHeight * 0.2f), _stage);
        _yesButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_yesButton.isDisabled())
                    return;
                _backButton.setDisabled(false);
                _accept.play(Overtone.SFXVolume);
                _background.setVisible(false);
                _yesButton.setVisible(false);
                _noButton.setVisible(false);
                _yesButton.setDisabled(true);
                _noButton.setDisabled(true);
                _showConfirmationScreen = false;

                if(_dataCleared)
                    System.out.println("Data has been Cleared.");
                else
                    Overtone.WriteScores(true);
            }
        });
        _yesButton.setDisabled(true);
        _yesButton.setVisible(false);

        _noButton = CreateImageButton("noButtons",_screenWidth * 0.1f, _screenWidth * 0.1f, new Vector2(_screenWidth * 0.525f, _screenHeight * 0.2f), _stage);
        _noButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_noButton.isDisabled())
                    return;
                _backButton.setDisabled(false);
                _decline.play(Overtone.SFXVolume);
                _background.setVisible(false);
                _yesButton.setVisible(false);
                _noButton.setVisible(false);
                _yesButton.setDisabled(true);
                _noButton.setDisabled(true);
                _showConfirmationScreen = false;
            }
        });
        _noButton.setDisabled(true);
        _noButton.setVisible(false);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _batch.begin();

        _glyphLayout.setText(_font36, "Options");
        _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.92f);

        _glyphLayout.setText(_font24, "Music Volume:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.65f);

        _glyphLayout.setText(_font24, (int)(Overtone.MusicVolume * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.7f - (_glyphLayout.width / 2.0f), _screenHeight * 0.65f);

        _glyphLayout.setText(_font24, "SFX Volume:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.5f);

        _glyphLayout.setText(_font24, (int)(Overtone.SFXVolume * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.7f - (_glyphLayout.width / 2.0f), _screenHeight * 0.5f);

        _glyphLayout.setText(_font24, "Saved Data:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.35f);

        _glyphLayout.setText(_font24, "High Scores:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.2175f, _screenHeight * 0.2f);

        _batch.end();

        _stage.draw();
        if( _showConfirmationScreen)
        {
            _batch.begin();
            _glyphLayout.setText(_font36,  "Are you sure?");
            _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.7f);
            _batch.end();
        }
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