package com.overtone.GeneticAlgorithm;

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
import jm.constants.Durations;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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

    private int[][] chords = {{C3, E3, G3}, {F3,A3, C3}, {G3, B3, D3}}; // Available chords to choose from

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
        Part[] song = new Part[6];
        song[0] = Mutation(bestThreeTracks[1]).GetTrack(); // Verse 1
        song[1] = Mutation(bestThreeTracks[0]).GetTrack(); // Chorus 1
        song[2] = Mutation(bestThreeTracks[1]).GetTrack(); // Verse 2
        song[3] = Mutation(bestThreeTracks[0]).GetTrack(); // Chorus 2
        song[4] = bestThreeTracks[2].GetTrack(); // bridge 1
        song[5] = Mutation(bestThreeTracks[1]).GetTrack(); // Chorus 3

        // Merges the song and adds it to the game music
        Part mergedPart = new Part();
        for(int i = 0; i < song.length; i++)
           for(int j = 0; j < song[i].length(); j++)
               mergedPart.appendPhrase(song[i].getPhrase(j));
        Overtone.GameMusic.addPart(mergedPart);

        // Generate the notes and store them in the game and backup arrays
        ArrayList<OvertoneNote> tempNote = GenerateGameNotes();
        Utilities.SortNotes(tempNote);

        // Set the current rater values
        for(int i = 0; i < Overtone.CurrentRaterValues.length; i++)
            Overtone.CurrentRaterValues[i] = Utilities.Clamp(Overtone.BestRaterValues[i] + 0.01f, 0.0f, 1.0f);

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

        Part[] tracks = new Part[3];
        tracks[0] = new Part();
        tracks[1] = new Part();
        tracks[2] = new Part();
        for(int i = 0; i < 10; i++)
        {
            Random r = new Random();
            int c = r.nextInt(chords.length);
            Phrase chord = new Phrase();
            chord.addChord(chords[c], QUARTER_NOTE);
            tracks[0].addPhrase(chord);
            tracks[1].addPhrase(new Phrase(new Note(C3, WHOLE_NOTE)));
            tracks[2].addPhrase(new Phrase(new Note(C2, QUARTER_NOTE)));
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
        Collections.shuffle(_mutators, new Random(System.nanoTime()));

        // Mutate the phrase
        Part mutation = o.GetTrack();
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
        Part[] children = new Part[2];
        children[0] = new Part();
        children[1] = new Part();

        Part p1 = parent1.GetTrack();
        Part p2 = parent2.GetTrack();

        // Find the shorter of the two lengths (used as base length)
        int length = p1.length() < p2.length() ? p1.length() : p2.length();

        // Crossover
        for(int i = 0; i < length; i++)
        {
            Phrase ph1 = p1.getPhrase(i);
            Phrase ph2 = p2.getPhrase(i);

            int index = Utilities.GetRandom(0, 1, 0.6f);
            if(index == 0)
            {
                children[0].addPhrase(ph1);
                children[1].addPhrase(ph2);
            }
            else
            {
                children[0].addPhrase(ph2);
                children[1].addPhrase(ph1);
            }
        }

        // Add remaining notes to the proper child phrase
        if(length - p1.length() == 0)
        {
            int remainingLength = Math.abs(length - p2.length());
            for(int i = 0; i < remainingLength; i++)
                children[1].addPhrase(p2.getPhrase(length + i));
        }
        else
        {
            int remainingLength = Math.abs(length - p1.length());
            for(int i = 0; i < remainingLength; i++)
                children[0].addPhrase(p1.getPhrase(length + i));
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
        Random r = new Random(System.nanoTime());

        ArrayList<OvertoneNote> tempNote = new ArrayList<OvertoneNote>();
        Part[] parts = Overtone.GameMusic.getPartArray();

        float elapsedTime = GameplayScreen.START_DELAY;
        int prevTarget    = 0;
        int target        = 0;

        for(int i = 0; i <  parts.length; i++)
        {
            Phrase[] phrases = parts[i].getPhraseArray();
            for(int j = 0; j < phrases.length; j++)
            {
                prevTarget = target;
                target = r.nextInt(Overtone.TargetZones.length);

                // If it is a reset then continue
                if(phrases[j].getNote(0).isRest())
                {
                    elapsedTime += phrases[j].getNote(0).getDuration();
                    continue;
                }
                else if(phrases[j].length() > 1) // else if it is a chord == double note
                {
                    int target2 = DetermineTarget(target);
                    OvertoneNote n1 = new OvertoneNote(OvertoneNote.NoteType.Double, Overtone.TargetZones[target], elapsedTime);
                    OvertoneNote n2 = new OvertoneNote(OvertoneNote.NoteType.Double, Overtone.TargetZones[target2], elapsedTime);

                    n1.SetOtherNote(n2);
                    n1.SetOtherNoteTime(elapsedTime);
                    n2.SetOtherNote(n1);
                    n2.SetOtherNoteTime(elapsedTime);

                    tempNote.add(n1);
                    tempNote.add(n2);

                    elapsedTime += phrases[j].getNote(phrases[j].length() - 1).getDuration();
                }
                else if(phrases[j].getNote(0).getRhythmValue() > QUARTER_NOTE) // if it is longer than a quarter note == hold note
                {
                    // Make sure that hold notes do not go to the same target in a row
                    while (target == prevTarget)
                        target = r.nextInt(Overtone.TargetZones.length);

                    OvertoneNote n1 = new OvertoneNote(OvertoneNote.NoteType.Hold, Overtone.TargetZones[target], elapsedTime);
                    float n1Time = elapsedTime;
                    elapsedTime += phrases[j].getNote(0).getDuration();

                    OvertoneNote n2 = new OvertoneNote(OvertoneNote.NoteType.Hold, Overtone.TargetZones[target], elapsedTime - 0.05f);
                    float n2Time = elapsedTime - 0.05f;

                    n1.SetOtherNote(n2);
                    n1.SetOtherNoteTime(n2Time);
                    n2.SetOtherNote(n1);
                    n2.SetOtherNoteTime(n1Time);

                    tempNote.add(n1);
                    tempNote.add(n2);
                }
                else // It is a normal note
                {
                    tempNote.add(new OvertoneNote(OvertoneNote.NoteType.Single, Overtone.TargetZones[target], elapsedTime));
                    elapsedTime += phrases[j].getNote(0).getDuration();
                }
            }
        }

        Overtone.TotalTime = elapsedTime;
        return tempNote;
    }

    /**
     * Randomly chooses a perpendicular target as a partner target for the double note
     * @param target The target to find a partner target for
     * @return The index of the other target
     */
    private int DetermineTarget(int target)
    {
        Random r = new Random(System.nanoTime());
        int num = r.nextInt(1);

        switch(target)
        {
            case 0:
            {
                if(num == 0)
                    return target + 1;
                else
                    return target + 2;
            }
            case 1:
            {
                if(num == 0)
                    return target - 1;
                else
                    return target + 2;
            }
            case 2:
            {
                if(num == 0)
                    return target + 1;
                else
                    return target - 2;
            }
            case 3:
            {
                if(num == 0)
                    return target - 1;
                else
                    return target - 2;
            }
            default:
                return 0;
        }
    }
}
