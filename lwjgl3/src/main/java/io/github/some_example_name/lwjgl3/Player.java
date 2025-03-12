package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.function.Consumer;

public class Player extends Entity implements PlayerMovable, Collidable {
    private float dx;
    private float dy;
    private boolean isSprinting;
    private Consumer<Collidable> collisionAction;
    private Rectangle bounds;
    private Texture texture; // Player's texture
    
    private float width;
    private float height;


    public Player(float x, float y, String texturePath, float speed) {
        super(x, y, null, speed); // No need for color when using a texture
        this.bounds = new Rectangle(x, y, 64, 64); // Player size
        this.texture = new Texture(texturePath); // Load texture dynamically
    }
    
    public Player(float x, float y, String texturePath, float speed, float width, float height) {
        super(x, y, null, speed); // No need for color when using a texture
        
        this.width = width;
        this.height = height;
        
        this.bounds = new Rectangle(x, y, width, height); // Player size
        this.texture = new Texture(texturePath); // Load texture dynamically
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
        this.bounds.setSize(width, height); // Update the rectangle size
    }


    @Override
    public void move() {
        float screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        float screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        // Update position
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());

        // Ensure Player stays within screen bounds
        float clampedX = Math.max(0, Math.min(getX(), screenWidth - bounds.width));
        float clampedY = Math.max(0, Math.min(getY(), screenHeight - bounds.height));

        setX(clampedX);
        setY(clampedY);

        // Update bounds position
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

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), width, height);
    }

}
