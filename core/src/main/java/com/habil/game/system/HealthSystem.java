package com.habil.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.habil.game.component.DamageComponent;
import com.habil.game.component.StatComponent;

public class HealthSystem extends IteratingSystem {
  ComponentMapper<StatComponent> sm = ComponentMapper.getFor(StatComponent.class);
  ComponentMapper<DamageComponent> dm = ComponentMapper.getFor(DamageComponent.class);

  public HealthSystem() {
    super(Family.all(StatComponent.class, DamageComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

    if(isMissed(entity)){
      System.out.println("Serangan missed " + sm.get(entity).name + " tidak menerima damage");
      entity.remove(DamageComponent.class);
      return;
    }

    int totalDamage = (int) Math.floor(sm.get(entity).getDamageReduction() * dm.get(entity).amount);
    System.out.println(sm.get(entity).name + " menerima damage = " + totalDamage);
    if (sm.get(entity).currentHp <= totalDamage) {
      System.out.println(sm.get(entity).name + " Telah Mati");
      entity.removeAll();
      getEngine().removeEntity(entity);
      return;
    }
    
    sm.get(entity).currentHp -= totalDamage;
    entity.remove(DamageComponent.class);

    System.out.println("Sisa HP -> " + sm.get(entity).name + " = " + sm.get(entity).currentHp);
  }

  private boolean isMissed(Entity entity){
    float dodgeChance = sm.get(entity).getDodgeChange();
    if(Math.random() > dodgeChance){
      return false;
    }
    
    return true;
  }

}
