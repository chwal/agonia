package com.agonia.game.entity;

import com.agonia.game.Agonia;
import com.agonia.game.input.Direction;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class EntityHandler {
    private List<Entity> gameEntities;
    private float stateTime;
    private Player player;

    public void initialize() {
        gameEntities = new ArrayList<>();
        stateTime = 0f;
        Animation<TextureRegion> playerAnimation = Utils.loadAnimation("sprites/player_walking.png", 2, 1);
        player = new Player(700, 2400, playerAnimation);
        gameEntities.add(player);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Entity gameEntity : gameEntities) {
            TextureRegion nextFrame;

            if(!gameEntity.isMoving()) {
                nextFrame = player.getAnimation().getKeyFrames()[0];
            } else {
                nextFrame = gameEntity.getAnimation().getKeyFrame(stateTime, true);
            }

            boolean flip = gameEntity.getFacing() != Direction.EAST;
            int width = nextFrame.getRegionWidth();
            float x = gameEntity.getX();
            spriteBatch.draw(nextFrame, flip ? x + width : x, gameEntity.getY(), flip ? -width : width, nextFrame.getRegionHeight());
        }
        spriteBatch.end();
    }

    public Player getPlayer() {
        return player;
    }
}
