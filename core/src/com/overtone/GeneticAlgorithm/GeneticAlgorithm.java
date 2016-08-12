package com.overtone.GeneticAlgorithm;

import com.badlogic.gdx.math.Vector2;
import com.overtone.Instraments.SineInst;
import com.overtone.Notes.OvertoneNote;
import com.overtone.Overtone;
import com.overtone.Utilities;
import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;

import java.util.ArrayList;

/**
 * Object to generate the music for the game
 * Created by trevor on 2016-08-01.
 */
public class GeneticAlgorithm implements Runnable, JMC
{
    /** Represents the number of iterations the algorithm is going to go through*/
    public static final int NUM_ITERATIONS = Integer.MAX_VALUE;

    private int _currentIteration; // The current iteration of the algorithm

    /**
     * Constructor
     */
    public GeneticAlgorithm()
    {
        _currentIteration = 0;
    }

    public void run()
    {
        Generate();
    }

    /**
     * @return The percentage of completion for the algorithm
     */
    public float GetPercentComplete()
    {
        return (float)_currentIteration / (float)NUM_ITERATIONS;
    }

    /**
     * @return True if the algorithm has completed, false otherwise
     */
    public boolean IsCompleted()
    {
        return (float)_currentIteration / (float)NUM_ITERATIONS >= 1.0f;
    }

    /**
     * Generates the notes.
     */
    public void Generate()
    {
        //Generate the tones
        Overtone.GameMusic = new Score();
        Overtone.GameMusic.setTempo(60);
        int numbOfTones = 10;
        Overtone.GameInstruments = new Instrument[numbOfTones];

        Phrase phr1 = new Phrase();
        for(int i = 0; i < numbOfTones; i++)
        {
            Note[] n = new Note[1];
            n[0] = new Note(C4, QUARTER_NOTE);
            phr1.addNoteList(n);
        }

        Phrase phr2 = new Phrase();
        for(int i = 0; i < numbOfTones; i++)
        {
            Note[] n = new Note[1];
            n[0] = new Note(C3, QUARTER_NOTE);
            phr2.addNoteList(n);
        }

        Phrase[] phrases = Crossover(phr1, phr2);
        Part p = new Part();
        p.addPhraseList(phrases);
        Overtone.GameMusic.addPart(p);

        // Generate the notes and store them in the game and backup arrays
        ArrayList<OvertoneNote> tempNote = GenerateGameNotes();
        Utilities.SortNotes(tempNote);

        // Set the total time
        Overtone.TotalTime   = 3.0f + (float)32 * 2.0f;

        // Set the current rater values
        for(int i = 0; i < Overtone.CurrentRaterValues.length; i++)
        {
            Overtone.CurrentRaterValues[i] = Utilities.Clamp(Overtone.BestRaterValues[i] + 0.01f, 0.0f, 1.0f);
        }

        // Write the music to a file for playback
        Write.midi(Overtone.GameMusic, "Music\\GeneratedMusic.mid");

        // Some extra work just cause
        for(int i = 0; i < NUM_ITERATIONS; i++){_currentIteration++;}
    }

