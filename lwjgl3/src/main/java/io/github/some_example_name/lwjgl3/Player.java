package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.function.Consumer;

public class Player extends Entity implements PlayerMovable, Collidable {
    private float dx;
    private float dy;
    private boolean isSprinting;
    private Consumer<Collidable> collisionAction;
    private Rectangle bounds;

    public Player(float x, float y, Color color, float speed) {
        super(x, y, color, speed);
        this.bounds = new Rectangle(x, y, 50, 50); // Default size
    }

    @Override
    public void move() {
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void setDirection(float dx, float dy) {
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());
        bounds.setPosition(getX(), getY());
    }


    @Override
    public void stop() {
        this.dx = 0;
        this.dy = 0;
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
    public void jump(int height) {
        setY(getY() + height);
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void sprint(boolean isSprinting) {
        this.isSprinting = isSprinting;
        setSpeed(isSprinting ? 8.0f : 4.0f);
    }

    @Override
    public void dash() {
        setX(getX() + dx * 20);
        setY(getY() + dy * 20);
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void draw(ShapeRenderer rd) {
        rd.setColor(getColor());
        rd.rect(getX(), getY(), bounds.width, bounds.height);
    }

    @Override
    public void draw(SpriteBatch sb) {
        // Draw player texture here if applicable
    }
}
