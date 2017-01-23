package com.david.bounceball.screens.windows;

import com.david.bounceball.screens.LevelScreen;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsLevelScreen extends LevelScreen {
    public WindowsLevelScreen(int packNumber) {
        super(packNumber);
    }

    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new WindowsGameScreen(packNumber, levelNumber));
    }
}
