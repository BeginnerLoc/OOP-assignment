package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class AboutScene extends Scene {
    private BackgroundEntity background;
    private CustomButton dismissButton;
    private CustomButton aboutButton;
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

        // ✅ Adjusted Text Positioning for Centering
        float textX = screenWidth / 2 - 250;  // Center align text
        float textY = screenHeight / 2 + 80;

        // ✅ Increase Font Size by Changing `scale` (Larger Numbers = Bigger Text)
        word = new Word(textX, textY, 0.1f, Color.WHITE, 
                        "OverTrashed is a fun and educational game!\nAvoid enemies & save the environment!", 2.5f);
        word1 = new Word(textX, textY - 80, 0.1f, Color.WHITE, 
                        "Credits: (P2T2) Gregory, Cavell, Wenjing, Royston, Aish, Luke", 2.0f);

        this.entityManager.addWord(word);
        this.entityManager.addWord(word1);

//        // Get screen dimensions
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();

        // Load Background Image
        background = new BackgroundEntity("about_bg.png", 0, 0);
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