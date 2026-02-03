package com.habil.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.habil.game.component.CoordinateComponent;
import com.habil.game.component.InventoryComponent;
import com.habil.game.component.ItemComponent;
import com.habil.game.component.RenderComponent;
import com.habil.game.component.flag.DirtyStatComponent;
import com.habil.game.component.flag.PlayerComponent;


public class PickUpSystem extends EntitySystem{
  private ImmutableArray<Entity> player;
  private ImmutableArray<Entity> items;
  private ComponentMapper<CoordinateComponent> cm = ComponentMapper.getFor(CoordinateComponent.class);
  private ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);

  @Override
  public void addedToEngine(Engine engine) {
    player = engine.getEntitiesFor(Family.all(PlayerComponent.class, CoordinateComponent.class).get());
    items = engine.getEntitiesFor(Family.all(ItemComponent.class, CoordinateComponent.class, RenderComponent.class).get());
  }

  @Override
  public void update(float deltaTime) {
    if(player.size() <= 0)return;

    CoordinateComponent coorPlayer = cm.get(player.first());

    for(Entity item : items){
      CoordinateComponent coorItem = cm.get(item);

      if(coorPlayer.x == coorItem.x && coorItem.y == coorPlayer.y){
        System.out.println("player mengambil item");
        InventoryComponent iPLayer = im.get(player.first());
        iPLayer.inventory.add(item);
        player.first().add(getEngine().createComponent(DirtyStatComponent.class));
        item.remove(RenderComponent.class);
        item.remove(CoordinateComponent.class);
      }
    }
  }

  
}
