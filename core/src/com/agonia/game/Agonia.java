package com.agonia.game;

import com.agonia.game.camera.GameCamera;
import com.agonia.game.entity.EntityHandler;
import com.agonia.game.input.InputHandler;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Agonia extends ApplicationAdapter {
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private GameMap gameMap;
    private SpriteBatch spriteBatch;
    private EntityHandler entityHandler;
    private InputHandler inputHandler;
    private GameCamera gameCamera;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        gameMap = new GameMap();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        entityHandler = new EntityHandler(gameMap);
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

        spriteBatch.setProjectionMatrix(gameCamera.getCamera().combined);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        gameMap.render(spriteBatch);
        entityHandler.render(spriteBatch, shapeRenderer);

        SpriteBatch debug = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        debug.begin();
        font.draw(debug, "X " + entityHandler.getPlayer().getX() + " - Y " + entityHandler.getPlayer().getY(), 50, 50);
        debug.end();
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

    public GameCamera getGameCamera() {
        return gameCamera;
    }
}
