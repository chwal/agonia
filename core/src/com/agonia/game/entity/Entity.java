package com.agonia.game.entity;

import com.agonia.game.input.Direction;
import com.agonia.game.item.Item;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private float x;
    private float y;
    private float velocity;
    private boolean moving;
    private Direction facing;
    private Animation<TextureRegion> animation;
    private List<Item> items;

    Entity(float x, float y, float velocity, Animation<TextureRegion> animation) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.animation = animation;
        this.moving = false;
        this.facing = Direction.EAST;
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    boolean isMoving() {
        return moving;
    }

    Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
