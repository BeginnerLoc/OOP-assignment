package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CustomButton extends Entity implements Clickable {
    private Texture texture;
    private Rectangle bounds;
    private Runnable action;

    public CustomButton(String imagePath, float x, float y, float width, float height) {
		super(x, y, Color.RED, 0);

        texture = new Texture(Gdx.files.internal(imagePath));
        bounds = new Rectangle(x, y, width, height);
    }
    
    // Method to change the button's image
    // TO CHECK IF IT ALIGNS W OOP PRINCIPLES
    public void setImage(String newImagePath) {
        // Dispose of the current texture to free memory
        if (texture != null) {
            texture.dispose();
        }
        // Load the new texture
        texture = new Texture(Gdx.files.internal(newImagePath));
    }
    

    public boolean isClicked(float touchX, float touchY) {
        return bounds.contains(touchX, touchY);
    }

    public void setOnClickAction(Runnable action) {
        this.action = action;
    }

    @Override
    public void onMouseClick() {
        if (action != null) {
            action.run();
        } else {
            System.out.println("No action assigned for button click.");
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void dispose() {
        texture.dispose();
    }

}

