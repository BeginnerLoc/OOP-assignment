package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CustomButton extends Entity implements Clickable {
    private Texture texture;
    private TextureRegion textureRegion;
    //private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Rectangle bounds;
    private Runnable action;
  

    public CustomButton(String imagePath, float x, float y, float width, float height) {
		super(x, y, Color.RED, 0);

        texture = new Texture(Gdx.files.internal(imagePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        
        bounds = new Rectangle(x, y, width, height);
        textureRegion = new TextureRegion(texture);
        Gdx.app.log("CustomButton", "Texture width: " + texture.getWidth() + ", height: " + texture.getHeight());
        Gdx.app.log("CustomButton", "Bounds width: " + bounds.width + ", height: " + bounds.height);
      
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
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
        //batch.end();
        /*shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();
        batch.begin(); // Restart SpriteBatch*/

    }
    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }

}

