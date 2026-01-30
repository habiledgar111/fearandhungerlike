package com.habil.game.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.habil.game.component.CoordinateComponent;
import com.habil.game.component.InventoryComponent;
import com.habil.game.component.ItemComponent;
import com.habil.game.component.RenderComponent;
import com.habil.game.component.StatComponent;
import com.habil.game.component.flag.EnemyComponent;
import com.habil.game.component.flag.PlayerComponent;
import com.habil.game.map.WorldConfig;

public class EntityFactory {
  private PooledEngine engine;

  public EntityFactory(PooledEngine engine) {
    this.engine = engine;
  }

  public Entity createEnemy(int difficultLevel) {
    Entity enemy = engine.createEntity();
    EnemyComponent ec = engine.createComponent(EnemyComponent.class);
    CoordinateComponent cc = engine.createComponent(CoordinateComponent.class);
    RenderComponent rc = engine.createComponent(RenderComponent.class);
    StatComponent sc = randomStatEnemy(difficultLevel);

    rc.glyph = EntitySymbol.MONSTER;

    enemy.add(rc);
    enemy.add(sc);
    enemy.add(ec);
    enemy.add(cc);
    engine.addEntity(enemy);

    return enemy;
  }

  private StatComponent randomStatEnemy(int difficultLevel) {
    StatComponent sc = engine.createComponent(StatComponent.class);
    int totalPoint = 20 * difficultLevel;
    int[] points = new int[4];
    for (int i = 0; i < totalPoint; i++) {
      int randomIndex = (int) Math.random() * 4;
      points[randomIndex]++;
    }
    sc.str = points[0];
    sc.agi = points[1];
    sc.vit = points[2];
    sc.intel = points[3];

    sc.maxHp = 100;
    sc.currentHp = 100;
    sc.baseArmor = 10;

    sc.name = "monster";

    return sc;
  }

  public Entity createPlayer(int str, int agi, int vit, int intel, int luck) {
    Entity player = engine.createEntity();

    StatComponent sc = engine.createComponent(StatComponent.class);

    sc.str = str;
    sc.agi = agi;
    sc.vit = vit;
    sc.intel = intel;
    sc.luck = luck;
    sc.maxHp = 70;
    sc.currentHp = 70;
    sc.baseArmor = 5;
    sc.name = "player";

    player.add(sc);

    
    player.add(engine.createComponent(PlayerComponent.class));
    
    InventoryComponent invetory = engine.createComponent(InventoryComponent.class);
    invetory.inventory = new Array<>();
    player.add(invetory);
    
    CoordinateComponent coordinate = engine.createComponent(CoordinateComponent.class);
    coordinate.x = WorldConfig.mapWidth() / 2;
    coordinate.y = WorldConfig.mapHeight() / 2;
    
    player.add(coordinate);
    
    RenderComponent icon = engine.createComponent(RenderComponent.class);
    icon.glyph = EntitySymbol.PLAYER;
    player.add(icon);

    engine.addEntity(player);
    return player;
  }

  public Entity createItem(String name, ItemComponent.type type, int amount) {
    Entity item = engine.createEntity();
    ItemComponent itemComponent = engine.createComponent(ItemComponent.class);
    itemComponent.amount = amount;
    itemComponent.typeItem = type;
    itemComponent.name = name;

    item.add(itemComponent);
    engine.addEntity(item);

    return item;
  }
}
