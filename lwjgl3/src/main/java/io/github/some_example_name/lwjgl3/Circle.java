package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle extends Entity{
	
	private float radius;

	public Circle() {
		this.setX(0);
		this.setY(0);
		this.setSpeed(0);
		this.setColor(null);
		this.setRadius(0);
	}
	
	public Circle(float x, float y, float speed, Color color, float radius) {
		super(x, y, color, speed);		
		this.radius = radius;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
    @Override
    public void draw(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(getColor() != null ? getColor() : Color.RED);
        sr.circle(getX(), getY(), radius);
        sr.end();
    }
	
	@Override
	public void movement() {
        if (Gdx.input.isKeyPressed(Keys.W)) 
            this.setY(this.getY() + 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.S)) 
            this.setY(this.getY() +-100 * Gdx.graphics.getDeltaTime());
	}

	@Override
	public void update() {
	    System.out.println("In circle of radius " + radius + " at " + getX() + "," + getY() + " position");		
	}
}
