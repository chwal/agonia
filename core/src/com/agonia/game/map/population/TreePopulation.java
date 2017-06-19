package com.agonia.game.map.population;

import com.agonia.game.gameobject.GameObject;
import com.agonia.game.map.GameMap;
import com.agonia.game.util.Position;
import com.agonia.game.util.Utils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TreePopulation {
    private static final int MAX_PROBABILITY = 11;
    private List<TreeSpawn> trees;

    public TreePopulation() {
        this.trees = new ArrayList<>();
    }

    public void initialize() {
        trees.add(new TreeSpawn(new GameObject(new Texture(Gdx.files.internal("sprites/tree_0.png"))), SpawnChance.HIGH));
        trees.add(new TreeSpawn(new GameObject(new Texture(Gdx.files.internal("sprites/tree_1.png"))), SpawnChance.HIGH));
        trees.add(new TreeSpawn(new GameObject(new Texture(Gdx.files.internal("sprites/tree_2.png"))), SpawnChance.LOW));
        trees.add(new TreeSpawn(new GameObject(new Texture(Gdx.files.internal("sprites/tree_3.png"))), SpawnChance.LOW));
    }

    private GameObject getNextTree(int probability) {
        Random treeRandom = new Random();

        List<TreeSpawn> possibleTrees = trees.stream()
                .filter(tree -> probability >= tree.spawnChance.low && probability <= tree.spawnChance.high)
                .collect(Collectors.toList());

        return possibleTrees.get(treeRandom.nextInt(possibleTrees.size())).gameObject;
    }

    public void populateMap(GameMap gameMap) {
        Random positionRan = new Random();
        Random probabilityRan = new Random();

        int treeCount = 1300;
        int treesPlaced = 0;
        while (treesPlaced <= treeCount) {
            int xPos = positionRan.nextInt((int) (GameMap.MAP_WIDTH / GameMap.TILE_SIZE));
            int yPos = positionRan.nextInt((int) (GameMap.MAP_HEIGHT / GameMap.TILE_SIZE));

            if(!gameMap.isBlockedTile(xPos, yPos) && !gameMap.isStaticCollisionTile(xPos, yPos) && !gameMap.isDynamicCollisionTile(xPos, yPos)) {
                int probability = probabilityRan.nextInt(MAX_PROBABILITY);
                GameObject gameObject = getNextTree(probability);
                gameMap.addGameObject(gameObject, new Position(xPos, yPos));
                treesPlaced++;
            }
        }
    }

    private class TreeSpawn {
        GameObject gameObject;
        SpawnChance spawnChance;

        TreeSpawn(GameObject gameObject, SpawnChance spawnChance) {
            this.gameObject = gameObject;
            this.spawnChance = spawnChance;
        }
    }
}
