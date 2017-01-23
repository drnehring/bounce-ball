package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by David on 8/21/2016.
 */
public class SpinningWall extends Wall {

    float period;
    float angle;
    private boolean ccw;

    public SpinningWall(float levelX, float levelY, int gridX, int gridY, int orientation, float period) {
        this(levelX, levelY, gridX, gridY, orientation, 0, period);
    }

    public SpinningWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, float period) {
        this(levelX, levelY, gridX, gridY, orientation, increment, 0, period);
    }

    public SpinningWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, int strength, float period) {
        super(levelX, levelY, gridX, gridY, orientation, increment, strength);
        this.period = period;
        this.angle = orientation * Wall.PI_OVER_EIGHT;
        ccw = period > 0;
        setSprite();
    }

    @Override
    protected void setSprite() {
        super.setSprite();
        sprite.setFlip(!ccw, false);
    }

    @Override
    protected String getSpriteMainName() {
        return super.getSpriteMainName() + "_spinning";
    }

    @Override
    public void touched(boolean left) {
    }

    public void spin(float deltaTime) {
        int newOrientation;
        angle += 2 * Math.PI * deltaTime / period;
        if (angle >= 2 * Math.PI)
            angle -= 2 * Math.PI;
        if (angle < 0)
            angle += 2 * Math.PI;
        newOrientation = (int) (angle / PI_OVER_EIGHT);
        increment = Math.abs(newOrientation - orientation.get());
        if (increment > 12)
            increment = 16 - increment;
        if (increment != 0)
            rotate(ccw);
    }

}
