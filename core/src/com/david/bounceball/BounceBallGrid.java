package com.david.bounceball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.david.bounceball.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by David on 9/2/2016.
 */
public class BounceBallGrid {

    private static final int MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE = 15;

    public float levelX;
    public float levelY;

    private GameObject[][][] grid;
    private int width;
    private int height;
    private List<Ball> balls;
    private List<Wall> walls;
    private List<Goal> goals;
    private List<Key> keys;
    private List<SpikeBall> spikeBalls;


    public BounceBallGrid(int width, int height, float levelX, float levelY) {
        this.width = width;
        this.height = height;
        this.levelX = levelX;
        this.levelY = levelY;
        grid = new GameObject[width][height][MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE]; //Balls are not stored in the grid
        balls = new ArrayList<Ball>();
        walls = new ArrayList<Wall>();
        goals = new ArrayList<Goal>();
        keys = new ArrayList<Key>();
        spikeBalls = new ArrayList<SpikeBall>();
    }

    public void update(float deltaTime) {
        List<SpinningWall> spinningWalls = getSpinningWalls();
        for (int i = 0; i < balls.size(); i++)
            balls.get(i).update(deltaTime);
        for (int i = 0; i < spinningWalls.size(); i++)
            spinningWalls.get(i).spin(deltaTime);
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).destroyed())
                remove(walls.get(i));
        }
    }

    public void draw(Batch batch) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE; k++) {
                    if (grid[i][j][k] != null)
                        grid[i][j][k].draw(batch);
                }
            }
        }
    }

    public void touchDown(int gridX, int gridY, boolean ccw) {
        GameObject[] objects = nonBallObjectsAt(gridX, gridY);
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Wall)
                ((Wall) objects[i]).touched(ccw);
        }
    }

    public void ballLaunched() {
        List<BasicWall> basicWalls = getBasicWalls();
        for (BasicWall basicWall : basicWalls) {
            if (basicWall.becomesFixed())
                basicWall.ballLaunched();
        }

    }

    public boolean add(GameObject object) {
        if (object instanceof Ball) {
            balls.add((Ball) object);
            ((Ball) object).setGrid(this);
        } else {
            int index = getEarliestAvailableIndex(object.gridX, object.gridY);
            if (index == -1)
                return false;
            grid[object.gridX][object.gridY][index] = object;
            if (object instanceof Wall) {
                walls.add((Wall) object);
            } else if (object instanceof Goal) {
                goals.add((Goal) object);
            } else if (object instanceof Key) {
                keys.add((Key) object);
            } else if (object instanceof  SpikeBall) {
                spikeBalls.add((SpikeBall) object);
            }
        }
        return true;
    }

    public boolean remove(GameObject object) {
        if (object instanceof Ball) {
            balls.remove(object);
        } else {
            int index = getIndex(object);
            if (index == -1)
                return false;
            grid[object.gridX][object.gridY][index] = null;
            if (object instanceof Wall) {
                walls.remove(object);
            } else if (object instanceof Goal) {
                goals.remove(object);
            } else if (object instanceof Key) {
                keys.remove(object);
            } else if (object instanceof SpikeBall) {
                spikeBalls.remove(object);
            }
        }
        return true;
    }

    public GameObject[] nonBallObjectsAt(int gridX, int gridY) {
        if (inBounds(gridX, gridY)) {
            return grid[gridX][gridY];
        }
        return new GameObject[0];
    }

    public Wall wallAt(int gridX, int gridY) {
        if (inBounds(gridX, gridY)) {
            for (int i = 0; i < MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE; i++) {
                if (grid[gridX][gridY][i] instanceof Wall)
                    return (Wall) grid[gridX][gridY][i];
            }
        }
        return null;
    }

    public SpikeBall spikeBallAt(int gridX, int gridY) {
        if (inBounds(gridX, gridY)) {
            for (int i = 0; i < MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE; i++) {
                if (grid[gridX][gridY][i] instanceof SpikeBall)
                    return (SpikeBall) grid[gridX][gridY][i];
            }
        }
        return null;
    }

    public Wall wallAt(int gridX, int gridY, int[] orientationOffset) {
        return wallAt(gridX + orientationOffset[0], gridY + orientationOffset[1]);
    }

    public SpikeBall spikeBallAt(int gridX, int gridY, int[] orientationOffset) {
        return spikeBallAt(gridX + orientationOffset[0], gridY + orientationOffset[1]);
    }

    public boolean inBounds(int gridX, int gridY) {
        return gridX >= 0 && gridX < width && gridY >= 0 && gridY < height;
    }

    private int getEarliestAvailableIndex(int gridX, int gridY) {
        for (int i = 0; i < MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE; i++) {
            if (grid[gridX][gridY][i] == null)
                return i;
        }
        return -1;
    }

    private int getIndex(GameObject object) {
        for (int i = 0; i < MAX_NON_BALL_GAMEOBJECTS_PER_SQUARE; i++) {
            if (grid[object.gridX][object.gridY][i] == object)
                return i;
        }
        return -1;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<BasicWall> getBasicWalls() {
        List<BasicWall> basicWalls = new ArrayList<BasicWall>();
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i) instanceof BasicWall)
                basicWalls.add((BasicWall) walls.get(i));
        }
        return basicWalls;
    }

    public List<ReactionWall> getReactionWalls() {
        List<ReactionWall> reactionWalls = new ArrayList<ReactionWall>();
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i) instanceof ReactionWall)
                reactionWalls.add((ReactionWall) walls.get(i));
        }
        return reactionWalls;
    }

    public List<FixedWall> getFixedWalls() {
        List<FixedWall> fixedWalls = new ArrayList<FixedWall>();
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i) instanceof FixedWall)
                fixedWalls.add((FixedWall) walls.get(i));
        }
        return fixedWalls;
    }

    public List<SpinningWall> getSpinningWalls() {
        List<SpinningWall> spinningWalls = new ArrayList<SpinningWall>();
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i) instanceof SpinningWall)
                spinningWalls.add((SpinningWall) walls.get(i));
        }
        return spinningWalls;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<SpikeBall> getSpikeBalls() {
        return spikeBalls;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
