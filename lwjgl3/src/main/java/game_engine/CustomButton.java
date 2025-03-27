package game_engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CustomButton extends Entity implements Clickable, Hoverable {
    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle bounds;
    private Runnable action;
    private boolean isHovering = false;
    private float originalScale = 1f;
    private float hoverScale = 1.1f;
    private Vector2 originalPosition;
    private static final float HOVER_OFFSET = 2f; // Pixels to lift when hovering
    
    public CustomButton(String imagePath, float x, float y, float width, float height) {
        super(x, y, Color.RED, 0, 5);
        texture = new Texture(Gdx.files.internal(imagePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        bounds = new Rectangle(x, y, width, height);
        textureRegion = new TextureRegion(texture);
        originalPosition = new Vector2(x, y);
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
    public void onHoverEnter() {
        if (!isHovering) {
            isHovering = true;
            bounds.y += HOVER_OFFSET; // Lift the button up slightly
        }
    }
    
    @Override
    public void onHoverExit() {
        if (isHovering) {
            isHovering = false;
            bounds.y = originalPosition.y; // Return to original position
        }
    }
    
    @Override
    public boolean isHovered(float worldX, float worldY) {
        return bounds.contains(worldX, worldY);
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (texture != null && textureRegion != null) {
            float scale = isHovering ? hoverScale : originalScale;
            float width = bounds.width * scale;
            float height = bounds.height * scale;
            // Center the scaling
            float x = bounds.x - (width - bounds.width) / 2;
            float y = bounds.y - (height - bounds.height) / 2;
            
            if (isHovering) {
                // Draw a subtle shadow
                batch.setColor(0, 0, 0, 0.3f);
                batch.draw(textureRegion, x + 2, originalPosition.y - 2, width, height);
                batch.setColor(Color.WHITE);
            }
            
            batch.draw(textureRegion, x, y, width, height);
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

