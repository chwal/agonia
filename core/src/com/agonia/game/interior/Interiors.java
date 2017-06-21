package com.agonia.game.interior;

import com.agonia.game.util.Position;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

//TODO: Render static collision layer tiles over interior tiles to show collision tiles inside an interior
public class Interiors {
    private TiledMapTileLayer interiorLayer;
    private MapLayer interiorObjectsLayer;

    private List<Interior> interiors;

    public Interiors(TiledMap tiledMap) {
        this.interiorObjectsLayer = tiledMap.getLayers().get("Interior");
        this.interiorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("InteriorLayer");
        this.interiors = new ArrayList<>();

    }

    public void initialize() {
        for (MapObject mapObject : interiorObjectsLayer.getObjects()) {
            if(mapObject instanceof RectangleMapObject) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                float x = rectangleMapObject.getRectangle().getX();
                float y = rectangleMapObject.getRectangle().getY();
                float width = rectangleMapObject.getRectangle().getWidth();
                float height = rectangleMapObject.getRectangle().getHeight();
                Interior interior = new Interior(x, y, width, height);
                for (float i = x; i < x + width; i += 40) {
                    for (float k = y; k  < y + height; k  += 40) {
                        Position tilePosition = Utils.toTilePosition(i, k);
                        TiledMapTileLayer.Cell cell = interiorLayer.getCell(tilePosition.x, tilePosition.y);
                        if(cell != null) {
                            InteriorTile interiorTile = new InteriorTile(tilePosition.x * 40, tilePosition.y * 40, cell.getTile());
                            interior.getInteriorTiles().add(interiorTile);
                        }
                    }
                }
                interiors.add(interior);
            }
        }
    }

    //TODO: Increase performance by storing current interior linked to the entity
    public Interior getInterior(float x, float y) {
        for (Interior interior : interiors) {
            if(x >= interior.getX() && x <= interior.getX() + interior.getWidth() && y >= interior.getY() && y <= interior.getY() + interior.getHeight())
                return interior;
        }
        return null;
    }
}
