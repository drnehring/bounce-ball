package com.david.bounceball.screens.windows;

import com.david.bounceball.screens.SettingsScreen;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsSettingsScreen extends SettingsScreen {

    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new WindowsMainMenuScreen());
    }
}
