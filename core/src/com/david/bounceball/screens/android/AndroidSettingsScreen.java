package com.david.bounceball.screens.android;

import com.david.bounceball.screens.SettingsScreen;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidSettingsScreen extends SettingsScreen {


    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new AndroidMainMenuScreen());
    }
}
