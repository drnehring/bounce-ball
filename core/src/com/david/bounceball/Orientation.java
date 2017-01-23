package com.david.bounceball;

import com.badlogic.gdx.math.Vector2;
import com.david.bounceball.screens.GameScreen;

/**
 * Created by David on 8/21/2016.
 */
/*
    Orientation indicates a multiple of 22.5 degrees (pi/ 8 radians) that some
    object is moving in the direction of or pointing in
 */
public class Orientation {

    private int orientation;

    public static int[] asIntVector(int orientation) {
        int vectorX;
        int vectorY;
        if (orientation % 2 == 1) {
            return null;
        }

        if (orientation == 4 || orientation == 12) {
            vectorX = 0;
        } else if (orientation < 4 || orientation > 12) {
            vectorX = 1;
        } else {
            vectorX = -1;
        }
        if (orientation == 0 || orientation == 8) {
            vectorY = 0;
        } else if (orientation > 0 && orientation < 8) {
            vectorY = 1;
        } else {
            vectorY = -1;
        }
        return new int[] {vectorX, vectorY};
    }

    public static Vector2 asUnitVector(int orientation) {
        int[] intVector = asIntVector(orientation);
        Vector2 unitVector = new Vector2((float) intVector[0], (float) intVector[1]);
        unitVector.nor();
        return unitVector;
    }

    public Orientation() {
        orientation = 0;
    }

    public Orientation(int orientation) {
        this.orientation = orientation % 16;
    }

    public Orientation(Orientation other) {
        this.orientation = other.orientation;
    }

    public Orientation cpy() {
        return new Orientation(orientation);
    }

    public void set(int orientation) {
        this.orientation = (orientation + 16) % 16;
    }

    public Orientation add(int a) {
        orientation = (orientation + a) % 16;
        return this;
    }

    public Orientation add(Orientation other) {
        return add(other.orientation);
    }

    public Orientation sub(int a) {
        orientation = (orientation - a + 16) % 16;
        return this;
    }

    public Orientation sub(Orientation other) {
        return sub(other.orientation);
    }

    //Follows the physical rule that the angle of incidence is equal to the angle of reflection
    public Orientation reflect(int axis) {
        orientation = (2 * axis - orientation + 16) % 16;
        return this;
    }

    public Orientation reflect(Orientation axis) {
        return reflect(axis.orientation);
    }


    public int[] asIntVector() {
        return asIntVector(orientation);
    }

    public Vector2 asUnitVector() {
        return asUnitVector(orientation);
    }

    public boolean opposite(int other) {
        return (orientation - other + 16) % 16 == 8;
    }

    public boolean opposite(Orientation other) {
        return opposite(other.get());
    }

    public boolean equals(int other) {
        return orientation == other;
    }

    public boolean equals(Orientation other) {
        return equals(other.get());
    }

    public boolean oppositeOrEquals(Orientation other) {
        return (orientation - other.orientation + 16) % 8 == 0;
    }

    /*
        Orientation o1 is greater than Orienation o2 if a counterclockwise turn from
        o2 will reach o1 faster than a clockwise turn
    */
    public boolean greaterThan(int other) {
        return (orientation - other + 16) % 16 < 8;
    }

    /*
        Orientation o1 is less than Orienation o2 if a clockwise turn from
        o2 will reach o1 faster than a counterclockwise turn
    */
    public boolean lessThan(int other) {
        return (orientation - other + 16) % 16 > 8;
    }

    public boolean greaterThan(Orientation other) {
        return greaterThan(other.get());
    }

    public boolean lessThan(Orientation other) {
        return lessThan(other.get());
    }

    public boolean between(int o1, int o2) {
        return greaterThan(o1) && lessThan(o2);
    }

    public boolean between(Orientation o1, Orientation o2) {
        return between(o1.get(), o2.get());
    }

    public Orientation flip() {
        return add(8);
    }

    public int get() {
        return orientation;
    }

}
