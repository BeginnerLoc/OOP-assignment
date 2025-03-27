package game.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * Strategy interface for enemy movement patterns
 */
public interface MovementStrategy {

    Vector2 calculateDirection(float currentX, float currentY, float targetX, float targetY, float deltaTime);
    
    void reset();
}