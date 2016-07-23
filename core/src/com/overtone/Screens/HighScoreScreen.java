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
import sun.util.resources.cldr.de.CalendarData_de_LI;

/**
 * Screen used for high scores
 * Created by trevor on 2016-05-01.
 */
public class HighScoreScreen extends OvertoneScreen
{
    private final Stage       _stage;
    private final TextButton  _easyButton;
    private final TextButton  _normalButton;
    private final TextButton  _hardButton;
    private final TextButton  _backButton;
    private TextButton        _currentButton;
    private int               _difficultyIndex;

    public HighScoreScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage           = new Stage();
        _difficultyIndex = 0;

        _backButton = CreateTextButton("back", "default", _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f), _stage);
        _backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_backButton.isDisabled())
                    return;
                _buttonPress.play(Overtone.SFXVolume);
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        _easyButton   = CreateTextButton("EASY", "group", _screenWidth * 0.2f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2075f, _screenHeight * 0.75f),_stage);
        _easyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _currentButton.setChecked(false);
                _easyButton.setChecked(true);
                _currentButton = _easyButton;
                _difficultyIndex = 0;
                _buttonPress.play(Overtone.SFXVolume);
            }
        });

        _normalButton = CreateTextButton("NORMAL", "group", _screenWidth * 0.2f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.4075f, _screenHeight * 0.75f), _stage);
        _normalButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _currentButton.setChecked(false);
                _normalButton.setChecked(true);
                _currentButton = _normalButton;
                _difficultyIndex = 1;
                _buttonPress.play(Overtone.SFXVolume);
            }
        });

        _hardButton   = CreateTextButton("HARD", "group", _screenWidth * 0.2f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.6075f, _screenHeight * 0.75f), _stage);
        _hardButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _currentButton.setChecked(false);
                _hardButton.setChecked(true);
                _currentButton = _hardButton;
                _difficultyIndex = 2;
                _buttonPress.play(Overtone.SFXVolume);

            }
        });

        _currentButton = _easyButton;
        _currentButton.setChecked(true);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.setText(_font36,  "High Scores");
        _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.92f);

        for(int i = 0; i < Overtone.NUM_SCORES; i++)
        {
            _glyphLayout.setText(_font24, (i + 1) + "");
            _font24.draw(_batch, _glyphLayout, _screenWidth * 0.3075f - (_glyphLayout.width / 2.0f), _screenHeight * 0.7f - (_screenHeight * 0.07f * (float)i));

            _glyphLayout.setText(_font24, Overtone.HighScores[_difficultyIndex][i] + "");
            _font24.draw(_batch, _glyphLayout, _screenWidth * 0.5075f - (_glyphLayout.width / 2.0f), _screenHeight * 0.7f - (_screenHeight * 0.07f * (float)i));

            _glyphLayout.setText(_font24, Overtone.CrowdRatings[_difficultyIndex][i] + "");
            _font24.draw(_batch, _glyphLayout, _screenWidth * 0.7075f - (_glyphLayout.width / 2.0f), _screenHeight * 0.7f - (_screenHeight * 0.07f * (float)i));
        }

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
        Gdx.input.setInputProcessor(_stage);
    }

    public void hide() {Gdx.input.setInputProcessor(null);}

    public void dispose ()
    {
        super.dispose();
        _stage.dispose();
    }
}