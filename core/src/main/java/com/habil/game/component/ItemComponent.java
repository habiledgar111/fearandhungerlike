package com.habil.game.component;

import java.util.Random;

import com.badlogic.ashley.core.Component;

public class ItemComponent implements Component{
  public String name;
  public type typeItem;
  public int amount;

  public enum type {
    STR,AGI,VIT,INTEL,LUCK
  }

  public ItemComponent.type randomType(){
    Random random = new Random();
    ItemComponent.type[] type = ItemComponent.type.values();
    return type[random.nextInt(type.length)];
  }
}
