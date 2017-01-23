package com.david.bounceball.screens.windows;

import com.david.bounceball.screens.MainMenuScreen;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsMainMenuScreen extends MainMenuScreen {


    @Override
    public void setNextScreen(int screenCode) {
        switch (screenCode) {
            case PACK_SCREEN:
                listener.newScreen(new WindowsPackScreen());
                break;
            case SETTINGS_SCREEN:
                listener.newScreen(new WindowsSettingsScreen());
                break;
            case HELP_SCREEN:
                listener.newScreen(new WindowsHelpScreen(0));
                break;
            case ABOUT_SCREEN:
                listener.newScreen(new WindowsAboutScreen());
                break;
        }
    }
}
