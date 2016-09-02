package com.overtone.GeneticAlgorithm;

import com.overtone.GeneticAlgorithm.Mutators.*;
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

import java.util.*;

/**
 * Object to generate the music for the game
 * Created by trevor on 2016-08-01.
 */
public class GeneticAlgorithm implements Runnable, JMC
{
    /** Array of valid chords that can be used in generation */
    public static int[][] CHORDS = {
            {C3, E3, G3},
            {F3, A3, C3},
            {G3, B3, D3}
    };
    /** Array of valid rhythms used in generation. */
    public static final ArrayList<Double> RHYTHMS = new ArrayList<Double>()
    {{
        add(THIRTYSECOND_NOTE);
        add(DOTTED_SIXTEENTH_NOTE);
        add(SIXTEENTH_NOTE);
        add(DOUBLE_DOTTED_EIGHTH_NOTE);
        add(DOTTED_EIGHTH_NOTE);
        add(EIGHTH_NOTE);
        add(DOUBLE_DOTTED_QUARTER_NOTE);
        add(DOTTED_QUARTER_NOTE);
        add(QUARTER_NOTE);
        add(HALF_NOTE);
        add(DOUBLE_DOTTED_HALF_NOTE);
        add(DOTTED_HALF_NOTE);
        add(WHOLE_NOTE);
    }};

    /** The number of sections(Verse, chorus, bridges) in the song. */
    public static int NUM_SECTIONS = 4;

    private int                _currentIteration; // The current iteration of the algorithm
    private ArrayList<Mutator> _mutators;         // Array of all of the mutators that may mutate a track.
    private Rater[]            _raters;           // Array of raters to rate the tracks
    private boolean            _isCompleted;      // True if the generation has completed;
    private Random             _random;

    /**
     * Constructor
     */
    public GeneticAlgorithm()
    {
        _currentIteration = 0;
        _raters            = new Rater[Overtone.NUM_RATERS];
        _raters[0]         = new NeighboringPitchRater();
        _raters[1]         = new PitchDirectionRater();
        _raters[2]         = new PitchRangeRater();
        _raters[3]         = new UniqueNoteRater();
        _raters[4]         = new UniqueRhythmValuesRater();
        _raters[5]         = new ContinuousSilenceRater();
        _raters[6]         = new DirectionStabilityRater();
        _raters[7]         = new SyncopationNoteRater();
        _raters[8]         = new EqualConsecutiveNoteRater();
        _mutators          = new ArrayList<>();
        _isCompleted       = false;
        _random            = new Random();
        _mutators.add(new NotePitchMutator());
        _mutators.add(new SimplifyMutator());
        _mutators.add(new SwapMutator());
        _mutators.add(new RhythmMutator());
    }

    public void run()
    {
        Generate();
    }

    /**
     * @return True if the algorithm has completed, false otherwise
     */
    public boolean IsCompleted()
    {
        return _isCompleted;
    }

    /**
     * Generates the notes.
     */
    private void Generate()
    {
        _isCompleted = false;
        // Initialize score for game music
        Overtone.GameMusic       = new Score();
        Overtone.GameInstruments = new Instrument[10];

        //Get Back the best two phrase from the genetic algorithm
        Organism[] bestThreeTracks = GenerateTracks();

        // Create phases that create the song. Structure of the song is verse, chorus, verse, chorus, bridge, chorus. Mutate each one so that there is a bit of variation between them
        Part[] song = new Part[NUM_SECTIONS];
        song[0] = bestThreeTracks[1].GetTrack(); // Verse 1
        song[1] = bestThreeTracks[0].GetTrack(); // Chorus 1
        song[2] = bestThreeTracks[2].GetTrack(); // bridge 1
        song[3] = Mutation(bestThreeTracks[0]).GetTrack(); // Chorus 2

        // Merges the song and adds it to the game music
        Part mergedPart = new Part();
        for(int i = 0; i < song.length; i++)
           for(int j = 0; j < song[i].length(); j++)
               mergedPart.appendPhrase(song[i].getPhrase(j));
        Overtone.GameMusic.addPart(mergedPart);

        // Generate the notes and store them in the game and backup arrays
        ArrayList<OvertoneNote> tempNotes = GenerateGameNotes();
        Utilities.SortNotes(tempNotes);

        // Set the current rater values
        for(int i = 0; i < Overtone.CurrentRaterValues.length; i++)
        {
            float sum = 0;
            for(int j = 0; j < bestThreeTracks.length; j++)
                sum += bestThreeTracks[j].GetOverallRating();
            sum /= bestThreeTracks.length;
            Overtone.CurrentRaterValues[i] = sum;
        }

        // Write the music to a file for playback
        Write.midi(Overtone.GameMusic, "Music\\GeneratedMusic.mid");
        _isCompleted = true;
    }

