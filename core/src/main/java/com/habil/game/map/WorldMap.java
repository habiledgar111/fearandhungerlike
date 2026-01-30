package com.habil.game.map;

public class WorldMap {
  private final int width;
  private final int height;
  private final TileType[][] tiles;
  private final int seed;

  public WorldMap(int width, int height, int seed) {
    this.width = width;
    this.height = height;
    tiles = new TileType[width][height];
    this.seed = seed;
  }

  public void set(int x, int y, TileType t) {
    tiles[x][y] = t;
  }

  public TileType get(int x, int y) {
    if (x < 0 || y < 0)
      return null;
    return tiles[x][y];
  }

  public boolean isWalkable(int x, int y) {
    if (x < 0 || y < 0 || x >= width || y >= height)
      return false;
    return tiles[x][y].walkable;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public TileType[][] getTiles() {
    return tiles;
  }

  public int getSeed() {
    return seed;
  }

}
