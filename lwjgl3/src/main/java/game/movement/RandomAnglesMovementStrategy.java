package game.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * Random angles movement strategy - makes unpredictable turns while pursuing target
 */
public class RandomAnglesMovementStrategy implements MovementStrategy {
    private float moveTimer = 0;
    
    @Override
    public Vector2 calculateDirection(float currentX, float currentY, float targetX, float targetY, float deltaTime) {
        Vector2 direction = new Vector2(targetX - currentX, targetY - currentY);
        
        if (direction.len() > 0) {
            direction.nor();
            
            // Change direction every 2 seconds
            moveTimer += deltaTime;
            if (moveTimer > 2.0f) {
                // Rotate by random angle between -45 and 45 degrees
                direction.rotateDeg((float)(Math.random() * 90 - 45));
                moveTimer = 0;
            }
        }
        
        return direction;
    }
    
    @Override
    public void reset() {
        moveTimer = 0;
    }
}