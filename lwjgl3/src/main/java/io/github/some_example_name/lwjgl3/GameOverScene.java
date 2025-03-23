package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScene extends Scene {
    private BackgroundRenderer backgroundRenderer;
    private CustomButton yesButton;
    private CustomButton noButton;
    
    public GameOverScene(String name) {
        super(name);
    }
    
    @Override
    public void create() {
        super.create();
        
        // Stop and dispose of the game scene background music
        this.ioManager.getSoundManager().stopSound("background_music_GS");
        this.ioManager.getSoundManager().disposeSound("background_music_GS");
        
        // Initialize the background renderer with fixed HD resolution
        backgroundRenderer = new BackgroundRenderer("gameover_bg.png");
        
        // Pass the background renderer's viewport to both input manager and entity manager
        this.ioManager.getInputManager().setViewport(backgroundRenderer.getViewport());
        this.entityManager.setViewport(backgroundRenderer.getViewport());
        
        // Clear any existing clickable objects when creating a new scene
        this.ioManager.getInputManager().clearClickables();
        
        // Use virtual coordinates for positioning
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        // Common button dimensions and positioning
        float buttonWidth = virtualWidth * 0.2f;
        float buttonHeight = virtualHeight * 0.15f;
        float spacing = buttonWidth * 0.5f;
        float totalWidth = (buttonWidth * 2) + spacing;
        
        // Center buttons horizontally and vertically
        float startX = (virtualWidth - totalWidth) * 0.5f;
        float buttonY = virtualHeight * 0.31f; 
        
        // Yes Button
        yesButton = new CustomButton("yes_button.png", startX, buttonY, buttonWidth, buttonHeight);
        yesButton.setOnClickAction(() -> {
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(yesButton);
        this.ioManager.getInputManager().registerClickable(yesButton);
        
        // No Button
        noButton = new CustomButton("no_button.png", startX + buttonWidth + spacing, buttonY, buttonWidth, buttonHeight);
        noButton.setOnClickAction(() -> {
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(noButton);
        this.ioManager.getInputManager().registerClickable(noButton);
    }
    
    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0, 0, 1);
        
        // Get the SpriteBatch from ServiceLocator
        SpriteBatch batch = ServiceLocator.get(SpriteBatch.class);
        
        // Render the background first
        if (batch != null && backgroundRenderer != null) {
            batch.begin();
            backgroundRenderer.render(batch);
            batch.end();
        }
        
        // Then render everything else
        super.render();
    }
    
    @Override
    public void resize(int width, int height) {
        if (backgroundRenderer != null) {
            backgroundRenderer.resize(width, height);
        }
    }
    
    @Override
    public void dispose() {
        if (backgroundRenderer != null) {
            backgroundRenderer.dispose();
        }
        this.ioManager.getSoundManager().disposeSound("game_over");
        
        // Clear clickables when disposing the scene
        this.ioManager.getInputManager().clearClickables();
        super.dispose();
    }
}
