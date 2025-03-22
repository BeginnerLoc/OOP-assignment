package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Entity implements AIMovable, Collidable {

    private Entity target;
    private Rectangle bounds;
    private float dx = 0;
    private float dy = 0;
    private Consumer<Collidable> collisionAction;
    
    private Texture texture;
    private float width;
    private float height;
    private boolean isActive = true;

    public Enemy(float x, float y, String texturePath, float speed) {
    	super(x, y, null, speed);
    	this.width = 64;
    	this.height = 64;
    	this.bounds = new Rectangle(x, y, width, height);
        this.texture = new Texture(texturePath);
    }
    
    public Enemy(float x, float y, String texturePath, float speed, float width, float height) {
        super(x, y, null, speed);
        
        this.width = width;
        this.height = height;
        
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = new Texture(texturePath);
    }

    /** Set a target entity to follow (e.g., the Player) */
    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void followEntity() {
        if (!isActive || target == null) return;
            
        Vector2 direction = new Vector2(target.getX() - getX(), target.getY() - getY());

        if (direction.len() > 10) {
            direction.nor(); 
            setDirection(direction.x, direction.y);
        } else {
            stop();
        }
    }

    @Override
    public void move() {
        if (!isActive) return;
        
        float screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        float screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        // Update position
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());
        
        // Ensure Enemy stays within the screen bounds
        float clampedX = Math.max(0, Math.min(getX(), screenWidth - width));
        float clampedY = Math.max(0, Math.min(getY(), screenHeight - height));

        setX(clampedX);
        setY(clampedY);

        // Update bounds position
        bounds.setSize(width, height);
        bounds.setPosition(getX(), getY());
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
        if (collisionAction != null && isActive) {
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
