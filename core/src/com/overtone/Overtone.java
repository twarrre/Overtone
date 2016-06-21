package com.overtone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.overtone.Screens.DifficultySelectScreen;
import com.overtone.Screens.GameplayScreen;
import com.overtone.Screens.MainMenuScreen;
import com.overtone.Screens.SongCompleteScreen;
import com.overtone.InputManager;

public class Overtone extends ApplicationAdapter
{
	public Screen currentScreen;

	private Array<Screen> _screens;
	private InputManager _input;

	@Override
	public void create ()
	{
		_input = new InputManager();

		_screens = new Array<Screen>();
		_screens.add(new MainMenuScreen("background1.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new DifficultySelectScreen("background2.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new SongCompleteScreen("background3.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		_screens.add(new GameplayScreen("background4.jpg", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		currentScreen = _screens.get(0);

		//currentScreen.hide();
		//currentScreen = _screens.get(3);
		//currentScreen.show();

		currentScreen.show();
	}

	@Override
	public void render ()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_input.Update();

		if(_input.ActionOccurred(InputManager.KeyBinding.BottomLeft, InputManager.ActionType.Pressed))
		{
			System.out.println("Key Pressed");
		}

		if(_input.ActionOccurred(InputManager.KeyBinding.BottomLeft, InputManager.ActionType.Released))
		{
			System.out.println("Key Released");
		}

		if(_input.ActionOccurred(InputManager.KeyBinding.BottomLeft, InputManager.ActionType.Held))
		{
			System.out.println("Key Held");
		}

		if(_input.ActionOccurred(InputManager.KeyBinding.BottomLeft, InputManager.ActionType.Up))
		{
			System.out.println("Key Up");
		}

		currentScreen.render(deltaTime);
	}
}
