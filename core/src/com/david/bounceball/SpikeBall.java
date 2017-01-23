package com.david.bounceball;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 12/17/2016.
 */
//Stores all the spikes on a certain square
public class SpikeBall extends GameObject {

    private Spike[] spikes = new Spike[8];

    public static SpikeBall Factory(float levelX, float levelY, String spikeBallData) {
        GameObject gameObject = GameObject.Factory(levelX, levelY, spikeBallData);
        String[] split = spikeBallData.split(" ");
        return new SpikeBall(levelX, levelY, gameObject.gridX, gameObject.gridY, split[2]);
    }

    public SpikeBall(float levelX, float levelY, int gridX, int gridY, String spikeOrientations) {
        super(levelX, levelY, gridX, gridY);
        String[] split = spikeOrientations.split("-");
        for(String string : split) {
            int orientation = Integer.parseInt(string);
            spikes[orientation / 2] = new Spike(levelX, levelY, gridX, gridY, orientation);
        }
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
        for (int i = 0; i < 8; i++) {
            if (spikes[i] != null)
                spikes[i].draw(batch);
        }
    }

    public Spike getSpike(int orientation) {
        return spikes[orientation / 2];
    }

    public void setSpikeSprites() {
        for (int i = 0; i < 8; i++) {
            if (spikes[i] != null)
                spikes[i].setSprite(i * 2);
        }
    }
}
