package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;

/**
 * Screen used for the main menu
 * Created by trevor on 2016-05-01.
 */
public class MainMenuScreen extends OvertoneScreen
{
    private final Stage   _stage; // The stage that holds buttons and stuff
    private final Texture _logo;  // Texture of the logo on screen

    /**
     * Constructor
     */
    public MainMenuScreen()
    {
        super();
        _stage = new Stage();
        _logo  = new Texture(Gdx.files.internal("Textures\\logo.png"));

        // Create the play button
        final Button playButton = CreateButton("PLAY", "default", Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.15f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.21f), _stage);
        playButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.DifficultySelect);
        }});

        // Create the high scores button
        final Button highScoreButton = CreateButton("HIGH SCORES", "default", Overtone.ScreenWidth * 0.18f, Overtone.ScreenHeight * 0.1f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.075f), _stage);
        highScoreButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.HighScore);
        }});

        // Create the options button
        final Button optionsButton = CreateButton("OPTIONS", "default", Overtone.ScreenWidth * 0.18f, Overtone.ScreenHeight * 0.1f, new Vector2(Overtone.ScreenWidth * 0.2983f, Overtone.ScreenHeight * 0.075f), _stage);
        optionsButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.Options);
        }});

        // Create the help button
        final Button helpButton = CreateButton("HELP", "default", Overtone.ScreenWidth * 0.18f, Overtone.ScreenHeight * 0.1f, new Vector2(Overtone.ScreenWidth * 0.5217f, Overtone.ScreenHeight * 0.075f), _stage);
        helpButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.Help);
        }});

        // Create the quit button
        final Button quitButton = CreateButton("QUIT", "default", Overtone.ScreenWidth * 0.18f, Overtone.ScreenHeight * 0.1f, new Vector2(Overtone.ScreenWidth * 0.745f, Overtone.ScreenHeight * 0.075f), _stage);
        quitButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Gdx.app.exit();
            }
        });
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();
        _glyphLayout.setText(_font12, "Trevor Ware ");
        _font12.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.955f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.035f - (_glyphLayout.height / 2.0f));

        _glyphLayout.setText(_font12, "Version 1.0");
        _font12.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.045f - (_glyphLayout.width / 2.0f), Overtone.ScreenHeight * 0.035f - (_glyphLayout.height / 2.0f));

        _batch.draw(_logo, Overtone.ScreenWidth * 0.5f - (_logo.getWidth() / 2.0f), Overtone.ScreenHeight * 0.7f - (_logo.getHeight() / 2.0f), _logo.getWidth(), _logo.getHeight());

        _batch.end();
        _stage.draw();
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

    public void dispose ()
    {
        super.dispose();
        _stage.dispose();
        _logo.dispose();
    }
}
