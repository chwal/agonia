package com.agonia.game.interior;

import java.util.HashSet;
import java.util.Set;

public class Interior {
    private float x, y, width, height;
    private Set<InteriorTile> interiorTiles;

    public Interior(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.interiorTiles = new HashSet<>();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Set<InteriorTile> getInteriorTiles() {
        return interiorTiles;
    }
}
