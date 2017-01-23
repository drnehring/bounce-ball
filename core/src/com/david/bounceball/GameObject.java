package com.david.bounceball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by David on 8/21/2016.
 */
public abstract class GameObject {

    protected static final String[] COLORS = {"red", "blue", "green", "yellow", "orange"};

    protected int gridX;
    protected int gridY;
    protected Shape bounds;

    protected static GameObject Factory(float levelX, float levelY, String gameObjectData) {
        return new GameObjectTemp(levelX, levelY, Integer.parseInt(gameObjectData.substring(0, 2)), Integer.parseInt(gameObjectData.substring(3, 5)));
    }

    /*
        GameObjectTemp and ShapeTemp exist because GameObject and Shape should be abstract,
        but to have common initialization code for bounds.center, we need to instantiate
        this classes
     */
    private static class GameObjectTemp extends GameObject {
        public GameObjectTemp(float levelX, float levelY, int gridX, int gridY) {
            super(levelX, levelY, gridX, gridY);
        }

        @Override
        protected void setSprite() {

        }

        @Override
        protected String getSpriteMainName() {
            return null;
        }
        @Override
        protected void setSpriteBounds() {
        }

        @Override
        protected void draw(Batch batch) {

        }
    }

    public GameObject(float levelX, float levelY, int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        bounds = new ShapeTemp(new Vector2((gridX + 0.5f) + levelX, (gridY + 0.5f) + levelY));
    }


    private class ShapeTemp extends Shape {
        ShapeTemp(Vector2 center) {
            super(center);
        }
    }

    protected abstract void setSprite();

    protected abstract String getSpriteMainName();
    protected abstract void setSpriteBounds();
    protected abstract void draw(Batch batch);

}
