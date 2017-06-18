package com.agonia.game.map;

import com.agonia.game.Agonia;
import com.agonia.game.entity.Entity;
import com.agonia.game.entity.Player;
import com.agonia.game.input.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap {
    public static float MAP_WIDTH;
    public static float MAP_HEIGHT;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;

    public void initialize(OrthographicCamera camera, Player player) {
        this.camera = camera;
        this.player = player;
        tiledMap = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        MAP_WIDTH = mapLayer.getTileWidth() * mapLayer.getWidth();
        MAP_HEIGHT = mapLayer.getTileHeight() * mapLayer.getHeight();
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
                moveCameraVertically();
                break;
            case EAST:
                newX = newX + distanceCovered;
                moveCameraHorizontally();
                break;
            case WEST:
                newX = newX - distanceCovered;
                moveCameraHorizontally();
                break;
            case SOUTH:
                newY = newY - distanceCovered;
                moveCameraVertically();
                break;
        }

        entity.setX(newX);
        entity.setY(newY);
    }

    private void moveCameraVertically() {
        float playerY = player.getY();

        if(playerY >= Agonia.WINDOW_HEIGHT / 2 && playerY <= GameMap.MAP_HEIGHT - (Agonia.WINDOW_HEIGHT / 2)) {
            camera.position.y = playerY;
            camera.update();
        }
    }

    private void moveCameraHorizontally() {
        float playerX = player.getX();

        if(playerX <= GameMap.MAP_WIDTH - (Agonia.WINDOW_WIDTH / 2) && playerX >= Agonia.WINDOW_WIDTH / 2) {
            camera.position.x = playerX;
            camera.update();
        }
    }

    public void dispose() {
        tiledMap.dispose();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
