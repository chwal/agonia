package com.agonia.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class EntityHandler {
    private List<Entity> gameEntities;

    public void initialize() {
        gameEntities = new ArrayList<>();
    }

    public void render(SpriteBatch spriteBatch, float stateTime) {
        for (Entity gameEntity : gameEntities) {
            TextureRegion currentFrame = gameEntity.getAnimation().getKeyFrame(stateTime, true);
            spriteBatch.draw(currentFrame, gameEntity.getX(), gameEntity.getY()); // Draw current frame at (50, 50)
        }
    }

    public List<Entity> getGameEntities() {
        return gameEntities;
    }
}
