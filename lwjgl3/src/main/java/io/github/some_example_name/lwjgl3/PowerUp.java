package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.function.Consumer;

public class PowerUp extends Entity implements Collidable {
    private Rectangle bounds;
    private Texture texture;
    private Consumer<Collidable> collisionAction;

    public PowerUp(float x, float y, String texturePath) {
        super(x, y, null, 0, 10);
        this.bounds = new Rectangle(x, y, 56, 56);
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
