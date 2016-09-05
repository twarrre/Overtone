package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.overtone.GeneticAlgorithm.Raters.NeighboringPitchRater;
import com.overtone.Overtone;
import com.overtone.Utilities;

/**
 * Created by trevor on 2016-09-04.
 */
public class MutationScreen extends OvertoneScreen
{
    private final Stage  _stage;      // Stage to hold the buttons and background
    private final Button _backButton; // The back button

    /**
     * Constructor
     */
    public MutationScreen()
    {
        super();
        _stage = new Stage();

        // Create back button
        _backButton = CreateButton("back", "small",  Overtone.ScreenWidth * 0.08f, Overtone.ScreenHeight * 0.05f, new Vector2(Overtone.ScreenWidth * 0.02f, Overtone.ScreenHeight * 0.92f), _stage);
        _backButton.addListener(new ClickListener() {
            public void clicked (InputEvent i, float x, float y) {
                _buttonPress.play(Overtone.SFXVolume);
                Overtone.SetScreen(Overtone.Screens.Options);
            }
        });

        // Pitch Mutators
        final Button pitchNextStart = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.45f, Overtone.ScreenHeight * 0.68f - Overtone.ScreenWidth * 0.025f), _stage);
        pitchNextStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PitchMutatorValues[0] = Utilities.Clamp(Overtone.PitchMutatorValues[0] + 0.01f, 0.0f, 1.0f);
            Overtone.PitchMutatorValues[1] = Utilities.Clamp(Overtone.PitchMutatorValues[1], 0.0f, Overtone.PitchMutatorValues[0]);
            Overtone.PitchMutatorValues[2] = Utilities.Clamp(Overtone.PitchMutatorValues[2], 0.0f, Overtone.PitchMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button pitchBackStart = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.35f, Overtone.ScreenHeight * 0.68f - Overtone.ScreenWidth * 0.025f), _stage);
        pitchBackStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PitchMutatorValues[0] = Utilities.Clamp(Overtone.PitchMutatorValues[0] - 0.01f, 0.0f, 1.0f);
            Overtone.PitchMutatorValues[1] = Utilities.Clamp(Overtone.PitchMutatorValues[1], 0.0f, Overtone.PitchMutatorValues[0]);
            Overtone.PitchMutatorValues[2] = Utilities.Clamp(Overtone.PitchMutatorValues[2], 0.0f, Overtone.PitchMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button pitchNextMin = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.65f, Overtone.ScreenHeight * 0.68f - Overtone.ScreenWidth * 0.025f), _stage);
        pitchNextMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PitchMutatorValues[1] = Utilities.Clamp(Overtone.PitchMutatorValues[1] + 0.01f, 0.0f, Overtone.PitchMutatorValues[0]);
            Overtone.PitchMutatorValues[2] = Utilities.Clamp(Overtone.PitchMutatorValues[2], 0.0f, Overtone.PitchMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button pitchBackMin = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.55f, Overtone.ScreenHeight * 0.68f - Overtone.ScreenWidth * 0.025f), _stage);
        pitchBackMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PitchMutatorValues[1] = Utilities.Clamp(Overtone.PitchMutatorValues[1] - 0.01f, 0.0f, Overtone.PitchMutatorValues[0]);
            Overtone.PitchMutatorValues[2] = Utilities.Clamp(Overtone.PitchMutatorValues[2], 0.0f, Overtone.PitchMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button pitchNextStep = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.68f - Overtone.ScreenWidth * 0.025f), _stage);
        pitchNextStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PitchMutatorValues[2] = Utilities.Clamp(Overtone.PitchMutatorValues[2] + 0.01f, 0.0f, Overtone.PitchMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button pitchBackStep = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.75f, Overtone.ScreenHeight * 0.68f - Overtone.ScreenWidth * 0.025f), _stage);
        pitchBackStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.PitchMutatorValues[2] = Utilities.Clamp(Overtone.PitchMutatorValues[2] - 0.01f, 0.0f, Overtone.PitchMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});



        // Rhythm Mutators
        final Button rhythmNextStart = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.45f, Overtone.ScreenHeight * 0.51f - Overtone.ScreenWidth * 0.025f), _stage);
        rhythmNextStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.RhythmMutatorValues[0] = Utilities.Clamp(Overtone.RhythmMutatorValues[0] + 0.01f, 0.0f, 1.0f);
            Overtone.RhythmMutatorValues[1] = Utilities.Clamp(Overtone.RhythmMutatorValues[1], 0.0f, Overtone.RhythmMutatorValues[0]);
            Overtone.RhythmMutatorValues[2] = Utilities.Clamp(Overtone.RhythmMutatorValues[2], 0.0f, Overtone.RhythmMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button rhythBackStart = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.35f, Overtone.ScreenHeight * 0.51f - Overtone.ScreenWidth * 0.025f), _stage);
        rhythBackStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.RhythmMutatorValues[0] = Utilities.Clamp(Overtone.RhythmMutatorValues[0] - 0.01f, 0.0f, 1.0f);
            Overtone.RhythmMutatorValues[1] = Utilities.Clamp(Overtone.RhythmMutatorValues[1], 0.0f, Overtone.RhythmMutatorValues[0]);
            Overtone.RhythmMutatorValues[2] = Utilities.Clamp(Overtone.RhythmMutatorValues[2], 0.0f, Overtone.RhythmMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button rhythmNextMin = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.65f, Overtone.ScreenHeight * 0.51f - Overtone.ScreenWidth * 0.025f), _stage);
        rhythmNextMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.RhythmMutatorValues[1] = Utilities.Clamp(Overtone.RhythmMutatorValues[1] + 0.01f, 0.0f, Overtone.RhythmMutatorValues[0]);
            Overtone.RhythmMutatorValues[2] = Utilities.Clamp(Overtone.RhythmMutatorValues[2], 0.0f, Overtone.RhythmMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button rhythBackMin = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.55f, Overtone.ScreenHeight * 0.51f - Overtone.ScreenWidth * 0.025f), _stage);
        rhythBackMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.RhythmMutatorValues[1] = Utilities.Clamp(Overtone.RhythmMutatorValues[1] - 0.01f, 0.0f, Overtone.RhythmMutatorValues[0]);
            Overtone.RhythmMutatorValues[2] = Utilities.Clamp(Overtone.RhythmMutatorValues[2], 0.0f, Overtone.RhythmMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button rhythmNextStep = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.51f - Overtone.ScreenWidth * 0.025f), _stage);
        rhythmNextStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.RhythmMutatorValues[2] = Utilities.Clamp(Overtone.RhythmMutatorValues[2] + 0.01f, 0.0f, Overtone.RhythmMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button rhythBackStep = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.75f, Overtone.ScreenHeight * 0.51f - Overtone.ScreenWidth * 0.025f), _stage);
        rhythBackStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.RhythmMutatorValues[2] = Utilities.Clamp(Overtone.RhythmMutatorValues[2] - 0.01f, 0.0f, Overtone.RhythmMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});




        // Simplify Mutators
        final Button simpNextStart = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.45f, Overtone.ScreenHeight * 0.34f - Overtone.ScreenWidth * 0.025f), _stage);
        simpNextStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SimplifyMutatorValues[0] = Utilities.Clamp(Overtone.SimplifyMutatorValues[0] + 0.01f, 0.0f, 1.0f);
            Overtone.SimplifyMutatorValues[1] = Utilities.Clamp(Overtone.SimplifyMutatorValues[1], 0.0f, Overtone.SimplifyMutatorValues[0]);
            Overtone.SimplifyMutatorValues[2] = Utilities.Clamp(Overtone.SimplifyMutatorValues[2], 0.0f, Overtone.SimplifyMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button simpBackStart = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.35f, Overtone.ScreenHeight * 0.34f - Overtone.ScreenWidth * 0.025f), _stage);
        simpBackStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SimplifyMutatorValues[0] = Utilities.Clamp(Overtone.SimplifyMutatorValues[0] - 0.01f, 0.0f, 1.0f);
            Overtone.SimplifyMutatorValues[1] = Utilities.Clamp(Overtone.SimplifyMutatorValues[1], 0.0f, Overtone.SimplifyMutatorValues[0]);
            Overtone.SimplifyMutatorValues[2] = Utilities.Clamp(Overtone.SimplifyMutatorValues[2], 0.0f, Overtone.SimplifyMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button simpNextMin = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.65f, Overtone.ScreenHeight * 0.34f - Overtone.ScreenWidth * 0.025f), _stage);
        simpNextMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SimplifyMutatorValues[1] = Utilities.Clamp(Overtone.SimplifyMutatorValues[1] + 0.01f, 0.0f, Overtone.SimplifyMutatorValues[0]);
            Overtone.SimplifyMutatorValues[2] = Utilities.Clamp(Overtone.SimplifyMutatorValues[2], 0.0f, Overtone.SimplifyMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button simpBackMin = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.55f, Overtone.ScreenHeight * 0.34f - Overtone.ScreenWidth * 0.025f), _stage);
        simpBackMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SimplifyMutatorValues[1] = Utilities.Clamp(Overtone.SimplifyMutatorValues[1] - 0.01f, 0.0f, Overtone.SimplifyMutatorValues[0]);
            Overtone.SimplifyMutatorValues[2] = Utilities.Clamp(Overtone.SimplifyMutatorValues[2], 0.0f, Overtone.SimplifyMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button simpNextStep = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.34f - Overtone.ScreenWidth * 0.025f), _stage);
        simpNextStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SimplifyMutatorValues[2] = Utilities.Clamp(Overtone.SimplifyMutatorValues[2] + 0.01f, 0.0f, Overtone.SimplifyMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button simpBackStep = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.75f, Overtone.ScreenHeight * 0.34f - Overtone.ScreenWidth * 0.025f), _stage);
        simpBackStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SimplifyMutatorValues[2] = Utilities.Clamp(Overtone.SimplifyMutatorValues[2] - 0.01f, 0.0f, Overtone.SimplifyMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});




        // Swap Mutators
        final Button swapNextStart = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.45f, Overtone.ScreenHeight * 0.17f - Overtone.ScreenWidth * 0.025f), _stage);
        swapNextStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SwapMutatorValues[0] = Utilities.Clamp(Overtone.SwapMutatorValues[0] + 0.01f, 0.0f, 1.0f);
            Overtone.SwapMutatorValues[1] = Utilities.Clamp(Overtone.SwapMutatorValues[1], 0.0f, Overtone.SwapMutatorValues[0]);
            Overtone.SwapMutatorValues[2] = Utilities.Clamp(Overtone.SwapMutatorValues[2], 0.0f, Overtone.SwapMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button swapBackStart = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.35f, Overtone.ScreenHeight * 0.17f - Overtone.ScreenWidth * 0.025f), _stage);
        swapBackStart.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SwapMutatorValues[0] = Utilities.Clamp(Overtone.SwapMutatorValues[0] - 0.01f, 0.0f, 1.0f);
            Overtone.SwapMutatorValues[1] = Utilities.Clamp(Overtone.SwapMutatorValues[1], 0.0f, Overtone.SwapMutatorValues[0]);
            Overtone.SwapMutatorValues[2] = Utilities.Clamp(Overtone.SwapMutatorValues[2], 0.0f, Overtone.SwapMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button swapNextMin = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.65f, Overtone.ScreenHeight * 0.17f - Overtone.ScreenWidth * 0.025f), _stage);
        swapNextMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SwapMutatorValues[1] = Utilities.Clamp(Overtone.SwapMutatorValues[1] + 0.01f, 0.0f, Overtone.SwapMutatorValues[0]);
            Overtone.SwapMutatorValues[2] = Utilities.Clamp(Overtone.SwapMutatorValues[2], 0.0f, Overtone.SwapMutatorValues[1]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button swapBackMin = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.55f, Overtone.ScreenHeight * 0.17f - Overtone.ScreenWidth * 0.025f), _stage);
        swapBackMin.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SwapMutatorValues[1] = Utilities.Clamp(Overtone.SwapMutatorValues[1] - 0.01f, 0.0f, Overtone.SwapMutatorValues[0]);
            Overtone.SwapMutatorValues[2] = Utilities.Clamp(Overtone.SwapMutatorValues[2], 0.0f, Overtone.SwapMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        final Button swapNextStep = CreateButton(null, "nextButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f, new Vector2(Overtone.ScreenWidth * 0.85f, Overtone.ScreenHeight * 0.17f - Overtone.ScreenWidth * 0.025f), _stage);
        swapNextStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SwapMutatorValues[2] = Utilities.Clamp(Overtone.SwapMutatorValues[2] + 0.01f, 0.0f, Overtone.SwapMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});

        // Create the back button for num iterations
        final Button swapBackStep = CreateButton(null, "backButton", Overtone.ScreenWidth * 0.025f, Overtone.ScreenWidth * 0.025f,  new Vector2(Overtone.ScreenWidth * 0.75f, Overtone.ScreenHeight * 0.17f - Overtone.ScreenWidth * 0.025f), _stage);
        swapBackStep.addListener(new ClickListener() {public void clicked (InputEvent i, float x, float y) {
            _buttonPress.play(Overtone.SFXVolume);
            Overtone.SwapMutatorValues[2] = Utilities.Clamp(Overtone.SwapMutatorValues[2] - 0.01f, 0.0f, Overtone.SwapMutatorValues[0]);
            Utilities.WriteMutationValues();
        }});
    }

    public void render (float deltaTime)
    {
        super.render(deltaTime);

        _batch.begin();

        _glyphLayout.setText(_font36, "Mutation Values");
        _font36.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.5f - _glyphLayout.width / 2.0f, Overtone.ScreenHeight * 0.92f);

        _glyphLayout.setText(_font30, "Mutators:");
        _font30.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.15f, Overtone.ScreenHeight * 0.8f);

        _glyphLayout.setText(_font30, "Initial:");
        _font30.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.37f, Overtone.ScreenHeight * 0.8f);

        _glyphLayout.setText(_font30, "Minimum:");
        _font30.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.555f, Overtone.ScreenHeight * 0.8f);

        _glyphLayout.setText(_font30, "Step:");
        _font30.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.77f, Overtone.ScreenHeight * 0.8f);

        _glyphLayout.setText(_font24, "Note Pitch:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.15f, Overtone.ScreenHeight * 0.66f);

        _glyphLayout.setText(_font24, "Rhythm:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.15f, Overtone.ScreenHeight * 0.49f);

        _glyphLayout.setText(_font24, "Simplify:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.15f, Overtone.ScreenHeight * 0.32f);

        _glyphLayout.setText(_font24, "Swap:");
        _font24.draw(_batch, _glyphLayout, Overtone.ScreenWidth * 0.15f, Overtone.ScreenHeight * 0.15f);

        _glyphLayout.setText(_font24, (int)(Overtone.PitchMutatorValues[0] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.4125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.675f);

        _glyphLayout.setText(_font24, (int)(Overtone.PitchMutatorValues[1] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.6125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.675f);

        _glyphLayout.setText(_font24, (int)(Overtone.PitchMutatorValues[2] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.8125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.675f);




        _glyphLayout.setText(_font24, (int)(Overtone.RhythmMutatorValues[0] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.4125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.505f);

        _glyphLayout.setText(_font24, (int)(Overtone.RhythmMutatorValues[1] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.6125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.505f);

        _glyphLayout.setText(_font24, (int)(Overtone.RhythmMutatorValues[2] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.8125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.505f);





        _glyphLayout.setText(_font24, (int)(Overtone.SimplifyMutatorValues[0] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.4125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.335f);

        _glyphLayout.setText(_font24, (int)(Overtone.SimplifyMutatorValues[1] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.6125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.335f);

        _glyphLayout.setText(_font24, (int)(Overtone.SimplifyMutatorValues[2] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.8125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.335f);





        _glyphLayout.setText(_font24, (int)(Overtone.SwapMutatorValues[0] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.4125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.165f);

        _glyphLayout.setText(_font24, (int)(Overtone.SwapMutatorValues[1] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.6125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.165f);

        _glyphLayout.setText(_font24, (int)(Overtone.SwapMutatorValues[2] * 100.0f) + "%");
        _font24.draw(_batch, _glyphLayout, (Overtone.ScreenWidth * 0.8125f - (_glyphLayout.width / 2.0f)), Overtone.ScreenHeight * 0.165f);

        _batch.end();
        _stage.draw();
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        _stage.act(deltaTime);
    }
    public void resize(int width, int height) {
        super.resize(width, height);
        _stage.getViewport().update(width, height, true);
    }
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(_stage);
    }
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
    public void dispose () {
        super.dispose();
        _stage.dispose();
    }
}
