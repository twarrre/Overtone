package com.overtone;

import com.badlogic.gdx.Gdx;
import com.overtone.GeneticAlgorithm.Organism;
import com.overtone.Notes.OvertoneNote;
import jm.JMC;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Has static utility methods
 * Created by trevor on 2016-08-02.
 */
public class Utilities implements JMC
{
    private static Random _rand = new Random();
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
     * Loads the generation values from file
     */
    public static void LoadGenerationValues()
    {
        try
        {
            // Open the file
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\GenerationValues.txt"));

            String line      = null;
            int counter      = 0;

            // Read two lines from the file and store in the appropriate variables
            while ((line = reader.readLine())!= null)
            {
                if(counter == 0)
                    Overtone.NumberOfIterations = Integer.parseInt(line);
                else if(counter == 1)
                    Overtone.PopulationSize = Integer.parseInt(line);
                else if(counter == 2)
                    Overtone.NumberOfElites = Integer.parseInt(line);
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
    public static void WriteGenerationValues()
    {
        try
        {
            File file = new File("Storage\\GenerationValues.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(Overtone.NumberOfIterations + "");
            writer.newLine();
            writer.write(Overtone.PopulationSize + "");
            writer.newLine();
            writer.write(Overtone.NumberOfElites + "");

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
    public static void WriteRaterValues(boolean clear)
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
                if(clear)
                    writer.write(0.5 + "");
                else
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
            Overtone.BestRaterValues[i] = Utilities.Clamp((Overtone.BestRaterValues[i] + Overtone.CurrentRaterValues[i]) / 2.0f, 0.0f, 1.0f);
    }

    /**
     * Clears rater values for next generation
     */
    public static void ClearRaterValues()
    {
        for(int i = 0; i < Overtone.NUM_RATERS; i++)
            Overtone.CurrentRaterValues[i] = 0.5f;
    }

    /**
     * Resorts the notes and saves a backup of them.
     * @param notes The notes to sort and create a backup from
     */
    public static void SortNotes(ArrayList<OvertoneNote> notes)
    {
        // Sort notes based on the time they appear on screen
        Collections.sort(notes);

        Overtone.NoteQueue   = new ArrayList<>();

        for(int i = 0; i < notes.size(); i++)
        {
            Overtone.NoteQueue.add(new OvertoneNote(notes.get(i)));
        }

        for(int i = 0; i < Overtone.NoteQueue.size(); i++)
        {
            if((Overtone.NoteQueue.get(i).GetType() == OvertoneNote.NoteType.Hold || Overtone.NoteQueue.get(i).GetType() == OvertoneNote.NoteType.Double) && Overtone.NoteQueue.get(i).GetOtherNote() == null )
            {
                int index = BinarySearch(Overtone.NoteQueue, Overtone.NoteQueue.get(i).GetOtherNoteTime(), 0, Overtone.NoteQueue.size());
                Overtone.NoteQueue.get(i).SetOtherNote(Overtone.NoteQueue.get(index));
                Overtone.NoteQueue.get(index).SetOtherNote(Overtone.NoteQueue.get(i));

                Overtone.NoteQueue.get(i).SetOtherNoteTime(Overtone.NoteQueue.get(index).GetTime());
                Overtone.NoteQueue.get(index).SetOtherNoteTime(Overtone.NoteQueue.get(i).GetTime());
            }
        }
    }

    /**
     * Binary search to search for the corresponding note
     * @param n The array to search
     * @param search The value to seach for
     * @param low low index of the array
     * @param high high index of the array
     * @return the index of the found note, -1 otherwise
     */
    private static int BinarySearch(ArrayList<OvertoneNote> n, float search, int low, int high)
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
    public static float Clamp(float number, float low, float high)
    {
        if(number > high)
            return high;
        else if (number < low)
            return low;
        else
            return number;
    }

    /**
     * Randomly returns one of either idx1 and idx2
     * @param idx1 The first index
     * @param idx2 The second index
     * @param prob The probability of getting the first idx
     * @return One of the two indexes
     */
    public static int GetRandom(int idx1, int idx2, float prob)
    {
        ArrayList<Integer> probability = new ArrayList<>();
        int len1 = (int)((float)1000 * prob);

        for(int i = 0; i < 1000; i++)
        {
            if(i < len1)
                probability.add(idx1);
            else
                probability.add(idx2);
        }

        Collections.shuffle(probability, _rand);

        int value = _rand.nextInt(999);
        return probability.get(value);
    }

    /**
     * Gets random number based on normal distribution
     * @param mean The mean of the normal distribution
     * @param deviation The deviation of the normal distribution
     * @return A random number
     */
    public static int GetRandomRangeNormalDistribution(float mean, float deviation, float high, float low, boolean allowRepeatingValues)
    {
        int counter = 0;
        int val =  (int)Math.round(_rand.nextGaussian() * deviation + mean);

        while((!allowRepeatingValues && val == mean) || val > high || val < low || counter < 20)
        {
            val = (int)Math.round(_rand.nextGaussian() * deviation + mean);
            counter++;
        }


        return Math.round(Utilities.Clamp(val, low, high));
    }

    /**
     * Loads the midi files for either the gameplay screen or the menus.
     * @param menu If true, load menu music, else load gameplay music
     */
    public static void LoadMidiMusic(boolean menu)
    {
        try
        {
            if(menu)
            {
                Overtone.MenuSequencer = MidiSystem.getSequencer();
                Overtone.MenuSequencer.open();
                InputStream is = new BufferedInputStream(new FileInputStream(new File("Music\\MenuMusic.mid")));
                Overtone.MenuSequencer.setSequence(is);
                Overtone.MenuSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
                Overtone.MenuSequencer.start();
            }
            else
            {
                Overtone.GameplaySequencer = MidiSystem.getSequencer();
                Overtone.GameplaySequencer.open();
                InputStream is = new BufferedInputStream(new FileInputStream(new File("Music\\GeneratedMusic.mid")));
                Overtone.GameplaySequencer.setSequence(is);
                Overtone.GameplaySequencer.start();
            }
        }
        catch(MidiUnavailableException x)
        {
            System.out.println("Midi Unavailable");
            Gdx.app.exit();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
            Gdx.app.exit();
        }
        catch(InvalidMidiDataException m)
        {
            System.out.println("Invalid Midi Data");
            Gdx.app.exit();
        }
        catch(IOException i)
        {
            System.out.println("IO exception");
            Gdx.app.exit();
        }
    }

    public static void LoadMutationValues()
    {
        try
        {
            // Open the file
            BufferedReader reader = new BufferedReader(new FileReader("Storage\\MutationValues.txt"));

            String line      = null;
            int counter      = 0;

            // Read two lines from the file and store in the appropriate variables
            while ((line = reader.readLine())!= null)
            {
                String[] result = line.split("\\s");
                if(counter == 0)
                {
                    for(int i = 0; i < Overtone.PitchMutatorValues.length; i++)
                    {
                        Overtone.PitchMutatorValues[i] = Float.parseFloat(result[i]);
                    }
                }
                else if(counter == 1)
                {
                    for(int i = 0; i < Overtone.RhythmMutatorValues.length; i++)
                    {
                        Overtone.RhythmMutatorValues[i] = Float.parseFloat(result[i]);
                    }
                }
                else if(counter == 2)
                {
                    for(int i = 0; i < Overtone.SimplifyMutatorValues.length; i++)
                    {
                        Overtone.SimplifyMutatorValues[i] = Float.parseFloat(result[i]);
                    }
                }
                else if(counter == 3)
                {
                    for(int i = 0; i < Overtone.SwapMutatorValues.length; i++)
                    {
                        Overtone.SwapMutatorValues[i] = Float.parseFloat(result[i]);
                    }
                }
                else if(counter == 4)
                {
                    for(int i = 0; i < Overtone.DynamicMutatorValues.length; i++)
                    {
                        Overtone.DynamicMutatorValues[i] = Float.parseFloat(result[i]);
                    }
                }
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

    public static void WriteMutationValues()
    {
        try
        {
            File file = new File("Storage\\MutationValues.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(Overtone.PitchMutatorValues[0] + " " + Overtone.PitchMutatorValues[1] + " " + Overtone.PitchMutatorValues[2]);
            writer.newLine();
            writer.write(Overtone.RhythmMutatorValues[0] + " " + Overtone.RhythmMutatorValues[1] + " " + Overtone.RhythmMutatorValues[2]);
            writer.newLine();
            writer.write(Overtone.SimplifyMutatorValues[0] + " " + Overtone.SimplifyMutatorValues[1] + " " + Overtone.SimplifyMutatorValues[2]);
            writer.newLine();
            writer.write(Overtone.SwapMutatorValues[0] + " " + Overtone.SwapMutatorValues[1] + " " + Overtone.SwapMutatorValues[2]);
            writer.newLine();
            writer.write(Overtone.DynamicMutatorValues[0] + " " + Overtone.DynamicMutatorValues[1] + " " + Overtone.DynamicMutatorValues[2]);

            writer.close();
        }
        catch(IOException x)
        {
            System.out.print("Volume data cannot be saved at this time.");
        }
    }

    // Implementing Fisher–Yates shuffle
    public static void ShuffleArray(float[] ar)
    {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = _rand.nextInt(i + 1);
            // Simple swap
            float a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
