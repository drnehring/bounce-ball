package com.david.bounceball;

import com.david.bounceball.Orientation;
import com.david.bounceball.SingleSpriteGameObject;

/**
 * Created by David on 1/9/2017.
 */
public abstract class OffsetableGameObject extends SingleSpriteGameObject {

    private int offsetOrientation;

    public OffsetableGameObject(float levelX, float levelY, int gridX, int gridY) {
        this(levelX, levelY, gridX, gridY, -1);
    }

    public OffsetableGameObject(float levelX, float levelY, int gridX, int gridY, int offsetOrientation) {
        super(levelX, levelY, gridX, gridY);
        this.offsetOrientation = offsetOrientation;
        if (this.offsetOrientation != -1) {
            int[] offsetAsIntVector = Orientation.asIntVector(this.offsetOrientation);
            bounds.center.add(offsetAsIntVector[0] * 0.25f, offsetAsIntVector[1] * 0.25f);
        }
    }

    public int getOffsetOrientation() {
        return offsetOrientation;
    }


}
