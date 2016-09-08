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
import com.overtone.Utilities;

/**
 * Screen used for options
 * Created by trevor on 2016-05-01.
 */
public class OptionsScreen extends OvertoneScreen
{
    private final Stage  _stage;                  // Stage to hold the buttons and background
    private final Image  _background;             // The background for the confirmation screen
    private final Button _yesButton;              // The yes button for the confirmation screen
    private final Button _noButton;               // The no button for the confirmation screen
    private final Button _backButton;             // The back button
    private boolean      _dataCleared;            // True if saved data is to be cleared, false if high score data is to be cleared
    private boolean      _showConfirmationScreen; // True if we are showing the confirmation screen, false otherwise

    /**
     * Constructor
     */
    public OptionsScreen()
    {
        super();
        _stage                  = new Stage();
        _showConfirmationScreen = false;
        _dataCleared            = true;

        // Create back button
        _backButton = CreateButton("back", "small",  Overtone.ScreenWidth * 0.08f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.02f, Overtone.ScreenHeight * 0.92f), _stage);
        _backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_backButton.isDisabled())
                    return;

                _buttonPress.play(Overtone.SFXVolume);
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        // Create next button for music volume
        final Button musicNext = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.7725f, Overtone.ScreenHeight * 0.8f - Overtone.ScreenWidth * 0.025f), _stage);
        musicNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.MusicVolume += 0.01f;
            if(Overtone.MusicVolume > 1.0f)
                Overtone.MusicVolume = 1.0f;
        }});

        // Create back button for music volume
        final Button musicBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.8f - Overtone.ScreenWidth * 0.025f), _stage);
        musicBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.MusicVolume -= 0.01f;
            if(Overtone.MusicVolume < 0.0f)
                Overtone.MusicVolume = 0.0f;
        }});

        // Create the next button for sfx
        final Button sfxNext = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.7725f, Overtone.ScreenHeight * 0.7f - Overtone.ScreenWidth * 0.025f), _stage);
        sfxNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SFXVolume += 0.01f;
            if(Overtone.SFXVolume > 1.0f)
                Overtone.SFXVolume = 1.0f;
        }});

        // Create the back button for sfx
        final Button sfxBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.7f - Overtone.ScreenWidth * 0.025f), _stage);
        sfxBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SFXVolume -= 0.01f;
            if(Overtone.SFXVolume < 0.0f)
                Overtone.SFXVolume = 0.0f;
        }});

        // Create the next button for num iterations
        final Button iterNex = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.7725f, Overtone.ScreenHeight * 0.6f - Overtone.ScreenWidth * 0.025f), _stage);
        iterNex.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.NumberOfIterations = (int)Utilities.Clamp(Overtone.NumberOfIterations + 1, 1, 1000);
            Utilities.WriteGenerationValues();
        }});

        // Create the back button for num iterations
        final Button iterBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.6f - Overtone.ScreenWidth * 0.025f), _stage);
        iterBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.NumberOfIterations = (int)Utilities.Clamp(Overtone.NumberOfIterations - 1, 1, 1000);
            Utilities.WriteGenerationValues();
        }});

        // Create the next button for the population size
        final Button popSizeNext = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.7725f, Overtone.ScreenHeight * 0.5f - Overtone.ScreenWidth * 0.025f), _stage);
        popSizeNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PopulationSize = (int)Utilities.Clamp(Overtone.PopulationSize + 1, 4, 1000);
            Utilities.WriteGenerationValues();
        }});

        // Create the back button for the population size
        final Button popSizeBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.5f - Overtone.ScreenWidth * 0.025f), _stage);
        popSizeBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            Overtone.PopulationSize = (int)Utilities.Clamp(Overtone.PopulationSize - 1, 4, 1000);

            if(Overtone.NumberOfElites > (Overtone.PopulationSize * 0.5f))
                Overtone.NumberOfElites = Math.round((Overtone.PopulationSize * 0.5f));

            Utilities.WriteGenerationValues();
        }});

        // Create the next button for the number of elites
        final Button numElitesNext = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.7725f, Overtone.ScreenHeight * 0.4f - Overtone.ScreenWidth * 0.025f), _stage);
        numElitesNext.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.NumberOfElites = (int)Utilities.Clamp(Overtone.NumberOfElites + 1, 2, Overtone.PopulationSize * 0.5f);
            Utilities.WriteGenerationValues();
        }});

        // Create the back button for the number of elites
        final Button numElitesBack = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.4f - Overtone.ScreenWidth * 0.025f), _stage);
        numElitesBack.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.NumberOfElites = (int)Utilities.Clamp(Overtone.NumberOfElites - 1, 2, Overtone.PopulationSize * 0.5f);
            Utilities.WriteGenerationValues();
        }});

        // Create the clear data button
        final Button clearDataButton = CreateButton("Clear Data", "default", Overtone.ScreenWidth * 0.2f, Overtone.ScreenHeight * 0.06f, new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.255f), _stage);
        clearDataButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(true);
            }
        });

        // Create the clear scores button
        final Button clearScoresButton = CreateButton("Clear Scores", "default", Overtone.ScreenWidth * 0.2f, Overtone.ScreenHeight * 0.06f, new Vector2(Overtone.ScreenWidth * 0.6f, Overtone.ScreenHeight * 0.155f), _stage);
        clearScoresButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                ButtonPress(false);
            }
        });

        // Create the clear scores button
        final Button changeMutationValues = CreateButton("Change Mutation Values", "default", Overtone.ScreenWidth * 0.5825f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.0555f), _stage);
        changeMutationValues.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _buttonPress.play(Overtone.SFXVolume);
                Overtone.SetScreen(Overtone.Screens.Mutation);
            }
        });

        // Create the background for the confirmation screen
        _background = new Image(new Texture(Gdx.files.internal("Textures\\background.png")));
        _background.setWidth(Overtone.ScreenWidth * 0.85f);
        _background.setHeight(Overtone.ScreenHeight * 0.75f);
        _background.setPosition(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.125f);
        _stage.addActor(_background);
        _background.setVisible(false);

        // Create the yes button for the confirmation screen
        _yesButton = CreateButton(null, "yesButtons",Overtone.ScreenWidth * 0.1f, Overtone.ScreenWidth * 0.1f, new Vector2(Overtone.ScreenWidth * 0.375f, Overtone.ScreenHeight * 0.2f), _stage);
        _yesButton.setDisabled(true);
        _yesButton.setVisible(false);
        _yesButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            if(_yesButton.isDisabled())
                return;
            ButtonPressConfirmationScreen(true);

            if(_dataCleared)
                Utilities.WriteRaterValues(true);
            else
                Utilities.WriteScores(true);
            }
        });

        // Create the no button for the confirmation screen
        _noButton = CreateButton(null, "noButtons",Overtone.ScreenWidth * 0.1f, Overtone.ScreenWidth * 0.1f, new Vector2(Overtone.ScreenWidth * 0.525f, Overtone.ScreenHeight * 0.2f), _stage);
        _noButton.setDisabled(true);
        _noButton.setVisible(false);
        _noButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            if(_noButton.isDisabled())
                return;
            ButtonPressConfirmationScreen(false);
            }
        });
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _batch.begin();

        _glyphLayout.setText(_font36, "Options");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - _glyphLayout.width / 2.0f, Overtone.ScreenHeight * 0.92f);

        _glyphLayout.setText(_font24, "Music Volume:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.8f);

        _glyphLayout.setText(_font24, (int)(Overtone.MusicVolume * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.7f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.8f);

        _glyphLayout.setText(_font24, "SFX Volume:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.7f);

        _glyphLayout.setText(_font24, (int)(Overtone.SFXVolume * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.7f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.7f);

        _glyphLayout.setText(_font24, "Number of Iterations:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.6f);

        _glyphLayout.setText(_font24, Overtone.NumberOfIterations + "");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.7f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.6f);

        _glyphLayout.setText(_font24, "Population Size:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.5f);

        _glyphLayout.setText(_font24, Overtone.PopulationSize + "");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.7f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.5f);

        _glyphLayout.setText(_font24, "Number of Elites:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.4f);

        _glyphLayout.setText(_font24, Overtone.NumberOfElites + "");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.7f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.4f);

        _glyphLayout.setText(_font24, "Saved Data:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.3f);

        _glyphLayout.setText(_font24, "High Scores:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.2175f, Overtone.ScreenHeight * 0.2f);

        _batch.end();
        _stage.draw();

        if( _showConfirmationScreen)
        {
            _batch.begin();
            _glyphLayout.setText(_font36,  "Are you sure?");
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
     * Called when a button is pressed
     * @param dataCleared true if the data cleared button was pressed, false if the clear high scores one was pressed
     */
    private void ButtonPress(boolean dataCleared)
    {
        _dataCleared            = dataCleared;
        _showConfirmationScreen = true;

        _buttonPress.play(Overtone.SFXVolume);
        _warning.play(Overtone.SFXVolume);

        _backButton.setDisabled(true);
        _background.setVisible(true);
        _yesButton.setVisible(true);
        _noButton.setVisible(true);
        _yesButton.setDisabled(false);
        _noButton.setDisabled(false);
    }

    /**
     * Called when a button on the confirmation screen was pressed
     * @param accept True if the accept button was pressed, false if the decline button was pressed
     */
    private void ButtonPressConfirmationScreen(boolean accept)
    {
        _showConfirmationScreen = false;

        if(accept)
            _accept.play(Overtone.SFXVolume);
        else
            _decline.play(Overtone.SFXVolume);

        _backButton.setDisabled(false);
        _background.setVisible(false);
        _yesButton.setVisible(false);
        _noButton.setVisible(false);
        _yesButton.setDisabled(true);
        _noButton.setDisabled(true);
    }
}