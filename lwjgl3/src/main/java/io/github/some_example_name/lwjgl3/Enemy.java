package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Entity implements AIMovable, Collidable {

    private Entity target;
    private Rectangle bounds;
    private float dx = 0;
    private float dy = 0;
    private Consumer<Collidable> collisionAction;


    public Enemy(float x, float y, float speed, Color color) {
        super(x, y, color, speed);
        this.bounds = new Rectangle(x, y, 50, 50);
    }

    /** Set a target entity to follow (e.g., the Player) */
    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void followEntity() {
        if (target != null) {
            Vector2 direction = new Vector2(target.getX() - getX(), target.getY() - getY());

            if (direction.len() > 10) {
                direction.nor(); 
                setDirection(direction.x, direction.y);
            } else {
                stop();
            }
        }
    }

    @Override
    public void move() {
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void setDirection(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void stop() {
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void draw(ShapeRenderer rd) {
        rd.setColor(this.getColor());
        rd.rect(this.getX(), this.getY(), this.bounds.width, this.bounds.height);
    }

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

}
