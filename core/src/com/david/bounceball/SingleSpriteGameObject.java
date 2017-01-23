package com.david.bounceball;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by David on 12/18/2016.
 */
public abstract class SingleSpriteGameObject extends GameObject {

    Sprite sprite;

    public SingleSpriteGameObject(float levelX, float levelY, int gridX, int gridY) {
        super(levelX, levelY, gridX, gridY);
    }

    @Override
    protected void setSprite() {
        sprite = Assets.getSprite(getSpriteMainName(), this);
        setSpriteBounds();
    }

    protected void setSprite(int index) {
        sprite = Assets.getSprite(getSpriteMainName(), this, index);
        setSpriteBounds();
    }

    @Override
    protected void draw(Batch batch) {
        sprite.draw(batch);
    }
}
