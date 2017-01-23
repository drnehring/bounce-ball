package com.david.bounceball;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by David on 8/23/2016.
 */
public class Circle extends Shape {

    protected float radius;

    public Circle(Vector2 center, float radius) {
        super(center);
        this.radius = radius;
    }

    public Circle(float x, float y, float radius) {
        super(x,y);
        this.radius = radius;
    }

}

