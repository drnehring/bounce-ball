package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.david.bounceball.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 8/21/2016.
 */
public class Ball extends SingleSpriteGameObject implements WallListener {


    //Will all become private static once value is finalized
    private static final float RADIUS = 0.25f;
    private static final float RADIUS_X = 0.1f;
    public static float RADIUS_VARIABLE = 0.1f;
    public static float SPEED = 3.4f;
    private static final float ACCELERATION_INTERVAL = 0.1f;
    public static float BOUNCE_LINE_ENTRY_DISTANCE = 0.6f; //Do not exceed 1.5f
    public static float COLLISION_OFFSET = 0.04f;
    public static float ARROW_X_OFFSET = -0.01f;
    public static float ARROW_WIDTH = 0.35f;
    public static float ARROW_HEIGHT = 0.19f;
    public static boolean ADJUST_SPEED = true;

    private Orientation orientation;
    private Goal goal;
    private BounceBallGrid grid;
    private States state;
    private Vector2 previousLogicalCenter = new Vector2();
    private Vector2 logicalCenter = new Vector2(); //The ball's center unaltered by the bounceLine
    private float accelerationTime;
    private Interpolation interpolation;
    private Vector2 justHitWallBoundsCenter = null;
    private Wall approachedWall;
    /*
      The lineType and bounceLine variables relate to the way a ball's bounce off a wall
      is altered to appear more realistic
    */
    private LineType lineType;
    private Vector2 bounceLineStartPoint = new Vector2();
    private Vector2 bounceLineEndPoint = new Vector2();
    private float bounceLineDistanceShorteningFactor;
    private Vector2 bounceLineUnitMovementDirection = new Vector2();
    private boolean onBounceLine;

    private boolean hittingWall;
    private Sprite arrow;

    enum States {
        UNLAUNCHED,
        MOVING,
        FINISHED,
        SMASHED
    }

    private enum LineType {
        ENTERING,
        EXITING,
        IN_BETWEEN,
    }


    public static Ball Factory(float levelX, float levelY, String ballData, List<Goal> goals) {
        GameObject gameObject = GameObject.Factory(levelX, levelY, ballData);
        String[] ballDataSplit = ballData.split(" ");
        if (ballDataSplit.length == 3) {
            return new Ball(levelX, levelY, gameObject.gridX, gameObject.gridY, Integer.parseInt(ballDataSplit[2]), goals.get(0));
        } else if (ballDataSplit.length == 4) {
            return new Ball(levelX, levelY, gameObject.gridX, gameObject.gridY, Integer.parseInt(ballDataSplit[2]), goals.get(Integer.parseInt(ballDataSplit[3])));
        }
        return null;
    }

    public Ball(float levelX, float levelY, int gridX, int gridY, int orientation, Goal goal) {
        super(levelX, levelY, gridX, gridY);
        this.orientation = new Orientation(orientation);
        this.goal = goal;
        bounds = new Circle(bounds.center, 0);
        logicalCenter.set(bounds.center);
        state = States.UNLAUNCHED;
        accelerationTime = -1;
        interpolation = Interpolation.linear;
        onBounceLine = false;
        setSprite();
        setArrowSprite();
    }

    @Override
    protected void setSpriteBounds() {
        float radius = 0;
        if (Assets.getBallStyle().equals("_out")) {
            radius = RADIUS;
        } else if (Assets.getBallStyle().equals("_in") || Assets.getBallStyle().equals("_in_fancy")) {
            radius = RADIUS_X;
        } else if (Assets.getBallStyle().equals("_var")) {
            radius = RADIUS_VARIABLE;
        }
        Circle c = (Circle) bounds;
        c.radius = radius;
        sprite.setBounds(c.center.x - c.radius, c.center.y - c.radius, c.radius * 2, c.radius * 2);
        sprite.setRotation(0);
        sprite.setOriginCenter();
    }

    @Override
    protected String getSpriteMainName() {
        return "ball_" + COLORS[goal.getGoalNumber()];
    }

    private void setArrowSprite() {
        arrow = Assets.getSprite("arrow_" + Goal.COLORS[goal.getGoalNumber()]);
        Vector2 offset = new Vector2(((Circle) bounds).radius + ARROW_X_OFFSET + ARROW_WIDTH / 2, 0);
        offset.rotate(orientation.get() * 22.5f);
        arrow.setBounds(offset.x + bounds.center.x - ARROW_WIDTH / 2, offset.y + bounds.center.y - ARROW_HEIGHT / 2, ARROW_WIDTH, ARROW_HEIGHT);
        arrow.setOriginCenter();
        arrow.setRotation(orientation.get() * 22.5f);
    }

    public void setGrid(BounceBallGrid grid) {
        this.grid = grid;
    }

    public void launch() {
        state = States.MOVING;
        accelerationTime = 0;
        grid.ballLaunched();
        setApproachedWall();
    }

