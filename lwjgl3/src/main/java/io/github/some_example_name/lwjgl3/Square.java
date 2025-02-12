package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
   
public class Square extends Entity implements Collidable {
	private float length;
    private float breadth;
    private Consumer<Collidable> collisionAction;
    
    // Default Constructor
    public Square()
    {
    	
    }
    
    // Parameterized Constructor
    public Square(float x, float y, float length, float breadth, Color color, float speed) 
    {
        super(x, y, color, speed);
        this.length = length;
        this.breadth = breadth;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), length, breadth);
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
    public void draw(ShapeRenderer rd) {
        rd.setColor(getColor());
        rd.rect(getX(), getY(), length, breadth);
    }
    
    @Override
	public void update() {
		
	}
    

}
