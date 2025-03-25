package game.entities;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import game_engine.Collidable;
import game_engine.Entity;

public class RecyclingItem extends Entity implements Collidable {
    private RecyclingItemType type;
    private Rectangle bounds;
    private boolean visible = true;

    public RecyclingItem(float x, float y) {
        super(x, y, null, 0, 5);
        this.type = RecyclingItemType.getRandomType(); // Randomize item type
        this.bounds = new Rectangle(x, y, 40, 40); // Smaller size
    }

    public RecyclingItemType getType() {
        return type;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void onCollision(Collidable other) {
        // Collision handling managed elsewhere
    }

    @Override
    public void setCollisionAction(Consumer<Collidable> action) {
        // Not needed
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Don't do any drawing in the SpriteBatch phase
    }
    
    @Override
    public void draw(ShapeRenderer renderer) {
        // Only draw using the ShapeRenderer in the ShapeRenderer phase
        if (!visible) return; // Skip drawing if hidden
        
        // Just set color and draw - don't call begin/end here
        renderer.setColor(type.getColor());
        renderer.circle(getX() + 16, getY() + 16, 16);
    }

    public void hide() {
        this.visible = false;
    }
}
