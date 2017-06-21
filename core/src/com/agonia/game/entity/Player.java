package com.agonia.game.entity;

import com.agonia.game.item.Gun;
import com.agonia.game.item.Item;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity{
    private float yaw;

    Player(float x, float y, Animation<TextureRegion> animation) {
        super(x, y, 150.0f, animation);
        this.yaw = 90.0f;
    }

    public Gun getPrimaryGun() {
        for (Item item : items) {
            if(item instanceof Gun)
                return (Gun) item;
        }

        return null;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
