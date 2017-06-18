package com.agonia.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity{
    Player(float x, float y, Animation<TextureRegion> animation) {
        super(x, y, 300.0f, animation);
    }
}
