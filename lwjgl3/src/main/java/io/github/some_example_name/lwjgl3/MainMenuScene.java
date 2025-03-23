package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScene extends Scene {
    private BackgroundRenderer backgroundRenderer;
    private CustomButton playButton;
    private CustomButton aboutButton;
    private CustomButton settingsButton;
    private CustomButton instructionsButton;
    private ShapeRenderer shapeRenderer;
    
    public MainMenuScene(String name) {
        super(name);
    }
    
    @Override
    public void create() {
        super.create();
     
        // Initialize the background renderer with fixed resolution
        backgroundRenderer = new BackgroundRenderer("OVERTRASHED.png");
        
        // Initialize ShapeRenderer
        shapeRenderer = new ShapeRenderer();
        
        // Pass the background renderer's viewport to the input manager
        this.ioManager.getInputManager().setViewport(backgroundRenderer.getViewport());
        this.entityManager.setViewport(backgroundRenderer.getViewport());
        
        // Clear any existing clickable objects when creating a new scene
        this.ioManager.getInputManager().clearClickables();
        
        // Load and play background music
        if (!this.ioManager.getSoundManager().isBackgroundMusicPlaying()) {
            this.ioManager.getSoundManager().loadSound("background_music_MMS", "mrbeansong.mp3");
            this.ioManager.getSoundManager().playBackgroundMusic("background_music_MMS");
        }
        
        // Use virtual coordinates for positioning
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        // Common button dimensions
        float buttonWidth = virtualWidth * 0.3f;
        float buttonHeight = virtualHeight * 0.1f;
        float buttonX = (virtualWidth - buttonWidth) * 0.5f;
        float spacing = buttonHeight * 0.2f;
        
        // Calculate total height of all buttons including spacing
        float totalButtonsHeight = (buttonHeight * 4) + (spacing * 3);
        // Start Y position to center all buttons vertically
        float startY = (virtualHeight + totalButtonsHeight) * 0.4f - buttonHeight;
        
        // Play Button - topmost
        playButton = new CustomButton("play_button.png", buttonX, startY, buttonWidth, buttonHeight);
        playButton.setOnClickAction(() -> {
            GameState.reset(); // Reset game state before starting new game
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(playButton);
        this.ioManager.getInputManager().registerClickable(playButton);
   
        // About Button
        float aboutY = startY - spacing - buttonHeight;
        aboutButton = new CustomButton("about_button.png", buttonX, aboutY, buttonWidth, buttonHeight);
        aboutButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(AboutScene.class);
            }
        });
        this.entityManager.addEntity(aboutButton);
        this.ioManager.getInputManager().registerClickable(aboutButton);
        
        // Instructions Button
        float instructionsY = aboutY - spacing - buttonHeight;
        instructionsButton = new CustomButton("instructions_button.png", buttonX, instructionsY, buttonWidth, buttonHeight);
        instructionsButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(InstructionsScene.class);
            }
        });
        this.entityManager.addEntity(instructionsButton);
        this.ioManager.getInputManager().registerClickable(instructionsButton);   
        
        // Settings Button - bottommost
        float settingsY = instructionsY - spacing - buttonHeight;
        settingsButton = new CustomButton("settings_button.png", buttonX, settingsY, buttonWidth, buttonHeight);
        settingsButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(SettingsScene.class);
            }
        });
        this.entityManager.addEntity(settingsButton);
        this.ioManager.getInputManager().registerClickable(settingsButton);
    }
    
    @Override
    public void render() {
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
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        super.dispose();
    }
}
