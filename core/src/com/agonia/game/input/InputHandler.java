package com.agonia.game.input;

import com.agonia.game.Agonia;
import com.agonia.game.entity.Entity;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
    private final Entity player;
    private final GameMap gameMap;

    public InputHandler(Agonia agonia) {
        gameMap = agonia.getGameMap();
        player = agonia.getEntityHandler().getPlayer();
    }

    public void handleInput(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            player.setVelocity(300.0f);
        } else {
            player.setVelocity(150.0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameMap.moveEntity(player, Direction.EAST, delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameMap.moveEntity(player, Direction.NORTH, delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameMap.moveEntity(player, Direction.WEST, delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameMap.moveEntity(player, Direction.SOUTH, delta);
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setMoving(false);
        }
    }
}
