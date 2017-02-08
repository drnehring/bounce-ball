package com.david.bounceball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.david.bounceball.ScreenWithListener;

/**
 * Created by David on 10/13/2016.
 */
public abstract class AboutScreen extends ScreenWithListener {

    SpriteBatch spriteBatch;
    TextureRegion background;
    Camera camera;

    public AboutScreen() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        spriteBatch = new SpriteBatch(1);
        background = new TextureRegion(new Texture(Gdx.files.internal("about_background.png")),320, 480);
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof InputEvent) {
            InputEvent inputEvent = (InputEvent) event;
            if (inputEvent.getType() == InputEvent.Type.touchDown) {
                setNextScreen(0);
                return true;
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
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, camera.position.x - camera.viewportWidth / 2, camera. position.y - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
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
        if (name.equals("background")) {
            return background;
        }
        return null;
    }
}
