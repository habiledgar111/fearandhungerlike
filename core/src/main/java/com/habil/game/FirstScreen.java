package com.habil.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.habil.game.component.InventoryComponent;
import com.habil.game.component.ItemComponent;
import com.habil.game.component.flag.DirtyStatComponent;
import com.habil.game.component.flag.PlayerComponent;
import com.habil.game.entity.EntityFactory;
import com.habil.game.map.WorldConfig;
import com.habil.game.map.WorldMap;
import com.habil.game.system.BattleSystem;
import com.habil.game.system.EngageSystem;
import com.habil.game.system.HealthSystem;
import com.habil.game.system.InputSystem;
import com.habil.game.system.RenderSystem;
import com.habil.game.system.StatSystem;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

  private PooledEngine engine;
  private EntityFactory factory;
  private Entity player;
  private WorldMap map;

  @Override
  public void show() {
    // Prepare your screen here.
    WorldConfig.SCREEN_WIDTH = 1280;
    WorldConfig.SCREEN_HEIGHT = 720;

    engine = new PooledEngine();
    factory = new EntityFactory(engine);
    factory.createEnemy(1);
    factory.createPlayer(40, 10, 15, 7, 5);
    player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();

    engine.addSystem(new HealthSystem());
    engine.addSystem(new StatSystem());


    map = new WorldMap(WorldConfig.mapWidth(), WorldConfig.mapHeight(), MathUtils.random(9999));
    engine.addSystem(new RenderSystem(map, factory));
    
    InputSystem inputSystem = new InputSystem(map);
    engine.addSystem(inputSystem);
    Gdx.input.setInputProcessor(inputSystem);

    engine.addSystem(new BattleSystem(player));
    engine.addSystem(new EngageSystem());
  }

  @Override
  public void render(float delta) {
    // Draw your screen here. "delta" is the time since last render in seconds.
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


    // if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
    //   for (int i = 1; i <= 3; i++) {
    //     Entity item = factory.createItem(("Item - " + i), randomItemType(), ((int) (Math.random() * 20)));
    //     equipItem(player, item);
    //   }
    // }

    // if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
    //   InventoryComponent inventoryComponent = player.getComponent(InventoryComponent.class);
    //   int random = (int) Math.random() * inventoryComponent.inventory.size;
    //   Entity item = inventoryComponent.inventory.get(random);
    //   unEquipItem(player, item);
    // }

    engine.update(delta);
  }

  // private ItemComponent.type randomItemType() {
  //   return ItemComponent.type.values()[(int) (Math.random() * ItemComponent.type.values().length)];
  // }

  @Override
  public void resize(int width, int height) {
    // If the window is minimized on a desktop (LWJGL3) platform, width and height
    // are 0, which causes problems.
    // In that case, we don't resize anything, and wait for the window to be a
    // normal size before updating.
    if (width <= 0 || height <= 0)
      return;

    // Resize your screen here. The parameters represent the new window size.
  }

  @Override
  public void pause() {
    // Invoked when your application is paused.
  }

  @Override
  public void resume() {
    // Invoked when your application is resumed after pause.
  }

  @Override
  public void hide() {
    // This method is called when another screen replaces this one.
  }

  @Override
  public void dispose() {
    // Destroy screen's assets here.
  }

  // private void equipItem(Entity user, Entity item) {

  //   user.getComponent(InventoryComponent.class).inventory.add(item);

  //   user.add(engine.createComponent(DirtyStatComponent.class));
  //   System.out.println("berhasil equip item");
  // }

  // private void unEquipItem(Entity user, Entity item) {

  //   boolean isUnEquip = user.getComponent(InventoryComponent.class).inventory.removeValue(item, false);

  //   if (isUnEquip) {
  //     System.out.println("berhasil unequip item");
  //     user.add(engine.createComponent(DirtyStatComponent.class));
  //     return;
  //   }
  //   System.out.println("gagal unequip item");
  // }

}