    /**
     * The genetic algorithm. Runs through the genetic algorithm.
     * @return The best two tracks generated after the genetic algorithm
     */
    private Organism[] GenerateTracks()
    {
        float bestRaterScoreAverage = 0;
        for(int i = 0; i < Overtone.BestRaterValues.length; i++)
            bestRaterScoreAverage += Overtone.BestRaterValues[i];
        bestRaterScoreAverage /= Overtone.BestRaterValues.length;

        // Initialization Phrase
        Organism[] population = new Organism[3];
        Organism[] parentPopulation = Initialization();
        float parentAverageRating = 0;

        for(int i = 0; i < parentPopulation.length; i++)
        {
            parentPopulation[i] = FitnessRating(parentPopulation[i]);
            parentAverageRating += parentPopulation[i].GetOverallRating();
        }
        parentAverageRating /= parentPopulation.length;

        //Genetic algorithm phase
        for(int i = 0; i < Overtone.NumberOfIterations; i++)
        {
            // Get the children
            population = Selection(parentPopulation);
            float populationRating = 0;

            // Rate the children
            for(int j = 0; j < population.length; j++)
            {
                population[j] = FitnessRating(population[j]);
                populationRating += population[j].GetOverallRating();
            }

            populationRating /= population.length;

            // If the children are better then choose them instead
            if(Math.abs(bestRaterScoreAverage - populationRating) > Math.abs(bestRaterScoreAverage - parentAverageRating))
            {
                parentPopulation    = population;
                parentAverageRating = populationRating;
            }
        }

        Arrays.sort(population, new RatingComparator());
        return new Organism[] {population[0], population[1], population[2]};
    }

    /**
     * The initialization phase of the genetic algorithm. Generates the initial population
     * @return an array of phrases that is the initial population of the algorithm
     */
    public Organism[] Initialization()
    {
        Organism[] population = new Organism[Overtone.PopulationSize];
        // TODO: Generate the initial population here
        //Mess with rest can check if rest
        // Do chords

        for(int i = 0; i < Overtone.PopulationSize; i++)
        {
            Part p = new Part();
            for(int j = 0; j < 12; j++)
            {
                _random.nextInt(3);

                if(i % 3 == 0)
                {
                    int c = _random.nextInt(CHORDS.length);
                    Phrase chord = new Phrase();
                    chord.addChord(CHORDS[c], QUARTER_NOTE);
                    p.addPhrase(chord);
                }
                else if (i % 2 == 0)
                {
                    p.addPhrase(new Phrase(new Note(C3 + j, QUARTER_NOTE)));
                }
                else
                {
                    p.addPhrase(new Phrase(new Note(C2 + j, WHOLE_NOTE)));
                }
            }

            population[i] = new Organism(p, Organism.STARTING_PROBABILITY);
        }

        return population;
    }

    /**
     * Selects organisms to crossover
     * @param parents The parents to crossover
     * @return A generation of children organisms
     */
    private Organism[] Selection(Organism[] parents)
    {
        Arrays.sort(parents, new RatingComparator());
        Organism[] children = new Organism[Overtone.PopulationSize];

        // Elitism, save the best ones
        int counter;
        for(counter = 0; counter < Overtone.NumberOfElites; counter++)
            children[counter] = parents[counter];

        while(counter < Overtone.PopulationSize)
        {
            int p1 = RouletteSelection(parents);
            int p2 = p1;
            while(p2 == p1)
                p2 = RouletteSelection(parents);

            Organism[] siblings = Crossover(parents[p1], parents[p2]);

            boolean overflow = false;
            for(int i = 0; i < siblings.length; i++)
            {
                if(counter + i >= Overtone.PopulationSize)
                {
                    overflow = true;
                    continue;
                }

                children[counter + i] = Mutation(siblings[i]);
            }

            if(overflow)
                counter++;
            else
                counter += siblings.length;
        }

        return children;
    }

