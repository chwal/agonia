package com.agonia.game.input;

import com.agonia.game.Agonia;
import com.agonia.game.entity.Entity;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class InputHandler {
    private final Entity player;
    private final GameMap gameMap;

    public InputHandler(Agonia agonia) {
        gameMap = agonia.getGameMap();
        player = agonia.getEntityHandler().getPlayer();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameMap.moveEntity(player, Direction.EAST);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameMap.moveEntity(player, Direction.NORTH);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameMap.moveEntity(player, Direction.WEST);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameMap.moveEntity(player, Direction.SOUTH);
        }
    }
}
