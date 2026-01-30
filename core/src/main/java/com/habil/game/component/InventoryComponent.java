package com.habil.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public class InventoryComponent implements Component{
  public Array<Entity> inventory;
}
