package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity {
	private float x;
	private float y;
	private float speed;
	private Color color;
	
	public Entity() {
	
	}
	
	public Entity(float x, float y, Color color, float speed) {
		super();
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.color = color;
		
	}


	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	public void draw(ShapeRenderer rd) {
		
	}
	
	public void draw(SpriteBatch sb) {
		
	}
	
	public void movement() {
		
	}
	
	public abstract void update();
	
}
