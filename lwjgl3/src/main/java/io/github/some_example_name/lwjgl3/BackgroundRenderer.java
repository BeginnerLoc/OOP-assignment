package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class BackgroundRenderer {
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private Viewport viewport;
    
    public static final float VIRTUAL_WIDTH = 800;
    public static final float VIRTUAL_HEIGHT = 600;
    
    public BackgroundRenderer(String texturePath) {
        this.backgroundTexture = new Texture(texturePath);
        
        // Create camera with fixed resolution
        this.camera = new OrthographicCamera();
        
        // Use FitViewport to maintain exact virtual dimensions while fitting the screen
        this.viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        
        // Apply the viewport and update the camera
        this.viewport.apply(true);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }

    public void render(SpriteBatch batch) {
        if (backgroundTexture != null) {
            // Set the projection matrix to ensure consistent rendering size
            batch.setProjectionMatrix(camera.combined);
            
            batch.draw(backgroundTexture, 
                      0, 0,            
                      VIRTUAL_WIDTH, VIRTUAL_HEIGHT); 
        }
    }
    
    /**
     * Updates the viewport if the screen size changes
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }
    

    public Viewport getViewport() {
        return viewport;
    }
    

    public OrthographicCamera getCamera() {
        return camera;
    }
    
    /**
     * Dispose of resources to prevent memory leaks
     */
    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
            backgroundTexture = null;
        }
    }
}