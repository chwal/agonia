package com.agonia.game.entity;

public class Bullet {
    private float x;
    private float y;
    private float directionX;
    private float directionY;
    private float velocity;

    public Bullet(float x, float y, float directionX, float directionY, float velocity) {
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.velocity = velocity;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDirectionX() {
        return directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelocity() {
        return velocity;
    }
}
