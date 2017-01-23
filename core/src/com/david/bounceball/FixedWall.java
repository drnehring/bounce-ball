package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by David on 8/21/2016.
 */
public class FixedWall extends Wall {

    public FixedWall(float levelX, float levelY, int gridX, int gridY, int orientation) {
        this(levelX, levelY, gridX, gridY, orientation, 0);
    }

    public FixedWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment) {
        this(levelX, levelY, gridX, gridY, orientation, increment, 0);
    }

    public FixedWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, int strength) {
        super(levelX, levelY, gridX, gridY, orientation, increment, strength);
        setSprite();
    }

    @Override
    protected String getSpriteMainName() {
        return super.getSpriteMainName() + "_fixed";
    }

    @Override
    public void touched(boolean left) {

    }

}
