package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScene extends Scene {

    private BackgroundEntity background;
    private CustomButton resumeButton;
    private CustomButton exitButton;
    

    public PauseScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();
        

       

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Load background image
        float backgroundScale = 1f;
        background = new BackgroundEntity("pause_bg.png", 0, 0, backgroundScale);
        this.entityManager.addEntity(background);

        // Resume Button
        float resumeButtonWidth = screenWidth * 0.3f;
        float resumeButtonHeight = screenHeight * 0.1f;
        float resumeButtonX = screenWidth * 0.5f - (resumeButtonWidth / 2);
        float resumeButtonY = screenHeight * 0.5f;

        resumeButton = new CustomButton("resume_button.png", resumeButtonX, resumeButtonY, resumeButtonWidth, resumeButtonHeight);
        resumeButton.setOnClickAction(() -> {
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(resumeButton);
        this.ioManager.getInputManager().registerClickable(resumeButton);

        // Exit Button
        float exitButtonWidth = screenWidth * 0.3f;
        float exitButtonHeight = screenHeight * 0.1f;
        float exitButtonX = screenWidth * 0.5f - (exitButtonWidth / 2);
        float exitButtonY = screenHeight * 0.35f;

        exitButton = new CustomButton("exit_button.png", exitButtonX, exitButtonY, exitButtonWidth, exitButtonHeight);
        exitButton.setOnClickAction(() -> {
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(exitButton);
        this.ioManager.getInputManager().registerClickable(exitButton);
    }

    @Override
    public void render() {
        super.render();


    }

    @Override
    public void dispose() {
        super.dispose();
        
    }
}