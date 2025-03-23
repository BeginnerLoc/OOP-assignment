package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsScene extends Scene {
    private BackgroundRenderer backgroundRenderer;
    private CustomButton dismissButton;
    private CustomButton lowVolumeButton;
    private CustomButton mediumVolumeButton;
    private CustomButton highVolumeButton;

    public SettingsScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();

        // Initialize background with fixed resolution
        backgroundRenderer = new BackgroundRenderer("settings_bg.png");

        // Pass the viewport to managers
        this.ioManager.getInputManager().setViewport(backgroundRenderer.getViewport());
        this.entityManager.setViewport(backgroundRenderer.getViewport());

        // Clear any existing clickable objects
        this.ioManager.getInputManager().clearClickables();

        // Load and play shared background music
        if (!this.ioManager.getSoundManager().isBackgroundMusicPlaying()) {
            this.ioManager.getSoundManager().loadSound("background_music_MMS", "MainMenu_Under the Sea - Fearless Flyers.mp3");
            this.ioManager.getSoundManager().playBackgroundMusic("background_music_MMS");
        }

        // Use virtual coordinates for positioning
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;

        // Common button dimensions
        float buttonWidth = virtualWidth * 0.1f;
        float buttonHeight = virtualHeight * 0.1f;

        // Dismiss button (top-right corner)
        float dismissX = virtualWidth * 0.95f - buttonWidth;
        float dismissY = virtualHeight * 0.95f - buttonHeight;
        dismissButton = new CustomButton("dismiss_button.png", dismissX, dismissY, buttonWidth, buttonHeight);
        dismissButton.setOnClickAction(() -> {
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(dismissButton);
        this.ioManager.getInputManager().registerClickable(dismissButton);

        // Calculate positions for volume buttons - centered on screen
        float volumeButtonsY = virtualHeight * 0.5f;  // Centered vertically
        float totalButtonsWidth = (buttonWidth * 3) + (virtualWidth * 0.05f * 2);  // 3 buttons + 2 gaps
        float startX = (virtualWidth - totalButtonsWidth) * 0.5f;  // Center horizontally
        float spacing = virtualWidth * 0.05f;  // 5% of screen width for spacing
        
        // Low Volume Button
        lowVolumeButton = new CustomButton("easy_level.png", startX, volumeButtonsY, buttonWidth, buttonHeight);
        lowVolumeButton.setOnClickAction(() -> {
            this.ioManager.getSoundManager().setVolume(0.1f);
            updateVolumeButtonsState("low");
        });
        this.entityManager.addEntity(lowVolumeButton);
        this.ioManager.getInputManager().registerClickable(lowVolumeButton);

        // Medium Volume Button
        mediumVolumeButton = new CustomButton("medium_level.png", startX + buttonWidth + spacing, volumeButtonsY, buttonWidth, buttonHeight);
        mediumVolumeButton.setOnClickAction(() -> {
            this.ioManager.getSoundManager().setVolume(0.6f);
            updateVolumeButtonsState("medium");
        });
        this.entityManager.addEntity(mediumVolumeButton);
        this.ioManager.getInputManager().registerClickable(mediumVolumeButton);

        // High Volume Button
        highVolumeButton = new CustomButton("hard_level.png", startX + (buttonWidth + spacing) * 2, volumeButtonsY, buttonWidth, buttonHeight);
        highVolumeButton.setOnClickAction(() -> {
            this.ioManager.getSoundManager().setVolume(1.0f);
            updateVolumeButtonsState("high");
        });
        this.entityManager.addEntity(highVolumeButton);
        this.ioManager.getInputManager().registerClickable(highVolumeButton);

        // Set initial volume button state based on current volume
        float currentVolume = this.ioManager.getSoundManager().getVolume();
        if (currentVolume <= 0.3f) {
            updateVolumeButtonsState("low");
        } else if (currentVolume <= 0.6f) {
            updateVolumeButtonsState("medium");
        } else {
            updateVolumeButtonsState("high");
        }
    }

    private void updateVolumeButtonsState(String activeVolume) {
        lowVolumeButton.setImage(activeVolume.equals("low") ? "easy_level2.png" : "easy_level.png");
        mediumVolumeButton.setImage(activeVolume.equals("medium") ? "medium_level2.png" : "medium_level.png");
        highVolumeButton.setImage(activeVolume.equals("high") ? "hard_level2.png" : "hard_level.png");
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