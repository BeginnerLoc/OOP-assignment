package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Trash extends Entity implements Collidable {
    private TrashType type;
    private Rectangle bounds;
    private Texture texture;
    private boolean isPickedUp = false;

    public enum TrashType {
        PLASTIC,
        PAPER,
        METAL,
        GLASS
    }

    public Trash(float x, float y, TrashType type, String texturePath) {
        super(x, y, null, 0);
        this.type = type;
        this.bounds = new Rectangle(x, y, 32, 32);
        this.texture = new Texture(texturePath);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void onCollision(Collidable other) {
        // Collision handling will be managed by the game scene
    }

    @Override
    public void setCollisionAction(java.util.function.Consumer<Collidable> action) {
        // Not needed for trash
    }

    public TrashType getType() {
        return type;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isPickedUp) {
            batch.draw(texture, getX(), getY(), bounds.width, bounds.height);
        }
    }
}
