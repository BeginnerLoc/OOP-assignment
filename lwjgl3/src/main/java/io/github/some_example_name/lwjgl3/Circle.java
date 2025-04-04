package io.github.some_example_name.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle extends Entity{
	
	private float radius;
	
	// Default Constructor
    public Circle() 
    {
        
    }
	
 	// Parameterized Constructor
    public Circle(float x, float y, float speed, float radius, Color color) 
    {
        super(x, y, color, speed);
        this.radius = radius;
    }
	
    public float getRadius() 
    {
        return radius;
    }

    public void setRadius(float radius) 
    {
    	this.radius = radius;
    }
	
	public void draw(ShapeRenderer shape) {
		shape.setColor(getColor());
		shape.circle(getX(), getY(), getRadius());
	}	
	
}

