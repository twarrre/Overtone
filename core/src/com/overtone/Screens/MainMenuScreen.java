package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen used for the main menu
 * Created by trevor on 2016-05-01.
 */
public class MainMenuScreen extends OvertoneScreen
{
    private final Skin skin;
    private final Stage stage;

    public MainMenuScreen(String backgroundImagePath, int screenWidth, int screenHeight)
    {
        super(backgroundImagePath, screenWidth, screenHeight);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        _font.getData().scale(1);
        skin.add("default", _font);
        skin.add("white", new Texture(pixmap));

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up      = skin.newDrawable("white", new Color(0.157f, 0.325f, 0.424f, 0.75f));
        textButtonStyle.down    = skin.newDrawable("white", new Color(0.012f, 0.137f, 0.212f, 0.75f));
        textButtonStyle.over    = skin.newDrawable("white", new Color(0.067f, 0.224f, 0.318f, 0.75f));

        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        final TextButton playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setWidth(_screenWidth * 0.85f);
        playButton.setHeight(_screenWidth * 0.08f);
        playButton.setPosition(_screenWidth * 0.5f - (playButton.getWidth() / 2.0f), _screenHeight * 0.25f - (playButton.getHeight() / 2.0f));
        stage.addActor(playButton);

        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Play Clicked");
            }
        });

        final TextButton highScoreButton = new TextButton("HIGH SCORES", textButtonStyle);
        highScoreButton.setWidth(_screenWidth * 0.25f);
        highScoreButton.setHeight(_screenWidth * 0.05f);
        highScoreButton.setPosition(_screenWidth * 0.075f, _screenHeight * 0.1f - (highScoreButton.getHeight() / 2.0f));
        stage.addActor(highScoreButton);

        highScoreButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("High Score Clicked");
            }
        });

        final TextButton optionsButton = new TextButton("OPTIONS", textButtonStyle);
        optionsButton.setWidth(_screenWidth * 0.25f);
        optionsButton.setHeight(_screenWidth * 0.05f);
        optionsButton.setPosition(_screenWidth * 0.5f - (optionsButton.getWidth() / 2.0f), _screenHeight * 0.1f - (optionsButton.getHeight() / 2.0f));
        stage.addActor(optionsButton);

        optionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Options Clicked");
            }
        });

        final TextButton helpButton = new TextButton("HELP", textButtonStyle);
        helpButton.setWidth(_screenWidth * 0.25f);
        helpButton.setHeight(_screenWidth * 0.05f);
        helpButton.setPosition(_screenWidth * 0.925f - helpButton.getWidth(), _screenHeight * 0.1f - (helpButton.getHeight() / 2.0f));
        stage.addActor(helpButton);

        helpButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Help Clicked");
            }
        });
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.reset();
        _font.getData().setScale(1);
        _glyphLayout.setText(_font,"Trevor Ware");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.965f - (_glyphLayout.width / 2.0f), _screenHeight * 0.035f - (_glyphLayout.height / 2.0f));

        _glyphLayout.reset();
        _font.getData().setScale(1);
        _glyphLayout.setText(_font,"Version 1.0");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.035f - (_glyphLayout.width / 2.0f), _screenHeight * 0.035f - (_glyphLayout.height / 2.0f));

        _batch.end();

        _font.getData().scale(1);
        stage.draw();
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);

        stage.act(deltaTime);
    }

    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    public void dispose ()
    {
        super.dispose();
        skin.dispose();
        stage.dispose();
    }
}
