package com.agonia.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity{
    private float yaw;

    Player(float x, float y, Animation<TextureRegion> animation) {
        super(x, y, 150.0f, animation);
        this.yaw = 90.0f;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