    /**
     * Goes through all of the raters to rate the passed in phrase
     * @param p the phrase to be rated
     * @return returns the organism after it's rating values have been filled in
     */
    private Organism FitnessRating(Organism p)
    {
        float overallRating = 0;
        for(int i = 0; i < Overtone.NUM_RATERS; i++)
        {
            p.SetRating(_raters[i].Rate(p), i);
            if(Overtone.BestRaterValues[i] == -1)
                p.SetQuality(Math.abs(p.GetRating(i)), i);
            else
                p.SetQuality(Math.abs(Overtone.BestRaterValues[i] - p.GetRating(i)), i);
            overallRating += p.GetQuality(i);
        }

        overallRating /= Overtone.NUM_RATERS;
        p.SetOverallRating(1.0f - overallRating);

        return p;
    }

    /**
     * Goes through all of the mutators and mutates the passed in phrase.
     * @param o The phrase to be mutated
     * @return A new Phrase that has been mutated
     */
    public Organism Mutation(Organism o)
    {
        // Randomize the order of the mutators
        Collections.shuffle(_mutators, _random);

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
        return o.GetProbability() - (Organism.MUTATION_STEP / (float)(_currentIteration / Overtone.NumberOfIterations));
    }

    /**
     * Selects a parent based on thief probability, higher ones are chose more often
     * @param parents The parent organisms
     * @return The index to the parent chosen for crossover
     */
    private int RouletteSelection(Organism[] parents)
    {
        int index = 0;

        float sum = 0;
        for(int i = 0; i < parents.length; i++)
            sum += parents[i].GetOverallRating();

        float rand = _random.nextFloat() * sum;

        sum = 0;
        for(int i = 0; i < parents.length; i++)
        {
            sum += parents[i].GetOverallRating();

            if(sum > rand)
            {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Goes through the generated tones to create note objects for gameplay
     * @return an array of notes for gameplay
     */
    public ArrayList<OvertoneNote> GenerateGameNotes()
    {
        ArrayList<OvertoneNote> tempNotes = new ArrayList<OvertoneNote>();
        Part[] parts = Overtone.GameMusic.getPartArray();

        float elapsedTime                  = GameplayScreen.START_DELAY;
        int prevTarget                     = 0;
        int target                         = 0;
        OvertoneNote.NoteType previousType = OvertoneNote.NoteType.Single;

        for(int i = 0; i <  parts.length; i++)
        {
            Phrase[] phrases = parts[i].getPhraseArray();
            for(int j = 0; j < phrases.length; j++)
            {
                boolean includeHoldNote = true;
                boolean includeDoubleNote = true;
                if(Overtone.Difficulty == Overtone.Difficulty.Easy)
                {
                    includeHoldNote = Utilities.GetRandom(0, 1, 0.2f) == 0 ? true : false;
                    includeDoubleNote = Utilities.GetRandom(0, 1, 0.2f) == 0 ? true : false;
                }
                else if(Overtone.Difficulty == Overtone.Difficulty.Normal)
                {
                    includeHoldNote = Utilities.GetRandom(0, 1, 0.45f) == 0 ? true : false;
                    includeDoubleNote = Utilities.GetRandom(0, 1, 0.45f) == 0 ? true : false;
                }

                prevTarget = target;
                target = _random.nextInt(Overtone.TargetZones.length);

                if(previousType == OvertoneNote.NoteType.Hold)
                    while (target == prevTarget)
                        target = _random.nextInt(Overtone.TargetZones.length);

                elapsedTime += phrases[j].getNote(phrases[j].length() - 1).getDuration();
                if(phrases[j].length() > 1 && includeDoubleNote) // else if it is a chord == double note
                {
                    OvertoneNote[] notes = CreateDoubleNote(target, elapsedTime);
                    tempNotes.add(notes[0]);
                    tempNotes.add(notes[1]);
                    previousType = OvertoneNote.NoteType.Double;
                }
                else if(phrases[j].getNote(0).isRest()) // if it is a rest, continue
                {
                    previousType = OvertoneNote.NoteType.Single;
                    continue;
                }
                else if(phrases[j].getNote(0).getRhythmValue() > DOUBLE_DOTTED_QUARTER_NOTE && includeHoldNote) // if it is longer than a quarter note == hold note
                {
                    OvertoneNote[] notes = CreateHoldNote(target, elapsedTime, phrases[j].getNote(0).getDuration());
                    tempNotes.add(notes[0]);
                    tempNotes.add(notes[1]);
                    previousType = OvertoneNote.NoteType.Hold;
                }
                else // It is a normal note
                {
                    tempNotes.add(CreateSingleNote(target, elapsedTime));
                    previousType = OvertoneNote.NoteType.Single;
                }
            }
        }

        Overtone.TotalTime = elapsedTime + GameplayScreen.COMPLETION_DELAY;
        return tempNotes;
    }

    /**
     * Creates a double note
     * @param target the target of the first note
     * @param elapsedTime the elapsed time of the song
     * @return and array of notes representing a double note
     */
    private OvertoneNote[] CreateDoubleNote(int target, float elapsedTime)
    {
        int target2 = DetermineTarget(target);
        OvertoneNote[] note = new OvertoneNote[2];
        note[0] = new OvertoneNote(OvertoneNote.NoteType.Double, Overtone.TargetZones[target], elapsedTime);
        note[1] = new OvertoneNote(OvertoneNote.NoteType.Double, Overtone.TargetZones[target2], elapsedTime);

        note[0].SetOtherNote(note[1]);
        note[0].SetOtherNoteTime(note[1].GetTime());
        note[1].SetOtherNote(note[0]);
        note[1].SetOtherNoteTime(note[0].GetTime());

        return note;
    }

    /**
     * Creates a hold note
     * @param target the target of the notes
     * @param elapsedTime the elapsed time of the song
     * @param noteDuration The duration of the first note
     * @return and array of notes that represents a hold note
     */
    private OvertoneNote[] CreateHoldNote(int target, float elapsedTime, double noteDuration)
    {
        OvertoneNote[] note = new OvertoneNote[2];
        note[0] = new OvertoneNote(OvertoneNote.NoteType.Hold, Overtone.TargetZones[target], elapsedTime);
        note[1] = new OvertoneNote(OvertoneNote.NoteType.Hold, Overtone.TargetZones[target], elapsedTime + (float)noteDuration - 0.05f);

        note[0].SetOtherNote(note[1]);
        note[0].SetOtherNoteTime(note[1].GetTime());
        note[1].SetOtherNote(note[0]);
        note[1].SetOtherNoteTime(note[0].GetTime());

        return note;
    }

    /**
     * Creates a single note
     * @param target the target of the note
     * @param elapsedTime the elapsed time of the song
     * @return a single note.
     */
    private OvertoneNote CreateSingleNote(int target, float elapsedTime)
    {
        return new OvertoneNote(OvertoneNote.NoteType.Single, Overtone.TargetZones[target], elapsedTime);
    }

    /**
     * Randomly chooses a perpendicular target as a partner target for the double note
     * @param target The target to find a partner target for
     * @return The index of the other target
     */
    private int DetermineTarget(int target)
    {
        int num = _random.nextInt(1);

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

    /**
     * Comparator for sorting organisms based on their ratings
     * Sorts them in reverse.
     */
    static class RatingComparator implements Comparator<Organism>
    {
        public int compare(Organism o1, Organism o2)
        {
            if(o1.GetOverallRating() < o2.GetOverallRating())
                return 1;
            else if (o1.GetOverallRating() == o2.GetOverallRating())
                return 0;
            else
                return -1;
        }
    }
}


