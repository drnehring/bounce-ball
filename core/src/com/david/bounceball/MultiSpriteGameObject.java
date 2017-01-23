package com.david.bounceball;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by David on 12/18/2016.
 */
public abstract class MultiSpriteGameObject extends GameObject {

    List<Sprite> sprites;

    public MultiSpriteGameObject(float levelX, float levelY, int gridX, int gridY) {
        super(levelX, levelY, gridX, gridY);
        sprites = new ArrayList<Sprite>();
    }

    @Override
    protected void draw(Batch batch) {
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }
    }

}
