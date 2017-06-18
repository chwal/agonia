package com.agonia.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.agonia.game.Agonia;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Agonia.WINDOW_HEIGHT;
		config.width = Agonia.WINDOW_WIDTH;
		new LwjglApplication(new Agonia(), config);
	}
}
