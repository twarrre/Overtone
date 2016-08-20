package com.overtone.GeneticAlgorithm;

import com.badlogic.gdx.math.Vector2;
import com.overtone.GeneticAlgorithm.Mutators.Mutator;
import com.overtone.GeneticAlgorithm.Mutators.NotePitchMutator;
import com.overtone.GeneticAlgorithm.Mutators.SimplifyMutator;
import com.overtone.GeneticAlgorithm.Mutators.SwapMutator;
import com.overtone.GeneticAlgorithm.Raters.*;
import com.overtone.Notes.OvertoneNote;
import com.overtone.Overtone;
import com.overtone.Screens.GameplayScreen;
import com.overtone.Utilities;
import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Object to generate the music for the game
 * Created by trevor on 2016-08-01.
 */
public class GeneticAlgorithm implements Runnable, JMC
{
    /** Represents the number of iterations the algorithm is going to go through*/
    public static final int NUM_ITERATIONS = Integer.MAX_VALUE;
    /** The size of the population of tracks. */
    public static final int POPULATION_SIZE = 100;

    private int                _currentIteration; // The current iteration of the algorithm
    private ArrayList<Mutator> _mutators;         // Array of all of the mutators that may mutate a track.
    private Rater[]            _raters;           // Array of raters to rate the tracks

