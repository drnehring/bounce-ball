package com.david.bounceball;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;

        import java.util.List;

/**
 * Created by David on 8/21/2016.
 */
public class Key extends OffsetableGameObject {

    private static final float BOUNDS_RADIUS = 0.1f;
    private static final float SPRITE_RADIUS = 0.17f;
    private Goal goal;

    public static Key Factory(float levelX, float levelY, String keyData, List<Goal> goals) {
        GameObject gameObject = GameObject.Factory(levelX, levelY, keyData);
        String[] keyDataSplit = keyData.split(" ");
        if (keyDataSplit.length == 2) {
            return new Key(levelX, levelY, gameObject.gridX, gameObject.gridY, goals.get(0));
        } else if (keyDataSplit.length == 3) {
            return new Key(levelX, levelY, gameObject.gridX, gameObject.gridY, goals.get(Integer.parseInt(keyDataSplit[2])));
        } else if (keyDataSplit.length == 4) {
            return new Key(levelX, levelY, gameObject.gridX, gameObject.gridY, goals.get(Integer.parseInt(keyDataSplit[2])), Integer.parseInt(keyDataSplit[3]));
        }
        return null;
    }

    public Key(float levelX, float levelY, int gridX, int gridY, Goal goal) {
        this(levelX, levelY, gridX, gridY, goal, -1);
    }

    public Key(float levelX, float levelY, int gridX, int gridY, Goal goal, int offset) {
        super(levelX, levelY, gridX, gridY, offset);
        this.goal = goal;
        bounds = new Circle(bounds.center, BOUNDS_RADIUS);
        setSprite();
    }

    @Override
    protected String getSpriteMainName() {
        return "key_" + COLORS[goal.getGoalNumber()];
    }

    @Override
    protected void setSpriteBounds() {
        Circle c = (Circle) bounds;
        sprite.setBounds(c.center.x - SPRITE_RADIUS, c.center.y - SPRITE_RADIUS, SPRITE_RADIUS * 2, SPRITE_RADIUS * 2);
        sprite.setRotation(0);
        sprite.setOriginCenter();
    }

    public void hit() {
        goal.keyHit();
    }

}
