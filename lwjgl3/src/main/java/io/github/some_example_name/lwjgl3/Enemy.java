package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class Enemy extends Entity implements AIMovable, Collidable {
    private Entity target;
    private Rectangle bounds;
    private float dx = 0;
    private float dy = 0;
    private Consumer<Collidable> collisionAction;
    private boolean isStunned = false;
    private Texture texture;
    private float width;
    private float height;
    private boolean isActive = true;
    private EnemyMovePattern movePattern;
    private float moveTimer = 0;
    private float circularAngle = 0;
    private boolean zigzagDirection = true;
    private float circularRadius = 100; // Radius for circular movement

    public Enemy(float x, float y, String texturePath, float speed, EnemyMovePattern pattern) {
        super(x, y, null, speed, 10);
        this.width = 36;
        this.height = 36;
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f);
        this.texture = new Texture(texturePath);
        this.movePattern = pattern;
    }
    
    public Enemy(float x, float y, String texturePath, float speed, float width, float height, EnemyMovePattern pattern) {
        super(x, y, null, speed, 10);
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f);
        this.texture = new Texture(texturePath);
        this.movePattern = pattern;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void followEntity() {
        if (!isActive || target == null || isStunned) return;
            
        Vector2 direction = new Vector2(target.getX() - getX(), target.getY() - getY());
        float distanceToTarget = direction.len();

        if (distanceToTarget > 3) {
            direction.nor();
            
            moveTimer += Gdx.graphics.getDeltaTime();
            
            switch (movePattern) {
                case DIRECT:
                    // Simply move directly towards the target
                    break;
                    
                case ZIGZAG:
                    // Change direction every 1 second
                    if (moveTimer > 1.0f) {
                        zigzagDirection = !zigzagDirection;
                        moveTimer = 0;
                    }
                    direction.rotateDeg(zigzagDirection ? 45 : -45);
                    break;
                    
                case FLANKING:
                    // Try to approach from the side
                    Vector2 perpendicular = new Vector2(-direction.y, direction.x);
                    if (distanceToTarget > 100) {
                        direction.scl(0.7f).add(perpendicular.scl(0.3f));
                        direction.nor();
                    }
                    break;
                    
                case CIRCULAR:
                    // Calculate orbital movement
                    circularAngle += Gdx.graphics.getDeltaTime() * 2; // Control rotation speed
                    float orbitX = target.getX() + circularRadius * (float)Math.cos(circularAngle);
                    float orbitY = target.getY() + circularRadius * (float)Math.sin(circularAngle);
                    direction.set(orbitX - getX(), orbitY - getY()).nor();
                    break;
                    
                case RANDOM_ANGLES:
                    // Change direction every 2 seconds
                    if (moveTimer > 2.0f) {
                        direction.rotateDeg((float)(Math.random() * 90 - 45));
                        moveTimer = 0;
                    }
                    break;
            }
            
            setDirection(direction.x, direction.y);
        } else {
            stop();
        }
    }

    @Override
    public void move() {
        if (!isActive || isStunned) return;
        
        // Use virtual viewport dimensions instead of raw screen dimensions
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;

        // Calculate new position
        float newX = getX() + dx * getSpeed();
        float newY = getY() + dy * getSpeed();
        
        // Ensure Enemy stays within virtual viewport bounds
        newX = Math.max(0, Math.min(newX, virtualWidth - width));
        newY = Math.max(0, Math.min(newY, virtualHeight - height));

        // Update position
        setX(newX);
        setY(newY);

        // Update bounds position with offset
        bounds.setSize(width * 0.5f, height * 0.5f);
        bounds.setPosition(getX() + width * 0.25f, getY() + height * 0.25f);
    }

    @Override
    public void setDirection(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void stop() {
        this.dx = 0;
        this.dy = 0;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void onCollision(Collidable other) {
        if (!isActive) return;

        if (other instanceof Enemy) {
            Enemy otherEnemy = (Enemy) other;
            if (!otherEnemy.isActive()) return;

            // Calculate the vector between the centers of the enemies
            float centerX = getX() + width / 2;
            float centerY = getY() + height / 2;
            float otherCenterX = otherEnemy.getX() + otherEnemy.width / 2;
            float otherCenterY = otherEnemy.getY() + otherEnemy.height / 2;

            float dx = centerX - otherCenterX;
            float dy = centerY - otherCenterY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            // If enemies are overlapping
            if (distance < (width + otherEnemy.width) / 2) {
                // Minimum separation distance
                float minDistance = (width + otherEnemy.width) / 2;
                
                // If centers are too close, choose a default direction
                if (distance < 0.1f) {
                    dx = 1;
                    dy = 0;
                    distance = 1;
                }

                // Calculate separation vector
                float separationX = (dx / distance) * (minDistance - distance) * 0.5f;
                float separationY = (dy / distance) * (minDistance - distance) * 0.5f;

                // Move both enemies apart slightly
                setX(getX() + separationX);
                setY(getY() + separationY);
                otherEnemy.setX(otherEnemy.getX() - separationX);
                otherEnemy.setY(otherEnemy.getY() - separationY);
            }
        }
        
        if (collisionAction != null) {
            collisionAction.accept(other);
        }
    }

    @Override
    public void setCollisionAction(Consumer<Collidable> action) {
        this.collisionAction = action;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setStunned(boolean stunned) {
        this.isStunned = stunned;
        if (stunned) {
            stop();
        }
    }

    public boolean isStunned() {
        return isStunned;
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (!isActive) return;
        batch.draw(texture, getX(), getY(), width, height);
    }
    
    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
