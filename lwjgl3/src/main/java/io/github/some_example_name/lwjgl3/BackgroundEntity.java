package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundEntity extends Entity {
    private Texture texture;

    public BackgroundEntity(String texturePath, float x, float y) {
        super(x, y, null, 0); // No color or speed needed for a static background
        this.texture = new Texture(texturePath); // Load image
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (texture != null) {
            sb.begin();
            sb.draw(texture, getX(), getY());
            sb.end();
        }
    }
}