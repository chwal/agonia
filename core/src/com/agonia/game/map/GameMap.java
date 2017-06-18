package com.agonia.game.map;

import com.agonia.game.entity.Entity;
import com.agonia.game.entity.Player;
import com.agonia.game.input.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap {
    private static final int TILE_SIZE = 40;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public void initialize(OrthographicCamera camera) {
        this.camera = camera;
        tiledMap = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render() {
        renderer.setView(camera);
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
                if(entity instanceof Player) {
                    camera.position.y += distanceCovered;
                    camera.update();
                }
                break;
            case EAST:
                newX = newX + distanceCovered;
                if(entity instanceof Player) {
                    camera.position.x += distanceCovered;
                    camera.update();
                }
                break;
            case WEST:
                newX = newX - distanceCovered;
                if(entity instanceof Player) {
                    camera.position.x -= distanceCovered;
                    camera.update();
                }
                break;
            case SOUTH:
                newY = newY - distanceCovered;
                if(entity instanceof Player) {
                    camera.position.y -= distanceCovered;
                    camera.update();
                }
                break;
        }

        entity.setX(newX);
        entity.setY(newY);
    }

    public void dispose() {
        tiledMap.dispose();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
