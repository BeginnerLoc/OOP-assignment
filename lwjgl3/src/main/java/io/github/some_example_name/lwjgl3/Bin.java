package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import game_engine.Collidable;
import game_engine.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bin extends Entity implements Collidable {
	private String type; // "plastic", "metal", "paper", "general"
    private Rectangle bounds;
    private Texture texture;

    public Bin(float x, float y, String type, String texturePath) {
        super(x, y, Color.GRAY, 0, 5);
        this.type = type;
        this.texture = new Texture(texturePath);
        this.bounds = new Rectangle(x, y, 100, 100); // Larger for collision detection
    }

    public String getType() {
        return type;
    }

    @Override
    public Rectangle getBounds() {
        bounds.setPosition(getX(), getY());
        return bounds;
    }

    @Override
    public void onCollision(Collidable other) {
        // Bins don't need to react to collisions
    	if (other instanceof Player) {
            System.out.println("Collided with " + type + " bin");
        }
    }

    @Override
    public void setCollisionAction(java.util.function.Consumer<Collidable> action) {
        // Not needed
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), bounds.width, bounds.height);
    }

}
