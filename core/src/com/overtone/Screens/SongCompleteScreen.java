package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;
import com.overtone.Ratings.Rating;
import com.overtone.Utilities;

/**
 * Screen used for the song complete screen
 * Created by trevor on 2016-05-01.
 */
public class SongCompleteScreen extends OvertoneScreen
{
    private final Stage       _stage;                   // Stage, stores buttons and such
    private final int         _score;                   // Score the player got previously
    private final String      _songCompleted;           // Displays whether the player completed the song or failed
    private final int[]       _counters;                // Array of different note rating counters
    private final String      _newHighScore;            // True if there is a new high score, false otherwise
    private final Image       _background;              // Background image for the "Did you like this" screen
    private final Button      _yesButton;               // Yes button for the "Did you like this" screen
    private final Button      _noButton;                // No button for the "Did you like this" screen
    private final Button      _retryButton;             // Retry button
    private final Button      _menuButton;              // Return to main menu button
    private final Button      _difficultyButton;        // Change difficulty button
    private Overtone.Screens  _nextScreen;              // Stores which screen to transition to next
    private boolean           _showConfirmationScreen;  // True if showing the "Did you like this" screen, false otherwise

    /**
     * Constructor
     * @param completed True if the song was completed successfully, false otherwise
     * @param score The score that the player got during the song
     * @param counters Counters for each type of rating for each note in the song
     */
    public SongCompleteScreen(boolean completed, int score, int ... counters)
    {
        super();
        _stage                  = new Stage();
        _score                  = score;
        _songCompleted          = completed ? "Song Completed!" : "Song Failed...";
        _counters               = counters;
        _nextScreen             = Overtone.Screens.MainMenu;
        _showConfirmationScreen = false;
        _newHighScore           = _score > Overtone.HighScores[Overtone.Difficulty.ordinal()][0] ? "New High Score!!" : "";

        // Set up retry button
        _retryButton = CreateButton("RETRY", "default", Overtone.ScreenWidth * 0.25f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.075f), _stage);
        _retryButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(Overtone.Screens.Loading);
                Overtone.Regenerate = false;
            }
        });

        // Set up main menu button
        _menuButton = CreateButton("MAIN MENU", "default", Overtone.ScreenWidth * 0.25f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.375f, Overtone.ScreenHeight * 0.075f), _stage);
        _menuButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(Overtone.Screens.MainMenu);
            }
        });

        // Set up change difficulty button
        _difficultyButton = CreateButton("DIFFICULTY", "default", Overtone.ScreenWidth * 0.25f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.675f, Overtone.ScreenHeight * 0.075f), _stage);
        _difficultyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(Overtone.Screens.DifficultySelect);
                Overtone.Regenerate = false;
            }
        });

        // Set up background for confirmation screen
        _background = new Image(new Texture(Gdx.files.internal("Textures\\background.png")));
        _background.setWidth(Overtone.ScreenWidth * 0.85f);
        _background.setHeight(Overtone.ScreenHeight * 0.75f);
        _background.setPosition(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.125f);
        _stage.addActor(_background);
        _background.setVisible(false);

        // Set up yes button for confirmation screen
        _yesButton = CreateButton(null, "yesButtons", Overtone.ScreenWidth * 0.1f, Overtone.ScreenWidth * 0.1f, new Vector2(Overtone.ScreenWidth * 0.375f, Overtone.ScreenHeight * 0.2f), _stage);
        _yesButton.setDisabled(true);
        _yesButton.setVisible(false);
        _yesButton.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _accept.play(Overtone.SFXVolume);
            Overtone.SetScreen(_nextScreen);
        }});

        // Set up yes button for confirmation screen
        _noButton = CreateButton(null, "noButtons", Overtone.ScreenWidth * 0.1f, Overtone.ScreenWidth * 0.1f, new Vector2(Overtone.ScreenWidth * 0.525f, Overtone.ScreenHeight * 0.2f), _stage);
        _noButton.setDisabled(true);
        _noButton.setVisible(false);
        _noButton.addListener(new ClickListener() { public void clicked (InputEvent i, float x, float y) {
            _decline.play(Overtone.SFXVolume);
            Overtone.SetScreen(_nextScreen);
        }});

        // Update the score if necessary
        Utilities.UpdateScore(_score, Overtone.CrowdRating.GetRating(_counters), Overtone.Difficulty);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        // Render the header
        _glyphLayout.setText(_font36, _songCompleted);
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - _glyphLayout.width / 2.0f, Overtone.ScreenHeight * 0.92f);

        _glyphLayout.setText(_font30, "Stats");
        _font30.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - _glyphLayout.width / 2.0f, Overtone.ScreenHeight * 0.75f);

        _glyphLayout.setText(_font24, "Difficulty:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.65f);

        _glyphLayout.setText(_font24, "Rating:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.57f);

        _glyphLayout.setText(_font24, "Score: ");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.49f);

        _glyphLayout.setText(_font24, "High Score: ");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.575f, Overtone.ScreenHeight * 0.41f);

        _glyphLayout.setText(_font24, "" + Overtone.Difficulty.toString());
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.925f - _glyphLayout.width, Overtone.ScreenHeight * 0.65f);

        _glyphLayout.setText(_font24, "" + Overtone.CrowdRating.GetRating(_counters).toString());
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.925f - _glyphLayout.width, Overtone.ScreenHeight * 0.57f);

        _glyphLayout.setText(_font24, "" + _score);
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.925f - _glyphLayout.width, Overtone.ScreenHeight * 0.49f);

        _glyphLayout.setText(_font24, Overtone.HighScores[Overtone.Difficulty.ordinal()][0] + "");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.925f - _glyphLayout.width, Overtone.ScreenHeight * 0.41f);

        _glyphLayout.setText(_font24, _newHighScore);
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - _glyphLayout.width / 2.0f, Overtone.ScreenHeight * 0.85f);

        for(int i = 0; i < _counters.length; i++)
        {
            _glyphLayout.setText(_font24, Rating.RatingType.values()[i].toString() + ": ");
            _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.65f - (Overtone.ScreenHeight * 0.07f * (float)i));

            _glyphLayout.setText(_font24, "" + _counters[i]);
            _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.425f - _glyphLayout.width, Overtone.ScreenHeight * 0.65f - (Overtone.ScreenHeight * 0.07f * (float)i));
        }

        _batch.end();
        _stage.draw();

        if(_showConfirmationScreen)
        {
            _batch.begin();
            _glyphLayout.setText(_font36,  "Did you like this song?");
            _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.7f);
            _batch.end();
        }
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        _stage.act(deltaTime);
    }
    public void resize(int width, int height) {
        super.resize(width, height);
        _stage.getViewport().update(width, height, true);
    }
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(_stage);
    }
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
    public void dispose () {
        super.dispose();
        _stage.dispose();
    }

    /**
     * Called when a button is pressed to disable and re-enable other buttons
     * @param screen The next screen to transition to
     */
    private void ButtonPress(Overtone.Screens screen)
    {
        // Play sound effects for warning and button presses
        _buttonPress.play(Overtone.SFXVolume);
        _warning.play(Overtone.SFXVolume);

        // Set variables
        _nextScreen             = screen;
        _showConfirmationScreen = true;

        // Show the confirmation screen
        _background.setVisible(true);
        _yesButton.setDisabled(false);
        _yesButton.setVisible(true);
        _noButton.setDisabled(false);
        _noButton.setVisible(true);
    }
}