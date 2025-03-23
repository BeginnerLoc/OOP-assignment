package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class CustomButton extends Entity implements Clickable {
    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle bounds;
    private Runnable action;
    
    public CustomButton(String imagePath, float x, float y, float width, float height) {
        super(x, y, Color.RED, 0);
        texture = new Texture(Gdx.files.internal(imagePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        bounds = new Rectangle(x, y, width, height);
        textureRegion = new TextureRegion(texture);
        Gdx.app.log("CustomButton", "Button created at: (" + x + ", " + y + ") with size: " + width + "x" + height);
    }
    
    public void setImage(String newImagePath) {
        if (texture != null) {
            texture.dispose();
        }
        texture = new Texture(Gdx.files.internal(newImagePath));
        textureRegion = new TextureRegion(texture);
    }
    
    public boolean isClicked(float worldX, float worldY) {
        return bounds.contains(worldX, worldY);
    }
    
    @Override
    public void onMouseClick() {
        if (action != null) {
            // Play click sound if available
            IOManager ioManager = ServiceLocator.get(IOManager.class);
            if (ioManager != null) {
                ioManager.getSoundManager().playSound("click");
            }
            action.run();
        } else {
            Gdx.app.log("CustomButton", "No action assigned for button click.");
        }
    }
    
    public void setOnClickAction(Runnable action) {
        this.action = action;
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (texture != null && textureRegion != null) {
            batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }
}

