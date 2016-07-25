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
 * Screen used for help
 * Created by trevor on 2016-05-01.
 */
public class HelpScreen extends OvertoneScreen
{
    /**The number of help screens that there are*/
    public static final int NUM_HELP_SCREENS = 4;

    private final Stage     _stage;           // Stage to hold buttons and such
    private final Texture[] _help;            // Array of images that are tutorial images
    private final Texture   _circleFilled;    // Texture of a filled circle, signifies that you are on that texture
    private final Texture   _circleNotFilled; // Texture of a unfilled circle, signifies that you are not on that texture
    private int             _helpIndex;       // Which texture are you on right now

    public HelpScreen()
    {
        super();
        _stage           = new Stage();
        _help            = new Texture[NUM_HELP_SCREENS];
        _circleFilled    = new Texture(Gdx.files.internal("Textures\\circleFilled.png"));
        _circleNotFilled = new Texture(Gdx.files.internal("Textures\\circle.png"));
        _helpIndex       = 0;

        // Load help textures
        for(int i = 0; i < NUM_HELP_SCREENS; i++)
            _help[i] = new Texture(Gdx.files.internal("Textures\\background.png"));

        // Create the back button
        final Button backButton = CreateButton("BACK", "default", Overtone.ScreenWidth * 0.11f, Overtone.ScreenHeight * 0.08f, new Vector2(Overtone.ScreenWidth * 0.075f, Overtone.ScreenHeight * 0.845f), _stage);
        backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SetScreen(Overtone.Screens.MainMenu);
         }});

        // Create the next button
        final Button next = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.6875f, Overtone.ScreenHeight * 0.82f), _stage);
        next.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _helpIndex++;

            if(_helpIndex >= _help.length)
                _helpIndex = 0;

            _buttonPress.play(Overtone.SFXVolume);
        }});

        // Create the back button
        final Button back = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.2875f, Overtone.ScreenHeight * 0.82f), _stage);
        back.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _helpIndex--;

            if(_helpIndex < 0)
                _helpIndex = _help.length - 1;

            _buttonPress.play(Overtone.SFXVolume);
        }});
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _batch.begin();

        _glyphLayout.setText(_font36, "Help");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - _glyphLayout.width / 2.0f, Overtone.ScreenHeight * 0.92f);

        for(int i = 0; i < _help.length; i++)
        {
            if(i == _helpIndex)
                _batch.draw(_circleFilled, Overtone.ScreenWidth * 0.375f + (Overtone.ScreenWidth * (0.3f / (float)_help.length) * (float)i), Overtone.ScreenHeight * 0.82f, Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f);
            else
                _batch.draw(_circleNotFilled, Overtone.ScreenWidth * 0.375f + (Overtone.ScreenWidth *  (0.3f / (float)_help.length) * (float)i), Overtone.ScreenHeight * 0.82f, Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f);
        }

        _batch.draw(_help[_helpIndex], Overtone.ScreenWidth * 0.125f, Overtone.ScreenHeight * 0.05f, Overtone.ScreenWidth * 0.75f, Overtone.ScreenHeight * 0.75f);

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

        for(int i = 0; i < _help.length; i++)
        {
            _help[i].dispose();
        }

        _circleFilled.dispose();
        _circleNotFilled.dispose();
    }
}