package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AboutScene extends Scene {
    private BackgroundRenderer backgroundRenderer;
    private CustomButton dismissButton;
    private Word word;
    private Word word1;

    public AboutScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        System.out.println("AboutScene created");
        super.create();
        
        // Initialize background with fixed resolution
        backgroundRenderer = new BackgroundRenderer("about_bg.png");
        
        // Pass the viewport to managers - removed null check since BackgroundRenderer always creates a valid viewport
        this.entityManager.setViewport(backgroundRenderer.getViewport());
        this.ioManager.getInputManager().setViewport(backgroundRenderer.getViewport());
        
        // Clear any existing clickable objects
        this.ioManager.getInputManager().clearClickables();
        
        // Load and play shared background music for MainMenuScene and AboutScene
        if (!this.ioManager.getSoundManager().isBackgroundMusicPlaying()) {
            this.ioManager.getSoundManager().loadSound("background_music_MMS", "MainMenu_Under the Sea - Fearless Flyers.mp3");
            this.ioManager.getSoundManager().playBackgroundMusic("background_music_MMS");
        }
        
        // Use virtual coordinates for positioning
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;

        // Center the text using virtual coordinates
        float textX = virtualWidth * 0.5f - 220;  // Center align text
        float textY = virtualHeight * 0.4f;       // Vertical center

        // Add text with proper scaling
        word = new Word(textX, textY, 0.1f, Color.BLACK, 
                        "OverTrashed is a fun and educational game! \nAvoid the GRANDMA & Save the Environment!", 1.5f);
        word1 = new Word(textX, textY - 80, 0.1f, Color.BLACK, 
                        "Credits: (P2T2) \nGregory, Cavell, Wenjing, Royston, Aish, Luke", 1f);

        this.entityManager.addWord(word);
        this.entityManager.addWord(word1);

        // Dismiss button dimensions and position (top-right corner)
        float buttonWidth = virtualWidth * 0.08f;
        float buttonHeight = virtualHeight * 0.08f;
        float buttonX = virtualWidth * 0.95f - buttonWidth;
        float buttonY = virtualHeight * 0.95f - buttonHeight;

        dismissButton = new CustomButton("dismiss_button.png", buttonX, buttonY, buttonWidth, buttonHeight);
        dismissButton.setOnClickAction(() -> {
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(dismissButton);
        this.ioManager.getInputManager().registerClickable(dismissButton);
    }

    @Override
    public void render() {
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
        this.ioManager.getInputManager().clearClickables();
        super.dispose();
    }
}