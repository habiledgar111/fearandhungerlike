package com.habil.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.habil.game.component.CoordinateComponent;
import com.habil.game.component.flag.EnemyComponent;
import com.habil.game.component.flag.JoinBattleComponent;
import com.habil.game.component.flag.PlayerComponent;

public class EngageSystem extends EntitySystem {
  private ImmutableArray<Entity> player;
  private ImmutableArray<Entity> enemies;
  private ComponentMapper<CoordinateComponent> cm = ComponentMapper.getFor(CoordinateComponent.class);

  public EngageSystem() {
  }

  @Override
  public void addedToEngine(Engine engine) {
    player = engine.getEntitiesFor(Family.all(PlayerComponent.class, CoordinateComponent.class).get());
    enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class, CoordinateComponent.class).get());
  }

  @Override
  public void update(float deltaTime) {
    if(player.size() <= 0)return;

    CoordinateComponent coorPlayer = cm.get(player.first());

    for(Entity enemy : enemies){
      CoordinateComponent coorEnemy = cm.get(enemy);

      if(coorPlayer.x == coorEnemy.x && coorEnemy.y == coorPlayer.y){
        if(enemy.getComponent(JoinBattleComponent.class) == null){
          enemy.add(getEngine().createComponent(JoinBattleComponent.class));
        }
      }
    }

    if(player.first().getComponent(JoinBattleComponent.class) == null){
      player.first().add(getEngine().createComponent(JoinBattleComponent.class));
    }
  }

}
