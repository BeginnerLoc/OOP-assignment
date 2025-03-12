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
    
    private Texture texture; // Player's texture
    private float width;
    private float height;


    public Enemy(float x, float y, String texturePath, float speed) {
    	super(x, y, null, speed); // No need for color when using a texture
    	this.bounds = new Rectangle(x, y, 64, 64); // Player size
        this.texture = new Texture(texturePath); // Load texture dynamically
    }
    
    
    public Enemy(float x, float y, String texturePath, float speed, float width, float height) {
        super(x, y, null, speed); // No need for color when using a texture
        
        this.width = width;
        this.height = height;
        
        this.bounds = new Rectangle(x, y, width, height); // Player size
        this.texture = new Texture(texturePath); // Load texture dynamically
    }

    /** Set a target entity to follow (e.g., the Player) */
    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void followEntity() {
    	    	
        if (target != null) {
            Vector2 direction = new Vector2(target.getX() - getX(), target.getY() - getY());

            if (direction.len() > 10) {
                direction.nor(); 
                setDirection(direction.x, direction.y);
            } else {
                stop();
            }
        }
    }

    @Override
    public void move() {
        float screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        float screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        // Update position
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());

        // Ensure Enemy stays within the screen bounds
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

//    @Override
//    public void draw(ShapeRenderer rd) {
//        rd.setColor(this.getColor());
//        rd.rect(this.getX(), this.getY(), this.bounds.width, this.bounds.height);
//    }

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
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), width, height);
    }

}
