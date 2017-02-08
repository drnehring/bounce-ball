package com.david.bounceball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.david.bounceball.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by David on 10/13/2016.
 */
public abstract class PackScreen extends ScreenWithListener {

    ScrollPane scrollPane;
    Table table;
    int numberOfPacks;
    TextButton[] packButtons;
    protected int packNumber;

    public PackScreen() {
        super();
        Skin uiskin = Assets.uiskin;
        table = new Table();
        scrollPane = new ScrollPane(table, uiskin);
        scrollPane.setBounds(30, 360 + 10, 420, 120 + 20);
        uiStage.addActor(scrollPane);

        numberOfPacks = Assets.numberOfPacks();

        packButtons = new TextButton[numberOfPacks];
        for (int i = 0; i < numberOfPacks; i++) {
            packButtons[i] = addButton("Pack " + (i+1), uiskin);
            table.add(packButtons[i]).size(100, 100).pad(10);
            if (!Assets.packAvailable(i))
                packButtons[i].setColor(0, 0, 0, 1);
        }
    }


    @Override
    public boolean handle(Event event) {
        super.handle(event);
        if (event instanceof ChangeListener.ChangeEvent) {
            Actor actor = event.getTarget();
            for (packNumber = 0; packNumber < numberOfPacks; packNumber++) {
                if (actor == packButtons[packNumber] && Assets.packAvailable(packNumber)) {
                    Assets.loadPack(packNumber);
                    setNextScreen(0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0, 0.199f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.act(delta);
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
    public Object getObjectByName(String name) {
        Object object = super.getObjectByName(name);
        if (object != null)
            return object;
        if (name.equals("table")) {
            return table;
        } else if (name.substring(0, name.length() - 3).equals("pack")) {
            return packButtons[Integer.parseInt(name.substring(5, name.length() - 1))];
        }
        return null;
    }
}
