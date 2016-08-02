package com.overtone;

import com.overtone.Notes.Note;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Has static utility methods
 * Created by trevor on 2016-08-02.
 */
public class Utilities
{
    /**
     * Loads high scores & crowd ratings from a file.
     */
    public static void LoadHighScores()
    {
        try
        {
            // Open the file
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\HighScores.txt"));

            String line      = null;
            int diffCounter  = -1;
            int scoreCounter = 0;

            while ((line = reader.readLine())!= null) // While there is a lind to read
            {
                String[] tokens = line.split(" "); // Tokenize it into "score" and "rating"

                if(tokens[0].compareTo("e") == 0  || tokens[0].compareTo("n") == 0  || tokens[0].compareTo("h") == 0 ) // Check the line is one of the difficulty markers
                {
                    // Move onto the next difficulty (next dimension in the array)
                    diffCounter++;
                    scoreCounter = 0;
                    continue;
                }

                // Store the high score and the associated crowd rating
                Overtone.HighScores[diffCounter][scoreCounter]   = Integer.parseInt(tokens[0]);
                Overtone.CrowdRatings[diffCounter][scoreCounter] = Overtone.CrowdRating.GetRating("" + tokens[1]);
                scoreCounter++;
            }
            reader.close();
        }
        catch (IOException e)
        {
            System.out.print("Data cannot be loaded at this time.");
        }
    }

