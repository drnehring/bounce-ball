package com.david.bounceball.screens.android;

import com.david.bounceball.screens.HelpScreen;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidHelpScreen extends HelpScreen {


    public AndroidHelpScreen(int helpScreenNumber) {
        super(helpScreenNumber);
    }

    @Override
    public void setNextScreen(int screenCode) {
        if (helpScreenNumber + 1 < NUMBER_OF_HELP_SCREENS) {
            listener.newScreen(new AndroidHelpScreen(helpScreenNumber + 1));
        } else {
            listener.newScreen(new AndroidMainMenuScreen());
        }
    }
}
