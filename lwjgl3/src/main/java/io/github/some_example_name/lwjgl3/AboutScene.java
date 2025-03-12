package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class AboutScene extends Scene {
    private BackgroundEntity background;
    private CustomButton dismissButton;
    private CustomButton aboutButton;
    private CustomButton infoButton;
    private CustomButton settingsButton;
    private Word word;
    private Word word1;


    public AboutScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();
        
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Adjusted Text Positioning for Centering
        float textX = screenWidth / 2 - 310;  // Center align text
        float textY = screenHeight / 2 - 50;

        // CHECK IF NEED TO CENTRALISE
        // Increase Font Size by Changing `scale` (Larger Numbers = Bigger Text)
        word = new Word(textX, textY, 0.1f, Color.BLACK, 
                        "OverTrashed is a fun and educational game! \nAvoid enemies & save the environment!", 2.0f);
        word1 = new Word(textX, textY - 80, 0.1f, Color.BLACK, 
                        "Credits: (P2T2) \nGregory, Cavell, Wenjing, Royston, Aish, Luke", 1.5f);

        this.entityManager.addWord(word);
        this.entityManager.addWord(word1);

//        // Get screen dimensions
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();

        // Load Background Image
        background = new BackgroundEntity("about_bg2.png", 0, 0);
        this.entityManager.addEntity(background);

        // Dismiss Button - Dimensions and Position as Percentage of Screen
        float buttonWidth = screenWidth * 0.06f;  // 25% of screen width
        float buttonHeight = screenHeight * 0.06f;  // 8% of screen height
        float buttonX = screenWidth * 0.93f - (buttonWidth)/2;  // Centered horizontally (50% - 25%/2)
        float buttonY = screenHeight * 0.93f - (buttonHeight / 2);  // Centered vertically (50% - buttonHeight/2)

        dismissButton = new CustomButton("dismiss_button.png", buttonX, buttonY, buttonWidth, buttonHeight);
        dismissButton.setOnClickAction(() -> {
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(dismissButton);
        this.ioManager.getInputManager().registerClickable(dismissButton);
        
        // Play Button - Dimensions and Position as Percentage of Screen
        float buttonWidth1 = screenWidth * 0.15f;  // 25% of screen width
        float buttonHeight1 = screenHeight * 0.05f;  // 8% of screen height
        float buttonX1 = screenWidth * 0.45f - (buttonWidth)/2;  // Centered horizontally (50% - 25%/2)
        float buttonY1 = screenHeight * 0.27f - (buttonHeight / 2);  // Centered vertically (50% - buttonHeight/2)

        infoButton = new CustomButton("instructions_button.png", buttonX1, buttonY1, buttonWidth1, buttonHeight1);
        infoButton.setOnClickAction(() -> {
            this.sceneManager.setScene(InstructionsScene.class);
        });
        this.entityManager.addEntity(infoButton);
        this.ioManager.getInputManager().registerClickable(infoButton);

        this.entityManager.addEntity(infoButton);
//        this.ioManager.getInputManager().registerClickable(infoButton);
//
//        // Additional buttons can be defined similarly using relative dimensions.
//        
//        // About Button
//        aboutButton = new CustomButton("about_button.png", buttonX, buttonY - 100f, buttonWidth, buttonHeight);
//        
//        this.entityManager.addEntity(aboutButton);
        
        
    }

    @Override
    public void render() {
        super.render();
    }

}