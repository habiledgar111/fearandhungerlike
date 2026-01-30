package com.habil.game.entity;

import com.badlogic.gdx.graphics.Color;

public enum EntitySymbol {
  PLAYER('@',Color.YELLOW),
  MONSTER('M',Color.RED);

  public final char glyph;
  public final Color color;
  
  private EntitySymbol(char glyph, Color color) {
    this.glyph = glyph;
    this.color = color;
  }
}
