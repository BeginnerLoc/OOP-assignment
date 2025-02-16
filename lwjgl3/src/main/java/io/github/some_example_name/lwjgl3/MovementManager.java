package io.github.some_example_name.lwjgl3;
import java.awt.*;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashMap;

public class MovementManager {
private List<Movable> movingEntities;
private Movable player;
private Map<Movable, Float> speedMap;

public MovementManager(List<Movable> movingEntities) {
	if (movingEntities == null) {
        throw new IllegalArgumentException("Player and movingEntities cannot be null");
    }

    this.movingEntities = movingEntities;
    this.speedMap = new HashMap<>();
}



public float getPlayerSpeed() {
	return speedMap.getOrDefault(player, 0.0f);
}

public void setPlayerSpeed(Movable p, float s) {
	speedMap.put(p, s);
}

public void setEntitySpeed(Movable entity, float speed) {
    speedMap.put(entity, speed);
}

public boolean checkBounds(Entity entity) {
    float x = entity.getX();
    float y = entity.getY();
    int maxWidth = Gdx.graphics.getWidth();
    int maxHeight = Gdx.graphics.getHeight();
    
    return (x >= 0 && x <= maxWidth) && (y >= 0 && y <= maxHeight);
}

public enum Direction {
    UP, DOWN, LEFT, RIGHT;
}


public void moveEntity(Movable entity, float deltaTime, Vector2 direction) {
    
	if (direction == null) return; 
	float speed = speedMap.getOrDefault(entity, 1.0f); // Default speed if not set
    float oldX = entity.getX();
    float oldY = entity.getY();
    
    entity.translate(direction.x * speed * deltaTime, direction.y * speed * deltaTime);

    
    System.out.println(entity + " moved from (" + oldX + "," + oldY + ") to (" + entity.getX() + "," + entity.getY() + ")");

}

public void updatePositions(float deltaTime) {
	for (Movable entity : movingEntities) {
        float speed = speedMap.getOrDefault(entity, 100.0f);
        if (speed != 0) {
            Vector2 direction = new Vector2(-1, 0); // Assuming leftward movement
            entity.translate(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
        }

    }

}

public List<Movable> getMovingEntities() {
    return movingEntities;
}

public void addMovingEntity(Movable entity) {
    movingEntities.add(entity);
}


public void handlePlayerInput(Direction input) {

}
}