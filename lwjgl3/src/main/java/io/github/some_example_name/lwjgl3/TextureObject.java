package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureObject extends Entity{
	
	private Texture tex;
    private boolean isAIControlled;

	
	public TextureObject() {
	}
	
	public TextureObject(String texImg, float x, float y, float speed) {
		super(x, y, null, speed);		
		this.tex = new Texture(Gdx.files.internal(texImg));
	}


	
	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}
	
	public boolean getAIControlled() {
		return isAIControlled;
	}

	public void setAIControlled(Boolean isAIControlled) {
		this.isAIControlled = isAIControlled;
	}

	@Override
	public void movement() {
		 if (isAIControlled) {
	            this.setY(this.getY() - this.getSpeed());
	            if (this.getY() <= 0) {
	                this.setY(400);
	               this.setSpeed(Math.min(this.getSpeed() + 1, 10));
	             }
	        } else {
	            if (Gdx.input.isKeyPressed(Keys.LEFT)) 
	                this.setX(this.getX() - 100 * Gdx.graphics.getDeltaTime());
	            if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
	                this.setX(this.getX() + 100 * Gdx.graphics.getDeltaTime());
	        }
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		sb.begin();
		sb.draw(this.getTex(), this.getX(), this.getY(), this.getTex().getWidth(), this.getTex().getHeight());
		sb.end();
	}
	
	@Override
	public void update() {
	    System.out.println("In Texture at position " + getX() + "," + getY());
	}
	
}
