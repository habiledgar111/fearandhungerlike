package com.habil.game.component;

import com.badlogic.ashley.core.Component;

public class ItemComponent implements Component{
  public String name;
  public type typeItem;
  public int amount;

  public enum type {
    STR,AGI,VIT,INTEL,LUCK
  }
}
