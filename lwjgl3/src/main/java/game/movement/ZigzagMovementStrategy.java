package game.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * ZigZag movement strategy - alternates left and right while moving toward target
 */
public class ZigzagMovementStrategy implements MovementStrategy {
    private boolean zigzagDirection = true;
    private float moveTimer = 0;
    
    @Override
    public Vector2 calculateDirection(float currentX, float currentY, float targetX, float targetY, float deltaTime) {
        Vector2 direction = new Vector2(targetX - currentX, targetY - currentY);
        
        if (direction.len() > 0) {
            direction.nor();
            
            // Change direction every 1 second
            moveTimer += deltaTime;
            if (moveTimer > 1.0f) {
                zigzagDirection = !zigzagDirection;
                moveTimer = 0;
            }
            
            // Rotate 45 degrees in alternating directions
            direction.rotateDeg(zigzagDirection ? 45 : -45);
        }
        
        return direction;
    }
    
    @Override
    public void reset() {
        moveTimer = 0;
        zigzagDirection = true;
    }
}