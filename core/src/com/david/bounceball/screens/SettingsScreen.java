package com.david.bounceball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.david.bounceball.*;

/**
 * Created by David on 10/10/2016.
 */
public abstract class SettingsScreen extends ScreenWithListener {

    TextButton soundEnabledButton;
    TextButton backButton;


    public SettingsScreen() {
        super();
        Skin uiskin = Assets.uiskin;
        soundEnabledButton = new TextButton("Sound enabled: " + Settings.soundEnabled, uiskin);
        soundEnabledButton.setPosition(0,840 - 100);
        soundEnabledButton.setSize(100, 100);
        backButton = new TextButton("Back", new Skin(Gdx.files.internal("data/uiskin.json")));
        backButton.setPosition(0, soundEnabledButton.getY() - 100);
        backButton.setSize(100, 100);
        uiStage.addActor(soundEnabledButton);
        uiStage.addActor(backButton);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height);
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
            if (actor == soundEnabledButton) {
                Settings.soundEnabled = !Settings.soundEnabled;
                soundEnabledButton.setText("Sound enabled: " + Settings.soundEnabled);
            } else if (actor == backButton) {
                setNextScreen(0);
            }
        }
        return false;
    }

    @Override
    public Object getObjectByName(String name) {
        Object object = super.getObjectByName(name);
        if (object != null)
            return object;
        if (name.equals("soundEnabled")) {
            return soundEnabledButton;
        } else if (name.equals("back")) {
            return backButton;
        }
        return null;
    }

}
