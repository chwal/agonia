package com.agonia.game.gameobject;

import com.badlogic.gdx.graphics.Texture;

public class GameObject {
    private Texture texture;

    public GameObject(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
