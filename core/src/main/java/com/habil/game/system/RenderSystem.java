package com.habil.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.habil.game.component.CoordinateComponent;
import com.habil.game.component.RenderComponent;
import com.habil.game.entity.EntityFactory;
import com.habil.game.map.TileType;
import com.habil.game.map.WorldConfig;
import com.habil.game.map.WorldGenerator;
import com.habil.game.map.WorldMap;

public class RenderSystem extends EntitySystem {

  SpriteBatch batch;
  BitmapFont font;

  ComponentMapper<CoordinateComponent> cm = ComponentMapper.getFor(CoordinateComponent.class);
  ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);

  ImmutableArray<Entity> entities;

  private WorldMap map;
  private WorldGenerator worldGenerator;
  private EntityFactory factory;

  public RenderSystem(WorldMap map, EntityFactory factory) {
    batch = new SpriteBatch();
    font = new BitmapFont();
    font.setUseIntegerPositions(true);
    this.map = map;
    worldGenerator = new WorldGenerator(map.getSeed());
    map = worldGenerator.generate(map);
    this.factory = factory;
  }

  @Override
  public void addedToEngine(Engine engine) {
    entities = engine.getEntitiesFor(
        Family.all(CoordinateComponent.class, RenderComponent.class).get());
    worldGenerator.spawnMonster(map, engine, factory, 2);
  }

  @Override
  public void update(float deltaTime) {
    batch.begin();
    WorldMapRender();
    RenderEntity();
    batch.end();
  }

  private void WorldMapRender() {
    for (int x = 0; x < map.getWidth(); x++) {
      for (int y = 0; y < map.getHeight(); y++) {
        TileType tile = map.get(x, y);

        if (tile == null)
          continue;

        font.setColor(tile.color);
        font.draw(batch, String.valueOf(tile.glyph), x * WorldConfig.TILE_W,
            (map.getHeight() - y) * WorldConfig.TILE_H);
      }
    }
  }

  private void RenderEntity() {
    for (Entity e : entities) {
      CoordinateComponent coordinate = cm.get(e);
      RenderComponent render = rm.get(e);

      font.setColor(render.glyph.color);

      font.draw(
          batch,
          String.valueOf(render.glyph.glyph),
          coordinate.x * WorldConfig.TILE_W,
          (map.getHeight() - coordinate.y) * WorldConfig.TILE_H);
    }
  }

  @Override
  public void removedFromEngine(Engine engine) {
    batch.dispose();
    font.dispose();
  }
}
