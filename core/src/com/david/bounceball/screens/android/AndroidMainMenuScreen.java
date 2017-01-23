package com.david.bounceball.screens.android;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.david.bounceball.Assets;

/**
 * Created by David on 12/31/2016.
 */
public class AndroidMainMenuScreen extends com.david.bounceball.screens.MainMenuScreen {

    Label test;

    public AndroidMainMenuScreen() {
        super();
        Skin uiskin = Assets.uiskin;
        test = new Label("Android!", uiskin);
        test.setBounds(0, 0, 100, 50);
        uiStage.addActor(test);
    }

    @Override
    public void setNextScreen(int screenCode) {
        switch (screenCode) {
            case PACK_SCREEN:
                listener.newScreen(new AndroidPackScreen());
                break;
            case SETTINGS_SCREEN:
                listener.newScreen(new AndroidSettingsScreen());
                break;
            case HELP_SCREEN:
                listener.newScreen(new AndroidHelpScreen(0));
                break;
            case ABOUT_SCREEN:
                listener.newScreen(new AndroidAboutScreen());
                break;
        }
    }


}
