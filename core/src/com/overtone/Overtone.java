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
	public enum TargetZone
	{
		TopLeft(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.85f),
		TopRight(Gdx.graphics.getWidth() * 0.85f, Gdx.graphics.getHeight() * 0.85f),
		BottomLeft(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.15f),
		BottomRight(Gdx.graphics.getWidth() * 0.85f, Gdx.graphics.getHeight() * 0.15f);

		public Vector2 value;
		TargetZone(float x, float y) { value = new Vector2(x, y);}

		// The size of the enum
		public static final int size = TargetZone.values().length;
	}

	public Screen _currentScreen;

	private Array<Screen> _screens;
	private InputManager _input;
	private NoteRenderer _renderer;
	private ArrayList<Note> _notes;
	private Vector2 _origin;

	@Override
	public void create ()
	{
		_input = new InputManager();
		_renderer = new NoteRenderer();
		_origin = new Vector2(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f );

		_screens = new Array<Screen>();
		_screens.add(new MainMenuScreen("background1.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen("background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen("background3.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen("background4.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_currentScreen = _screens.get(0);

		_notes = new ArrayList<Note>();
		for(int i = 0; i < TargetZone.size; i++)
		{
			_notes.add(new Note(Note.NoteType.singleNote,
					new Vector2(_origin.x, _origin.y),
					TargetZone.values()[i].value,
					new Vector2(Gdx.graphics.getWidth() * 0.025f, Gdx.graphics.getWidth() * 0.025f),
					Note.DifficultyMultiplier.easy
			));

			_notes.get(i).SetVisibility(true);
		}

		_currentScreen.show();
	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		Update(deltaTime);

		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_currentScreen.render(deltaTime);
		_renderer.Draw(_notes);
	}

	public void Update(float deltaTime)
	{
		_input.Update();

		for(int i = 0; i < _notes.size(); i++)
		{
			if(_notes.get(i).IsVisible())
				_notes.get(i).Update(deltaTime);
		}
	}
}
