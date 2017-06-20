package com.agonia.game.input;

import com.agonia.game.Agonia;
import com.agonia.game.camera.GameCamera;
import com.agonia.game.entity.Bullet;
import com.agonia.game.entity.EntityHandler;
import com.agonia.game.entity.Player;
import com.agonia.game.map.GameMap;
import com.agonia.game.util.MathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import java.awt.geom.Point2D;

public class InputHandler {
    private final Player player;
    private final GameMap gameMap;
    private final GameCamera gameCamera;
    private final EntityHandler entityHandler;

    public InputHandler(Agonia agonia) {
        gameCamera = agonia.getGameCamera();
        gameMap = agonia.getGameMap();
        entityHandler = agonia.getEntityHandler();
        player = entityHandler.getPlayer();
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

        //TODO: Fix yaw calculation when touching top/bottom map restriction
        Vector3 screenPos = gameCamera.getCamera().project(new Vector3(player.getX(), player.getY(), 0));
        Point2D playerPos = new Point2D.Float(screenPos.x, screenPos.y);
        Point2D mousePos = new Point2D.Float(Gdx.input.getX(), Gdx.input.getY());
        float angle = MathUtils.getAngle(playerPos, mousePos);
        if(angle < 270 && angle > 90) {
            player.setFacing(Direction.WEST);
        } else {
            player.setFacing(Direction.EAST);
        }

        player.setYaw(-angle);

        if(Gdx.input.justTouched()) {
            Point2D direction = MathUtils.angleToDirection(-angle + 90);
            entityHandler.getBullets().add(new Bullet(player.getX()+ 20, player.getY()+ 20, (float) direction.getX(), (float) direction.getY(), 20));
        }
    }
}
