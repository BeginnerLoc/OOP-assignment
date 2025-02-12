package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Rectangl extends Entity {
	private float length;
    private float breadth;
    
    // Default Constructor
    public Rectangl()
    {
    	
    }
    
    // Parameterized Constructor
    public Rectangl(float x, float y, float length, float breadth, Color color, float speed) 
    {
        super(x, y, color, speed);
        this.length = length;
        this.breadth = breadth;
    }
    

    @Override
    public void draw(ShapeRenderer rd) {
        rd.setColor(getColor());
        rd.rect(getX(), getY(), length, breadth);
    }
    
    @Override
	public void update() {
    	setX(getX() + getSpeed());
		
	}
    

}
