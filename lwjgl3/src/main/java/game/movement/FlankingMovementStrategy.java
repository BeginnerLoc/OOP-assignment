package game.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * Flanking movement strategy - approaches target from the side
 */
public class FlankingMovementStrategy implements MovementStrategy {
    
    @Override
    public Vector2 calculateDirection(float currentX, float currentY, float targetX, float targetY, float deltaTime) {
        Vector2 direction = new Vector2(targetX - currentX, targetY - currentY);
        float distanceToTarget = direction.len();
        
        if (distanceToTarget > 0) {
            direction.nor();
            
            // Try to approach from the side when at a distance
            if (distanceToTarget > 100) {
                // Create perpendicular vector (90 degrees to direct path)
                Vector2 perpendicular = new Vector2(-direction.y, direction.x);
                
                // Mix direct and perpendicular vectors (70% direct, 30% side)
                direction.scl(0.7f).add(perpendicular.scl(0.3f));
                direction.nor();
            }
        }
        
        return direction;
    }
    
    @Override
    public void reset() {
        // No state to reset
    }
}