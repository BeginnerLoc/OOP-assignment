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
        super(x, y, null, 0);
        this.bounds = new Rectangle(x, y, 32, 32);
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

    public void applyEffect(Player player) {
        // Randomly choose an effect
        int effect = (int)(Math.random() * 3);
        switch(effect) {
            case 0:
                // Health boost
                player.restoreHealth(1);
                break;
            case 1:
                // Speed boost
                player.setSpeed(player.getSpeed() * 1.2f);
                break;
            case 2:
                // Temporary invincibility (would need implementation)
                break;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), bounds.width, bounds.height);
    }
}
