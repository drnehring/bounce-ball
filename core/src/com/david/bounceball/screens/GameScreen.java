package com.david.bounceball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.math.Rectangle;
import com.david.bounceball.Assets;
import com.david.bounceball.Level;
import com.david.bounceball.ScreenWithListener;
import com.david.bounceball.Settings;

/**
 * Created by David on 9/26/2016.
 */
public abstract class GameScreen extends ScreenWithListener {

    private final Rectangle leveluiBounds = new Rectangle(480 * 0.05f, 840 * 0.3f, 480 * 0.9f, 840 * 0.5f);

    public static String debugText = "";

    int levelNumber;
    int packNumber;
    SpriteBatch batch;
    Stage gameStage;
    protected Level level;
    BitmapFont font;
    TextButton back;
    TextButton previousLevel;
    TextButton restart;
    TextButton nextLevel;
    Image background;
    TextureRegion grid;

    public GameScreen(int packNumber, int levelNumber) {
        InputMultiplexer inputMultiplexer;
        Skin uiskin = Assets.uiskin;
        back = new TextButton("Back", uiskin);
        back.setBounds(0, 720, 120, 120);
        uiStage.addActor(back);
        previousLevel = new TextButton("Previous\nlevel", uiskin);
        previousLevel.setBounds(0, 120, 120, 120);
        uiStage.addActor(previousLevel);
        restart = new TextButton("Restart", uiskin);
        restart.setBounds(180, 120, 120, 120);
        uiStage.addActor(restart);
        nextLevel = new TextButton("Next\nlevel", uiskin);
        nextLevel.setBounds(360, 120, 120, 120);
        uiStage.addActor(nextLevel);

        grid = new TextureRegion(new Texture(Gdx.files.internal("grid.png")));

        gameStage = new Stage(new StretchViewport(1, 1, new OrthographicCamera()), new SpriteBatch(100));
        loadLevel(packNumber, levelNumber);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        background = new Image(new Texture(Gdx.files.internal("pool_table.png")));
        background.setBounds(0, 0, uiStage.getWidth(), uiStage.getHeight());
        background.setColor(0.2f, 0.51f, 0.2f, 1f);

        font = new BitmapFont();
        batch = new SpriteBatch(20);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(uiStage.getCamera().combined);
        batch.begin();
        background.draw(batch, 1);
        font.draw(batch, debugText, 0, 100);
        batch.end();
        batch.setProjectionMatrix(gameStage.getCamera().combined);
        batch.begin();
        batch.draw(grid, level.x, level.y, level.gridWidth, level.gridHeight);
        batch.end();
        uiStage.act(delta);
        uiStage.draw();
        gameStage.act(delta);
        gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
        gameStage.getViewport().update(width, height, true);
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
            if (actor == back) {
                setNextScreen(0);
            } else if (actor == previousLevel) {
                loadLevel(packNumber, levelNumber - 1);
            } else if (actor == restart) {
                loadLevel(packNumber, levelNumber);
            } else if (actor == nextLevel) {
                loadLevel(packNumber, levelNumber + 1);
            }
        }
        return false;
    }

    private void loadLevel(int packNumber, int levelNumber) {
        Level newLevel = Level.Factory(packNumber, levelNumber, this);
        if (newLevel != null) {
            if (level != null)
                level.remove();
            level = newLevel;
            gameStage.addActor(level);
            this.levelNumber = levelNumber;
            this.packNumber = packNumber;
            Settings.lastLevelNumber = levelNumber;
            Settings.lastPackNumber = packNumber;
            grid.setU2((level.gridWidth * 10f + level.gridWidth - 1) / 109f);
            grid.setV2((level.gridHeight * 10f + level.gridHeight - 1) / 109f);
        }
    }

    /*
        Sets the viewport of gameStage so that each square of the grid is 1 long
        and the grid is as large as it can be within the bounds specified by
        leveluiBounds
     */
    public Vector2 setLevelBounds(int gridWidth, int gridHeight) {

        float x;
        float y;
        float widthRatio = leveluiBounds.width / gridWidth;
        float heightRatio = leveluiBounds.height / gridHeight;
        float gridUnitUiSize;

        if (widthRatio > heightRatio) {
            gridUnitUiSize = heightRatio;
            x = ((leveluiBounds.width - gridUnitUiSize * gridWidth) / 2 + leveluiBounds.x) / gridUnitUiSize;
            y = leveluiBounds.y / gridUnitUiSize;
        } else {
            gridUnitUiSize = widthRatio;
            x = leveluiBounds.x / gridUnitUiSize;
            y = ((leveluiBounds.height - gridUnitUiSize * gridHeight) / 2 + leveluiBounds.y) / gridUnitUiSize;
        }

        gameStage.getViewport().setWorldSize(uiStage.getWidth() / gridUnitUiSize,  uiStage.getHeight() / gridUnitUiSize);
        gameStage.getViewport().apply(true);

        return new Vector2(x, y);

    }

    public abstract void levelTouched(InputEvent event);

    @Override
    public Object getObjectByName(String name) {
        Object object = super.getObjectByName(name);
        if (object != null)
            return object;
        if (name.equals("gameStage")) {
            return gameStage;
        } else if (name.equals("font")) {
            return font;
        } else if (name.equals("back")) {
            return back;
        } else if (name.equals("previousLevel")) {
            return previousLevel;
        } else if (name.equals("restart")) {
            return restart;
        } else if (name.equals("nextLevel")) {
            return nextLevel;
        } else if (name.equals("background")) {
            return background;
        }
        return null;
    }

    public Level getLevel() {
        return level;
    }
}
