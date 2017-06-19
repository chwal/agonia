package com.agonia.game.item;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private Texture texture;
    private float xOffset;
    private float yOffset;

    public Item(Texture texture, float xOffset, float yOffset) {
        this.texture = texture;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
