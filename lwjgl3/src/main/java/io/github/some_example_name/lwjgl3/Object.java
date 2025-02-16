package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Object extends Entity implements Collidable, Movable {
	private float length;
    private float breadth;
    private Consumer<Collidable> collisionAction;
    
    // Default Constructor
    public Object()
    {
    	
    }
    
    // Parameterized Constructor
    public Object(float x, float y, float length, float breadth, Color color, float speed) 
    {
        super(x, y, color, speed);
        this.length = length;
        this.breadth = breadth;
    }
	
	
	
	@Override
	public void movement(float deltaTime, Vector2 direction) {
        if (direction != null) {
            this.setX(this.getX() + (direction.x * super.getSpeed() * deltaTime));
            this.setY(this.getY() + (direction.y * super.getSpeed() * deltaTime));
        }      		
	}

	@Override
	public void translate(float dx, float dy) {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
	}

	@Override
	public Rectangle getBounds() {
		//Generating an invisible rectangle to act as a hit box area
		return new Rectangle(getX(), getY(), length, breadth);
	}

	@Override
	public void onCollision(Collidable other) {
		//Runs upon collision with another object
        if (collisionAction != null) {
            collisionAction.accept(other);
        } else {
            System.out.println(getClass().getSimpleName() + " collided with " + other.getClass().getSimpleName());
        }
	}

	@Override
	public void setCollisionAction(Consumer<Collidable> action) {
		//Set what action happens during collision
        this.collisionAction = action;
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public void draw(ShapeRenderer rd) {
        rd.setColor(getColor());
        rd.rect(getX(), getY(), length, breadth);
    }
    
}
