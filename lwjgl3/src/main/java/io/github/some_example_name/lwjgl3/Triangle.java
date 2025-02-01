package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Triangle extends Entity{
	
	public Triangle() {
		this.setX(0);
		this.setY(0);
		this.setSpeed(0);
		this.setColor(null);
	}
	
	public Triangle(float x, float y, Color color, float speed) {
		super(x, y, color, speed);		
	}

    @Override
    public void draw(ShapeRenderer sr) {
        sr.setColor(this.getColor());

        sr.begin(ShapeRenderer.ShapeType.Filled);

        float x1 = this.getX();                    // Bottom-left vertex
        float y1 = this.getY();
        float x2 = this.getX() + 50;               // Bottom-right vertex
        float y2 = this.getY();
        float x3 = this.getX() + 25;               // Top vertex
        float y3 = this.getY() + 50;

        // Draw the triangle
        sr.triangle(x1, y1, x2, y2, x3, y3);

        // End the shape rendering
        sr.end();
    }

	
	@Override
	public void movement() {
        if (Gdx.input.isKeyPressed(Keys.A)) 
            this.setX(this.getX() - 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.D)) 
            this.setX(this.getX() + 100 * Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void update() {
	    System.out.println("In triangle at position " + getX() + "," + getY());
	}
}