    /**
     * Writes the scores and crowd ratings to a file
     * @param reset true if you want to reset the high scores, false otherwise
     */
    public static void WriteScores(boolean reset)
    {
        if(reset)
        {
            Overtone.HighScores   = new int[Overtone.Difficulty.values().length][Overtone.NUM_SCORES];
            Overtone.CrowdRatings = new Overtone.CrowdRating[Overtone.Difficulty.values().length][Overtone.NUM_SCORES];
        }

        try
        {
            File file = new File("Storage\\HighScores.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String[] diff         = {"e", "n", "h"};

            for(int i = 0; i < diff.length; i++)
            {
                writer.write(diff[i]);
                writer.newLine();
                for(int j = 0; j < Overtone.NUM_SCORES; j++)
                {
                    if(reset)
                    {
                        Overtone.CrowdRatings[i][j] = Overtone.CrowdRating.None;
                        Overtone.HighScores[i][j]   = 0;
                        writer.write(0 + " ---");
                    }
                    else
                        writer.write(Overtone.HighScores[i][j] + " " + Overtone.CrowdRatings[i][j]);

                    writer.newLine();
                }
            }

            writer.close();
        }
        catch(IOException x)
        {
            System.out.print("Data cannot be saved at this time.");
        }
    }

    /**
     * Checks if the passed in score is better than any of the stored scores and updates the scores.
     * @param score The scores to be checked
     * @param rating The rating of the score
     * @param difficulty The difficultly of the song
     */
    public static void UpdateScore(int score, Overtone.CrowdRating rating, Overtone.Difficulty difficulty)
    {
        boolean replaced            = false;
        int scoreToReplace          = score;
        Overtone.CrowdRating ratingToReplace = rating;

        for(int i = 0; i < Overtone.HighScores[difficulty.ordinal()].length; i++)
        {
            if(scoreToReplace > Overtone.HighScores[difficulty.ordinal()][i])
            {
                replaced = true;

                // Replace the score
                int tempScore                                = Overtone.HighScores[difficulty.ordinal()][i];
                Overtone.HighScores[difficulty.ordinal()][i] = scoreToReplace;
                scoreToReplace                               = tempScore;

                // Replace the rating
                Overtone.CrowdRating tempRating                         = Overtone.CrowdRatings[Overtone.Difficulty.ordinal()][i];
                Overtone.CrowdRatings[Overtone.Difficulty.ordinal()][i] = ratingToReplace;
                ratingToReplace                                         = tempRating;
            }
        }

        // If any scores were changed, write the scores to the file
        if(replaced)
            WriteScores(false);
    }

    /**
     * Loads the music and sound effects volumes from a file
     */
    public static void LoadVolume()
    {
        try
        {
            // Open the file
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\Volume.txt"));

            String line      = null;
            int counter      = 0;

            // Read two lines from the file and store in the appropriate variables
            while ((line = reader.readLine())!= null)
            {
                if(counter == 0)
                    Overtone.MusicVolume = Float.parseFloat(line);
                else if(counter == 1)
                    Overtone.SFXVolume = Float.parseFloat(line);
                else
                    break;
                counter++;
            }

            reader.close();
        }
        catch (IOException e)
        {
            System.out.print("Volume data cannot be loaded at this time.");
        }
    }

    /**
     * Writes the new volume values to a file
     */
    public static void WriteVolume()
    {
        try
        {
            File file = new File("Storage\\Volume.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(Overtone.MusicVolume + "");
            writer.newLine();
            writer.write(Overtone.SFXVolume + "");

            writer.close();
        }
        catch(IOException x)
        {
            System.out.print("Volume data cannot be saved at this time.");
        }
    }

    /**
     * Load the rater values from a file
     */
    public static void LoadRaterValues()
    {
        try
        {
            // Open the file
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\GeneticRaters.txt"));

            String line  = null;
            int    index = 0;

            while ((line = reader.readLine())!= null && index < Overtone.NUM_RATERS) // While there is a lind to read
            {
                Overtone.BestRaterValues[index] = Float.parseFloat(line);
                index++;
            }

            reader.close();
        }
        catch (IOException e)
        {
            System.out.print("Data cannot be loaded at this time.");
        }
    }

    /**
     * Write the rater values to a file
     */
    public static void WriteRaterValues()
    {
        try
        {
            File file = new File("Storage\\GeneticRaters.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for(int i = 0; i < Overtone.BestRaterValues.length; i++)
            {
                writer.write(Overtone.BestRaterValues[i] + "");
                writer.newLine();
            }

            writer.close();
        }
        catch(IOException x)
        {
            System.out.print("Data cannot be saved at this time.");
        }
    }

    /**
     * Average the rater scores if the user says that they like the song
     */
    public static void AverageRaterValues()
    {
        for(int i = 0; i < Overtone.NUM_RATERS; i++)
            Overtone.BestRaterValues[i] = (Overtone.BestRaterValues[i] + Overtone.CurrentRaterValues[i]) / 2.0f;
    }

    /**
     * Resorts the notes and saves a backup of them.
     * @param notes The notes to sort and create a backup from
     */
    public static void SortNotes(ArrayList<Note> notes)
    {
        // Sort notes based on the time they appear on screen
        Collections.sort(notes);

        Overtone.NoteQueue   = new ArrayList<Note>();
        Overtone.BackupQueue = new ArrayList<Note>();

        for(int i = 0; i < notes.size(); i++)
        {
            Overtone.NoteQueue.add(new Note(notes.get(i)));
            Overtone.BackupQueue.add(new Note(notes.get(i)));
        }

        for(int i = 0; i < Overtone.NoteQueue.size(); i++)
        {
            if((Overtone.NoteQueue.get(i).GetType() == Note.NoteType.Hold || Overtone.NoteQueue.get(i).GetType() == Note.NoteType.Double) && Overtone.NoteQueue.get(i).GetOtherNote() == null )
            {
                int index = BinarySearch(Overtone.NoteQueue, Overtone.NoteQueue.get(i).GetOtherNoteTime(), 0, Overtone.NoteQueue.size() - 1);
                Overtone.NoteQueue.get(i).SetOtherNote(Overtone.NoteQueue.get(index));
                Overtone.NoteQueue.get(index).SetOtherNote(Overtone.NoteQueue.get(i));

                Overtone.BackupQueue.get(i).SetOtherNote(Overtone.BackupQueue.get(index));
                Overtone.BackupQueue.get(index).SetOtherNote(Overtone.BackupQueue.get(i));
            }
        }
    }

    /**
     * Binary search to search for the coresponding note
     * @param n The array to search
     * @param search The value to seach for
     * @param low low index of the array
     * @param high high index of the array
     * @return the index of the found note, -1 otherwise
     */
    private static int BinarySearch(ArrayList<Note> n, float search, int low, int high)
    {
        int len = (high - low);
        int index = (high + low) / 2;

        if(len == 1 && n.get(index).GetTime() == search)
            return index;
        else if(len == 1 && n.get(index).GetTime() != search)
            return -1;

        if(search < n.get(index).GetTime())
            return BinarySearch(n, search, low, index);
        else if(search > n.get(index).GetTime())
            return BinarySearch(n, search, index, high);
        else
            return index;
    }

    /**
     * Clamps a number between these two values
     * @param number the number to be clamped
     * @param low the lower bound
     * @param high the upper bound
     * @return returns the float, clamped if necessary
     */
    public float Clamp(float number, float low, float high)
    {
        if(number > high)
            return high;
        else if (number < low)
            return low;
        else
            return number;
    }
}