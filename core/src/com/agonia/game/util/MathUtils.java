package com.agonia.game.util;


import java.awt.geom.Point2D;

public class MathUtils {
    public static Point2D angleToDirection(float angle) {
        double theta = Math.toRadians(angle - 90);
        Point2D.Float direction = new Point2D.Float(0,0);
        direction.setLocation((float) Math.cos(theta), (float) Math.sin(theta));

        return direction;
    }

    public static float getAngle(Point2D origin, Point2D target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX()));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
}
