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

/**
 * Created by David on 10/13/2016.
 */
public abstract class LevelScreen extends ScreenWithListener {

    private static final int LEVEL_BUTTONS_PER_LINE = 4;

    int numberOfLevels = 0;
    protected int packNumber;
    protected int levelNumber;
    TextButton[] levelButtons;
    Table table;
    ScrollPane scrollPane;


    public LevelScreen(int packNumber) {
        super();
        Skin uiskin = Assets.uiskin;
        this.packNumber = packNumber;

        table = new Table();
        table.setFillParent(true);
        table.top();

        scrollPane = new ScrollPane(table, uiskin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFillParent(true);

        uiStage.addActor(scrollPane);
        Assets.command.remove();
        uiStage.addActor(Assets.command);

        FileHandle levels = Gdx.files.internal("pack_" + packNumber + ".txt");
        String[] splitData = levels.readString().split("\n");
        numberOfLevels = splitData.length / Level.LINES_PER_LEVEL;
        levelButtons = new TextButton[numberOfLevels];
        for (int i = 0; i < numberOfLevels; i++) {
            levelButtons[i] = new TextButton("Level " + (i+1), uiskin);
            if (i % LEVEL_BUTTONS_PER_LINE == 0)
                table.row().size(100).pad(10);
            table.add(levelButtons[i]);
        }
    }

    @Override
    public boolean handle(Event event) {
        super.handle(event);
        if (event instanceof ChangeListener.ChangeEvent) {
            Actor actor = event.getTarget();
            for (levelNumber = 0; levelNumber < numberOfLevels; levelNumber++) {
                if (actor == levelButtons[levelNumber]) {
                    setNextScreen(0);
                    break;
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
        uiStage.act();
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
        if (name.substring(0, name.length() - 3).equals("levels")) {
            return levelButtons[Integer.parseInt(name.substring(7, name.length() - 1))];
        } else if (name.equals("table")) {
            return table;
        } else if (name.equals("scrollPane")) {
            return scrollPane;
        }
        return null;
    }
}
