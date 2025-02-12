package io.github.some_example_name.lwjgl3;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Triangle extends Entity {
	
	// Default Constructor
	public Triangle()
	{
		// this should create zero and or NULL values
		super(10,10,Color.BLACK,2);
	}
	
	// Parameterized Constructor
	public Triangle(float x, float y, float speed, Color color) 
    {
        super(x, y, color, speed);
        
    }
	
	// Method to draw the triangle
    @Override
    public void draw(ShapeRenderer shape) 
    {
    	shape.setColor(getColor());
    	
    	// Defining size of the triangle
        float width = 100; // Width of triangle
        float height = 100; // Height of triangle
    	
        // Calculate triangle points based on x, y position
        float p1x = getX() - width / 2; // Bottom-left point (x)
        float p1y = getY() - height / 2; // Bottom-left point (y)
        
        float p2x = getX() + width / 2; // Bottom-right point (x)
        float p2y = getY() - height / 2; // Bottom-right point (y)
        
        float p3x = getX(); // Top-middle point (x)
        float p3y = getY() + height / 2; // Top-middle point (y)
        
        // Drawing  triangle using ShapeRenderer
        shape.triangle(p1x, p1y, p2x, p2y, p3x, p3y);
    }
	
	@Override
	public void movement() {
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//        	this.setY(this.getY() + super.getSpeed());
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//        	this.setY(this.getY() - super.getSpeed());
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//        	this.setX(this.getX() - super.getSpeed());
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//        	this.setX(this.getX() + super.getSpeed());
//        }
	}

	@Override
	public void update() {

		
	}
}
