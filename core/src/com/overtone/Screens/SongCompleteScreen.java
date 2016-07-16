package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.overtone.Notes.Note;

/**
 * Screen used for the song complete screen
 * Created by trevor on 2016-05-01.
 */
public class SongCompleteScreen extends OvertoneScreen
{
    private final Stage _stage;
    private final int _score;
    private final int _prevHighScore;
    private final Note.DifficultyMultiplier _difficulty;
    private final boolean _songCompleted;

    public SongCompleteScreen(int screenWidth, int screenHeight, boolean completed, Note.DifficultyMultiplier diff, int score, int highScore)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();
        _score = score;
        _prevHighScore = highScore;
        _difficulty = diff;
        _songCompleted = completed;
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();

        _batch.begin();

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "High Score: ");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.775f - _glyphLayout.width, _screenHeight * 0.92f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font, "Difficulty: ");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.225f, _screenHeight * 0.92f);

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