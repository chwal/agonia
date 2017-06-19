package com.agonia.game.input;

import com.agonia.game.Agonia;
import com.agonia.game.entity.Player;
import com.agonia.game.item.Item;
import com.agonia.game.map.GameMap;
import com.agonia.game.util.MathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import java.awt.geom.Point2D;

public class InputHandler {
    private final Player player;
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


        //TODO: Use the actual position of the player (cuz hes not always in the center of the screen)
        Point2D playerPos = new Point2D.Float(Agonia.WINDOW_WIDTH/2, Agonia.WINDOW_HEIGHT/2);
        Point2D mousePos = new Point2D.Float(Gdx.input.getX(), Gdx.input.getY());
        float angle = MathUtils.getAngle(playerPos, mousePos);
        player.setYaw(-angle);
    }
}
