package com.agonia.game.camera;

import com.agonia.game.Agonia;
import com.agonia.game.entity.Player;
import com.agonia.game.input.Direction;
import com.agonia.game.map.GameMap;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera {
    private OrthographicCamera camera;
    private Player player;

    public GameCamera(Player player) {
        this.player = player;
        this.camera = new OrthographicCamera();
    }

    public void initialize() {
        camera.setToOrtho(false, Agonia.WINDOW_WIDTH, Agonia.WINDOW_HEIGHT);
        camera.position.x = player.getX() + 25;
        camera.position.y = player.getY() + 25;
        camera.update();
    }

    public void moveCameraVertically(Direction direction, float newY) {
        float playerY = newY + 20;

        if(playerY >= Agonia.WINDOW_HEIGHT / 2 && playerY <= GameMap.MAP_HEIGHT - (Agonia.WINDOW_HEIGHT / 2)) {
            if(direction.equals(Direction.NORTH))
                camera.position.y = playerY;
            else
                camera.position.y = playerY;
            camera.update();
        }
    }

    public void moveCameraHorizontally(Direction direction, float newX) {
        float playerX = newX + 20;

        if(playerX <= GameMap.MAP_WIDTH - (Agonia.WINDOW_WIDTH / 2) && playerX >= Agonia.WINDOW_WIDTH / 2) {
            if(direction.equals(Direction.EAST))
                camera.position.x = playerX;
            else
                camera.position.x = playerX;
            camera.update();
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
