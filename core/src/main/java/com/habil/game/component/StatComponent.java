package com.habil.game.component;

import com.badlogic.ashley.core.Component;

public class StatComponent implements Component {

  /*
   * str digunakan untuk dmg raw psychal
   * agi untuk chace menghindar, turn order
   * vit digunakan untuk calculasi tambahan max hp dan armor tambahan
   * int digunakan untuk dmg magic dan calculation max mind
   * luck untuk loot drop?? dan crit change
   * 
   * max hp ya maxhp
   * maxmind ya max mind untuk mengukur player ketahannya untuk gila
   */
  public String name;
  public Integer str, bonusStr = 0;
  public Integer agi, bonusAgi = 0;
  public Integer vit, bonusVit = 0;
  public Integer intel, bonusItel = 0;
  public Integer luck, bonusLuck = 0;

  public Integer maxHp, bonusMaxHp = 0;
  public Integer currentHp = 0;
  public Integer maxMind = 0;
  public Integer currentMind = 0;
  public Integer baseArmor, bonusArmor = 0;

  public boolean isDirty = true;

  public float getDamageReduction() {
    return (float) (0.06 * (vit + bonusVit + baseArmor + bonusArmor)
        / (1 + 0.06f * Math.abs(vit + bonusVit + baseArmor + bonusArmor)));
  }

  public int getMaxHp() {
    return 40 + ((vit + bonusVit) * 5);
  }

  public int getBaseDamage() {
    return (int) Math.floor((bonusStr + str) * 0.7);
  }

  public int getBaseMagicDamage() {
    return (int) Math.floor((intel + bonusItel) * 0.9);
  }

  public float getDodgeChange() {
    return (float) (agi + bonusAgi) / (agi + bonusAgi + 30);
  }

  public float getLuchChange() {
    return (float) (luck + bonusLuck) / (luck + bonusLuck + 30);
  }

  @Override
  public String toString() {
    return "StatComponent [name=" + name + ", str=" + str + ", bonusStr=" + bonusStr + ", agi=" + agi + ", bonusAgi="
        + bonusAgi + ", vit=" + vit + ", bonusVit=" + bonusVit + ", intel=" + intel + ", bonusItel=" + bonusItel
        + ", luck=" + luck + ", bonusLuck=" + bonusLuck + ", maxHp=" + maxHp + ", bonusMaxHp=" + bonusMaxHp
        + ", currentHp=" + currentHp + ", maxMind=" + maxMind + ", currentMind=" + currentMind + ", baseArmor="
        + baseArmor + ", bonusArmor=" + bonusArmor + "]";
  }
}
