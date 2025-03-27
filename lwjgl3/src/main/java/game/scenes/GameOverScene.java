package game.scenes;
import com.badlogic.gdx.utils.ScreenUtils;

import game.utils.GameState;
import game_engine.BackgroundRenderer;
import game_engine.CustomButton;
import game_engine.Scene;
import game_engine.ServiceLocator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class GameOverScene extends Scene {
    private BackgroundRenderer backgroundRenderer;
    private CustomButton yesButton;
    private CustomButton noButton;
    private BitmapFont font;
    
    public GameOverScene(String name) {
        super(name);
    }
    
    @Override
    public void create() {
        super.create();
        
        // Stop and dispose of the game scene background music
        this.ioManager.getSoundManager().stopSound("background_music_GS");
        this.ioManager.getSoundManager().disposeSound("background_music_GS");

        // Play the game over sound
        this.ioManager.getSoundManager().playSound("background_music_MMS");
        
        // Initialize the background renderer with fixed HD resolution
        backgroundRenderer = new BackgroundRenderer("gameover_bg.png");
        
        // Initialize font
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(BackgroundRenderer.VIRTUAL_WIDTH / 600f);
        
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
            GameState.reset(); // Reset game state before transitioning
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(yesButton);
        this.ioManager.getInputManager().registerClickable(yesButton);
        
        // No Button
        noButton = new CustomButton("no_button.png", startX + buttonWidth + spacing, buttonY, buttonWidth, buttonHeight);
        noButton.setOnClickAction(() -> {
            GameState.reset(); // Reset game state before transitioning to main menu as well
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
            
            // Draw score
            float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
            float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
            float textX = virtualWidth * 0.5f - 100; // Center the text
            float textY = virtualHeight * 0.6f;      // Above the buttons
            
            font.draw(batch, "Final Score: " + GameState.getScore(), textX, textY);
            
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
        if (font != null) {
            font.dispose();
        }
        
        // Dispose any specific sounds loaded by this scene
        this.ioManager.getSoundManager().disposeSound("background_music_MMS");
        
        // Clear clickables when disposing the scene
        this.ioManager.getInputManager().clearClickables();
        super.dispose();
    }
}
