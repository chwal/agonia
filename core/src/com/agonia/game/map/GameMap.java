package com.agonia.game.map;

import com.agonia.game.camera.GameCamera;
import com.agonia.game.entity.Entity;
import com.agonia.game.entity.Player;
import com.agonia.game.gameobject.GameObject;
import com.agonia.game.input.Direction;
import com.agonia.game.interior.Interior;
import com.agonia.game.interior.InteriorTile;
import com.agonia.game.interior.Interiors;
import com.agonia.game.item.Armor;
import com.agonia.game.item.Item;
import com.agonia.game.map.population.TreePopulation;
import com.agonia.game.util.Position;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameMap {
    public static float MAP_WIDTH;
    public static float MAP_HEIGHT;
    public static int TILE_SIZE = 40;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer staticCollisionLayer;
    private TiledMapTileLayer blockedTilesLayer;

    private GameCamera gameCamera;
    private Player player;

    private Map<Position, GameObject> gameObjects;
    private Map<Position, Item> gameItems;
    private Interiors interiors;

    public void initialize(GameCamera gameCamera, Player player) {
        this.gameCamera = gameCamera;
        this.player = player;
        this.gameObjects = new HashMap<>();
        this.gameItems = new HashMap<>();
        this.tiledMap = new TmxMapLoader().load("maps/map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.staticCollisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("StaticCollisionLayer");
        this.blockedTilesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BlockedTilesLayer");

        MAP_WIDTH = staticCollisionLayer.getTileWidth() * staticCollisionLayer.getWidth();
        MAP_HEIGHT = staticCollisionLayer.getTileHeight() * staticCollisionLayer.getHeight();

        TreePopulation treePopulation = new TreePopulation();
        treePopulation.initialize();
        treePopulation.populateMap(this);

        interiors = new Interiors(tiledMap);
        interiors.initialize();

        gameItems.put(new Position(16, 62), new Armor(new Texture(Gdx.files.internal("sprites/armor.png")), 0, 0));
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        renderer.setView(gameCamera.getCamera());

        Interior currentPlayerInterior = interiors.getInterior(player.getX(), player.getY());
        if(currentPlayerInterior != null) {
            spriteBatch.begin();
            for (InteriorTile interiorTile : currentPlayerInterior.getInteriorTiles()) {
                TiledMapTile tile = interiorTile.getTile();
                spriteBatch.draw(tile.getTextureRegion(), interiorTile.getX(), interiorTile.getY());
            }
            //TODO: Render items within an interior
            //TODO: Render dynamic game object within an interior
            spriteBatch.end();
        } else {
            renderer.render();


            for (Map.Entry<Position, Item> itemEntry : gameItems.entrySet()) {
                Position position = itemEntry.getKey();
                Item item = itemEntry.getValue();
                int x = position.x * TILE_SIZE;
                int y = position.y * TILE_SIZE;
                spriteBatch.begin();
                spriteBatch.draw(item.getTexture(), x, y);
                spriteBatch.end();

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.YELLOW);
                shapeRenderer.rect(x, y, item.getTexture().getWidth(), item.getTexture().getHeight());
                shapeRenderer.end();
            }


            spriteBatch.begin();
            for (Map.Entry<Position, GameObject> gameObjectEntry : gameObjects.entrySet()) {
                Position position = gameObjectEntry.getKey();
                GameObject gameObject = gameObjectEntry.getValue();
                spriteBatch.draw(gameObject.getTexture(), position.x * TILE_SIZE, position.y * TILE_SIZE);
            }
            spriteBatch.end();
        }
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

        //TODO: Increase performance (e.g. only check for items when actually changing layer position)
        Position tilePosition = Utils.toTilePosition(newX + 20, newY + 20);
        Optional<Map.Entry<Position, Item>> optMapItem = getMapItem(tilePosition.x, tilePosition.y);
        if(optMapItem.isPresent()) {
            Map.Entry<Position, Item> itemEntry = optMapItem.get();
            entity.getItems().add(itemEntry.getValue());
            gameItems.remove(itemEntry.getKey());
        }

        entity.setMoving(true);
        entity.setX(newX);
        entity.setY(newY);
    }

    private Optional<Map.Entry<Position, Item>> getMapItem(int tileX, int tileY) {
        return gameItems.entrySet().stream()
                .filter(entry -> entry.getKey().x == tileX && entry.getKey().y == tileY)
                .findFirst();
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
