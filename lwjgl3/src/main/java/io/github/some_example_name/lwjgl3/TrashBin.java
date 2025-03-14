package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TrashBin extends Entity implements Collidable {
    private Trash.TrashType acceptedType;
    private Rectangle bounds;
    private Texture texture;

    public TrashBin(float x, float y, Trash.TrashType type, String texturePath) {
        super(x, y, null, 0);
        this.acceptedType = type;
        this.bounds = new Rectangle(x, y, 64, 64);
        this.texture = new Texture(texturePath);
    }

    public Trash.TrashType getAcceptedType() {
        return acceptedType;
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
        // Not needed for trash bins
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), bounds.width, bounds.height);
    }
}
