package game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import game_engine.BackgroundRenderer;
import game_engine.Collidable;
import game_engine.Entity;
import game_engine.PlayerMovable;

import java.util.function.Consumer;

public class Player extends Entity implements PlayerMovable, Collidable {
    private float dx;
    private float dy;
    private boolean isSprinting;
    private Consumer<Collidable> collisionAction;
    private Rectangle bounds;
    private Texture texture; 
    private Trash heldTrash;
    private Trash droppedTrash;
    private int health = 3;
    private String texturePath; 
    
    private float width;
    private float height;

    public Player(float x, float y, String texturePath, float speed) {
        super(x, y, null, speed, 10); 
        this.width = 36;
        this.height = 36;
        // Make the collision bounds smaller than the sprite
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f); 
        this.texturePath = texturePath;
        this.texture = new Texture(texturePath); 
    }
    
    public Player(float x, float y, String texturePath, float speed, float width, float height) {
        super(x, y, null, speed, 10); 
        
        this.width = width;
        this.height = height;
        // Make the collision bounds smaller than the sprite
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f);
        this.texture = new Texture(texturePath);
    }

    public void applyGravity(float gravity) {
        dy += gravity;
        move();
    }

    public void setVelocity(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    public void setDimensions(float width, float height) {
        this.width = width;
        this.height = height;
        this.bounds.setSize(width, height); 
    }

    @Override
    public void move() {
        // Use virtual viewport dimensions for bounds
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;

        // Calculate new position based on velocity and speed
        float newX = getX() + dx * getSpeed();
        float newY = getY() + dy * getSpeed();
        
        // Clamp position within bounds
        newX = Math.max(0, Math.min(newX, virtualWidth - width));
        newY = Math.max(0, Math.min(newY, virtualHeight - height));

        // Update position and bounds
        setX(newX);
        setY(newY);
        bounds.setPosition(newX + width * 0.25f, newY + height * 0.25f);

        // Update held trash position if we have any
        updateHeldTrashPosition();
    }
    
    // Update held trash positioning
    private void updateHeldTrashPosition() {
        if (heldTrash != null) {
            // Position trash relative to player
            float playerCenterX = getX() + width / 2;
            float playerCenterY = getY() + height / 2;
            
            // Position slightly above player's center
            heldTrash.setX(playerCenterX - 16);
            heldTrash.setY(playerCenterY + 10);
        }
    }

    @Override
    public void setDirection(float dx, float dy) {
        // Set velocity instead of directly updating position
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void stop() {
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void onCollision(Collidable other) {
        if (collisionAction != null) {
            collisionAction.accept(other);
        }
    }

    @Override
    public void setCollisionAction(Consumer<Collidable> action) {
        this.collisionAction = action;
    }

    @Override
    public void jump(int height) {
        this.dy = height; // Apply vertical velocity instead of setting position directly
    }

    @Override
    public void sprint(boolean isSprinting) {
        this.isSprinting = isSprinting;
        setSpeed(isSprinting ? 8.0f : 4.0f);
    }

    @Override
    public void dash() {
        setX(getX() + dx * 20);
        setY(getY() + dy * 20);
        bounds.setPosition(getX(), getY());
    }

    public void pickupTrash(Trash trash) {
        if (heldTrash == null) {
            heldTrash = trash;
            // Ensure the trash's position is updated immediately
            updateHeldTrashPosition();
        }
    }
    
    public Trash droppedTrash() {
    	return droppedTrash;
    }

    public void dropTrash() {
        if (heldTrash != null) {
            // Place trash a bit in front of the player
            float playerCenterX = getX() + width / 2;
            float playerCenterY = getY() + height / 2;
            
            // Position the dropped trash
            heldTrash.setX(playerCenterX + 50);
            heldTrash.setY(playerCenterY);
            heldTrash.setPickedUp(false);
            droppedTrash = heldTrash;
            heldTrash = null;
        }
    }

    public Trash getHeldTrash() {
        return heldTrash;
    }

    public void restoreHealth(int amount) {
        health += amount;
        if (health > 3) {
            health = 3;
        }
    }

    public int getHealth() {
        return health;
    }

    /**
     * Changes the player's texture to a new one
     * @param newTexturePath Path to the new texture
     */
    public void changeTexture(String newTexturePath) {
        // Dispose of the old texture to avoid memory leaks
        if (texture != null) {
            texture.dispose();
        }
        
        // Load and apply the new texture
        texture = new Texture(newTexturePath);
        this.texturePath = newTexturePath;
    }
    
    /**
     * Gets the current texture path
     * @return The path of the current texture
     */
    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), width, height);
    }

    @Override 
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
