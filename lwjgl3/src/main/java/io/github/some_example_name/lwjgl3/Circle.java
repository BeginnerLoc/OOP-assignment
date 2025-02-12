package io.github.some_example_name.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle extends Entity{
	private float radius;
	
	Circle(){
		super(70,70,Color.GREEN,2);
		this.radius = 0; 
	}
	
	Circle(float inputX, float inputY, float inputRadius, Color inputColour, float inputSpeed){ 
		super(inputX,inputY,inputColour,inputSpeed);
		this.radius = inputRadius;
	}
	
	void setRadius(float x) {
		radius = x;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void draw(ShapeRenderer shape) {
		shape.setColor(this.getColor());
		shape.circle(getX(),getY(),radius);
	}
	
	@Override
	public void movement() {
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//        	this.setY(this.getY() + super.getSpeed());
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//        	this.setY(this.getY() - super.getSpeed());
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//        	this.setX(this.getX() - super.getSpeed());
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//        	this.setX(this.getX() + super.getSpeed());
//        }
	}

	@Override
	public void update() {
		
		
	}
	
	
}