    public void update(float deltaTime) {
        int newGridX;
        int newGridY;
        float speed = SPEED;
        if (state == States.MOVING) {
            //The ball starts out by accelerating to its top speed
            if (accelerationTime != -1) {
                if (accelerationTime / ACCELERATION_INTERVAL < 1) {
                    speed = interpolation.apply(0, SPEED, accelerationTime / ACCELERATION_INTERVAL);
                    accelerationTime += deltaTime;
                } else {
                    accelerationTime = -1;
                }
            }

            previousLogicalCenter.set(logicalCenter);

            float deltaDistance = speed * deltaTime;
            if (onBounceLine && ADJUST_SPEED)
                deltaDistance *= bounceLineDistanceShorteningFactor;
            logicalCenter.add(orientation.asUnitVector().scl(deltaDistance));

            if (onBounceLine) {
                if (lineType == LineType.ENTERING || lineType == LineType.IN_BETWEEN) {
                    if (OverlapTester.pathCrosses(previousLogicalCenter, logicalCenter, approachedWall.bounds.center)) {
                        hitWallMiddle();
                    } else {
                        setBounceLineBoundsCenter();
                    }
                } else if (lineType == LineType.EXITING) {
                    if (logicalCenter.dst2(justHitWallBoundsCenter) > BOUNCE_LINE_ENTRY_DISTANCE * BOUNCE_LINE_ENTRY_DISTANCE) {
                        exitBounceLine();
                    } else {
                        setBounceLineBoundsCenter();
                    }
                }
            } else {
                if (approachedWall == null) {
                    bounds.center.set(logicalCenter);
                } else {
                    if (bounds.center.dst2(approachedWall.bounds.center) < BOUNCE_LINE_ENTRY_DISTANCE * BOUNCE_LINE_ENTRY_DISTANCE) {
                        lineType = LineType.ENTERING;
                        setBounceLine(false);
                    } else {
                        bounds.center.set(logicalCenter);
                    }
                }
            }

            sprite.setPosition(bounds.center.x - ((Circle) bounds).radius, bounds.center.y - ((Circle) bounds).radius);

            newGridX = (int) (logicalCenter.x - grid.levelX);
            newGridY = (int) (logicalCenter.y - grid.levelY);
            int[] intVector = orientation.asIntVector();

            //If on the offchance we end up moving a square we shouldn't be be in, we ignore that square
            if (newGridX == gridX + intVector[0] && newGridY == gridY + intVector[1]) {
                gridX = newGridX;
                gridY = newGridY;
                setApproachedWall();
            }

        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if (state == States.UNLAUNCHED)
            arrow.draw(batch);
    }

    private void setApproachedWall() {
        approachedWall = null;
        if (approachingCenter()) {
            approachedWall = grid.wallAt(gridX, gridY);
        }
        if (approachedWall == null) {
            approachedWall = grid.wallAt(gridX, gridY, orientation.asIntVector());
        }
    }


    private void setBounceLine(boolean reset) {
        setBounceLinePoints(reset);
        setBounceLineBoundsCenter();
        onBounceLine = true;
        if (!reset) { //If we're resetting, we are already listening to approached wall if we should be
            if (lineType == LineType.ENTERING || lineType == lineType.IN_BETWEEN)
                approachedWall.addListener(this);
        }
    }

    private void setBounceLinePoints(boolean reset) {
        if (!reset) {
            //Resetting does not require resetting bounceLineStartPoint
            if (lineType == LineType.ENTERING) {
                bounceLineStartPoint.set(approachedWall.bounds.center).add(orientation.cpy().flip().asUnitVector().scl(BOUNCE_LINE_ENTRY_DISTANCE));
            } else if (lineType == LineType.EXITING || lineType == LineType.IN_BETWEEN) {
                bounceLineStartPoint.set(bounceLineEndPoint);
            }
        }
        if (lineType == LineType.ENTERING  || lineType == LineType.IN_BETWEEN) {
            if (orientation.oppositeOrEquals(approachedWall.orientation)) {
                bounceLineEndPoint.set(approachedWall.bounds.center);
            } else {
                /*
                    This is where we set a ball's altered path as it bounces off a wall.  Instead
                    of heading straight to the wall's center and making a turn as soon as it hits
                    it, the ball heads to a point at a offset perpendicular to the wall's orientation.
                    The offset points is on the side of the wall that the ball it on
                 */
                Vector2 offset = new Vector2(COLLISION_OFFSET, 0);
                Orientation wallOrientionFlip = approachedWall.orientation.cpy().flip();
                if (orientation.between(approachedWall.orientation, wallOrientionFlip)) {
                    offset.rotate(approachedWall.orientation.cpy().add(12).get() * 22.5f);
                } else if (orientation.between(wallOrientionFlip, approachedWall.orientation)) {
                    offset.rotate(approachedWall.orientation.cpy().add(4).get() * 22.5f);
                }
                bounceLineEndPoint.set(approachedWall.bounds.center).add(offset);
            }
        } else {
            bounceLineEndPoint.set(justHitWallBoundsCenter).add(orientation.asUnitVector().scl(BOUNCE_LINE_ENTRY_DISTANCE));
        }
        /*
            A bounce line is slightly shorter that a ball's normal path, so we set this variable
            to alter the ball's speed to make it constant
         */

        if (lineType == LineType.ENTERING) {
            bounceLineDistanceShorteningFactor = bounceLineEndPoint.dst(bounceLineStartPoint) / approachedWall.bounds.center.dst(bounceLineStartPoint);
        } else if (lineType == LineType.EXITING) {
            bounceLineDistanceShorteningFactor = bounceLineEndPoint.dst(bounceLineStartPoint) / justHitWallBoundsCenter.dst(bounceLineEndPoint);
        } else {
            bounceLineDistanceShorteningFactor = bounceLineEndPoint.dst(bounceLineStartPoint) / (approachedWall.bounds.center.dst(justHitWallBoundsCenter));
        }
        bounceLineUnitMovementDirection.set(bounceLineEndPoint).sub(bounceLineStartPoint).nor();
    }


    private void setBounceLineBoundsCenter() {
        if (lineType == LineType.ENTERING) {
            bounds.center.set(bounceLineUnitMovementDirection).scl(logicalCenter.dst(bounceLineStartPoint) * bounceLineDistanceShorteningFactor).add(bounceLineStartPoint);
        } else {
            bounds.center.set(bounceLineUnitMovementDirection).scl(logicalCenter.dst(justHitWallBoundsCenter) * bounceLineDistanceShorteningFactor).add(bounceLineStartPoint);
        }
    }

    private void exitBounceLine() {
        justHitWallBoundsCenter = null;
        bounds.center.set(logicalCenter);
        onBounceLine = false;
    }

    public void hitWallMiddle() {
        int originalOrientation = approachedWall.orientation.get();
        /*
            hittingWall is set purely so we don't execute the code in wallRotated when we
            hit a ReactionWall
        */
        hittingWall = true;
        approachedWall.hit();
        hittingWall = false;
        if (approachedWall != null) { //Will be null if we destroyed the wall.
            orientation.reflect(originalOrientation);
            float overshoot = logicalCenter.dst(approachedWall.bounds.center);
            logicalCenter.set(approachedWall.bounds.center).add(orientation.asUnitVector().scl(overshoot));
            approachedWall.removeListener(this);
            justHitWallBoundsCenter = new Vector2(approachedWall.bounds.center);
            setApproachedWall();
            if (approachedWall == null) {
                lineType = LineType.EXITING;
                setBounceLine(false);
            } else {
                lineType = LineType.IN_BETWEEN;
                setBounceLine(false);
            }
        }
    }

    public void hitWallEdge() {
        Smash();
    }

    public void hitBall() {
        Smash();
    }

    public void hitSpike() {Smash(); }

    private void Smash() {
        state = States.SMASHED;
    }

    public void hitGoal(Goal goal) {
        if (goal == this.goal) {
            if (goal.isOpen()) {
                bounds.center.set(goal.bounds.center);
                sprite.setPosition(bounds.center.x - ((Circle) bounds).radius, bounds.center.y - ((Circle) bounds).radius);
                state = States.FINISHED;
            }
        }
    }

    @Override
    public void wallRotated(Wall wall) {
        if (!hittingWall) {
            setBounceLine(true);
        }
    }

    @Override
    public void wallDestroyed(Wall wall) {
        approachedWall.removeListener(this);
        approachedWall = null;
        exitBounceLine();
    }

    //Returns whether we are approaching the center of the current square we are in
    public boolean approachingCenter() {
        float toCenterX = gridX + grid.levelX + 0.5f - logicalCenter.x;
        float toCenterY = gridY + grid.levelY + 0.5f - logicalCenter.y;
        int toCenterSignumX;
        int toCenterSignumY;
        if (Math.abs(toCenterX) < 0.00005f) {
            toCenterSignumX = 0;
        } else {
            toCenterSignumX = toCenterX > 0 ? 1 : -1;
        }
        if (Math.abs(toCenterY) < 0.00005f) {
            toCenterSignumY = 0;
        } else {
            toCenterSignumY = toCenterY > 0 ? 1 : -1;
        }
        int[] intVector = orientation.asIntVector();
        return toCenterSignumX == intVector[0] && toCenterSignumY == intVector[1];
    }

    public int getOrientationFromCenter() {
        if (approachingCenter()) {
            return orientation.cpy().flip().get();
        } else {
            return orientation.get();
        }
    }

    public int getOrientationFromCenterFlip() {
        if (approachingCenter()) {
            return orientation.get();
        } else {
            return orientation.cpy().flip().get();
        }
    }

    public States getState () {
        return state;
    }

    public Orientation getOrientation() {return orientation;}

    public Vector2 getLogicalCenter() {
        return logicalCenter;
    }

    public Vector2 getPreviousLogicalCenter() {
        return previousLogicalCenter;
    }

}
