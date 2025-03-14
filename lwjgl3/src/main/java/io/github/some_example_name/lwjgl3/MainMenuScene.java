package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScene extends Scene {
    private CustomButton startButton;
    private CustomButton howToPlayButton;
    private CustomButton exitButton;
    private Texture howToPlayTexture;
    private boolean showingInstructions = false;

    public MainMenuScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();
        
        // Create buttons with broccoli texture for now
        startButton = new CustomButton("broccoli.png", 220, 300, 200, 50);
        howToPlayButton = new CustomButton("broccoli.png", 220, 200, 200, 50);
        exitButton = new CustomButton("broccoli.png", 220, 100, 200, 50);
        
        // Load how to play texture
        howToPlayTexture = new Texture("broccoli.png");

        startButton.setOnClickAction(() -> {
            GameState.reset();
            this.sceneManager.setScene(GameScene.class);
        });

        howToPlayButton.setOnClickAction(() -> {
            showingInstructions = true;
        });

        exitButton.setOnClickAction(() -> {
            Gdx.app.exit();
        });

        this.entityManager.addEntity(startButton);
        this.entityManager.addEntity(howToPlayButton);
        this.entityManager.addEntity(exitButton);

        this.ioManager.getInputManager().registerClickable(startButton);
        this.ioManager.getInputManager().registerClickable(howToPlayButton);
        this.ioManager.getInputManager().registerClickable(exitButton);
    }

    @Override
    public void render() {
        super.render();
        
        if (showingInstructions) {
            SpriteBatch batch = ServiceLocator.get(SpriteBatch.class);
            batch.begin();
            batch.draw(howToPlayTexture, 100, 100, 440, 280);
            batch.end();
            
            if (Gdx.input.justTouched()) {
                showingInstructions = false;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        howToPlayTexture.dispose();
    }
}
