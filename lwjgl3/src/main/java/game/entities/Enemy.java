package game.entities;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import game.movement.MovementStrategy;
import game.movement.MovementStrategyFactory;
import game.utils.EnemyMovePattern;
import game_engine.AIMovable;
import game_engine.BackgroundRenderer;
import game_engine.Collidable;
import game_engine.Entity;

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
    private MovementStrategy movementStrategy;

    public Enemy(float x, float y, String texturePath, float speed, EnemyMovePattern pattern) {
        super(x, y, null, speed, 10);
        this.width = 36;
        this.height = 36;
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f);
        this.texture = new Texture(texturePath);
        this.movementStrategy = MovementStrategyFactory.createStrategy(pattern);
    }
    
    public Enemy(float x, float y, String texturePath, float speed, float width, float height, EnemyMovePattern pattern) {
        super(x, y, null, speed, 10);
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f);
        this.texture = new Texture(texturePath);
        this.movementStrategy = MovementStrategyFactory.createStrategy(pattern);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void followEntity() {
        if (!isActive || target == null || isStunned) return;
        
        float distanceToTarget = new Vector2(target.getX() - getX(), target.getY() - getY()).len();
        
        if (distanceToTarget > 3) {
            // Use the strategy to calculate the movement direction
            Vector2 direction = movementStrategy.calculateDirection(
                getX(), getY(), 
                target.getX(), target.getY(), 
                Gdx.graphics.getDeltaTime()
            );
            
            setDirection(direction.x, direction.y);
        } else {
            stop();
        }
    }


    public void setMovementPattern(EnemyMovePattern pattern) {
        this.movementStrategy = MovementStrategyFactory.createStrategy(pattern);
    }
    

    public void setMovementStrategy(MovementStrategy strategy) {
        if (strategy != null) {
            this.movementStrategy = strategy;
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
