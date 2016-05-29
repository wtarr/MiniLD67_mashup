package com.bugsandcode.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bugsandcode.game.GameLauncher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SnakMan";
		//config.width = 480;
		//config.height = 352;
		new LwjglApplication(new GameLauncher(), config);
	}
}
