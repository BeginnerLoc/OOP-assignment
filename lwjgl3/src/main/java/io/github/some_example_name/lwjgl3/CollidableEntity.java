package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Consumer;

//added Movable interface for Dependency Inversion Principle (Movement)
public class CollidableEntity extends Entity implements Collidable, Movable {
    private float width;
    private float height;
    private Consumer<Collidable> collisionAction;
    private float speed;
    private Vector2 position;

    public CollidableEntity(float x, float y, float width, float height, Color color, float speed) {
        super(x, y, color, speed);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.position = new Vector2(x, y);

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
    //Movement logic moved here
    @Override
    public void movement(float deltaTime, Vector2 direction) {
        if (direction != null) {
            position.x += direction.x * speed * deltaTime;
            position.y += direction.y * speed * deltaTime;
        } 
        }
    @Override
     public void setX(float x) {
    	 super.setX(x);

    }
     @Override
    public float getX() {
    	return super.getX();
    }
    
    @Override
    public void translate(float dx, float dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

}



