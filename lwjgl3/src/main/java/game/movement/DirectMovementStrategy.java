package game.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * Direct movement strategy - moves directly toward the target
 */
public class DirectMovementStrategy implements MovementStrategy {
    
    @Override
    public Vector2 calculateDirection(float currentX, float currentY, float targetX, float targetY, float deltaTime) {
        Vector2 direction = new Vector2(targetX - currentX, targetY - currentY);
        
        if (direction.len() > 0) {
            direction.nor();
        }
        
        return direction;
    }
    
    @Override
    public void reset() {
        // No state to reset
    }
}
