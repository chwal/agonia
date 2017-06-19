package com.agonia.game.entity;

import com.agonia.game.Agonia;
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
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        for (Entity gameEntity : gameEntities) {
            TextureRegion currentFrame = gameEntity.getAnimation().getKeyFrame(stateTime, true);
            spriteBatch.draw(currentFrame, gameEntity.getX(), gameEntity.getY());
        }

        TextureRegion currentFrame = player.getAnimation().getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, player.getX(), player.getY());
        spriteBatch.end();
    }

    public Player getPlayer() {
        return player;
    }
}
