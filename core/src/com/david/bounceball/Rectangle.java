package com.david.bounceball;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by David on 8/23/2016.
 */
public class Rectangle extends Shape {

    protected float width;
    protected float height;

    public Rectangle(Vector2 center, float width, float height) {
        super(center);
        this.width = width;
        this.height = height;
    }

    public Rectangle(float x, float y, float width, float height) {
        super(x,y);
        this.width = width;
        this.height = height;
    }

    public Vector2 lowerLeft() {
        return center.cpy().sub(width/2, height/2);
    }

}

