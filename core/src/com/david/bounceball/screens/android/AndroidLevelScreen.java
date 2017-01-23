package com.david.bounceball.screens.android;

import com.david.bounceball.screens.LevelScreen;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidLevelScreen extends LevelScreen {

    public AndroidLevelScreen(int packNumber) {
        super(packNumber);
    }

    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new AndroidGameScreen(packNumber, levelNumber));
    }
}
