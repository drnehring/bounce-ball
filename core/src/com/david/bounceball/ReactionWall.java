package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by David on 8/21/2016.
 */
public class ReactionWall extends Wall {

    private static final float LINK_SPRITE_HEIGHT = 0.1f;

    private boolean ccw;
    private ReactionWall forwardlink;
    private ReactionWall backwardlink;
    private Sprite linkSprite;

    public ReactionWall(float levelX, float levelY, int gridX, int gridY, int orientation) {
        this(levelX, levelY, gridX, gridY, orientation, 4);
    }

    public ReactionWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment) {
        this(levelX, levelY, gridX, gridY, orientation, increment, 0);
    }

    public ReactionWall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, int strength) {
        super(levelX, levelY, gridX, gridY, orientation, increment, strength);
        ccw = true;
        if (this.increment < 0) {
            this.increment = -this.increment;
            ccw = false;
        }
        setSprite();
    }

    @Override
    protected void setSprite() {
        super.setSprite();
        sprite.setFlip(!ccw, false);
    }

    @Override
    protected String getSpriteMainName() {
        return super.getSpriteMainName() + "_reaction_" + increment;
    }

    public void setForwardLink(ReactionWall forwardlink) {
        this.forwardlink = forwardlink;
        if (forwardlink != null) {
            //If we have a link, we draw a line from this to the link
            Vector2 difference = forwardlink.bounds.center.cpy().sub(bounds.center);
            Vector2 center = difference.cpy().scl(0.5f).add(bounds.center);
            linkSprite = Assets.getSprite("wall_fixed", this);
            linkSprite.setBounds(center.x - difference.len() / 2, center.y - LINK_SPRITE_HEIGHT / 2, difference.len(), LINK_SPRITE_HEIGHT);
            linkSprite.setOriginCenter();
            linkSprite.setRotation(difference.angle());
        } else {
            linkSprite = null;
        }
    }

    public void setBackwardLink(ReactionWall backwardlink) {
        this.backwardlink = backwardlink;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if (forwardlink != null)
            linkSprite.draw(batch);
    }

    @Override
    public void touched(boolean left) {
    }

    @Override
    public void hit() {
        super.hit();
        if (destroyed()) {
            if (backwardlink != null) {
                backwardlink.setForwardLink(null);
            }
            if (forwardlink != null) {
                forwardlink.setBackwardLink(null);
            }
        } else {
            rotate();
            propogateRotation(true);
            propogateRotation(false);
        }
    }

    public void rotate() {
        super.rotate(ccw);
    }

    private void propogateRotation(boolean forward) {
        if (forward) {
            if (forwardlink != null) {
                forwardlink.rotate();
                forwardlink.propogateRotation(forward);
            }
        } else {
            if (backwardlink != null) {
                backwardlink.rotate();
                backwardlink.propogateRotation(forward);
            }
        }
    }

}
