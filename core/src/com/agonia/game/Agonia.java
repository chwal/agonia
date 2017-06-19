package com.agonia.game;

import com.agonia.game.camera.GameCamera;
import com.agonia.game.entity.EntityHandler;
import com.agonia.game.input.InputHandler;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Agonia extends ApplicationAdapter {
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private GameMap gameMap;
    private SpriteBatch spriteBatch;
    private EntityHandler entityHandler;
    private InputHandler inputHandler;
    private GameCamera gameCamera;

    @Override
    public void create() {
        gameMap = new GameMap();
        spriteBatch = new SpriteBatch();
        spriteBatch = new SpriteBatch();

        entityHandler = new EntityHandler();
        entityHandler.initialize();

        gameCamera = new GameCamera(entityHandler.getPlayer());
        gameMap.initialize(gameCamera);
        gameCamera.initialize();

        inputHandler = new InputHandler(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();

        inputHandler.handleInput(delta);
        entityHandler.update(delta);

        gameMap.render();

        spriteBatch.setProjectionMatrix(gameCamera.getCamera().combined);
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
