package com.overtone.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.overtone.Overtone;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Overtone";
		config.width = 1280;
		config.height = 720;
		config.fullscreen = true;
		config.vSyncEnabled = true;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.forceExit = true;
		config.addIcon("Assets\\Textures\\icon32x32.png",  Files.FileType.Internal);
		config.addIcon("Assets\\Textures\\icon16x16.png",  Files.FileType.Internal);

		new LwjglApplication(new Overtone(), config);
	}
}
