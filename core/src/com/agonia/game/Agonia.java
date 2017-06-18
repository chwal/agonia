package com.agonia.game;

import com.agonia.game.entity.Entity;
import com.agonia.game.entity.EntityHandler;
import com.agonia.game.input.InputHandler;
import com.agonia.game.map.GameMap;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Agonia extends ApplicationAdapter {
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private GameMap gameMap;
    private SpriteBatch spriteBatch;
    private float stateTime;
    private EntityHandler entityHandler;
    private InputHandler inputHandler;

    @Override
    public void create() {
        gameMap = new GameMap();
        gameMap.initialize();
        spriteBatch = new SpriteBatch();

        Animation<TextureRegion> playerAnimation = Utils.loadAnimation("sprites/player_walking.png", 2, 1);
        Entity player = new Entity(50, 50, playerAnimation);

        inputHandler = new InputHandler(gameMap, player);

        entityHandler = new EntityHandler();
        entityHandler.initialize();
        entityHandler.getGameEntities().add(player);

        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        inputHandler.handleInput();

        gameMap.render();

        spriteBatch.begin();
        entityHandler.render(spriteBatch, stateTime);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        gameMap.dispose();
    }
}
