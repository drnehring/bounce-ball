package com.david.bounceball.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.david.bounceball.*;


/**
 * Created by David on 9/26/2016.
 */
public abstract class MainMenuScreen extends ScreenWithListener {
    Label title;
    TextButton startButton;
    TextButton settingsButton;
    TextButton helpButton;
    TextButton aboutButton;

    protected static final int PACK_SCREEN = 0;
    protected static final int SETTINGS_SCREEN = 1;
    protected static final int HELP_SCREEN = 2;
    protected static final int ABOUT_SCREEN = 3;

    public MainMenuScreen() {
        super();
        Skin uiskin = Assets.uiskin;
        title = new Label("Bounce Ball", uiskin);
        title.setBounds(72, 690, 336, 60);
        title.setAlignment(Align.center);
        title.setFontScale(5f, 5f);
        startButton = new TextButton("Start", uiskin);
        startButton.setBounds(120, 480, 240, 48);
        helpButton = new TextButton("Help", uiskin);
        helpButton.setBounds(0, 120, 240, 48);
        settingsButton = new TextButton("Settings", uiskin);
        settingsButton.setBounds(144, 288, 192, 48);
        aboutButton = new TextButton("About", uiskin);
        aboutButton.setBounds(240, 120, 240, 48);
        uiStage.addActor(title);
        uiStage.addActor(startButton);
        uiStage.addActor(helpButton);
        uiStage.addActor(settingsButton);
        uiStage.addActor(aboutButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0, 0.199f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean handle(Event event) {
        super.handle(event);
        if (event instanceof ChangeListener.ChangeEvent) {
            Actor actor = event.getTarget();
            if (actor == startButton) {
                setNextScreen(PACK_SCREEN);
            } else if (actor == settingsButton) {
                setNextScreen(SETTINGS_SCREEN);
            }else if (actor == helpButton) {
                setNextScreen(HELP_SCREEN);
            } else if (actor == aboutButton) {
                setNextScreen(ABOUT_SCREEN);
            }
        }
        return false;
    }

    @Override
    public Object getObjectByName(String name) {
        Object object = super.getObjectByName(name);
        if (object != null)
            return object;
        if (name.equals("start")) {
            return startButton;
        } else if (name.equals("settings")) {
            return settingsButton;
        } else if (name.equals("help")) {
            return helpButton;
        } else if (name.equals("about")) {
            return aboutButton;
        }
        return null;
    }
}
