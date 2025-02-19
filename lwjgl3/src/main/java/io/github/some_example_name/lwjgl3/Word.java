package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Word extends Entity {

	// string
	private String word;
	
	public Word(float x, float y, float speed, Color color, String word) {
        super(x, y, color, speed);
        this.word = word;
	
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	

}
