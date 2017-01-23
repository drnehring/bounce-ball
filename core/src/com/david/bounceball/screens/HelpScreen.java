package com.david.bounceball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.david.bounceball.ScreenWithListener;

/**
 * Created by David on 10/13/2016.
 */
public abstract class HelpScreen extends ScreenWithListener {

    protected static int NUMBER_OF_HELP_SCREENS = 4;

    protected int helpScreenNumber;
    TextButton forwardButton;
    TextureRegion background;
    SpriteBatch spriteBatch;

    public HelpScreen(int helpScreenNumber) {
        super();
        this.helpScreenNumber = helpScreenNumber;
        forwardButton = new TextButton("Next", new Skin(Gdx.files.internal("data/uiskin.json")));
        forwardButton.setBounds(480 - 100, 0 , 100, 100);
        uiStage.addActor(forwardButton);
        background = new TextureRegion(new Texture(Gdx.files.internal("help_screen_" + helpScreenNumber + ".png")), 320, 480);
        spriteBatch = new SpriteBatch(1);
    }

    @Override
    public boolean handle(Event event) {
        super.handle(event);
        if (event instanceof ChangeListener.ChangeEvent) {
            Actor actor = event.getTarget();
            if (actor == forwardButton) {
                setNextScreen(0);
            }
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(uiStage.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, uiStage.getViewport().getWorldWidth(), uiStage.getViewport().getWorldHeight());
        spriteBatch.end();
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
        if (name.equals("forward")) {
            return forwardButton;
        } else if (name.equals("background")) {
            return background;
        }
        return null;
    }
}
