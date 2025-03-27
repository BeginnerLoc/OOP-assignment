package game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import game_engine.Collidable;
import game_engine.Entity;

import java.util.function.Consumer;

public class BananaPeel extends Entity implements Collidable {
    private Rectangle bounds;
    private Texture texture;
    private Consumer<Collidable> collisionAction;
    private int width;
    private int height;
    private float stunDuration = 3.0f; // 3 seconds stun
    
    public BananaPeel(float x, float y, String texturePath, int width, int height) {
        super(x, y, null, 0, 5);
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = new Texture(texturePath);
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
    
    public float getStunDuration() {
        return stunDuration;
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), bounds.width, bounds.height);
    }
    
    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}