package io.github.some_example_name.lwjgl3;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Triangle extends Entity {
	
	Triangle(){
//		this should create zero and or NULL values
		super(10,10,Color.BLACK,2);
	}
	
	Triangle(float inputX, float inputY, Color inputColour, float inputSpeed){
		super(inputX,inputY,inputColour,inputSpeed);
	}
	
	public void draw(ShapeRenderer shape) {
		shape.setColor(this.getColor());
		shape.triangle(-50+getX(), -50+getY(), getX()+50, -50+getY(), getX(), getY()+50);

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
