package com.david.bounceball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;


//The "Main" class
public class BounceBall extends ApplicationAdapter implements ScreenListener {
	Initializer initializer;
	ScreenWithListener screen;
	FPSLogger logger;

	public BounceBall(Initializer initializer) {
		this.initializer = initializer;
	}
	
	@Override
	public void create () {
		screen = initializer.getScreen();
		screen.setListener(this);
		screen.show();
		logger = new FPSLogger();
	}

	@Override
	public void render() {
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1/30f);
		screen.render(deltaTime);
		logger.log();
	}

	@Override
	public void newScreen(ScreenWithListener screen) {
		this.screen = screen;
		screen.setListener(this);
	}

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	@Override
	public void dispose() {

	}
}
