package com.david.bounceball;

/**
 * Created by David on 8/21/2016.
 */
public class Goal extends OffsetableGameObject {

    private static final float BOUNDS_RADIUS = 0.13f;
    private static final float BOUNDS_RADIUS_X = 0.2f;
    private static final float SPRITE_RADIUS = 0.17f;
    private static final float SPRITE_RADIUS_X = 0.25f;
    private int keysRemaining;
    private String lockedTexture;
    private String unlockedTexture;
    private int goalNumber;

    public static Goal Factory(float levelX, float levelY, String goalData, int goalNumber) {
        GameObject gameObject =  GameObject.Factory(levelX, levelY, goalData);
        String[] goalDataSplit = goalData.split(" ");
        if (goalDataSplit.length == 2) {
            return new Goal(levelX, levelY, gameObject.gridX, gameObject.gridY, goalNumber);
        } else if (goalDataSplit.length == 3) {
            return new Goal(levelX, levelY, gameObject.gridX, gameObject.gridY, goalNumber, Integer.parseInt(goalDataSplit[2]));
        }else if (goalDataSplit.length == 4) {
            return new Goal(levelX, levelY, gameObject.gridX, gameObject.gridY, goalNumber, Integer.parseInt(goalDataSplit[2]), Integer.parseInt(goalDataSplit[3]));
        }
        return null;
    }

    public Goal(float levelX, float levelY, int gridX, int gridY, int goalNumber) {
        this(levelX, levelY, gridX, gridY, goalNumber, 0);
    }

    public Goal(float levelX, float levelY, int gridX, int gridY, int goalNumber, int keysRemaining) {
        this(levelX, levelY, gridX, gridY, goalNumber, keysRemaining, -1);
    }

    public Goal(float levelX, float levelY, int gridX, int gridY, int goalNumber, int keysRemaining, int offset) {
        super(levelX, levelY, gridX, gridY, offset);
        this.keysRemaining = keysRemaining;
        if (Assets.getGoalStyle().equals("_out")) {
            bounds = new Circle(bounds.center, BOUNDS_RADIUS);
        } else {
            bounds = new Circle(bounds.center, BOUNDS_RADIUS_X);
        }
        this.goalNumber = goalNumber;
        lockedTexture = "goal_" + GameObject.COLORS[goalNumber] + "_locked";
        unlockedTexture = "goal_" + GameObject.COLORS[goalNumber];
        setSprite();
    }

    @Override
    protected String getSpriteMainName() {
        return isOpen()? unlockedTexture : lockedTexture;
    }

    @Override
    protected void setSpriteBounds() {
        float radius = 0;
        if (Assets.getGoalStyle().equals("_out")) {
            radius = SPRITE_RADIUS;
        } else if (Assets.getGoalStyle().equals("_in")) {
            radius = SPRITE_RADIUS_X;
        }
        Circle c = (Circle) bounds;
        sprite.setBounds(c.center.x - radius, c.center.y - radius, radius * 2, radius * 2);
        sprite.setRotation(0);
        sprite.setOriginCenter();
    }

    public void keyHit() {
        keysRemaining -= 1;
        setSprite();
    }

    public boolean isOpen() {
        return keysRemaining == 0;
    }

    public int getGoalNumber() {
        return goalNumber;
    }

}
