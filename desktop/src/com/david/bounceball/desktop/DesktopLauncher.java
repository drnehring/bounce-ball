package com.david.bounceball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.david.bounceball.BounceBall;
import com.david.bounceball.WindowsInitializer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 840;
		new LwjglApplication(new BounceBall(new WindowsInitializer()), config);
	}
}
