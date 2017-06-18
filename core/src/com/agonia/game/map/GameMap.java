package com.agonia.game.map;

import com.agonia.game.entity.Entity;
import com.agonia.game.input.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap {
    private static final int TILE_SIZE = 40;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public void initialize() {
        map = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        renderer.setView(camera);
    }

    public void render() {
        renderer.render();
    }

    public void moveEntity(Entity entity, Direction direction) {
        float newX = entity.getX();
        float newY = entity.getY();

        float velocity = entity.getVelocity();
        float distanceCovered = velocity * Gdx.graphics.getDeltaTime();

        switch (direction) {
            case NORTH:
                newY = newY + distanceCovered;
                break;
            case EAST:
                newX = newX + distanceCovered;
                break;
            case WEST:
                newX = newX - distanceCovered;
                break;
            case SOUTH:
                newY = newY - distanceCovered;
                break;
        }

        entity.setX(newX);
        entity.setY(newY);
    }

    public void dispose() {
        map.dispose();
    }
}
