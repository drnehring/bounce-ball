package com.david.bounceball.screens.android;

import com.david.bounceball.screens.AboutScreen;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidAboutScreen extends AboutScreen {


    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new AndroidMainMenuScreen());
    }
}
