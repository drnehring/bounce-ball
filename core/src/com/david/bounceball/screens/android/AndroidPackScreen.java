package com.david.bounceball.screens.android;

import com.david.bounceball.screens.PackScreen;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidPackScreen extends PackScreen {

    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new AndroidLevelScreen(packNumber));
    }
}
