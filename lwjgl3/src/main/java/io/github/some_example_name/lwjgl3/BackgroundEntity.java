package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundEntity extends Entity {
    private Texture texture;
    private float width;
    private float height;

    // Define screen width & height constants 
    private static final int SCREEN_WIDTH = 1080;  
    private static final int SCREEN_HEIGHT = 1080; 

    public BackgroundEntity(String texturePath, float x, float y) {
        super(x, y, null, 0); 
        this.texture = new Texture(texturePath); // Load image
        this.width = SCREEN_WIDTH;  // Set background width to fit screen
        this.height = SCREEN_HEIGHT; // Set background height to fit screen
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (texture != null) {
            sb.draw(texture, getX(), getY(), width, height); 
        }
    }
}
