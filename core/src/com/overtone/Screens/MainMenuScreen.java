package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;
import com.overtone.Utilities;

/**
 * Screen used for the main menu
 * Created by trevor on 2016-05-01.
 */
public class MainMenuScreen extends OvertoneScreen
{
    private final Stage   _stage;         // The stage that holds buttons and stuff
    private final Texture _logoNoGlow;    // Texture of the logo on screen
    private final Texture _glow;          // Texture for the glow of the logo
    private float         _glowAlpha;     // The alpha of the glow for the logo
    private float         _glowDirection; // The direction of the glow alpha

    /**
     * Constructor
     */
    public MainMenuScreen()
    {
        super();
        _stage              = new Stage();
        _logoNoGlow         = new Texture(Gdx.files.internal("Assets\\Textures\\logo_noglow.png"));
        _glow               = new Texture(Gdx.files.internal("Assets\\Textures\\glow.png"));
        _glowAlpha          = 0.0f;
        _glowDirection      = 0.01f;
        Overtone.Regenerate = true;

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

        _batch.setColor(1.0f, 1.0f, 1.0f, _glowAlpha);
        _batch.draw(_glow, Overtone.ScreenWidth * 0.5f - (_glow.getWidth() / 2.0f), Overtone.ScreenHeight * 0.7f - (_glow.getHeight() / 2.0f), _glow.getWidth(), _glow.getHeight());
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        _batch.draw(_logoNoGlow, Overtone.ScreenWidth * 0.5f - (_logoNoGlow.getWidth() / 2.0f), Overtone.ScreenHeight * 0.7f - (_logoNoGlow.getHeight() / 2.0f), _logoNoGlow.getWidth(), _logoNoGlow.getHeight());

        _batch.end();
        _stage.draw();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
        _stage.act(deltaTime);

        // Update glow alpha value
        _glowAlpha = Utilities.Clamp(_glowAlpha + _glowDirection, 0, 1);

        // If the alpha is 1 or 0 change the direction that the alpha is switch
        if(_glowAlpha >= 1.0f || _glowAlpha <= 0.0f)
            _glowDirection *= -1.0f;

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
        _logoNoGlow.dispose();
        _glow.dispose();
    }
}
