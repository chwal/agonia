package com.agonia.game.entity;

import com.agonia.game.input.Direction;
import com.agonia.game.item.Item;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
        Animation<TextureRegion> playerAnimation = Utils.loadAnimation("sprites/player_walking2.png", 2, 1);
        player = new Player(700, 2400, playerAnimation);
        player.getItems().add(new Item(new Texture(Gdx.files.internal("sprites/sniper_gun2.png")), 18, 20));
        gameEntities.add(player);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Entity gameEntity : gameEntities) {
            TextureRegion nextEntityAnimFrame;

            if(!gameEntity.isMoving()) {
                nextEntityAnimFrame = player.getAnimation().getKeyFrames()[0];
            } else {
                nextEntityAnimFrame = gameEntity.getAnimation().getKeyFrame(stateTime, true);
            }

            boolean flip = gameEntity.getFacing() != Direction.EAST;

            int entityWidth = nextEntityAnimFrame.getRegionWidth();
            float entityX = gameEntity.getX();
            float entityY = gameEntity.getY();
            //TODO: Flip player based on the yaw angle
            spriteBatch.draw(nextEntityAnimFrame, flip ? entityX + entityWidth : entityX, entityY, flip ? -entityWidth : entityWidth, nextEntityAnimFrame.getRegionHeight());

            for (Item item : gameEntity.getItems()) {
                Texture itemTexture = item.getTexture();
                if(gameEntity instanceof Player) {
                    Player player = (Player) gameEntity;
                    //TODO: Flip items based on the yaw angle
                    spriteBatch.draw(itemTexture, entityX+item.getxOffset(), entityY+item.getyOffset(), 0, 0,itemTexture.getWidth(), itemTexture.getHeight(), 1, 1, player.getYaw(),0, 0, itemTexture.getWidth(), itemTexture.getHeight(), false, false);
                }
            }
        }
        spriteBatch.end();
    }

    public Player getPlayer() {
        return player;
    }
}
