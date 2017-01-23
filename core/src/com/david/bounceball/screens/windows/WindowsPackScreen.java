package com.david.bounceball.screens.windows;

import com.david.bounceball.screens.PackScreen;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsPackScreen extends PackScreen {

    @Override
    public void setNextScreen(int screenCode) {
        listener.newScreen(new WindowsLevelScreen(packNumber));
    }
}
