package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.david.bounceball.screens.GameScreen;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 8/21/2016.
 */
public class Level extends Actor {

    public static final int LINES_PER_LEVEL = 6;

    public float x;
    public float y;
    public int gridWidth;
    public int gridHeight;
    States state;
    GameScreen gameScreen;
    BounceBallGrid grid;

    enum States {
        PLAYING,
        WON,
        LOST
    }

    public static Level Factory(int levelNumber, GameScreen gameScreen) {
        Vector2 position;
        String[] levelData = Assets.levelDataSplit(levelNumber);

        int gridWidth = Integer.parseInt(levelData[0].substring(0, 2));
        int gridHeight = Integer.parseInt(levelData[0].substring(3, 5));

        position = gameScreen.setLevelBounds(gridWidth, gridHeight);

        BounceBallGrid grid = new BounceBallGrid(gridWidth, gridHeight, position.x, position.y);

        List<Ball> balls = new ArrayList<Ball>();
        List<Goal> goals = new ArrayList<Goal>();

        String[] split = levelData[1].split(",");
        //Goals instantiated first because Balls and Keys require them
        for (int i = 0; i < split.length; i++) {
            Goal goal = Goal.Factory(position.x, position.y, split[i], i);
            goals.add(goal);
            grid.add(goal);
        }

        split = levelData[2].split(",");
        for (int i = 0; i < split.length; i++) {
            Ball ball = Ball.Factory(position.x, position.y, split[i], goals);
            balls.add(ball);
            grid.add(ball);
        }

        if (!levelData[3].equals("_")) {
            split = levelData[3].split(",");
            for (int i = 0; i < split.length; i++) {
                Wall wall = Wall.Factory(position.x, position.y, split[i]);
                grid.add(wall);
            }
        }

        Wall.makeLinks(); /*
                            Links require instantiated walls, so this can only be done after
                            all walls have been instantiated
                            */

        if (!levelData[4].equals("_")) {
            split = levelData[4].split(",");
            for (int i = 0; i < split.length; i++) {
                Key key = Key.Factory(position.x, position.y, split[i], goals);
                grid.add(key);
            }
        }

        if (!levelData[5].equals("_")) {
            split = levelData[5].split(",");
            for (int i = 0; i < split.length; i++) {
                SpikeBall spikeBall = SpikeBall.Factory(position.x, position.y, split[i]);
                grid.add(spikeBall);
            }
        }

        return new Level(position.x, position.y, gridWidth, gridHeight, gameScreen, grid);
    }

