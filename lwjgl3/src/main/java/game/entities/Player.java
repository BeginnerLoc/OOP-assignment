package game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

import game_engine.BackgroundRenderer;
import game_engine.Collidable;
import game_engine.Entity;
import game_engine.PlayerMovable;
import game.utils.GameState;

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
    private float animationTimer = 0;
    private float bobHeight = 2f; // How much the character bobs up and down
    private boolean isMoving = false;

    private Texture idleTexture;
    private Texture[] runningTextures;
    private static final float ANIMATION_FRAME_DURATION = 0.1f; // Time to show each frame
    private static final int NUM_RUNNING_FRAMES = 2;
    
    public Player(float x, float y, String texturePath, float speed) {
        super(x, y, null, speed, 10); 
        this.width = 36;
        this.height = 36;
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f); 
        this.texturePath = texturePath;
        loadTextures();
        this.texture = idleTexture;
    }
    
    public Player(float x, float y, String texturePath, float speed, float width, float height) {
        super(x, y, null, speed, 10); 
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x + width * 0.25f, y + height * 0.25f, width * 0.5f, height * 0.5f);
        this.texturePath = texturePath;
        loadTextures();
        this.texture = idleTexture;
    }

    private void loadTextures() {
        idleTexture = new Texture("mr_bean.png");
        runningTextures = new Texture[NUM_RUNNING_FRAMES];
        runningTextures[0] = new Texture("running_1.png");
        runningTextures[1] = new Texture("running_2.png");
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
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;

        // Update animation timer when moving
        if (dx != 0 || dy != 0) {
            isMoving = true;
            float deltaTime = Gdx.graphics.getDeltaTime();
            animationTimer += deltaTime * (isSprinting ? 1.5f : 1.0f); // Faster animation when sprinting
            
            // Calculate bob effect
            float verticalOffset = (float) Math.abs(Math.sin(animationTimer * 10)) * bobHeight;
            
            // Calculate new position with bob effect when moving
            float newX = getX() + dx * getSpeed();
            float newY = getY() + dy * getSpeed() + (isMoving ? verticalOffset : 0);
            
            // Clamp position within bounds
            newX = Math.max(0, Math.min(newX, virtualWidth - width));
            newY = Math.max(0, Math.min(newY, virtualHeight - height));

            // Update position and bounds
            setX(newX);
            setY(newY - verticalOffset); // Subtract offset to keep base position consistent
            bounds.setPosition(newX + width * 0.25f, newY + height * 0.25f);
        } else {
            isMoving = false;
            animationTimer = 0;
        }

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
        setSpeed(isSprinting ? GameState.getPlayerSpeed() * 2.0f : GameState.getPlayerSpeed());
        bobHeight = isSprinting ? 4f : 2f; // Increase bob height when sprinting
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
        // Dispose of the old textures to avoid memory leaks
        if (idleTexture != null) {
            idleTexture.dispose();
        }
        if (runningTextures != null) {
            for (Texture tex : runningTextures) {
                if (tex != null) {
                    tex.dispose();
                }
            }
        }
        
        // If it's a special texture (like car or slow), use it for all states
        if (newTexturePath.contains("car") || newTexturePath.contains("slow")) {
            idleTexture = new Texture(newTexturePath);
            runningTextures = new Texture[NUM_RUNNING_FRAMES];
            for (int i = 0; i < NUM_RUNNING_FRAMES; i++) {
                runningTextures[i] = new Texture(newTexturePath);
            }
        } else {
            // Otherwise load the normal animation textures
            idleTexture = new Texture(newTexturePath);
            runningTextures[0] = new Texture("running_1.png");
            runningTextures[1] = new Texture("running_2.png");
        }
        texture = idleTexture;
        this.texturePath = newTexturePath;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (idleTexture != null && runningTextures != null) {
            // Apply slight rotation based on movement, more pronounced when sprinting
            float tilt = 0;
            if (isMoving) {
                float tiltAmount = isSprinting ? 8f : 5f;
                tilt = (float) Math.sin(animationTimer * 10) * tiltAmount;
                
                // Calculate which frame of the running animation to show
                int frameIndex = (int)((animationTimer / ANIMATION_FRAME_DURATION) % NUM_RUNNING_FRAMES);
                texture = runningTextures[frameIndex];
            } else {
                texture = idleTexture;
            }
            
            // Draw with rotation around center
            batch.draw(texture, 
                getX(), getY(),
                width/2, height/2,
                width, height,
                1, 1,
                tilt,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false);
        }
    }

    @Override 
    public void dispose() {
        if (idleTexture != null) {
            idleTexture.dispose();
        }
        if (runningTextures != null) {
            for (Texture tex : runningTextures) {
                if (tex != null) {
                    tex.dispose();
                }
            }
        }
    }
}
