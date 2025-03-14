package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class RecyclingItem extends Entity implements Collidable {
    private RecyclingItemType type;
    private Rectangle bounds;
    private boolean visible = true;

    public RecyclingItem(float x, float y) {
        super(x, y, null, 0);
        this.type = RecyclingItemType.getRandomType(); // Randomize item type
        this.bounds = new Rectangle(x, y, 32, 32); // Smaller size
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
    	//if (other instanceof Player) {
        //    Player player = (Player) other;
        //    if (!player.isCarryingItem()) {
        //        System.out.println("Picked up: " + type.getName());
        //        player.pickupItem(this); // Give the item to the player
        //    }
        //}
    }
    @Override
    public void setCollisionAction(Consumer<Collidable> action) {
    	
    }
    @Override
    public void draw(SpriteBatch batch) {
        // Draw the colored circle
    	if (!visible) return; // Skip drawing if hidden
        ShapeRenderer renderer = ServiceLocator.get(ShapeRenderer.class);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(type.getColor());
        renderer.circle(getX() + 16, getY() + 16, 16);
        renderer.end();
    }
    public void hide() {
        this.visible = false;
    }

}
