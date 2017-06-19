package com.agonia.game.map;

import com.agonia.game.camera.GameCamera;
import com.agonia.game.entity.Entity;
import com.agonia.game.entity.Player;
import com.agonia.game.input.Direction;
import com.agonia.game.util.Position;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap {
    public static float MAP_WIDTH;
    public static float MAP_HEIGHT;
    public static int TILE_SIZE = 40;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer staticCollisionLayer;
    private TiledMapTileLayer blockedTilesLayer;
    private TiledMapTileLayer interiorLayer;

    private GameCamera gameCamera;

    public void initialize(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
        tiledMap = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        staticCollisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("StaticCollisionLayer");
        blockedTilesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BlockedTilesLayer");
        interiorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("InteriorLayer");
        MAP_WIDTH = staticCollisionLayer.getTileWidth() * staticCollisionLayer.getWidth();
        MAP_HEIGHT = staticCollisionLayer.getTileHeight() * staticCollisionLayer.getHeight();
    }

    public void render() {
        renderer.setView(gameCamera.getCamera());
        renderer.render();
    }

    //TODO: Rework collision system (currently uses entity center as fix point)
    public void moveEntity(Entity entity, Direction direction, float delta) {
        float newX = entity.getX();
        float newY = entity.getY();

        float velocity = entity.getVelocity();
        float distanceCovered = velocity * delta;

        switch (direction) {
            case NORTH:
                if(isCollisionTile(Utils.toTilePosition(newX + 20, newY + 20 + distanceCovered))) {
                    return;
                }
                newY = newY + distanceCovered;
                if(entity instanceof Player)
                    gameCamera.moveCameraVertically(direction, newY);
                break;
            case EAST:
                if(isCollisionTile(Utils.toTilePosition(newX + distanceCovered + 20, newY + 20))) {
                    return;
                }
                newX = newX + distanceCovered;
                if(entity instanceof Player)
                    gameCamera.moveCameraHorizontally(direction, newX);
                break;
            case WEST:
                if(isCollisionTile(Utils.toTilePosition(newX - distanceCovered + 20, newY + 20))) {
                    return;
                }
                newX = newX - distanceCovered;
                if(entity instanceof Player)
                    gameCamera.moveCameraHorizontally(direction, newX);
                break;
            case SOUTH:
                if(isCollisionTile(Utils.toTilePosition(newX + 20, newY - distanceCovered + 20))) {
                    return;
                }
                newY = newY - distanceCovered;
                if(entity instanceof Player)
                    gameCamera.moveCameraVertically(direction, newY);
                break;
        }

        entity.setX(newX);
        entity.setY(newY);
    }

    private boolean isCollisionTile(Position position) {
        return staticCollisionLayer.getCell((int) position.x, (int) position.y) != null;
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
