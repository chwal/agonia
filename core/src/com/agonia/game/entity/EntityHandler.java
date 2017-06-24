package com.agonia.game.entity;

import com.agonia.game.input.Direction;
import com.agonia.game.item.Armor;
import com.agonia.game.item.Gun;
import com.agonia.game.item.Item;
import com.agonia.game.map.GameMap;
import com.agonia.game.util.MathUtils;
import com.agonia.game.util.Position;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class EntityHandler {
    private GameMap gameMap;
    private List<Entity> gameEntities;
    private List<Bullet> bullets;
    private float stateTime;
    private Player player;

    public EntityHandler(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void initialize() {
        gameEntities = new ArrayList<>();
        bullets = new ArrayList<>();
        stateTime = 0f;
        Animation<TextureRegion> playerAnimation = Utils.loadAnimation("sprites/player_walking_no_arms.png", 2, 1);
        player = new Player(700, 2400, playerAnimation);
        player.getItems().add(new Gun(new Texture(Gdx.files.internal("sprites/sniper_gun_with_arm.png")), 14, 20));
        player.getItems().add(new Armor(new Texture(Gdx.files.internal("sprites/helmet.png")), 0, 0));
        gameEntities.add(player);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
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
            spriteBatch.draw(nextEntityAnimFrame, flip ? entityX + entityWidth : entityX, entityY, flip ? -entityWidth : entityWidth, nextEntityAnimFrame.getRegionHeight());

            //Render armor (if equipped?)
            for (Item item : gameEntity.getItems()) {
                if(item instanceof Armor) {
                    Texture armorTexture = item.getTexture();
                    int armorWidth = armorTexture.getWidth();
                    spriteBatch.draw(armorTexture, flip ? entityX + armorWidth : entityX, entityY, flip ? -armorWidth : armorWidth, armorTexture.getHeight());
                }
            }

            if(gameEntity instanceof Player) {
                Player player = (Player) gameEntity;
                Gun primaryGun = player.getPrimaryGun();

                if(primaryGun != null) {
                    Texture gunTexture = primaryGun.getTexture();
                    float itemX = entityX + primaryGun.getxOffset();
                    float itemY = entityY + primaryGun.getyOffset();
                    int itemWidth = gunTexture.getWidth();
                    spriteBatch.draw(gunTexture, flip ? itemX + itemWidth / 2 : itemX, itemY, 0, 0, flip ? -itemWidth : itemWidth, gunTexture.getHeight(), 1, 1, flip ? player.getYaw() + 180 : player.getYaw(), 0, 0, itemWidth, gunTexture.getHeight(), false, false);
                }
            }
        }
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        //TODO: Move bullet rendering to separate class or represent bullet as an entity?
        //TODO: Use textures for bullet visualisation instead of rendering circles
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            float bulletX = bullet.getX();
            float bulletY = bullet.getY();
            bullet.setX(bulletX + bullet.getDirectionX() * bullet.getVelocity());
            bullet.setY(bulletY + bullet.getDirectionY() * bullet.getVelocity());
            shapeRenderer.circle(bulletX, bulletY, 3);

            Position bulletTilePos = Utils.toTilePosition(bulletX, bulletY);
            if(this.gameMap.isStaticCollisionTile(bulletTilePos.x, bulletTilePos.y) || this.gameMap.isDynamicCollisionTile(bulletTilePos.x, bulletTilePos.y)
                    || bulletY > GameMap.MAP_HEIGHT - GameMap.TILE_SIZE || bulletY < 0 || bulletX > GameMap.MAP_WIDTH - GameMap.TILE_SIZE || bulletX < 0) {
                bulletsToRemove.add(bullet);
            }
        }

        bullets.removeAll(bulletsToRemove);
        shapeRenderer.end();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
