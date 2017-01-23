package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by David on 8/21/2016.
 */
public class BasicWall extends Wall {

    private boolean becomesFixed = false;
    private boolean ballLaunched = false;

    public BasicWall(float levelX, float levelY, int gridX, int gridY, int orientation) {
        this(levelX, levelY, gridX, gridY, orientation, 4);
    }

    public BasicWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment) {
        this(levelX, levelY, gridX, gridY, orientation, increment,  0);
    }

    public BasicWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, int strength) {
        super(levelX, levelY, gridX, gridY, orientation, increment, strength);
        becomesFixed = false;
        setSprite();
    }

    public BasicWall(float levelX, float levelY, int gridX, int gridY, int orientation, boolean becomesFixed) {
        this(levelX, levelY, gridX, gridY, orientation, 4, becomesFixed);
    }

    public BasicWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, boolean becomesFixed) {
        this(levelX, levelY, gridX, gridY, orientation, increment, 0, becomesFixed);
    }

    public BasicWall(float levelX, float levelY, int gridX, int gridY, int orientation,int increment, int strength,  boolean becomesFixed) {
        super(levelX, levelY, gridX, gridY, orientation, increment, strength);
        this.becomesFixed = becomesFixed;
        setSprite();
    }

    @Override
    protected String getSpriteMainName() {
        if (becomesFixed) {
            if (ballLaunched) {
                return super.getSpriteMainName() + "_fixed";
            } else {
                return super.getSpriteMainName() + "_basic_fixed_" + increment;
            }
        } else {
            return super.getSpriteMainName() + "_basic_" + increment;
        }
    }


    @Override
    public void touched(boolean left) {
        if (!becomesFixed || !ballLaunched)
            rotate(left);
    }

    public void ballLaunched() {
        ballLaunched = true;
        setSprite();
    }

    public boolean becomesFixed() {
        return becomesFixed;
    }

}
