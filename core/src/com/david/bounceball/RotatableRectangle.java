package com.david.bounceball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by David on 8/23/2016.
 */
public class RotatableRectangle extends Rectangle {

    private float angle;

    public RotatableRectangle(Vector2 center, float width, float height, float angle) {
        super(center, width, height);
        this.angle = angle;
    }

    public RotatableRectangle(float x, float y, float width, float height, float angle) {
        super(x, y, width, height);
        this.angle = angle;
    }

    @Override
    public Vector2 lowerLeft() {
        return super.lowerLeft().sub(center).rotateRad(angle).add(center);
    }

    public Vector2 unrotatedLowerLeft() {
        return super.lowerLeft();
    }

    public void rotate(float angle) {
        setAngle(angle + this.angle);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle % (float) (2 * Math.PI);
    }
}