    /**
     * Constructor
     */
    public GeneticAlgorithm()
    {
        _currentIteration = 0;
        _raters           = new Rater[5];
        _raters[0]        = new NeighboringPitchRater();
        _raters[1]        = new PitchDirectionRater();
        _raters[2]        = new PitchRangerRater();
        _raters[3]        = new UniqueNoteRater();
        _raters[4]        = new RepetitionRater();
        _mutators         = new ArrayList<>();
        _mutators.add(new NotePitchMutator());
        _mutators.add(new SimplifyMutator());
        _mutators.add(new SwapMutator());
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
        // Initialize score for game music
        Overtone.GameMusic       = new Score();
        Overtone.GameInstruments = new Instrument[10];

        //Get Back the best two phrase from the genetic algorithm
        Organism[] bestThreeTracks = GenerateTracks();

        // Create phases that create the song. Structure of the song is verse, chorus, verse, chorus, bridge, chorus. Mutate each one so that there is a bit of variation between them
        Phrase[] song = new Phrase[6];
        song[0] = Mutation(bestThreeTracks[1]).GetTrack(); // Verse 1
        song[1] = Mutation(bestThreeTracks[0]).GetTrack(); // Chorus 1
        song[2] = Mutation(bestThreeTracks[1]).GetTrack(); // Verse 2
        song[3] = Mutation(bestThreeTracks[0]).GetTrack(); // Chorus 2
        song[4] = Mutation(bestThreeTracks[2]).GetTrack(); // bridge 1
        song[5] = Mutation(bestThreeTracks[1]).GetTrack(); // Chorus 3

        // Adds the song to the game track
        Part p = new Part();
        p.addPhraseList(song);
        Overtone.GameMusic.addPart(p);

        // Generate the notes and store them in the game and backup arrays
        ArrayList<OvertoneNote> tempNote = GenerateGameNotes();
        Utilities.SortNotes(tempNote);

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
     * The genetic algorithm. Runs through the genetic algorithm.
     * @return The best two tracks generated after the genetic algorithm
     */
    private Organism[] GenerateTracks()
    {
        // Initialization Phrase
        Organism[] initialPopulation = Initialization();

        //Genetic algorithm phase
        for(int i = 0; i < NUM_ITERATIONS; i++)
        {
            // TODO: Do iteration stuff in here
            // TODO: Selection / Fitness Rating
            // TODO: Crossover
        }

        return new Organism[] {initialPopulation[0], initialPopulation[1], initialPopulation[0]};
    }

    /**
     * The initialization phase of the genetic algorithm. Generates the initial population
     * @return an array of phrases that is the initial population of the algorithm
     */
    private Organism[] Initialization()
    {
        Organism[] population = new Organism[POPULATION_SIZE];
        // TODO: Generate the initial population here
        //Mess with dynamics (loudness, softness)
        //Mess with rest can check if rest
        //mess with pan

        Phrase[] tracks = new Phrase[3];
        tracks[0] = new Phrase();
        tracks[1] = new Phrase();
        tracks[2] = new Phrase();
        for(int i = 0; i < 10; i++)
        {
            Note[] n = new Note[1];
            n[0] = new Note(C4, QUARTER_NOTE);
            tracks[0].addNoteList(n);

            Note[] n2 = new Note[1];
            n2[0] = new Note(C3, WHOLE_NOTE);
            tracks[1].addNoteList(n2);

            Note[] n3 = new Note[1];
            n3[0] = new Note(C2, QUARTER_NOTE);
            tracks[2].addNoteList(n3);
        }

        return new Organism[] {new Organism(tracks[0], Organism.STARTING_PROBABILITY), new Organism(tracks[1], Organism.STARTING_PROBABILITY), new Organism(tracks[2], Organism.STARTING_PROBABILITY)};
    }

    /**
     * Gets the top rated tracks out of the array passed in
     * @param o All the tracks for the generation
     * @return An array of the the best tracks
     */
    private Organism[] Eliteism(Organism[] o)
    {
        return o;
    }

    /**
     * Selects orgganisms to crossover
     * @param parents The parents to crossover
     * @return
     */
    private Organism[] Selection(Organism[] parents)
    {
        return parents;
    }

    /**
     * Goes through all of the raters to rate the passed in phrase
     * @param p the phrase to be rated
     * @return returns the organism after it's rating values have been filled in
     */
    private Organism FitnessRating(Organism p)
    {
        for(int i = 0; i < Overtone.NUM_RATERS; i++)
            p.SetRating(_raters[i].Rate(p), i);

        return p;
    }

    /**
     * Goes through all of the mutators and mutates the passed in phrase.
     * @param o The phrase to be mutated
     * @return A new Phrase that has been mutated
     */
    private Organism Mutation(Organism o)
    {
        // Randomize the order of the mutators
        long seed = System.nanoTime();
        Collections.shuffle(_mutators, new Random(seed));

        // Mutate the phrase
        Phrase mutation = o.GetTrack();
        for(int i = 0; i < _mutators.size(); i++)
            mutation = _mutators.get(i).Mutate(mutation, o.GetProbability());
        o.SetTrack(mutation);
        return o;
    }

    /**
     * Crossover method for the genetic algorithm.
     * Takes two phrases and creates two children that are a mixture of both.
     * @param parent1 The first phrase
     * @param parent2 The second phrase
     * @return an array of children phrases
     */
    private Organism[] Crossover(Organism parent1, Organism parent2)
    {
        // Create two children
        Phrase[] children = new Phrase[2];
        children[0] = new Phrase();
        children[1] = new Phrase();

        Phrase p1 = parent1.GetTrack();
        Phrase p2 = parent2.GetTrack();

        // Find the shorter of the two lengths (used as base length)
        int length = p1.length() < p2.length() ? p1.length() : p2.length();

        // Crossover
        for(int i = 0; i < length; i++)
        {
            Note n1 = p1.getNote(i);
            Note n2 = p2.getNote(i);

            int index = Utilities.GetRandom(0, 1, 0.6f);
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

        return new Organism[] { new Organism(children[0], GetProbability(parent1)), new Organism(children[1], GetProbability(parent2))};
    }

    /**
     * Calculates the probability for mutation of the child organism
     * @param o the parent organism
     * @return The probability for mutation for the child organism.
     */
    private float GetProbability(Organism o)
    {
        return o.GetProbability() - (Organism.MUTATION_STEP / (float)(_currentIteration / NUM_ITERATIONS));
    }

    /**
     * Goes through the generated tones to create note objects for gameplay
     * @return an array of notes for gameplay
     */
    private ArrayList<OvertoneNote> GenerateGameNotes()
    {
        long seed = System.nanoTime();
        Random r = new Random(seed);
        ArrayList<OvertoneNote> tempNote = new ArrayList<OvertoneNote>();
        Part[] parts = Overtone.GameMusic.getPartArray();
        float elapsedTime = GameplayScreen.START_DELAY + 0.001f;
        int prevTarget = 0;
        int target     = 0;

        for(int i = 0; i <  parts.length; i++)
        {
            Phrase[] phrases = parts[i].getPhraseArray();
            for(int j = 0; j < phrases.length; j++)
            {
                Note[] notes = phrases[j].getNoteArray();
                for(int k = 0; k < notes.length; k++)
                {
                    prevTarget = target;
                    target = r.nextInt(Overtone.TargetZones.length);

                    if(notes[k].isRest())
                    {
                        continue;
                    }
                    else if(notes[k].getRhythmValue() > QUARTER_NOTE)
                    {
                        while (target == prevTarget)
                            target = r.nextInt(Overtone.TargetZones.length);

                        OvertoneNote n1 = new OvertoneNote(
                            OvertoneNote.NoteType.Hold, // Note type
                            Overtone.TargetZones[target], // target
                            elapsedTime);
                        float n1Time = elapsedTime;

                        elapsedTime += notes[k].getDuration();

                        OvertoneNote n2 = new OvertoneNote(
                            OvertoneNote.NoteType.Hold, // Note type
                            Overtone.TargetZones[target], // target
                            elapsedTime - 0.05f);
                        float n2Time = elapsedTime - 0.05f;

                        n1.SetOtherNote(n2);
                        n1.SetOtherNoteTime(n2Time);
                        n2.SetOtherNote(n1);
                        n2.SetOtherNoteTime(n1Time);

                        tempNote.add(n1);
                        tempNote.add(n2);
                    }
                    // Handle double notes
                    else
                    {
                        tempNote.add(new OvertoneNote(
                            OvertoneNote.NoteType.Single, // Note type
                            Overtone.TargetZones[target], // target
                            elapsedTime)); // time
                        elapsedTime += notes[k].getDuration();
                    }
                }
            }
        }

        Overtone.TotalTime = elapsedTime;
        return tempNote;
    }
}
