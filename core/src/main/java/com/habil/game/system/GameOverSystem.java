package com.habil.game.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.habil.game.component.flag.PlayerComponent;

public class GameOverSystem extends EntitySystem{
  private ImmutableArray<Entity> player;

  @Override
  public void addedToEngine(Engine engine) {
    player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
  }

  @Override
  public void update(float deltaTime) {
    if(player.size() == 0){
      System.out.println("Game Over Player Mati");
      getEngine().removeSystem(this);
    }
  }
  
}
