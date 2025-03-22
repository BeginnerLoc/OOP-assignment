package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Word extends Entity {
    private String word;
    private BitmapFont font;
    private float scale; // Font scale
    
    public Word(float x, float y, float speed, Color color, String word) {
      super(x, y, color, speed);
      this.word = word;
      this.scale = 1.0f;
      
      // Initialize with default scale
      font = new BitmapFont();
      font.setColor(color);
	}

    public Word(float x, float y, float speed, Color color, String word, float scale) {
        super(x, y, color, speed);
        this.word = word;
        this.scale = scale;
        
        // Initialize BitmapFont with fixed scale
        font = new BitmapFont();
        font.getData().setScale(scale); // Set fixed font size
        font.setColor(color);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    public float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        // No need to update scale every frame if it's fixed
        font.draw(spriteBatch, word, getX(), getY());
    }
}

