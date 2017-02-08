package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 9/26/2016.
 */
public abstract class ScreenWithListener implements Screen, EventListener {

    protected ScreenListener listener;
    protected Stage uiStage;


    public ScreenWithListener() {
        Assets.command.remove();
        Assets.command.setScreen(this);
        uiStage = new Stage(new StretchViewport(480, 840));
        Gdx.input.setInputProcessor(uiStage);
        uiStage.addListener(this);
        uiStage.addActor(Assets.command);
        uiStage.setKeyboardFocus(null);
        Assets.uiskin.getFont("default-font").getData().setScale(1.3f);
        Assets.uiskin.getPatch("default-round").setLeftWidth(15);
        Assets.uiskin.getPatch("default-round").setRightWidth(15);
        Assets.uiskin.getPatch("default-round").setTopHeight(15);
        Assets.uiskin.getPatch("default-round").setBottomHeight(15);
        Assets.uiskin.getPatch("default-round-down").setLeftWidth(15);
        Assets.uiskin.getPatch("default-round-down").setRightWidth(15);
        Assets.uiskin.getPatch("default-round-down").setTopHeight(15);
        Assets.uiskin.getPatch("default-round-down").setBottomHeight(15);
    }

    protected TextButton addButton(String text, Skin skin) {
        TextButton button = new TextButton(text, skin);
        uiStage.addActor(button);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Settings.soundEnabled)
                    Assets.goodClickDown.play();
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (Settings.soundEnabled)
                    Assets.goodClickUp.play();
            }
        });
        return button;
    }

    protected TextButton addButton(float x, float y, float width, float height, String text, Skin skin) {
        TextButton button = addButton(text, skin);
        button.setBounds(x, y, width, height);
        return button;
    }

    void setListener(ScreenListener listener) {
        this.listener = listener;
    }

    public boolean handle(Event event) {
        if (event instanceof InputEvent) {
            InputEvent inputEvent = (InputEvent) event;
            if (inputEvent.getType() == InputEvent.Type.keyDown) {
                if (inputEvent.getKeyCode() == Input.Keys.COMMA) {
                    Assets.command.setVisible(!Assets.command.isVisible());
                    uiStage.setKeyboardFocus(Assets.command.isVisible() ? Assets.command : null);
                }
            }
        }
        return false;
    }

    public Object getObjectByName(String name) {
        if (name.equals("uiStage")) {
            return uiStage;
        } else if (name.equals("uiskin")) {
            return Assets.uiskin;
        }
        return null;
    }

    public abstract void setNextScreen(int screenCode);

}
