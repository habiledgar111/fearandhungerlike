package com.habil.game.map;

public class WorldConfig {
  public static int SCREEN_WIDTH;
  public static int SCREEN_HEIGHT;


  public static final int TILE_W = 12;
  public static final int TILE_H = 16;

  public static int mapWidth() {
    return SCREEN_WIDTH / TILE_W;
  }

  public static int mapHeight() {
    return SCREEN_HEIGHT / TILE_H;
  }
}
