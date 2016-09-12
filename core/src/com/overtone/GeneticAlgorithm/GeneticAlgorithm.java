package com.overtone.GeneticAlgorithm;

import com.overtone.GeneticAlgorithm.Mutators.*;
import com.overtone.GeneticAlgorithm.Raters.*;
import com.overtone.Notes.OvertoneNote;
import com.overtone.Overtone;
import com.overtone.Screens.GameplayScreen;
import com.overtone.Utilities;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

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
            //C Major
            {C3, E3, G3},
            {C3, G3, E4},
            {C3, E4, G4},

            // C minor
            {C3, EF3, G3},
            {C3, G3, EF4},
            {C3, EF4, G4},

            // D major
            {D3, FS3, A3},
            {D3, A3, FS4},
            {D3, FS4, A4},

            // D minor
            {D3, F3, A3},
            {D3, A3, F4},
            {D3, F4, A4},

            // E major
            {E3, GS3, B3},
            {E3, B3, GS4},
            {E3, GS4, B4},

            // E minor
            {E3, G3, B3},
            {E3, B3, G4},
            {E3, G4, B4},

            // F Major
            {F2, A2, C3},
            {F2, C3, A3},
            {F2, A3, C4},
            {F2, C4, A4,},

            // F minor
            {F2, AF2, C3},
            {F2, C3, AF3},
            {F2, AF3, C4},
            {F2, C4, AF4},

            // G major
            {G2, B2, D3},
            {G2, D3, B3},
            {G2, B3, D4},
            {G2, D4, B4},

            // G minor
            {G2, BF2, D3},
            {G2, D3, BF3},
            {G2, BF3, D4},
            {G2, D4, BF4},

            // A major
            {A2, CS3, E3},
            {A2, E3, CS4},
            {A2, CS4, E4},

            // A minor
            {A2, C3, E3},
            {A2, E3, C4},
            {A2, C4, E4},

            // B Major
            {B2, DS3, FS3},
            {B2, FS3, DS4},
            {B2, DS4, FS4},

            // B minor
            {B2, D3, FS3},
            {B2, D4, FS3},
            {B2, D4, FS4},
    };
    /** Array of valid rhythms used in generation. */
    public static final ArrayList<Double> RHYTHMS = new ArrayList<Double>()
    {{
        //add(THIRTYSECOND_NOTE);
        //add(SIXTEENTH_NOTE);
        //add(DOTTED_SIXTEENTH_NOTE);
        add(EIGHTH_NOTE);
        add(DOTTED_EIGHTH_NOTE);
        add(DOUBLE_DOTTED_EIGHTH_NOTE);
        add(QUARTER_NOTE);
        add(DOTTED_QUARTER_NOTE);
        add(DOUBLE_DOTTED_QUARTER_NOTE);
        add(HALF_NOTE);
        add(DOTTED_HALF_NOTE);
        add(DOUBLE_DOTTED_HALF_NOTE);
        add(WHOLE_NOTE);
    }};

    /** The number of sections(Verse, chorus, bridges) in the song. */
    public static int NUM_SECTIONS = 4;
    /** The number of notes for each track in the initialization phase. */
    public static int NUM_NOTES = 12;
    /** Size of an octave */
    public static final int OCTAVE = 13;
    /** The highest pitch available */
    public static int HIGH_PITCH = CF6;
    /** The lowest pitch available */
    public static int LOW_PITCH = CF2;
    /** The highest dynamic available */
    public static int HIGH_DYNAMIC = CF6;
    /** The lowest dynamic available */
    public static int LOW_DYNAMIC = CF2;

    private int                _currentIteration; // The current iteration of the algorithm
    private ArrayList<Mutator> _mutators;         // Array of all of the mutators that may mutate a track.
    private Rater[]            _raters;           // Array of raters to rate the tracks
    private boolean            _isCompleted;      // True if the generation has completed;
    private Random             _random;
    private int                _indexForLargestRhythmChord;
    private boolean            _regenerate;
    private ArrayList<Double>  _chordAverages;

    /**
     * Constructor
     */
    public GeneticAlgorithm(boolean regenerate)
    {
        _regenerate        = regenerate;
        _currentIteration  = 0;
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
        _raters[9]         = new DynamicRangeRater();
        _raters[10]        = new DynamicStabilityRater();
        _raters[11]        = new NeighboringDynamicRater();
        _raters[12]        = new NeighboringRhythmRater();
        _raters[13]        = new RhythmRangeRater();
        _raters[14]        = new UniqueDynamicRater();
        _raters[15]        = new ChordRatioRater();
        _mutators          = new ArrayList<Mutator>();
        _isCompleted       = false;
        _random            = new Random();
        _mutators.add(new NotePitchMutator());
        _mutators.add(new SimplifyMutator());
        _mutators.add(new SwapMutator());
        _mutators.add(new RhythmMutator());
        _mutators.add(new DynamicMutator());

        Collections.sort(GeneticAlgorithm.RHYTHMS);
        _indexForLargestRhythmChord = RHYTHMS.indexOf(DOUBLE_DOTTED_QUARTER_NOTE);

        _chordAverages = new ArrayList();
        double[] influence3 = new double[] {0.5, 0.4, 0.1};
        double[] influence4 = new double[] {0.4, 0.3, 0.2, 0.1};
        for(int i = 0; i < CHORDS.length; i++)
        {
            double average = 0;
            for(int j = 0; j < CHORDS[i].length; j++)
            {
                if(CHORDS[i].length == 3)
                    average += CHORDS[i][j] * influence3[j];
                else
                    average += CHORDS[i][j] * influence4[j];
            }
            _chordAverages.add(average);
        }
    }

    public void run()
    {
        if(_regenerate)
            Generate();
        else
            Regenerate();
    }

    /**
     * @return True if the algorithm has completed, false otherwise
     */
    public boolean IsCompleted()
    {
        return _isCompleted;
    }

    private void Regenerate()
    {
        _isCompleted = false;
        Utilities.LoadSequencers();
        ArrayList<OvertoneNote> tempNotes = GenerateGameNotes();
        Utilities.SortNotes(tempNotes);
        _isCompleted = true;
    }
    /**
     * Generates the notes.
     */
    private void Generate()
    {
        _isCompleted = false;
        //Get Back the best two phrase from the genetic algorithm
        Organism[] bestThreeTracks = GenerateTracks();

        // Set the current rater values
        for(int i = 0; i < Overtone.CurrentRaterValues.length; i++)
        {
            float sum = 0;
            for(int j = 0; j < bestThreeTracks.length; j++)
                sum += bestThreeTracks[j].GetRating(i);
            sum /= bestThreeTracks.length;
            Overtone.CurrentRaterValues[i] = sum;
        }

        // Create phases that create the song. Structure of the song is verse, chorus, verse, chorus, bridge, chorus.
        Part[] song = new Part[NUM_SECTIONS];
        song[0] = bestThreeTracks[1].GetTrack(); // Verse 1
        song[1] = bestThreeTracks[0].GetTrack(); // Chorus 1
        song[2] = bestThreeTracks[2].GetTrack(); // bridge 1
        song[3] = bestThreeTracks[0].GetTrack(); // Chorus 2

        // Merges the song and adds it to the game music
        Part mergedSong = new Part();
        for(int i = 0; i < song.length; i++)
            for(int j = 0; j < song[i].length(); j++)
                mergedSong.appendPhrase(song[i].getPhrase(j));
        mergedSong = CorrectStartTime(CorrectDuration(mergedSong));
        Overtone.GameMusic = mergedSong.copy();

        // TODO: CHECK THIS TO MAKE SURE THAT IT IS RIGHT
        // Generate the notes and store them in the game and backup arrays
        ArrayList<OvertoneNote> tempNotes = GenerateGameNotes();
        Utilities.SortNotes(tempNotes);
        double f = Overtone.TotalTime;

        // Write the notes to files
        Overtone.GameMusicStartTimes = new ArrayList<Double>();
        for(int i = 0; i < mergedSong.size(); i++)
        {
            double start = mergedSong.getPhrase(i).getStartTime();
            Phrase p = mergedSong.getPhrase(i).copy();
            p.setStartTime(0);
            Overtone.GameMusicStartTimes.add(start);
            Write.midi(p, "Music\\" + i + ".mid");
        }

        Utilities.LoadSequencers();
        _isCompleted = true;
    }

    /**
     * The genetic algorithm. Runs through the genetic algorithm.
     * @return The best two tracks generated after the genetic algorithm
     */
    private Organism[] GenerateTracks()
    {
         // Initialization Phrase
        Organism[] parentPopulation = Initialization();

        // Rate all of the parents
        for(int i = 0; i < parentPopulation.length; i++)
            parentPopulation[i] = FitnessRating(parentPopulation[i]);

        // Get the elites from the parent population
        Organism[] elites = Elitism(parentPopulation);

        //Genetic algorithm phase
        for(int i = 0; i < Overtone.NumberOfIterations; i++)
        {
            _currentIteration = i + 1;

            // Get the children, by selecting from the elites
            Organism[] children = Selection(elites);

            // Rate the children
            for(int j = 0; j < children.length; j++)
                children[j] = FitnessRating(children[j]);

            // Get the elites of the children
            elites = Elitism(children);
        }

        Arrays.sort(elites, new RatingComparator());
        return new Organism[] {elites[0], elites[1], elites[2]};
    }

    /**
     * The initialization phase of the genetic algorithm. Generates the initial population
     * @return an array of phrases that is the initial population of the algorithm
     */
    public Organism[] Initialization()
    {
        Organism[] population = new Organism[Overtone.PopulationSize];
        double prevPitch = Math.round(Utilities.Clamp((_random.nextInt((HIGH_PITCH - LOW_PITCH) + 1) + LOW_PITCH), LOW_PITCH, HIGH_PITCH));

        for(int i = 0; i < Overtone.PopulationSize; i++)
        {
            Part p = new Part();
            int pitchSeed   = Math.round(Utilities.Clamp((_random.nextInt((HIGH_PITCH - LOW_PITCH) + 1) + LOW_PITCH), LOW_PITCH, HIGH_PITCH));           // Random pitch between 30 and 95
            int dynamicSeed = Math.round(Utilities.Clamp((_random.nextInt((HIGH_DYNAMIC - LOW_DYNAMIC) + 1) + LOW_DYNAMIC), LOW_DYNAMIC, HIGH_DYNAMIC)); // Random dynamic between 30 and 95
            int rhythmSeed  = Math.round(Utilities.Clamp(_random.nextInt(RHYTHMS.size() + 1), 0, RHYTHMS.size() - 1));

            float chordProbability = _random.nextFloat() * (0.45f - 0.01f) + 0.01f;
            float restProbability  = _random.nextFloat() * (0.10f - 0.01f) + 0.01f;

            for(int j = 0; j < NUM_NOTES; j++)
            {
                //if(j % 8 == 0)
                //{
                   // pitchSeed   = Utilities.GetRandomRangeNormalDistribution(pitchSeed, OCTAVE, HIGH_PITCH, LOW_PITCH, false);
                    //dynamicSeed = Utilities.GetRandomRangeNormalDistribution(dynamicSeed, OCTAVE, HIGH_DYNAMIC, LOW_DYNAMIC, false);
                    //rhythmSeed  = Utilities.GetRandomRangeNormalDistribution(rhythmSeed, 3, RHYTHMS.size() - 1, 0.0f, false);
                //}

                boolean chord = Utilities.GetRandom(0, 1, chordProbability) == 0;
                boolean rest  = Utilities.GetRandom(0, 1, restProbability)  == 0;

                if(chord && rest)
                {
                    int change = Utilities.GetRandom(0, 1, 0.5f);
                    if(change == 0)
                    {
                        chord = true;
                        rest = false;
                    }
                    else
                    {
                        rest = true;
                        chord = false;
                    }
                }

                Phrase phrase = new Phrase();
                int pitch   = Utilities.GetRandomRangeNormalDistribution(pitchSeed, OCTAVE, HIGH_PITCH, LOW_PITCH, true);
                int dynamic = Utilities.GetRandomRangeNormalDistribution(dynamicSeed, OCTAVE, HIGH_DYNAMIC, LOW_DYNAMIC, true);
                int rhythm  = Utilities.GetRandomRangeNormalDistribution(rhythmSeed, 2, RHYTHMS.size() - 1, 0.0f, true);

                if(chord)
                {
                    while(rhythm > _indexForLargestRhythmChord)
                        rhythm = Utilities.GetRandomRangeNormalDistribution((_indexForLargestRhythmChord / 2.0f), 3, _indexForLargestRhythmChord, 0.0f, true);

                    int chordIndex = Utilities.FindClosestChord(_chordAverages, prevPitch);

                    phrase.addChord(CHORDS[chordIndex], RHYTHMS.get(rhythm));
                    for(int k = 0; k < phrase.length(); k++)
                        phrase.getNote(k).setDynamic(dynamic);

                    prevPitch = _chordAverages.get(chordIndex);
                }
                else if(rest)
                {
                    phrase.addNote(new Note(REST, RHYTHMS.get(rhythm), dynamic));
                }
                else
                {
                    phrase.addNote(new Note(pitch, RHYTHMS.get(rhythm), dynamic));
                }
                p.appendPhrase(phrase);

                if(!chord)
                    prevPitch = pitch;
            }
            population[i] = new Organism(CorrectStartTime(CorrectDuration(p.copy())), _currentIteration);
        }
        return population;
    }

    /**
     * Gets the elites out of the population
     * @param population the population to get the elites from
     * @return array of elites.
     */
    public Organism[] Elitism(Organism[] population)
    {
        Organism[] elites = new Organism[Overtone.NumberOfElites];
        Arrays.sort(population, new RatingComparator());

        for(int i = 0; i < Overtone.NumberOfElites; i++)
            elites[i] = new Organism(population[i]);

        return elites;
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
        int counter = 0;
        while(counter < Overtone.PopulationSize)
        {
            float[] probabilities = new float[parents.length];
            for(int i = 0; i < parents.length; i++)
                probabilities[i] = parents[i].GetOverallRating();

            int p1 = RouletteSelection(probabilities);
            int p2 = p1;
            while(p2 == p1)
                p2 = RouletteSelection(probabilities);

            Organism[] siblings = Crossover(parents[p1], parents[p2]);
            children[counter++] = new Organism(Mutation(siblings[0]));
            children[counter++] = new Organism(Mutation(siblings[1]));
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
            p.SetQuality(Math.abs(Overtone.BestRaterValues[i] - p.GetRating(i)), i);
            overallRating += p.GetQuality(i);
        }

        overallRating /= Overtone.NUM_RATERS;
        p.SetOverallRating(1.0f - overallRating);

        return new Organism(p);
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
        Part mutation = o.GetTrack().copy();

        for(int i = 0; i < _mutators.size(); i++)
        {
            float probability = 0.0f;
            if(_mutators.get(i) instanceof NotePitchMutator)
                probability = o.GetPitchProbability();
            else if(_mutators.get(i) instanceof RhythmMutator)
                probability = o.GetRhythmProbability();
            else if(_mutators.get(i) instanceof SimplifyMutator)
                probability = o.GetSimplifyProbability();
            else if(_mutators.get(i) instanceof SwapMutator)
                probability = o.GetSwapProbability();
            else if(_mutators.get(i) instanceof DynamicMutator)
                probability = o.GetDynamicProbability();

            mutation = _mutators.get(i).Mutate(mutation, probability);
        }

        Organism mutated = new Organism(o);
        mutated.SetTrack(mutation);
        return mutated;
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

        Part p1 = parent1.GetTrack().copy();
        Part p2 = parent2.GetTrack().copy();

        // Find the shorter of the two lengths (used as base length)
        int length = p1.length() < p2.length() ? p1.length() : p2.length();

        // Crossover
        for(int i = 0; i < length; i++)
        {
            Phrase ph1 = p1.getPhrase(i).copy();
            Phrase ph2 = p2.getPhrase(i).copy();

            int index = Utilities.GetRandom(0, 1, 0.5f);
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

        children[0] = CorrectStartTime(CorrectDuration(children[0]));
        children[1] = CorrectStartTime(CorrectDuration(children[1]));

        return new Organism[] { new Organism(children[0], _currentIteration), new Organism(children[1], _currentIteration)};
    }

    /**
     * Selects a parent based on thief probability, higher ones are chose more often
     * @param values The parent organism probabilities
     * @return The index to the parent chosen for crossover
     */
    private int RouletteSelection(float[] values)
    {
        Utilities.ShuffleArray(values);

        int index = 0;
        float sum = 0;
        for(int i = 0; i < values.length; i++)
            sum += values[i];

        float rand = _random.nextFloat() * sum;

        sum = 0;
        for(int i = 0; i < values.length; i++)
        {
            sum += values[i];

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

        double startTime                   = 0;
        int target                         = 0;
        int lastHoldTarget                 = -1;
        double prevDuration                = -1;

        for(int j = 0; j < Overtone.GameMusic.size(); j++)
        {
            Phrase phrase = Overtone.GameMusic.getPhrase(j);

            boolean includeHoldNote   = true;
            boolean includeDoubleNote = true;
            if(Overtone.Difficulty == Overtone.Difficulty.Easy)
            {
                includeHoldNote   = Utilities.GetRandom(0, 1, 0.2f) == 0 ? true : false;
                includeDoubleNote = Utilities.GetRandom(0, 1, 0.2f) == 0 ? true : false;
            }
            else if(Overtone.Difficulty == Overtone.Difficulty.Normal)
            {
                includeHoldNote   = Utilities.GetRandom(0, 1, 0.45f) == 0 ? true : false;
                includeDoubleNote = Utilities.GetRandom(0, 1, 0.45f) == 0 ? true : false;
            }

            target = _random.nextInt(Overtone.TargetZones.length);
            int target2 = DetermineTarget(target);

            startTime = phrase.getStartTime();
            prevDuration = phrase.getNote(phrase.length() - 1).getDuration();

            while (target == lastHoldTarget || target2 == lastHoldTarget)
            {
                target = _random.nextInt(Overtone.TargetZones.length);
                target2 = DetermineTarget(target);
            }

            if(phrase.length() > 1 && includeDoubleNote) // else if it is a chord == double note
            {
                OvertoneNote[] notes = CreateDoubleNote(target, target2, startTime);
                tempNotes.add(notes[0]);
                tempNotes.add(notes[1]);
            }
            else if(phrase.getNote(0).isRest()) // if it is a rest, continue
            {
                continue;
            }
            else if(phrase.getNote(0).getRhythmValue() > DOUBLE_DOTTED_QUARTER_NOTE && includeHoldNote) // if it is longer than a quarter note == hold note
            {
                OvertoneNote[] notes = CreateHoldNote(target, startTime, phrase.getNote(0).getDuration());
                tempNotes.add(notes[0]);
                tempNotes.add(notes[1]);
                lastHoldTarget = target;
            }
            else // It is a normal note
            {
                tempNotes.add(CreateSingleNote(target, startTime));
            }
        }

        Overtone.TotalTime = startTime + prevDuration + GameplayScreen.COMPLETION_DELAY;
        return tempNotes;
    }

    /**
     * Creates a double note
     * @param target the target of the first note
     * @param elapsedTime the elapsed time of the song
     * @return and array of notes representing a double note
     */
    private OvertoneNote[] CreateDoubleNote(int target, int target2,  double elapsedTime)
    {
        OvertoneNote[] note = new OvertoneNote[2];
        note[0] = new OvertoneNote(OvertoneNote.NoteType.Double, Overtone.TargetZones[target], (float)elapsedTime);
        note[1] = new OvertoneNote(OvertoneNote.NoteType.Double, Overtone.TargetZones[target2], (float)elapsedTime);

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
    private OvertoneNote[] CreateHoldNote(int target, double elapsedTime, double noteDuration)
    {
        OvertoneNote[] note = new OvertoneNote[2];
        note[0] = new OvertoneNote(OvertoneNote.NoteType.Hold, Overtone.TargetZones[target], (float)elapsedTime);
        note[1] = new OvertoneNote(OvertoneNote.NoteType.Hold, Overtone.TargetZones[target], (float)elapsedTime + (float)noteDuration);

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
    private OvertoneNote CreateSingleNote(int target, double elapsedTime)
    {
        return new OvertoneNote(OvertoneNote.NoteType.Single, Overtone.TargetZones[target], (float)elapsedTime);
    }

    /**
     * Randomly chooses a perpendicular target as a partner target for the double note
     * @param target The target to find a partner target for
     * @return The index of the other target
     */
    private int DetermineTarget(int target)
    {
        int num = _random.nextInt(2);

        switch(target)
        {
            case 0:
            {
                if(num == 0)
                    return (int)Utilities.Clamp(target + 1, 0, 3);
                else
                    return (int)Utilities.Clamp(target + 2, 0, 3);
            }
            case 1:
            {
                if(num == 0)
                    return (int)Utilities.Clamp(target - 1, 0, 3);
                else
                    return (int)Utilities.Clamp(target + 2, 0, 3);
            }
            case 2:
            {
                if(num == 0)
                    return (int)Utilities.Clamp(target + 1, 0, 3);
                else
                    return (int)Utilities.Clamp(target - 2, 0, 3);
            }
            case 3:
            {
                if(num == 0)
                    return (int)Utilities.Clamp(target - 1, 0, 3);
                else
                    return (int)Utilities.Clamp(target - 2, 0, 3);
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

    public static Part CorrectDuration(Part p)
    {
        for(int i = 0; i < p.length(); i++)
        {
            double rhythmValue = p.getPhrase(i).getNote(p.getPhrase(i).length() - 1).getRhythmValue();
            for(int j = 0; j < p.getPhrase(i).length(); j++)
            {
                p.getPhrase(i).getNote(j).setDuration(rhythmValue * Note.DEFAULT_DURATION_MULTIPLIER);
            }
        }

        Part newP = p.copy();
        RenameTracks(newP);
        return newP;
    }

    public static Part CorrectStartTime(Part p)
    {
        double startTime = GameplayScreen.START_DELAY;
        for(int i = 0; i < p.length(); i++)
        {
            p.getPhrase(i).setStartTime(startTime);
            startTime += p.getPhrase(i).getNote(p.getPhrase(i).length() -1).getRhythmValue();
        }

        Part newP = p.copy();
        RenameTracks(newP);
        return newP;
    }

    public static void RenameTracks(Part p)
    {
        for(int i = 0; i < p.length(); i++)
        {
            p.setTitle(i + " ");
            for(int j = 0; j < p.getPhrase(i).length(); j++)
                p.getPhrase(i).setTitle(j + " ");
        }
    }
}


