package com.agonia.game;

import com.agonia.game.entity.EntityHandler;
import com.agonia.game.input.InputHandler;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Agonia extends ApplicationAdapter {
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private GameMap gameMap;
    private SpriteBatch spriteBatch;
    private EntityHandler entityHandler;
    private InputHandler inputHandler;

    @Override
    public void create() {
        gameMap = new GameMap();
        spriteBatch = new SpriteBatch();
        spriteBatch = new SpriteBatch();

        entityHandler = new EntityHandler();
        entityHandler.initialize();

        OrthographicCamera camera = new OrthographicCamera();
        gameMap.initialize(camera);
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) gameMap.getTiledMap().getLayers().get(0);
        camera.setToOrtho(false, Agonia.WINDOW_WIDTH, Agonia.WINDOW_HEIGHT);
        camera.position.x = Agonia.WINDOW_WIDTH/2;
        camera.position.y = (mapLayer.getHeight()*mapLayer.getTileHeight()) - Agonia.WINDOW_HEIGHT/2;
        camera.update();

        inputHandler = new InputHandler(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();

        inputHandler.handleInput();
        entityHandler.update(delta);

        gameMap.render();

        spriteBatch.begin();
        entityHandler.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        gameMap.dispose();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }
}
