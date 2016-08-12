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

        Overtone.GameMusic = new Score();
        Overtone.GameMusic.setTempo(60);
        int numbOfTones = 10;
        Overtone.GameInstruments = new Instrument[numbOfTones];

        Part p = new Part();
        for(int i = 0; i < numbOfTones; i++)
        {
            Note n = new Note(C4, QUARTER_NOTE);
            Phrase phr = new Phrase();
            phr.addNote(n);
            p.addPhrase(phr);
        }

        for(int i = 0; i < numbOfTones; i++)
        {
            Note n = new Note(C3, QUARTER_NOTE);
            Phrase phr = new Phrase();
            phr.addNote(n);
            p.addPhrase(phr);
        }
        Overtone.GameMusic.addPart(p);

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

        Utilities.SortNotes(tempNote);
        Overtone.TotalTime   = 3.0f + (float)32 * 2.0f;
        for(int i = 0; i < Overtone.CurrentRaterValues.length; i++)
        {
            Overtone.CurrentRaterValues[i] = Utilities.Clamp(Overtone.BestRaterValues[i] + 0.01f, 0.0f, 1.0f);
        }

        Write.midi(Overtone.GameMusic, "GeneratedMusic.mid");
        for(int i = 0; i < NUM_ITERATIONS; i++){_currentIteration++;}
    }


}
