package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Overtone;

/**
 * Screen used for help
 * Created by trevor on 2016-05-01.
 */
public class HelpScreen extends OvertoneScreen
{
    private final Stage _stage;
    private final Texture[] _help;
    private final Texture _circleFilled;
    private final Texture _circleNotFilled;
    private int _helpIndex;

    public HelpScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();

        _help = new Texture[4];
        _help[0] = new Texture(Gdx.files.internal("Textures\\background.png"));
        _help[1] = new Texture(Gdx.files.internal("Textures\\background.png"));
        _help[2] = new Texture(Gdx.files.internal("Textures\\background.png"));
        _help[3] = new Texture(Gdx.files.internal("Textures\\background.png"));
        _circleFilled = new Texture(Gdx.files.internal("Textures\\circleFilled.png"));
        _circleNotFilled = new Texture(Gdx.files.internal("Textures\\circle.png"));
        _helpIndex = 0;

        final TextButton backButton = CreateTextButton("BACK", "default", _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f), _stage);
        backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {_buttonPress.play(); Overtone.SetScreen(Overtone.Screens.MainMenu);}});

        final ImageButton next = CreateImageButton("nextButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.6875f, _screenHeight * 0.82f), _stage);
        next.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _helpIndex++;

            if(_helpIndex >= _help.length)
                _helpIndex = 0;
            _buttonPress.play();
        }});

        final ImageButton back = CreateImageButton("backButton",_screenWidth * 0.025f, _screenWidth * 0.025f, new Vector2(_screenWidth * 0.2875f, _screenHeight * 0.82f), _stage);
        back.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _helpIndex--;

            if(_helpIndex < 0)
                _helpIndex = _help.length - 1;
            _buttonPress.play();
        }});
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();

        _batch.begin();

        _glyphLayout.setText(_font36, "Help");
        _font36.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.92f);

        for(int i = 0; i < _help.length; i++)
        {
            if(i == _helpIndex)
                _batch.draw(_circleFilled, _screenWidth * 0.375f + (_screenWidth * (0.3f / (float)_help.length) * (float)i), _screenHeight * 0.82f, _screenWidth * 0.025f, _screenWidth * 0.025f);
            else
                _batch.draw(_circleNotFilled, _screenWidth * 0.375f + (_screenWidth *  (0.3f / (float)_help.length) * (float)i), _screenHeight * 0.82f, _screenWidth * 0.025f, _screenWidth * 0.025f);
        }

        _batch.draw(_help[_helpIndex], _screenWidth * 0.125f, _screenHeight * 0.05f, _screenWidth * 0.75f, _screenHeight * 0.75f);

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

        for(int i = 0; i < _help.length; i++)
        {
            _help[i].dispose();
        }

        _circleFilled.dispose();
        _circleNotFilled.dispose();
    }
}