    /**
     * Goes through the generated tones to create note objects for gameplay
     * @return an array of notes for gameplay
     */
    private ArrayList<OvertoneNote> GenerateGameNotes()
    {
        ArrayList<OvertoneNote> tempNote = new ArrayList<OvertoneNote>();

        // Create a double note
        OvertoneNote d1 = new OvertoneNote(OvertoneNote.NoteType.Double, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[0], 3.0f + (float)0 * 2.0f);
        OvertoneNote d2 = new OvertoneNote(OvertoneNote.NoteType.Double, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[1], 3.0f + (float)0 * 2.0f);
        d1.SetOtherNote(d2);
        d2.SetOtherNote(d1);
        d1.SetOtherNoteTime(3.0f + (float)0 * 2.0f);
        d2.SetOtherNoteTime(3.0f + (float)0 * 2.0f);
        tempNote.add(d1);
        tempNote.add(d2);

        // Create a hold note
        OvertoneNote d3 = new OvertoneNote(OvertoneNote.NoteType.Hold, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[0], 3.0f + (float)1 * 2.0f);
        OvertoneNote d4 = new OvertoneNote(OvertoneNote.NoteType.Hold, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[0], 3.0f + (float)2 * 2.0f);
        d3.SetOtherNote(d4);
        d4.SetOtherNote(d3);
        d3.SetOtherNoteTime(3.0f + (float)2 * 2.0f);
        d4.SetOtherNoteTime(3.0f + (float)1 * 2.0f);
        tempNote.add(d3);
        tempNote.add(d4);

        // Create a hold note
        OvertoneNote d5 = new OvertoneNote(OvertoneNote.NoteType.Hold, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[1], 3.0f + (float)3 * 2.0f);
        OvertoneNote d6 = new OvertoneNote(OvertoneNote.NoteType.Hold, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[1], 3.0f + (float)5 * 2.0f);
        d5.SetOtherNote(d6);
        d6.SetOtherNote(d5);
        d5.SetOtherNoteTime(3.0f + (float)5 * 2.0f);
        d6.SetOtherNoteTime(3.0f + (float)3 * 2.0f);
        tempNote.add(d5);
        tempNote.add(d6);

        // Create a hold note
        OvertoneNote d7 = new OvertoneNote(OvertoneNote.NoteType.Hold, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[2], 3.0f + (float)4 * 2.0f);
        OvertoneNote d8 = new OvertoneNote(OvertoneNote.NoteType.Hold, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[2], 3.0f + (float)6 * 2.0f);
        d7.SetOtherNote(d8);
        d8.SetOtherNote(d7);
        d7.SetOtherNoteTime(3.0f + (float)6 * 2.0f);
        d8.SetOtherNoteTime(3.0f + (float)4 * 2.0f);
        tempNote.add(d7);
        tempNote.add(d8);

        // Load notes
        for(int i = 7; i < 32; i++)
        {
            OvertoneNote n = new OvertoneNote(OvertoneNote.NoteType.Single, new Vector2(Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f), new Vector2(Overtone.ScreenWidth / 2.0f, Overtone.ScreenHeight / 2.0f),  Overtone.TargetZones[i % Overtone.TargetZones.length], 3.0f + (float)i * 2.0f);
            tempNote.add(n);
        }
        return tempNote;
    }

    /**
     * Crossover method for the genetic algorithm.
     * Takes two phrases and creates two children that are a mixture of both.
     * @param p1 The first phrase
     * @param p2 The second phrase
     * @return an array of children phrases
     */
    private Phrase[] Crossover(Phrase p1, Phrase p2)
    {
        // Create two children
        Phrase[] children = new Phrase[2];
        children[0] = new Phrase();
        children[1] = new Phrase();

        // Find the shorter of the two lengths (used as base length)
        int length = p1.length() < p2.length() ? p1.length() : p2.length();

        // Crossover
        for(int i = 0; i < length; i++)
        {
            Note n1 = p1.getNote(i);
            Note n2 = p2.getNote(i);

            int index = Utilities.GetRandom(0, 1, 0.6f, 0.4f);
            if(index == 0)
            {
                children[0].addNote(n1);
                children[1].addNote(n2);
            }
            else
            {
                children[0].addNote(n2);
                children[1].addNote(n1);
            }
        }

        // Add remaining notes to the proper child phrase
        if(length - p1.length() == 0)
        {
            int remainingLength = Math.abs(length - p2.length());
            for(int i = 0; i < remainingLength; i++)
                children[1].addNote(p2.getNote(length + i));
        }
        else
        {
            int remainingLength = Math.abs(length - p1.length());
            for(int i = 0; i < remainingLength; i++)
                children[0].addNote(p1.getNote(length + i));
        }

        return children;
    }
}
