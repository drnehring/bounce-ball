package com.david.bounceball.screens.windows;

import com.david.bounceball.screens.HelpScreen;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsHelpScreen extends HelpScreen {

    public WindowsHelpScreen(int helpScreenNumber) {
        super(helpScreenNumber);
    }

    @Override
    public void setNextScreen(int screenCode) {
        if (helpScreenNumber + 1 < NUMBER_OF_HELP_SCREENS) {
            listener.newScreen(new WindowsHelpScreen(helpScreenNumber + 1));
        } else {
            listener.newScreen(new WindowsMainMenuScreen());
        }
    }
}
