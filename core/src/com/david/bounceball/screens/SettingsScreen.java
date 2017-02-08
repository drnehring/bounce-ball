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
    TextButton resetProgressButton;
    TextButton backButton;


    public SettingsScreen() {
        super();
        soundEnabledButton = addButton(120, 480, 240, 48, "Sound enabled: " + Settings.soundEnabled, Assets.uiskin);
        resetProgressButton = addButton(120, 360, 240, 48, "Reset progress", Assets.uiskin);
        backButton = addButton(0, 720, 120, 120, "Back", Assets.uiskin);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0, 0.199f, 0.5f);
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
                return true;
            } else if (actor == resetProgressButton) {
                Assets.resetProgress();
                return true;
            } else if (actor == backButton) {
                setNextScreen(0);
                return true;
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
