package com.david.bounceball;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Created by David on 12/21/2016.
 */
public class Spike extends SingleSpriteGameObject {

    private static final float BOUNDS_DISTANCE_FROM_CENTER = 0.23f;
    private static final float SPRITE_DISTANCE_FROM_CENTER = 0.23f;
    private static final float SPRITE_HEIGHT = 0.1f;

    public Spike(float levelX, float levelY, int gridX, int gridY, int orientation) {
        super(levelX, levelY, gridX, gridY);
        setSprite(orientation);
        Vector2 offsetFromCenter = new Vector2(Wall.BOUNDS_WIDTH / 4 + BOUNDS_DISTANCE_FROM_CENTER / 2, 0).rotate(orientation * 22.5f);
        bounds = new RotatableRectangle(offsetFromCenter.add(bounds.center), Wall.BOUNDS_WIDTH / 2  - BOUNDS_DISTANCE_FROM_CENTER, Wall.BOUNDS_HEIGHT, orientation * Wall.PI_OVER_EIGHT);
    }


    @Override
    protected String getSpriteMainName() {
        return "spike";
    }

    @Override
    protected void setSpriteBounds() {
    }

    protected void setSpriteBounds(int orientation) {
        Vector2 spriteCenter = new Vector2();
        float spriteWidth = Wall.SPRITE_WIDTH / 2 - SPRITE_DISTANCE_FROM_CENTER;
        float spriteHeight = SPRITE_HEIGHT;
        spriteCenter.set(SPRITE_DISTANCE_FROM_CENTER + spriteWidth / 2, 0).rotate(orientation * 22.5f).add(bounds.center);
        sprite.setBounds(spriteCenter.x - spriteWidth / 2, spriteCenter.y - spriteHeight / 2, spriteWidth, spriteHeight);
        sprite.setRotation(orientation * 22.5f);
        sprite.setOrigin(spriteWidth / 2, spriteHeight / 2);
    }

    protected void setSprite(int orientation) {
        sprite = Assets.getSprite(getSpriteMainName(), this);
        setSpriteBounds(orientation);
    }

}
