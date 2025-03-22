package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Trash extends Entity implements Collidable {
    private TrashType type;
    private Rectangle bounds;
    private Texture texture;
    private boolean isPickedUp = false;
    private float width = 32;
    private float height = 32;

    public enum TrashType {
        PLASTIC,
        PAPER,
        METAL,
        GLASS
    }

    public Trash(float x, float y, TrashType type, String texturePath) {
        super(x, y, null, 0);
        this.type = type;
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = new Texture(Gdx.files.internal(texturePath));
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        // Update bounds position when Y changes
        if (bounds != null) {
            bounds.setPosition(getX(), getY());
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        // Update bounds position when X changes
        if (bounds != null) {
            bounds.setPosition(getX(), getY());
        }
    }

    @Override
    public Rectangle getBounds() {
        if (bounds != null) {
            bounds.setSize(width, height);
        }
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
        this.isPickedUp = pickedUp;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isPickedUp) {
            batch.draw(texture, getX(), getY(), width, height);
        }
    }

    public Texture getImage() {
        return texture;
    }
    
    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
