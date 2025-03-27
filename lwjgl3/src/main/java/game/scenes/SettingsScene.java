package game.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game_engine.BackgroundRenderer;
import game_engine.CustomButton;
import game_engine.Scene;
import game_engine.ServiceLocator;

public class SettingsScene extends Scene {
    private BackgroundRenderer backgroundRenderer;
    private CustomButton dismissButton;
    private CustomButton zeroVolumeButton;
    private CustomButton halfVolumeButton;
    private CustomButton fullVolumeButton;

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

        // Dismiss button (top-right corner)
        float buttonWidth = virtualWidth * 0.1f;
        float buttonHeight = virtualHeight * 0.1f;
        float dismissX = virtualWidth * 0.95f - buttonWidth;
        float dismissY = virtualHeight * 0.95f - buttonHeight;
        dismissButton = new CustomButton("dismiss_button.png", dismissX, dismissY, buttonWidth, buttonHeight);
        dismissButton.setOnClickAction(() -> {
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(dismissButton);
        this.ioManager.getInputManager().registerClickable(dismissButton);

        // Adjusted for three volume buttons with increased size
        float volumeButtonWidth = virtualWidth * 0.28f;  
        float volumeButtonHeight = virtualHeight * 0.18f;
        float volumeButtonsY = virtualHeight * 0.5f - 60f;  // Centered vertically with a slight offset
        float spacing = virtualWidth * 0.00005f;  
        float totalButtonsWidth = (volumeButtonWidth * 3) + (spacing * 2);  // 3 buttons + 2 gaps
        float startX = (virtualWidth - totalButtonsWidth) * 0.5f;  // Center horizontally
        
        // 0% Volume Button
        zeroVolumeButton = new CustomButton("0_volumebtn.png", startX, volumeButtonsY, volumeButtonWidth, volumeButtonHeight);
        zeroVolumeButton.setOnClickAction(() -> {
            this.ioManager.getSoundManager().setVolume(0.0f);
        });
        this.entityManager.addEntity(zeroVolumeButton);
        this.ioManager.getInputManager().registerClickable(zeroVolumeButton);

        // 50% Volume Button
        halfVolumeButton = new CustomButton("50_volumebtn.png", startX + volumeButtonWidth + spacing, volumeButtonsY, volumeButtonWidth, volumeButtonHeight);
        halfVolumeButton.setOnClickAction(() -> {
            this.ioManager.getSoundManager().setVolume(0.5f);
        });
        this.entityManager.addEntity(halfVolumeButton);
        this.ioManager.getInputManager().registerClickable(halfVolumeButton);

        // 100% Volume Button
        fullVolumeButton = new CustomButton("100_volumebtn.png", startX + (volumeButtonWidth + spacing) * 2, volumeButtonsY, volumeButtonWidth, volumeButtonHeight);
        fullVolumeButton.setOnClickAction(() -> {
            this.ioManager.getSoundManager().setVolume(1.0f);
        });
        this.entityManager.addEntity(fullVolumeButton);
        this.ioManager.getInputManager().registerClickable(fullVolumeButton);
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