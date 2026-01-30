package com.habil.game.map;

import com.badlogic.gdx.graphics.Color;

public enum TileType {
  GRASS('.', true, Color.WHITE),
  TREE('T', true, Color.GREEN),
  WATER('~', false, Color.BLUE),
  HILL('^', true, Color.BROWN),
  ROCK('#', false, Color.DARK_GRAY);

  public final char glyph;
  public final boolean walkable;
  public final Color color;

  TileType(char glyph, boolean walkable, Color color) {
    this.glyph = glyph;
    this.walkable = walkable;
    this.color = color;
  }
}
