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
import com.overtone.Ratings.Rating;

/**
 * Screen used for the song complete screen
 * Created by trevor on 2016-05-01.
 */
public class SongCompleteScreen extends OvertoneScreen
{
    private final Stage       _stage;
    private final int         _score;
    private final String      _songCompleted;
    private final int[]       _counters;
    private final ImageButton _yesButton;
    private final ImageButton _noButton;
    private final TextButton  _retryButton;
    private final TextButton  _menuButton;
    private final TextButton  _difficultyButton;
    private final Image       _background;

    private Overtone.Screens _nextScreen;
    private boolean          _showConfirmationScreen;
    private boolean          _newHighScore;

    /**
     * Constructor
     * @param screenWidth The width of the screen
     * @param screenHeight The height of the screen
     * @param completed True if the song was completed successfully, false otherwise
     * @param score The score that the player got during the song
     * @param counters Counters for each type of rating for each note in the song
     */
    public SongCompleteScreen(int screenWidth, int screenHeight, boolean completed, int score, int ... counters)
    {
        super(screenWidth, screenHeight);

        _stage                   = new Stage();
        _score                  = score;
        _songCompleted          = completed ? "Song Completed!" : "Song Failed...";
        _counters               = counters;
        _nextScreen             = Overtone.Screens.MainMenu;
        _showConfirmationScreen = false;

        // Set up retry button
        _retryButton = CreateTextButton("RETRY", "default", _screenWidth * 0.25f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f), _stage);
        _retryButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _nextScreen = Overtone.Screens.Gameplay;
                _background.setVisible(true);
                _yesButton.setDisabled(false);
                _yesButton.setVisible(true);
                _noButton.setDisabled(false);
                _noButton.setVisible(true);
                _showConfirmationScreen = true;
            }
        });

        // Set up main menu button
        _menuButton = CreateTextButton("MAIN MENU", "default", _screenWidth * 0.25f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.375f, _screenHeight * 0.075f), _stage);
        _menuButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _nextScreen = Overtone.Screens.MainMenu;
                _background.setVisible(true);
                _yesButton.setDisabled(false);
                _yesButton.setVisible(true);
                _noButton.setDisabled(false);
                _noButton.setVisible(true);
                _showConfirmationScreen = true;
            }
        });

        // Set up change difficulty button
        _difficultyButton = CreateTextButton("MODIFY DIFFICULTY", "default", _screenWidth * 0.25f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.675f, _screenHeight * 0.075f), _stage);
        _difficultyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _nextScreen = Overtone.Screens.DifficultySelect;
                _background.setVisible(true);
                _yesButton.setDisabled(false);
                _yesButton.setVisible(true);
                _noButton.setDisabled(false);
                _noButton.setVisible(true);
                _showConfirmationScreen = true;
            }
        });

        // Set up background for confirmation screen
        _background = new Image(new Texture(Gdx.files.internal("Textures\\background.png")));
        _background.setWidth(_screenWidth * 0.85f);
        _background.setHeight(_screenHeight * 0.75f);
        _background.setPosition(screenWidth * 0.075f, _screenHeight * 0.125f);
        _stage.addActor(_background);
        _background.setVisible(false);

        // Set up yes button for confirmation screen
        _yesButton = CreateImageButton("yesButtons",_screenWidth * 0.1f, _screenWidth * 0.1f, new Vector2(_screenWidth * 0.375f, _screenHeight * 0.2f), _stage);
        _yesButton.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {Overtone.SetScreen(_nextScreen);}});
        _yesButton.setDisabled(true);
        _yesButton.setVisible(false);

        // Set up yes button for confirmation screen
        _noButton = CreateImageButton("noButtons",_screenWidth * 0.1f, _screenWidth * 0.1f, new Vector2(_screenWidth * 0.525f, _screenHeight * 0.2f), _stage);
        _noButton.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {Overtone.SetScreen(_nextScreen);}});
        _noButton.setDisabled(true);
        _noButton.setVisible(false);

        _newHighScore = _score > Overtone.HighScores[Overtone.Difficulty.ordinal()][0];
        // Update the score if necessary
        Overtone.UpdateScore(_score, Overtone.CrowdRating.GetRating(_counters), Overtone.Difficulty);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.setText(_font36, _songCompleted);
        _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.92f);

        _glyphLayout.setText(_font30, "Stats");
        _font30.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.75f);

        _glyphLayout.setText(_font24, "Difficulty:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.65f);

        _glyphLayout.setText(_font24, "Rating:");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.57f);

        _glyphLayout.setText(_font24, "Score: ");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.49f);

        _glyphLayout.setText(_font24, "High Score: ");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.41f);

        _glyphLayout.setText(_font24, "" + Overtone.Difficulty.toString());
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.65f);

        _glyphLayout.setText(_font24, "" + Overtone.CrowdRating.GetRating(_counters).toString());
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.57f);

        _glyphLayout.setText(_font24, "" + _score);
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.49f);

        _glyphLayout.setText(_font24, (Overtone.HighScores[Overtone.Difficulty.ordinal()][0] > _score) ? Overtone.HighScores[Overtone.Difficulty.ordinal()][0] + "" : _score + "");
        _font24.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.41f);

        if(_newHighScore)
        {
            _glyphLayout.setText(_font24, "New High Score!!");
            _font24.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.85f);
        }

        for(int i = 0; i < _counters.length; i++)
        {
            _glyphLayout.setText(_font24, Rating.RatingType.values()[i].toString() + ": ");
            _font24.draw(_batch, _glyphLayout, _screenWidth * 0.075f, _screenHeight * 0.65f - (_screenHeight * 0.07f * (float)i));
            _glyphLayout.setText(_font24, "" + _counters[i]);
            _font24.draw(_batch, _glyphLayout, _screenWidth * 0.425f - _glyphLayout.width, _screenHeight * 0.65f - (_screenHeight * 0.07f * (float)i));
        }

        _batch.end();

        _stage.draw();
        if(_showConfirmationScreen)
        {
            _batch.begin();
            _glyphLayout.setText(_font36,  "Did you like this song?");
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