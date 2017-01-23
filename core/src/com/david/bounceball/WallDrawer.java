package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

/**
 * Created by David on 11/28/2016.
 */
//Alters a walls rotation from one orientation to another so that it is smooth
public class WallDrawer {

    private static final float STANDARD_ROTATION_INTERVAL = 0.2f;
    private Wall wall;
    private Interpolation irp;
    private int startOrientation;
    private int endOrientation;
    private boolean ccw;
    private float rotationTime = -1;

    public WallDrawer(Wall wall) {
        this.wall = wall;
        irp = new Interpolation.PowOut(3);
    }

    public void beginRotation(int startOrientation, int endOrientation, boolean ccw) {
        this.startOrientation = startOrientation;
        this.endOrientation = endOrientation;
        this.ccw = ccw;
        if (ccw && (this.endOrientation < this.startOrientation)) {
            this.endOrientation += 16;
        } else if (!ccw && (this.endOrientation > this.startOrientation)) {
            this.startOrientation += 16;
        }
        rotationTime = 0;
    }

    public void draw(Batch batch) {
        if (rotationTime != -1) {
            rotationTime += Gdx.graphics.getDeltaTime();
            if (rotationTime / STANDARD_ROTATION_INTERVAL  < 1) {
                wall.sprite.setRotation(irp.apply(startOrientation, endOrientation, rotationTime/STANDARD_ROTATION_INTERVAL) * 22.5f);
            } else {
                rotationTime = -1;
            }
        }
        wall.sprite.draw(batch);
    }


}
