package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.Notes.Note;
import com.overtone.Overtone;
import java.io.*;


/**
 * Screen used form the difficulty select screen
 * Created by trevor on 2016-05-01.
 */
public class DifficultySelectScreen extends OvertoneScreen
{
    private final Stage _stage;
    private int[] _scores;
    private Note.DifficultyMultiplier _multiplier;
    private int _difficultyIndex;
    private final TextButton _easyButton;
    private final TextButton _normalButton;
    private final TextButton _hardButton;
    private TextButton _currentButton;

    public DifficultySelectScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();
        _multiplier = Note.DifficultyMultiplier.easy;
        _difficultyIndex = 0;

        final TextButton startButton = CreateTextButton("START", "default", _screenWidth * 0.85f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f),_stage);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.Gameplay, _multiplier, _scores[_difficultyIndex]);
            }
        });

        final TextButton backButton = CreateTextButton("BACK", "default", _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f), _stage);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

        _easyButton   = CreateTextButton("EASY", "group", _screenWidth * 0.1325f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.3f),_stage);
        _easyButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _multiplier = Note.DifficultyMultiplier.easy;
                _difficultyIndex = 0;
                _currentButton.setChecked(false);
                _easyButton.setChecked(true);
                _currentButton = _easyButton;
            }
        });

        _normalButton = CreateTextButton("NORMAL", "group", _screenWidth * 0.1325f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.2075f, _screenHeight * 0.3f), _stage);
        _normalButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _multiplier = Note.DifficultyMultiplier.normal;
                _difficultyIndex = 1;
                _currentButton.setChecked(false);
                _normalButton.setChecked(true);
                _currentButton = _normalButton;
            }
        });

        _hardButton   = CreateTextButton("HARD", "group", _screenWidth * 0.1325f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.34f, _screenHeight * 0.3f), _stage);
        _hardButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _multiplier = Note.DifficultyMultiplier.hard;
                _difficultyIndex = 2;
                _currentButton.setChecked(false);
                _hardButton.setChecked(true);
                _currentButton = _hardButton;
            }
        });

        _currentButton = _easyButton;
        _currentButton.setChecked(true);

        _scores = new int[3];
        LoadHighScores();
    }

    private void LoadHighScores()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\HighScores.txt"));

            boolean readNextValue = false;
            String line           = null;
            int counter           = 0;

            while ((line = reader.readLine()) != null)
            {
                if(readNextValue)
                {
                    _scores[counter] = Integer.parseInt(line);
                    readNextValue = false;
                    counter++;
                }

                if(line.compareTo("e") == 0  || line.compareTo("n") == 0  || line.compareTo("h") == 0 )
                    readNextValue = true;
            }

            reader.close();
        }
        catch (IOException e)
        {
            try
            {
                System.out.println("Saved data cannot be found. Creating Data.");
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

                _scores[0] = 0;
                _scores[1] = 0;
                _scores[2] = 0;
            }
            catch(IOException x)
            {
                _scores[0] = 0;
                _scores[1] = 0;
                _scores[2] = 0;
                System.out.print("Data Cannot be saved at this time.");
            }
        }
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);
        _stage.draw();

        _batch.begin();

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font,  "High Score: " + _scores[_difficultyIndex]);
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.355f);

        _glyphLayout.reset();
        _font.getData().setScale(2);
        _glyphLayout.setText(_font,  "Rating: Brilliant");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.575f, _screenHeight * 0.43f);

        _glyphLayout.reset();
        _font.getData().setScale(3);
        _glyphLayout.setText(_font,  "Choose your Difficulty");
        _font.draw(_batch, _glyphLayout, _screenWidth * 0.5f - (_glyphLayout.width / 2.0f), _screenHeight * 0.9f);

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