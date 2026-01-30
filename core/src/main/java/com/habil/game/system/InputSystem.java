package com.habil.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.habil.game.component.CoordinateComponent;
import com.habil.game.component.flag.PlayerComponent;
import com.habil.game.map.WorldMap;

public class InputSystem extends IteratingSystem implements InputProcessor {
  private ComponentMapper<CoordinateComponent> cm = ComponentMapper.getFor(CoordinateComponent.class);
  private WorldMap map;
  private ImmutableArray<Entity> player;

  public InputSystem(WorldMap map) {
    super(Family.all(CoordinateComponent.class, PlayerComponent.class).get());
    this.map = map;
  }

  @Override
  public boolean keyDown(int keycode) {
    if(player.size() == 0)return false;
    CoordinateComponent coorPlayer = cm.get(player.first());
    if (keycode == Input.Keys.UP)
      if (tryMove(0, -1, coorPlayer))
        coorPlayer.y--;
    if (keycode == Input.Keys.DOWN)
      if (tryMove(0, 1, coorPlayer))
        coorPlayer.y++;
    if (keycode == Input.Keys.LEFT)
      if (tryMove(-1, 0, coorPlayer))
        coorPlayer.x--;
    if (keycode == Input.Keys.RIGHT)
      if (tryMove(1, 0, coorPlayer))
        coorPlayer.x++;

    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

  }

  private boolean tryMove(int x, int y, CoordinateComponent coorPlayer) {
    int targetX;
    int targetY;

    if (x == coorPlayer.x)
      targetX = x;
    else
      targetX = coorPlayer.x + x;
    if (y == coorPlayer.y)
      targetY = y;
    else
      targetY = coorPlayer.y + y;

    if (targetX < 0 || targetX >= map.getWidth() || targetY < 0 || targetY >= map.getHeight())
      return false;

    return map.get(targetX, targetY).walkable;
  }

  @Override
  public void addedToEngine(Engine engine) {
    super.addedToEngine(engine);

    player = engine.getEntitiesFor(Family.all(PlayerComponent.class, CoordinateComponent.class).get());
  }
}
