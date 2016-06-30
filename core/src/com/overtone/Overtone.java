package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.overtone.Notes.Note;
import com.overtone.Notes.NoteRenderer;
import com.overtone.Screens.DifficultySelectScreen;
import com.overtone.Screens.GameplayScreen;
import com.overtone.Screens.MainMenuScreen;
import com.overtone.Screens.SongCompleteScreen;

import java.util.ArrayList;

public class Overtone extends ApplicationAdapter
{
	public Screen currentScreen;

	private Array<Screen> _screens;
	private InputManager _input;
	private NoteRenderer _renderer;
	private ArrayList<Note> _notes;

	@Override
	public void create ()
	{
		_input = new InputManager();
		_renderer = new NoteRenderer();

		_screens = new Array<Screen>();
		_screens.add(new MainMenuScreen("background1.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen("background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen("background3.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen("background4.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		currentScreen = _screens.get(0);

		//currentScreen.hide();
		//currentScreen = _screens.get(3);
		//currentScreen.show();

		_notes = new ArrayList<Note>();
		for(int i = 0; i < 10; ++i)
		{
			_notes.add(new Note(Note.NoteType.singleNote, new Vector2(i * 100, i * 100), new Vector2(i * 1000, i * 1000), Note.DifficultyMultiplier.easy));
			_notes.get(i).SetVisiblilty(true);
		}

		currentScreen.show();
	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_input.Update();

		for(Note n : _notes)
			n.Update(deltaTime);

		currentScreen.render(deltaTime);
		_renderer.Draw(_notes);
	}
}
