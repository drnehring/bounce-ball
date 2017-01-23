package com.david.bounceball;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Created by David on 8/23/2016.
 */
public class OverlapTester {

    public static boolean shapesOverlap(Shape shape1, Shape shape2) {
        if (shape1 instanceof Circle) {
            if (shape2 instanceof Circle) {
                return circlesOverlap((Circle) shape1, (Circle) shape2);
            } else if (shape2 instanceof RotatableRectangle) {
                return circleRotatableRectangleOverlap((Circle) shape1, (RotatableRectangle) shape2);
            } else if (shape2 instanceof Rectangle) {
                return circleRectangleOverlap((Circle) shape1, (Rectangle) shape2);
            }
        } else if (shape1 instanceof RotatableRectangle) {
            if (shape2 instanceof Circle) {
                return circleRotatableRectangleOverlap((Circle) shape2, (RotatableRectangle) shape1);
            }
        } else if (shape1 instanceof Rectangle) {
            if (shape2 instanceof Circle) {
                return circleRectangleOverlap((Circle) shape2, (Rectangle) shape1);
            } else if (shape2 instanceof Rectangle) {
                return rectanglesOverlap((Rectangle) shape1, (Rectangle) shape2);
            }
        }
        return false;
    }

    public static boolean circlesOverlap(Circle c1, Circle c2) {
        float distance = c1.center.dst2(c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }

    public static boolean circleRectangleOverlap(Circle c, Rectangle r) {
        float closestX = c.center.x;
        float closestY = c.center.y;

        if(c.center.x < r.lowerLeft().x) {
            closestX = r.lowerLeft().x;
        }
        else if(c.center.x > r.lowerLeft().x + r.width) {
            closestX = r.lowerLeft().x + r.width;
        }

        if(c.center.y < r.lowerLeft().y) {
            closestY = r.lowerLeft().y;
        }
        else if(c.center.y > r.lowerLeft().y + r.height) {
            closestY = r.lowerLeft().y + r.height;
        }

        return c.center.dst2(closestX, closestY) < c.radius * c.radius;
    }

    public static float circleRectangleYOverlapAmount(Circle c, Rectangle r) {
        float circleHigher = c.center.y + c.radius;
        float circleLower = c.center.y - c.radius;
        float rectangleHigher = r.center.y + r.height / 2;
        float rectangleLower = r.center.y - r.height / 2;
//        if (circleLower > rectangleHigher) {
//            return 0;
//        } else if (circleLower > rectangleLower) {
//            if (circleHigher > rectangleHigher) {
//                return rectangleHigher - circleLower;
//            } else {
//                return c.radius * 2;
//            }
//        } else {
//            if (circleHigher > rectangleHigher) {
//                return r.height;
//            } else if (circleHigher > rectangleLower) {
//                return circleHigher - rectangleLower;
//            } else {
//                return 0;
//            }
//        }
        if (circleLower > rectangleHigher) {
            return 0;
        }
        if (circleHigher < rectangleLower) {
            return 0;
        }
        return Math.min(rectangleHigher - circleLower, circleHigher - rectangleLower);


    }

    public static float circleRotatableRectangleYOverlapAmount(Circle c, RotatableRectangle rr) {
        return circleRectangleYOverlapAmount(c, new Rectangle(rr.center.cpy().sub(c.center).rotateRad(-rr.getAngle()).add(c.center), rr.width, rr.height));
    }

    public static boolean circleRotatableRectangleOverlap(Circle c, RotatableRectangle rr) {
        return circleRectangleOverlap(c, new Rectangle(rr.center.cpy().sub(c.center).rotateRad(-rr.getAngle()).add(c.center), rr.width, rr.height));
    }

    public static boolean rectanglesOverlap(Rectangle r1, Rectangle r2) {
        return r1.lowerLeft().x < r2.lowerLeft().x + r2.width &&
                r1.lowerLeft().x + r1.width > r2.lowerLeft().x &&
                r1.lowerLeft().y < r2.lowerLeft().y + r2.height &&
                r1.lowerLeft().y + r1.height > r2.lowerLeft().y;
    }

    public static boolean pointInShape(Vector2 p, Shape s) {
        if (s instanceof Circle) {
            return pointInCircle(p, (Circle) s);
        } else if (s instanceof  RotatableRectangle) {
            return pointInRotatableRectangle(p, (RotatableRectangle) s);
        } else if (s instanceof Rectangle) {
            return pointInRectangle(p, (Rectangle) s);
        }
        return false;
    }

    public static boolean pointInCircle(Vector2 p, Circle c) {
        return p.dst2(c.center) <= c.radius * c.radius;
    }

    public static boolean pointInRectangle(Vector2 p, Rectangle r) {
        return r.lowerLeft().x <= p.x && r.lowerLeft().x + r.width >= p.x && r.lowerLeft().y <= p.y && r.lowerLeft().y + r.height >= p.y;
    }

    public static boolean pointInRotatableRectangle(Vector2 p, RotatableRectangle rr) {
        return pointInRectangle(p.cpy().sub(rr.center).rotateRad(-rr.getAngle()).add(rr.center), new Rectangle(rr.center, rr.width, rr.height));
    }

    public static boolean pathCrosses(Vector2 pathStart, Vector2 pathEnd, Vector2 point) {
        Vector2 pointToStart = pathStart.cpy().sub(point);
        Vector2 pointToEnd = pathEnd.cpy().sub(point);
        return pointToStart.isCollinearOpposite(pointToEnd, 0.3f);
    }

}

