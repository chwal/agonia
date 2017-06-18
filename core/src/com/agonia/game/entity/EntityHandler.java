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
    private BitmapFont font;

    public void initialize() {
        gameEntities = new ArrayList<>();
        stateTime = 0f;
        Animation<TextureRegion> playerAnimation = Utils.loadAnimation("sprites/player_walking.png", 2, 1);
        player = new Player(Agonia.WINDOW_WIDTH, Agonia.WINDOW_HEIGHT, playerAnimation);
        font = new BitmapFont();
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch spriteBatch) {
        for (Entity gameEntity : gameEntities) {
            TextureRegion currentFrame = gameEntity.getAnimation().getKeyFrame(stateTime, true);
            spriteBatch.draw(currentFrame, gameEntity.getX(), gameEntity.getY());
        }

        font.draw(spriteBatch, "X " + player.getX() + " - Y " + player.getY(), 50, 50);

        TextureRegion currentFrame = player.getAnimation().getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, player.getX(), player.getY());
    }

    public Player getPlayer() {
        return player;
    }
}
