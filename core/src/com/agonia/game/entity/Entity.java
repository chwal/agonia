package com.agonia.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Entity {
    private float x;
    private float y;
    private float velocity;
    private Animation<TextureRegion> animation;

    Entity(float x, float y, Animation<TextureRegion> animation) {
        this.x = x;
        this.y = y;
        this.velocity = 100.0f;
        this.animation = animation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setY(float y) {
        this.y = y;
    }
}
