package com.agonia.game.interior;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class InteriorTile {
    private float x, y;
    private TiledMapTile tile;

    public InteriorTile(float x, float y, TiledMapTile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TiledMapTile getTile() {
        return tile;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        InteriorTile that = (InteriorTile) o;

        if(Float.compare(that.x, x) != 0) return false;
        return Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }
}
