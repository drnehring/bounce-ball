package com.david.bounceball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by David on 8/23/2016.
 */
public abstract class Shape {

    public Vector2 center = new Vector2();

    public Shape(Vector2 center) {
        this.center.set(center);

    }

    public Shape(float x, float y) {
        this.center.set(x,y);
    }

}

