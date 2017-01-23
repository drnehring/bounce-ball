package com.david.bounceball;


import com.david.bounceball.screens.android.AndroidMainMenuScreen;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidInitializer implements Initializer {

    @Override
    public ScreenWithListener getScreen() {
        return new AndroidMainMenuScreen();
    }
}
