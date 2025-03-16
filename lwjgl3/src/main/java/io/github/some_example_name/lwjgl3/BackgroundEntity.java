package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundEntity extends Entity {
    private Texture texture;
    private float width;
    private float height;
    private float scale;

    public BackgroundEntity(String texturePath, float x, float y, float scale) {
        super(x, y, null, 0); 
        this.texture = new Texture(texturePath); // Load image
        this.scale = scale;
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