package com.habil.game.system;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.habil.game.component.DamageComponent;
import com.habil.game.component.StatComponent;
import com.habil.game.component.flag.EnemyComponent;
import com.habil.game.component.flag.JoinBattleComponent;

public class BattleSystem extends EntitySystem {
  private Entity player;
  private ImmutableArray<Entity> enemies;
  private ComponentMapper<StatComponent> sm = ComponentMapper.getFor(StatComponent.class);
  private int nowTurn = 0;

public BattleSystem(Entity player){
  this.player = player;
}

  @Override
  public void addedToEngine(Engine engine) {
    enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class, JoinBattleComponent.class).get());
  }

  @Override
  public Engine getEngine() {
    // TODO Auto-generated method stub
    return super.getEngine();
  }

  @Override
  public void update(float deltaTime) {
    if(enemies.size() == 0) return;
    battle();
  }
  

  private void battle(){
    
    if(player == null || sm.get(player) == null)return;
    
    ArrayList<Entity> turnOrder = new ArrayList<>();
    turnOrder.add(player);
    for(Entity e : enemies){
      turnOrder.add(e);
    }

    if(enemies.size() == 0 || turnOrder.size() <= 1){
      player.remove(JoinBattleComponent.class);
      return;
    }

    if(nowTurn >= turnOrder.size())nowTurn = 0;

    turnOrder.sort((a,b) -> (sm.get(b).agi + sm.get(b).bonusAgi) - (sm.get(a).agi + sm.get(a).bonusAgi));

    if(turnOrder.get(nowTurn) != player){
      attack(turnOrder.get(nowTurn), player);
    }else{
      attack(player, enemies.first());
    }

    nowTurn++;
  }

  private void attack(Entity attacker, Entity receiver){
    DamageComponent dmg = getEngine().createComponent(DamageComponent.class);
    dmg.amount = sm.get(attacker).getBaseDamage();
    receiver.add(dmg);
    System.out.println(sm.get(attacker).name + " Menyerang "+sm.get(receiver).name + " dengan total " + sm.get(attacker).getBaseDamage());
  }
}
