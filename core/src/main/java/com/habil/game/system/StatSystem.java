package com.habil.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.habil.game.component.InventoryComponent;
import com.habil.game.component.ItemComponent;
import com.habil.game.component.StatComponent;
import com.habil.game.component.flag.DirtyStatComponent;

public class StatSystem extends IteratingSystem {
  private ComponentMapper<StatComponent> sm = ComponentMapper.getFor(StatComponent.class);
  private ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);
  private ComponentMapper<ItemComponent> itemm = ComponentMapper.getFor(ItemComponent.class);

  public StatSystem() {
    super(Family.all(StatComponent.class, InventoryComponent.class, DirtyStatComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    System.out.println(entity.toString());
    StatComponent statEntity = sm.get(entity);
    InventoryComponent invetory = im.get(entity);

    statCalculation(entity, statEntity, invetory);

  }

  private void statCalculation(Entity entity, StatComponent statEntity, InventoryComponent inventory) {
    System.out.println("Sebelum ====");
    System.out.println(statEntity.toString());
    statEntity.bonusStr = 0;
    statEntity.bonusAgi = 0;
    statEntity.bonusVit = 0;
    statEntity.bonusItel = 0;
    statEntity.bonusLuck = 0;

    Array<Entity> items;
    items = inventory.inventory;
    for (Entity i : items) {
      ItemComponent item = itemm.get(i);
      switch (item.typeItem) {
        case STR:
          statEntity.bonusStr += item.amount;
          break;

        case AGI:
          statEntity.bonusAgi += item.amount;

          break;

        case VIT:
          statEntity.bonusVit += item.amount;

          break;

        case INTEL:
          statEntity.bonusItel += item.amount;

          break;

        case LUCK:
          statEntity.bonusLuck += item.amount;

          break;
      }
    }
    System.out.println("setelah ====");
    System.out.println(statEntity.toString());
    entity.remove(DirtyStatComponent.class);
  }

}
