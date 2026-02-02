package com.habil.game.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.habil.game.component.CoordinateComponent;
import com.habil.game.component.ItemComponent;
import com.habil.game.component.ItemComponent.type;
import com.habil.game.entity.EntityFactory;
import com.habil.game.lib.FastNoiseLite;

import squidpony.squidgrid.mapping.OrganicMapGenerator;
import squidpony.squidmath.StatefulRNG;

public class WorldGenerator {
  private FastNoiseLite biomeNoise;
  private StatefulRNG rng;

  public WorldGenerator(int seed) {
    // heightNoise = new FastNoiseLite(seed);
    // heightNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    // heightNoise.SetFrequency(0.05f);

    // vegetationNoise = new FastNoiseLite(MathUtils.random(seed));
    // vegetationNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    // vegetationNoise.SetFrequency(0.10f);

    biomeNoise = new FastNoiseLite(seed);
    biomeNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    biomeNoise.SetFrequency(0.06f);

    rng = new StatefulRNG(seed);
  }

  public WorldMap generate(WorldMap map) {
    int width = map.getWidth();
    int height = map.getHeight();

    OrganicMapGenerator organic = new OrganicMapGenerator(width, height, rng);
    organic.noiseMin = 0.25;
    char[][] baseMap = organic.generate();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        float n = biomeNoise.GetNoise(x, y);
        char c = baseMap[x][y];

        TileType tile;

        if (n < -0.4f) {
          tile = TileType.WATER;
        } else if (n > 0.5f) {
          tile = TileType.HILL;
        } else {
          tile = TileType.GRASS;
        }

        if (c == '#' && tile != TileType.WATER) {
          if (n > 0.4f) {
            tile = TileType.ROCK;
          } else {
            tile = TileType.TREE;
          }
        }
        map.set(x, y, tile);
      }
    }
    return map;
  }

  public void spawnMonster(WorldMap map, Engine engine, EntityFactory factory, int difficultLevel) {
    List<int[]> walkableTiles = new ArrayList<>();
    ComponentMapper<CoordinateComponent> cm = ComponentMapper.getFor(CoordinateComponent.class);
    int count = calculateMonsterCount(difficultLevel);

    for (int x = 0; x < map.getWidth(); x++) {
      for (int y = 0; y < map.getHeight(); y++) {
        if (map.isWalkable(x, y))
          walkableTiles.add(new int[] { x, y });
      }
    }

    if (walkableTiles.isEmpty())
      return;

    for (int i = 0; i < count; i++) {
      int index = rng.nextInt(walkableTiles.size());
      int[] pos = walkableTiles.remove(index);

      Entity monster = factory.createEnemy(difficultLevel);
      CoordinateComponent coorMonster = cm.get(monster);

      coorMonster.x = pos[0];
      coorMonster.y = pos[1];
    }
  }

  private int calculateMonsterCount(int difficultLevel) {
    float baseMonnster = 5f;
    float growthRate = 1.2f;

    double count = baseMonnster * Math.pow(growthRate, difficultLevel - 1);

    return Math.max(1, (int) count);
  }

  public void spawnItem(WorldMap map, Engine engine, EntityFactory factory, int rareLevel) {
    List<int[]> walkableTiles = new ArrayList<>();
    ComponentMapper<CoordinateComponent> cm = ComponentMapper.getFor(CoordinateComponent.class);

    for (int x = 0; x < map.getWidth(); x++) {
      for (int y = 0; y < map.getHeight(); y++) {
        if(map.isWalkable(x, y)){
          walkableTiles.add(new int[] {x,y});
        }
      }
    }

    if(walkableTiles.isEmpty()){
      return;
    }

    int count = calculateMonsterCount(rareLevel);
    for(int i =0;i<count;i++){
      int index = rng.nextInt(walkableTiles.size());
      int[] pos = walkableTiles.remove(index);
      ItemComponent type = new ItemComponent();
      Entity item = factory.createItem("item-"+index, type.randomType(), 9);

      CoordinateComponent coor = cm.get(item);

      coor.x = pos[0];
      coor.y = pos[1];
    }
  }

  
}
