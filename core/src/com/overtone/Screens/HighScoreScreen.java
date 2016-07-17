package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Notes.Note;
import com.overtone.Overtone;

import java.io.*;

/**
 * Screen used for high scores
 * Created by trevor on 2016-05-01.
 */
public class HighScoreScreen extends OvertoneScreen
{
    private final Stage _stage;
    private final TextButton _easyButton;
    private final TextButton _normalButton;
    private final TextButton _hardButton;
    private TextButton _currentButton;
    private int[][] _scores;
    private int _difficultyIndex;
    private final Image _background;
    private final ImageButton _yesButton;
    private final ImageButton _noButton;
    private final TextButton _backButton;
    private final TextButton _resetButton;
    private boolean _showConfirmationScreen;

    public HighScoreScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();
        _difficultyIndex = 0;
        _scores = new int[3][5];
        LoadHighScores();

        _backButton = CreateTextButton("BACK", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2175f, _screenHeight * 0.075f), _stage);
        _backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_backButton.isDisabled())
                    return;

                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });
        //_backButton.setZIndex(1);

        _resetButton = CreateTextButton("RESET", "default", _screenWidth * 0.25f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.5475f, _screenHeight * 0.075f), _stage);
        _resetButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_resetButton.isDisabled())
                    return;

                _backButton.setDisabled(true);
                _resetButton.setDisabled(true);
                _background.setVisible(true);
                _yesButton.setVisible(true);
                _noButton.setVisible(true);
                _yesButton.setDisabled(false);
                _noButton.setDisabled(false);
                _showConfirmationScreen = true;
            }
        });
        //_resetButton.setZIndex(1);

        _easyButton   = CreateTextButton("EASY", "group", _screenWidth * 0.2f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.2075f, _screenHeight * 0.75f),_stage);
        _easyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _currentButton.setChecked(false);
                _easyButton.setChecked(true);
                _currentButton = _easyButton;
                _difficultyIndex = 0;
            }
        });
        //_easyButton.setZIndex(1);

        _normalButton = CreateTextButton("NORMAL", "group", _screenWidth * 0.2f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.4075f, _screenHeight * 0.75f), _stage);
        _normalButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _currentButton.setChecked(false);
                _normalButton.setChecked(true);
                _currentButton = _normalButton;
                _difficultyIndex = 1;
            }
        });
        //_normalButton.setZIndex(1);

        _hardButton   = CreateTextButton("HARD", "group", _screenWidth * 0.2f, _screenHeight * 0.1f, new Vector2(_screenWidth * 0.6075f, _screenHeight * 0.75f), _stage);
        _hardButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _currentButton.setChecked(false);
                _hardButton.setChecked(true);
                _currentButton = _hardButton;
                _difficultyIndex = 2;
            }
        });
        //_hardButton.setZIndex(1);

        _background = new Image(new Texture(Gdx.files.internal("Textures\\background.png")));
        _background.setWidth(_screenWidth * 0.85f);
        _background.setHeight(_screenHeight * 0.75f);
        _background.setPosition(screenWidth * 0.075f, _screenHeight * 0.125f);
        _stage.addActor(_background);
        _background.setVisible(false);
        //_background.setZIndex(2);

        _yesButton = CreateImageButton("yesButtons",_screenWidth * 0.1f, _screenWidth * 0.1f, new Vector2(_screenWidth * 0.375f, _screenHeight * 0.2f), _stage);
        _yesButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_yesButton.isDisabled())
                    return;

                _background.setVisible(false);
                _yesButton.setVisible(false);
                _noButton.setVisible(false);
                _backButton.setDisabled(false);
                _resetButton.setDisabled(false);
                _yesButton.setDisabled(true);
                _noButton.setDisabled(true);
                _showConfirmationScreen = false;
                ResetScores();
            }
        });
        _yesButton.setDisabled(true);
        _yesButton.setVisible(false);
        //_yesButton.setZIndex(3);


        _noButton = CreateImageButton("noButtons",_screenWidth * 0.1f, _screenWidth * 0.1f, new Vector2(_screenWidth * 0.525f, _screenHeight * 0.2f), _stage);
        _noButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                if(_noButton.isDisabled())
                    return;

                _background.setVisible(false);
                _yesButton.setVisible(false);
                _noButton.setVisible(false);
                _backButton.setDisabled(false);
                _resetButton.setDisabled(false);
                _yesButton.setDisabled(true);
                _noButton.setDisabled(true);
                _showConfirmationScreen = false;
            }
        });
        _noButton.setDisabled(true);
        _noButton.setVisible(false);
        //_noButton.setZIndex(3);

        _showConfirmationScreen = false;

        _currentButton = _easyButton;
        _currentButton.setChecked(true);
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.reset();
        _font.getData().setScale(3);
        _glyphLayout.setText(_font,  "High Scores");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.95f);

        for(int i = 0; i < 5; i++)
        {
            _glyphLayout.reset();
            _font.getData().setScale(2);
            _glyphLayout.setText(_font, (i + 1) + "");
            _font.draw(_batch, _glyphLayout, _screenWidth * 0.3047f, _screenHeight * 0.65f - (_screenHeight * 0.07f * (float)i));

            _glyphLayout.reset();
            _font.getData().setScale(2);
            _glyphLayout.setText(_font, _scores[_difficultyIndex][i] + "");
            _font.draw(_batch, _glyphLayout, _screenWidth * 0.7075f - _glyphLayout.width, _screenHeight * 0.65f - (_screenHeight * 0.07f * (float)i));
        }

        _batch.end();

        _stage.draw();

        if( _showConfirmationScreen)
        {
            _batch.begin();
            _glyphLayout.reset();
            _font.getData().setScale(4);
            _glyphLayout.setText(_font,  "Are you sure?");
            _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.7f);
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

    private void LoadHighScores()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\HighScores.txt"));

            String line      = null;
            int diffCounter  = -1;
            int scoreCounter = 0;

            while ((line = reader.readLine())!= null)
            {
                if(line.compareTo("e") == 0  || line.compareTo("n") == 0  || line.compareTo("h") == 0 )
                {
                    diffCounter++;
                    scoreCounter = 0;
                    continue;
                }

                _scores[diffCounter][scoreCounter] = Integer.parseInt(line);
                scoreCounter++;
            }

            reader.close();
        }
        catch (IOException e)
        {
           ResetScores();
        }
    }

    private void ResetScores()
    {
        _scores = new int[3][5];

        try
        {
            File file = new File("Storage\\HighScores.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fw);
            String[] diff = {"e", "n", "h"};

            for(int i = 0; i < diff.length; i++)
            {
                writer.write(diff[i]);
                writer.newLine();
                for(int j = 0; j < 5; j++)
                {
                    writer.write("" + 0);
                    writer.newLine();
                }
            }

            writer.close();
        }
        catch(IOException x)
        {
            System.out.print("Data Cannot be reset at this time.");
        }
    }
}