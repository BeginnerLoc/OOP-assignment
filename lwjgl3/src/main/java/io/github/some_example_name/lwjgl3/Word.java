//package io.github.some_example_name.lwjgl3;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.math.Rectangle;
//
//public class Word extends Entity {
//
//	// string
//	private String word;
//	
//	public Word(float x, float y, float speed, Color color, String word) {
//        super(x, y, color, speed);
//        this.word = word;
//	
//	}
//
//	public String getWord() {
//		return word;
//	}
//
//	public void setWord(String word) {
//		this.word = word;
//	}
//	
//
//}
//
package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Word extends Entity {
    private String word;
    private BitmapFont font;
    private float scale; // Font scale

    public Word(float x, float y, float speed, Color color, String word, float scale) {
        super(x, y, color, speed);
        this.word = word;
        this.scale = scale;

        // ✅ Initialize BitmapFont and Apply Scaling
        font = new BitmapFont();
        font.getData().setScale(scale); // Adjust font size
        font.setColor(color);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        font.getData().setScale(scale); // Ensure scaling is applied
        font.draw(spriteBatch, word, getX(), getY());
    }
}