    public Level(float x, float y, int gridWidth, int gridHeight, GameScreen gameScreen, BounceBallGrid grid) {
        this.x = x;
        this.y = y;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.gameScreen = gameScreen;
        this.grid = grid;
        state = States.PLAYING;
        setBounds(x, y, gridWidth, gridHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        grid.update(delta);
        checkCollisions();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        List<Ball> balls = grid.getBalls();
        for(int i = 0; i < balls.size(); i++) {
            balls.get(i).draw(batch);
        }
        grid.draw(batch);
    }

    public void touchDown(Vector2 touchPoint, boolean left) {
        int gridX = (int) (touchPoint.x - x);
        int gridY = (int) (touchPoint.y - y);
        List<Ball> balls = grid.getBalls();
        for(int i = 0; i < balls.size(); i++) {
            if (balls.get(i).getState() == Ball.States.UNLAUNCHED) {
                if(balls.get(i).gridX == gridX && balls.get(i).gridY == gridY) {
                    balls.get(i).launch();
                    return;
                }
            }
        }
        grid.touchDown(gridX, gridY, left);
    }

    @Override
    public boolean fire(Event event) {
        super.fire(event);
        if (event instanceof InputEvent) {
            gameScreen.levelTouched((InputEvent) event);
            return true;
        }
        return false;
    }

    //Checks all collisions except wallMiddleCollisions, which due to complications is handled by the ball
    private void checkCollisions() {
        checkBallCollisions();
        List<Ball> balls = grid.getBalls();
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            if (ball.getState() == Ball.States.MOVING) {
                GameObject[] objects = grid.nonBallObjectsAt(ball.gridX,ball.gridY);
                for (int j = 0; j < objects.length; j++) {
                    GameObject object = objects[j];
                    if (object instanceof Wall) {
                        Wall wall = (Wall) object;
                        if (ball.getOrientation().oppositeOrEquals(wall.orientation)) {
                            if (ball.approachingCenter()) {
                                if (OverlapTester.shapesOverlap(ball.bounds, wall.bounds)) {
                                    ball.hitWallEdge();
                                    state = States.LOST;
                                }
                            }
                        }
                    } else if (object instanceof Goal) {
                        Goal goal = (Goal) object;
                        if (OverlapTester.pathCrosses(ball.getPreviousLogicalCenter(), ball.getLogicalCenter(), goal.bounds.center)) {
                            ball.hitGoal(goal);
                            checkGameEnd();
                        }
                    } else if (object instanceof Key) {
                        Key key = (Key) object;
                        if (key.getOffsetOrientation() == -1) {
                            if (OverlapTester.shapesOverlap(ball.bounds, key.bounds)) {
                                key.hit();
                                grid.remove(key);
                            }
                        } else {
                            if (ball.getOrientationFromCenter() == key.getOffsetOrientation()) {
                                if (OverlapTester.shapesOverlap(ball.bounds, key.bounds)) {
                                    key.hit();
                                    grid.remove(key);
                                }
                            }
                        }
                    } else if (object instanceof SpikeBall) {
                        Spike spike = ((SpikeBall) object).getSpike(ball.getOrientationFromCenter());
                        if (spike != null) {
                            if (OverlapTester.shapesOverlap(ball.bounds, spike.bounds)) {
                                ball.hitSpike();
                                state = States.LOST;
                            }
                        }
                    }
                }

                Wall nextSquareWall = grid.wallAt(ball.gridX, ball.gridY, ball.getOrientation().asIntVector());
                if (nextSquareWall != null) {
                    if (nextSquareWall.orientation.oppositeOrEquals(ball.getOrientation())) {
                        if (OverlapTester.shapesOverlap(ball.bounds, nextSquareWall.bounds)) {
                            ball.hitWallEdge();
                            state = States.LOST;
                        }
                    }
                }
                SpikeBall nextSpikeBall = grid.spikeBallAt(ball.gridX, ball.gridY, ball.getOrientation().asIntVector());
                if (nextSpikeBall != null) {
                    Spike spike = nextSpikeBall.getSpike(ball.getOrientationFromCenterFlip());
                    if (spike != null) {
                        if (OverlapTester.shapesOverlap(ball.bounds, spike.bounds)) {
                            ball.hitSpike();
                            state = States.LOST;
                        }
                    }
                }
            }
        }
    }

    private void checkBallCollisions() {
        Ball ball1;
        Ball ball2;
        List<Ball> balls = grid.getBalls();
        for(int i = 0; i < balls.size(); i++) {
            for(int j = i + 1; j < balls.size(); j++) {
                ball1 = balls.get(i);
                ball2 = balls.get(j);
                if ((ball1.getState() == Ball.States.MOVING || ball1.getState() == Ball.States.UNLAUNCHED) && (ball2.getState() == Ball.States.MOVING || ball2.getState() == Ball.States.UNLAUNCHED)) {
                    if (ball1.gridX == ball2.gridX && ball1.gridY == ball2.gridY) {
                        Wall wall = grid.wallAt(ball1.gridX, ball1.gridY);
                        if (wall != null) {
                            if (wall.orientation.between(ball1.getOrientationFromCenter(), ball2.getOrientationFromCenter()) || wall.orientation.cpy().flip().between(ball1.getOrientationFromCenter(), ball2.getOrientationFromCenter()))
                                continue;
                        }
                    }
                    if (OverlapTester.shapesOverlap(ball1.bounds, ball2.bounds)) {
                        ball1.hitBall();
                        ball2.hitBall();
                    }
                }
            }
        }
    }

    public void checkGameEnd() {
        List<Ball> balls = grid.getBalls();
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).getState() != Ball.States.FINISHED)
                return;
        }
        state = States.WON;
        gameScreen.levelWon();
    }

    public BounceBallGrid getGrid() {
        return grid;
    }

}
