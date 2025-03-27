package game.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * Circular movement strategy - orbits around the target while approaching
 */
public class CircularMovementStrategy implements MovementStrategy {
    private float circularAngle = 0;
    private float circularRadius = 100;
    
    @Override
    public Vector2 calculateDirection(float currentX, float currentY, float targetX, float targetY, float deltaTime) {
        // Update orbital angle
        circularAngle += deltaTime * 2; // Control rotation speed
        
        // Calculate position on the orbit
        float orbitX = targetX + circularRadius * (float)Math.cos(circularAngle);
        float orbitY = targetY + circularRadius * (float)Math.sin(circularAngle);
        
        // Direction vector toward the orbit position
        Vector2 direction = new Vector2(orbitX - currentX, orbitY - currentY);
        if (direction.len() > 0) {
            direction.nor();
        }
        
        return direction;
    }
    
    @Override
    public void reset() {
        circularAngle = 0;
    }
    
    /**
     * Set the radius of the circular orbit
     * @param radius The orbit radius
     */
    public void setRadius(float radius) {
        this.circularRadius = radius;
    }
}