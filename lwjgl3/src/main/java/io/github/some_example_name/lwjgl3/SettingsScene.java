package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;

public class SettingsScene extends Scene {
    private BackgroundEntity background;
    private CustomButton dismissButton;
    private CustomButton aboutButton;
    private CustomButton settingsButton;
    
    private CustomButton easylvlButton;
    private CustomButton mediumlvlButton;
    private CustomButton hardlvlButton;


    public SettingsScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();

        // Get screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Load Background Image
        background = new BackgroundEntity("settings_bg.png", 0, 0);
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

        // Additional buttons can be defined similarly using relative dimensions.
        
        // Easy Level Button
        easylvlButton = new CustomButton("easy_level.png", buttonX - 890f, buttonY - 450f, buttonWidth, buttonHeight);
        easylvlButton.setOnClickAction(() -> {
        	
        	// Change the button's image to another emoji when clicked
        	easylvlButton.setImage("easy_level2.png");

        });
        this.entityManager.addEntity(easylvlButton);
        this.ioManager.getInputManager().registerClickable(easylvlButton);
        
        // Medium Level Button
        mediumlvlButton = new CustomButton("medium_level.png", buttonX - 790f, buttonY - 450f, buttonWidth, buttonHeight);
        mediumlvlButton.setOnClickAction(() -> {
        	
        	// Change the button's image to another emoji when clicked
        	mediumlvlButton.setImage("medium_level2.png");

        });
        this.entityManager.addEntity(mediumlvlButton);
        this.ioManager.getInputManager().registerClickable(mediumlvlButton);
        
     // Hard Level Button
        hardlvlButton = new CustomButton("hard_level.png", buttonX - 690f, buttonY - 450f, buttonWidth, buttonHeight);
        hardlvlButton.setOnClickAction(() -> {
        	
        	// Change the button's image to another emoji when clicked
        	hardlvlButton.setImage("hard_level2.png");

        });
        this.entityManager.addEntity(hardlvlButton);
        this.ioManager.getInputManager().registerClickable(hardlvlButton);
        
        
    }

    @Override
    public void render() {
        super.render();
    }

}