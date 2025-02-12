package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.function.Consumer;

public class CollidableEntity extends Entity implements Collidable {
    private float width;
    private float height;
    private Consumer<Collidable> collisionAction;

    public CollidableEntity(float x, float y, float width, float height, Color color, float speed) {
        super(x, y, color, speed);
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }


    public void setCollisionAction(Consumer<Collidable> action) {
        this.collisionAction = action;
    }

    @Override
    public void onCollision(Collidable other) {
        if (collisionAction != null) {
            collisionAction.accept(other);
        } else {
            System.out.println(getClass().getSimpleName() + " collided with " + other.getClass().getSimpleName());
        }
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(ShapeRenderer rd) {
        rd.setColor(getColor());
        rd.rect(getX(), getY(), width, height);
    }

}


