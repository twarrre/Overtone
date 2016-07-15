package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.overtone.Overtone;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Screen used form the difficulty select screen
 * Created by trevor on 2016-05-01.
 */
public class DifficultySelectScreen extends OvertoneScreen
{
    private final Stage _stage;
    private int[] _scores;

    public DifficultySelectScreen(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        _stage = new Stage();

        final TextButton startButton = CreateTextButton("START", "default", _screenWidth * 0.3975f, _screenHeight * 0.15f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.075f),_stage);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.Gameplay);
            }
        });

        final TextButton backButton = CreateTextButton("BACK", "default", _screenWidth * 0.11f, _screenHeight * 0.08f, new Vector2(_screenWidth * 0.075f, _screenHeight * 0.845f), _stage);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Overtone.SetScreen(Overtone.Screens.MainMenu);
            }
        });

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