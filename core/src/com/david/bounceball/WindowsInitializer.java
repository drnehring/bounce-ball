package com.david.bounceball;

import com.david.bounceball.screens.windows.WindowsMainMenuScreen;
import java.util.*;

/**
 * Created by David on 12/31/2016.
 */
public class WindowsInitializer implements Initializer {

    @Override
    public ScreenWithListener getScreen() {
        return new WindowsMainMenuScreen();
    }
}
