package com.agonia.game.input;

import com.agonia.game.entity.Entity;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
    private GameMap gameMap;
    private Entity player;

    public InputHandler(GameMap gameMap, Entity player) {
        this.gameMap = gameMap;
        this.player = player;
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
