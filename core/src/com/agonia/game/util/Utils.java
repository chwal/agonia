package com.agonia.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
    public static Animation<TextureRegion> loadAnimation(String path, int frameCols, int frameRows) {
        Texture walkSheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / frameCols,
                walkSheet.getHeight() / frameRows);

        TextureRegion[] walkFrames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(0.25f, walkFrames);
    }
}