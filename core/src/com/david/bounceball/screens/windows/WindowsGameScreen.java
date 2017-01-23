package com.david.bounceball.screens.windows;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.david.bounceball.screens.GameScreen;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsGameScreen extends GameScreen {
    public WindowsGameScreen(int packNumber, int levelNumber) {
        super(packNumber, levelNumber);
    }

    @Override
    public void levelTouched(InputEvent event) {
        if (event.getType() == InputEvent.Type.touchDown) {
            level.touchDown(new Vector2(event.getStageX(), event.getStageY()), event.getButton() == 0);
        }
    }

    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new WindowsMainMenuScreen());
    }
}
