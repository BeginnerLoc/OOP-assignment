package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class BackgroundEntity extends Entity {
    private Texture texture;
    private float width;
    private float height;

    /**
     * Creates a background that covers the entire screen
     * @param texturePath Path to the texture file
     */
    public BackgroundEntity(String texturePath) {
        super(0, 0, null, 0); 
        this.texture = new Texture(texturePath);
        // Set the size to match the screen dimensions
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
    }

    /**
     * Legacy constructor with position and scaling
     * @param texturePath Path to the texture file
     * @param x X position
     * @param y Y position
     * @param scale Scaling factor
     */
    public BackgroundEntity(String texturePath, float x, float y, float scale) {
        super(x, y, null, 0); 
        this.texture = new Texture(texturePath);
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (texture != null) {
            sb.draw(texture, getX(), getY(), width, height); 
        }
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}