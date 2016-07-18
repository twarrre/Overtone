package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Notes.Note;
import com.overtone.Overtone;
import com.overtone.Ratings.Rating;

import java.io.*;

/**
 * Screen used for the song complete screen
 * Created by trevor on 2016-05-01.
 */
public class SongCompleteScreen extends OvertoneScreen
{
    private final Stage _stage;
    private final int _score;
    private final String _songCompleted;
    private final int[] _counters;

    public SongCompleteScreen(int screenWidth, int screenHeight, boolean completed, int score, int ... counters)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();
        _score = score;
        _songCompleted =  completed ? "Song Completed!" : "Song Failed...";
        _counters = counters;

        final TextButton retryButton = CreateTextButton("RETRY", "default", _screenWidth * 0.25f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f), _stage);
        retryButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone.SetScreen(Overtone.Screens.Gameplay);
            }
        });

        final TextButton menuButton = CreateTextButton("MAIN MENU", "default", _screenWidth * 0.25f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.375f, _screenHeight * 0.075f), _stage);
        menuButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        final TextButton difficultyButton = CreateTextButton("CHANGE DIFFICULTY", "default", _screenWidth * 0.25f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.675f, _screenHeight * 0.075f), _stage);
        difficultyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                Overtone.SetScreen(Overtone.Screens.DifficultySelect);
            }
        });

        Overtone.UpdateScore(_score, Rating.ScoreRating.GetRating(_counters), Overtone._difficulty);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();

        _batch.begin();

        _glyphLayout.reset();
        _font.getData().setScale(3);
        _glyphLayout.setText(_font, _songCompleted);
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.92f);

        _glyphLayout.reset();
        _font.getData().setScale(3);
        _glyphLayout.setText(_font, "Notes");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.25f - _glyphLayout.width / 2.0f, _screenHeight * 0.75f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "Difficulty:");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.65f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "Rating:");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.57f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "Score: ");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.49f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "High Score: ");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.41f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "" + Overtone._difficulty.toString());
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.65f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "" + Rating.ScoreRating.GetRating(_counters).toString());
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.57f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "" + _score);
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.49f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, (Overtone._scores[Overtone._difficulty.ordinal()][0] > _score) ? Overtone._scores[Overtone._difficulty.ordinal()][0] + "" : _score + "");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.925f - _glyphLayout.width, _screenHeight * 0.41f);

        if(_score > Overtone._scores[Overtone._difficulty.ordinal()][0])
        {
            _glyphLayout.reset();
            _font.getData().setScale(2);
            _glyphLayout.setText(_font, "New High Score!!");
            _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - _glyphLayout.width / 2.0f, _screenHeight * 0.85f);
        }

        for(int i = 0; i < _counters.length; i++)
        {
            _glyphLayout.reset();
            _font.getData().setScale(2);
            _glyphLayout.setText(_font, Rating.RatingValue.values()[i].toString() + ": ");
            _font.draw(_batch, _glyphLayout, _screenWidth * 0.075f, _screenHeight * 0.65f - (_screenHeight * 0.07f * (float)i));

            _glyphLayout.reset();
            _font.getData().setScale(2);
            _glyphLayout.setText(_font, "" + _counters[i]);
            _font.draw(_batch, _glyphLayout, _screenWidth * 0.425f - _glyphLayout.width, _screenHeight * 0.65f - (_screenHeight * 0.07f * (float)i));
        }

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
    }
}