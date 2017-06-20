package com.agonia.game.map;

import com.agonia.game.Agonia;
import com.agonia.game.camera.GameCamera;
import com.agonia.game.entity.Entity;
import com.agonia.game.entity.Player;
import com.agonia.game.gameobject.GameObject;
import com.agonia.game.input.Direction;
import com.agonia.game.map.population.TreePopulation;
import com.agonia.game.util.Position;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.HashMap;
import java.util.Map;

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

    private Map<Position, GameObject> gameObjects;

    private TreePopulation treePopulation;

    public void initialize(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
        this.gameObjects = new HashMap<>();
        this.tiledMap = new TmxMapLoader().load("maps/map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.staticCollisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("StaticCollisionLayer");
        this.blockedTilesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BlockedTilesLayer");
        this.interiorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("InteriorLayer");

        MAP_WIDTH = staticCollisionLayer.getTileWidth() * staticCollisionLayer.getWidth();
        MAP_HEIGHT = staticCollisionLayer.getTileHeight() * staticCollisionLayer.getHeight();

        treePopulation = new TreePopulation();
        treePopulation.initialize();
        treePopulation.populateMap(this);
    }

    public void render(SpriteBatch spriteBatch) {
        renderer.setView(gameCamera.getCamera());

        renderer.render();

        spriteBatch.begin();
        for (Map.Entry<Position, GameObject> entry : gameObjects.entrySet()) {
            Position position = entry.getKey();
            GameObject gameObject = entry.getValue();
            spriteBatch.draw(gameObject.getTexture(), position.x * TILE_SIZE, position.y * TILE_SIZE);
        }
        spriteBatch.end();
    }

    //TODO: Rework collision system (currently uses entity center as fix point)
    public void moveEntity(Entity entity, Direction direction, float delta) {
        float newX = entity.getX();
        float newY = entity.getY();

        float velocity = entity.getVelocity();
        float distanceCovered = velocity * delta;

        switch (direction) {
            case NORTH:
                newY = newY + distanceCovered;
                if(isColliding(newX, newY))
                    return;

                if(entity instanceof Player)
                    gameCamera.moveCameraVertically(direction, newY);
                break;
            case EAST:
                newX = newX + distanceCovered;
                if(isColliding(newX, newY))
                    return;

                if(entity instanceof Player)
                    gameCamera.moveCameraHorizontally(direction, newX);
                break;
            case WEST:
                newX = newX - distanceCovered;
                if(isColliding(newX, newY))
                    return;

                if(entity instanceof Player)
                    gameCamera.moveCameraHorizontally(direction, newX);
                break;
            case SOUTH:
                newY = newY - distanceCovered;
                if(isColliding(newX, newY))
                    return;

                if(entity instanceof Player)
                    gameCamera.moveCameraVertically(direction, newY);
                break;
        }

        entity.setMoving(true);
        entity.setX(newX);
        entity.setY(newY);
    }

    public boolean isStaticCollisionTile(int tileX, int tileY) {
        return staticCollisionLayer.getCell(tileX, tileY) != null;
    }

    public boolean isBlockedTile(int tileX, int tileY) {
        return blockedTilesLayer.getCell(tileX, tileY) != null;
    }

    public void dispose() {
        tiledMap.dispose();
    }

    public void addGameObject(GameObject gameObject, Position position) {
        gameObjects.put(position, gameObject);
    }

    public boolean isDynamicCollisionTile(int xPos, int yPos) {
        return gameObjects.containsKey(new Position(xPos, yPos));
    }

    public boolean isColliding(float newX, float newY) {
        Position tilePosition = Utils.toTilePosition(newX + 20, newY + 20);
        return isStaticCollisionTile(tilePosition.x, tilePosition.y) || isDynamicCollisionTile(tilePosition.x, tilePosition.y) || newY > MAP_HEIGHT - TILE_SIZE || newY < 0 || newX > MAP_WIDTH - TILE_SIZE || newX < 0;
    }
}
