package com.david.bounceball;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 8/21/2016.
 */
public abstract class Wall extends SingleSpriteGameObject {

    public static final float PI_OVER_EIGHT = (float) (Math.PI / 8);
    public static final float TO_DEGREES = (float) (180 / Math.PI);
    protected static final float SPRITE_WIDTH = 1f;
    protected static final float SPRITE_HEIGHT = 0.3f;
    protected static final float BOUNDS_WIDTH = 1f;
    protected static final float BOUNDS_HEIGHT = 0.06f;
    protected static final int MAX_STRENGTH = 4;
    private static List<ReactionWall> reactionWalls = new ArrayList<ReactionWall>();
    private static List<int[]> links = new ArrayList<int[]>();

    protected Orientation orientation;
    protected int increment;
    protected int strength;
    protected int hits;
    protected WallDrawer drawer;
    protected List<WallListener> listeners;

    public static Wall Factory(float levelX, float levelY, String wallData) {
        GameObject gameObject = GameObject.Factory(levelX, levelY, wallData);
        String[] split = wallData.split("\\|");
        String[] wallLevelDataSplit = split[0].split(" ");
        String[] wallTypeLevelDataSplit = split[1].split(" ");
        int orientation = Integer.parseInt(wallLevelDataSplit[2]);
        int increment = 0;
        if (wallLevelDataSplit.length > 3)
            increment = Integer.parseInt(wallLevelDataSplit[3]);
        int strength = 0;
        if (wallLevelDataSplit.length > 4)
            strength = Integer.parseInt(wallLevelDataSplit[4]);
        String type = wallTypeLevelDataSplit[0];
        boolean becomesFixed = false;
        boolean linked = false;
        int[] link = null;
        float period = 0;
        if (type.equals("Basic")) {
            if (wallTypeLevelDataSplit.length > 1)
                becomesFixed = Boolean.parseBoolean(wallTypeLevelDataSplit[1]);
        } else if (type.equals("Reaction")) {
            if (wallTypeLevelDataSplit.length > 1) {
                linked = true;
                link = new int[2];
                link[0] = Integer.parseInt(wallTypeLevelDataSplit[1]);
                if (wallTypeLevelDataSplit.length > 2) {
                    link[1] = Integer.parseInt(wallTypeLevelDataSplit[2]);
                } else {
                    link[1] = -1;
                }
            }
        } else if (type.equals("Spinning")) {
            period = Float.parseFloat(wallTypeLevelDataSplit[1]);
        }

        if (type.equals("Basic")) {
            if (wallLevelDataSplit.length == 3) {
                if (wallTypeLevelDataSplit.length == 1) {
                    return new BasicWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation);
                } else {
                    return new BasicWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, becomesFixed);
                }
            } else if (wallLevelDataSplit.length == 4) {
                if (wallTypeLevelDataSplit.length == 1) {
                    return new BasicWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment);
                } else {
                    return new BasicWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, becomesFixed);
                }
            } else if (wallLevelDataSplit.length == 5) {
                if (wallTypeLevelDataSplit.length == 1) {
                    return new BasicWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, strength);
                } else {
                    return new BasicWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, strength, becomesFixed);
                }
            }
        } else if (type.equals("Fixed")) {
            if (wallLevelDataSplit.length == 3) {
                return new FixedWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation);
            } else if (wallLevelDataSplit.length == 4) {
                return new FixedWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment);
            } else if (wallLevelDataSplit.length == 5) {
                return new FixedWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, strength);
            }
        } else if (type.equals("Reaction")) {
            ReactionWall newReactionWall = null;
            if (wallLevelDataSplit.length == 3) {
                newReactionWall = new ReactionWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation);
            } else if (wallLevelDataSplit.length == 4) {
                newReactionWall = new ReactionWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment);
            } else if (wallLevelDataSplit.length == 5) {
                newReactionWall = new ReactionWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, strength);
            }
            if (linked) {
                reactionWalls.add(newReactionWall);
                links.add(link);
            }
            return newReactionWall;
        } else if (type.equals("Spinning")) {
            if (wallLevelDataSplit.length == 3) {
                return new SpinningWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, period);
            } else if (wallLevelDataSplit.length == 4) {
                return new SpinningWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, period);
            } else if (wallLevelDataSplit.length == 5) {
                return new SpinningWall(levelX, levelY, gameObject.gridX, gameObject.gridY, orientation, increment, strength, period);
            }
        }
        return null;
    }

    public static void makeLinks() {
        for (int i = 0; i < reactionWalls.size(); i++) {
            if (links.get(i)[1] != -1) {
                for (int j = 0; j < reactionWalls.size(); j++) {
                    if (links.get(i)[1] == links.get(j)[0]) {
                        reactionWalls.get(i).setForwardLink(reactionWalls.get(j));
                        reactionWalls.get(j).setBackwardLink(reactionWalls.get(i));
                    }
                }
            }
        }
        links.clear();
        reactionWalls.clear();
    }

    public Wall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment) {
        this(levelX, levelY, gridX, gridY, orientation, increment, 0);
    }

    public Wall(float levelX, float levelY, int gridX, int gridY, int orientation, int increment, int strength) {
        super(levelX, levelY, gridX, gridY);
        this.orientation = new Orientation(orientation);
        this.increment = increment;
        this.strength = strength;
        this.hits = 0;
        bounds = new RotatableRectangle(bounds.center, BOUNDS_WIDTH, BOUNDS_HEIGHT, orientation * PI_OVER_EIGHT);
        drawer = new WallDrawer(this);
        listeners = new ArrayList<WallListener>();
    }

    @Override
    protected String getSpriteMainName() {
        if (strength == 0) {
            return "wall";
        } else {
            return "wall_weak";
        }
    }

    @Override
    protected void setSprite() {
        if (strength == 0) {
            super.setSprite();
        } else {
            super.setSprite(MAX_STRENGTH + 1 - strength + hits);
        }
    }

    @Override
    protected void setSpriteBounds() {
        RotatableRectangle rr = (RotatableRectangle) bounds;
        sprite.setBounds(bounds.center.x - SPRITE_WIDTH / 2, bounds.center.y - SPRITE_HEIGHT / 2, SPRITE_WIDTH, SPRITE_HEIGHT);
        sprite.setRotation(rr.getAngle() * TO_DEGREES);
        sprite.setOrigin(SPRITE_WIDTH / 2, SPRITE_HEIGHT / 2);
    }

    public void hit() {
        if (strength != 0) {
            if (!destroyed()) { //On the offchance that two balls hit the wall in the same frame
                hits += 1;
                if (!destroyed()) {
                    setSprite();
                } else {
                    //wallDestroyed always removes a listener, so this works without increment
                    for (int i = 0; i < listeners.size();) {
                        listeners.get(i).wallDestroyed(this);
                    }
                }
            }
        }
    }

    public void rotate(boolean ccw) {
        RotatableRectangle rr = (RotatableRectangle) bounds;
        int startOrientation = orientation.get();
        if (ccw) {
            orientation.add(increment);
        } else {
            orientation.sub(increment);
        }
        rr.setAngle(orientation.get() * PI_OVER_EIGHT);
        drawer.beginRotation(startOrientation, orientation.get(), ccw);
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).wallRotated(this);
        }
    }

    @Override
    public void draw(Batch batch) {
        drawer.draw(batch);
    }

    public abstract void touched(boolean left);

    public boolean destroyed() {
        if (strength == 0)
            return false;
        return hits == strength;
    }

    public float getAngle() {
        return ((RotatableRectangle) bounds).getAngle();
    }

    public void addListener(WallListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WallListener listener) {
        listeners.remove(listener);
    }

}
