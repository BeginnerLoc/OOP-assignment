package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class InstructionsScene extends Scene {
    private BackgroundEntity background;
    private CustomButton dismissButton;
    private Word word;
    
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    public InstructionsScene(String name) {
        super(name);
 
    }

    @Override
    public void create() {
        super.create();

        // Load Background Image
        background = new BackgroundEntity("instructions_bg.png", 0, 0);
        this.entityManager.addEntity(background);

//        // ✅ Define Game Instructions (Bullet Points)
//        instructions = new String[]{
//            "• Use mouse to move",
//            "• Avoid enemy",
//            "• Collect recyclable items and throw in the correct bins",
//            "• Try to score as much before time runs out!"
//        };

        float textX = screenWidth / 2 - 350;  // Center align text
        float textY = screenHeight / 2 + 200;
        
        word = new Word(textX, textY, 0.1f, Color.BLACK, 
                "Use mouse to move\nAvoid enemy\nCollect recyclable items and throw in the correct bins\nTry to score as much before time runs out! ", 2.0f);
        this.entityManager.addWord(word);
        
        // Dismiss Button - Dimensions and Position as Percentage of Screen
        float buttonWidth = screenWidth * 0.06f;  // 25% of screen width
        float buttonHeight = screenHeight * 0.06f;  // 8% of screen height
        float buttonX = screenWidth * 0.93f - (buttonWidth)/2;  // Centered horizontally (50% - 25%/2)
        float buttonY = screenHeight * 0.93f - (buttonHeight / 2);  // Centered vertically (50% - buttonHeight/2)

        dismissButton = new CustomButton("dismiss_button.png", buttonX, buttonY, buttonWidth, buttonHeight);
        dismissButton.setOnClickAction(() -> {
        	this.ioManager.getSoundManager().dispose();
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(dismissButton);
        this.ioManager.getInputManager().registerClickable(dismissButton);
        
        this.ioManager.getSoundManager().loadSound("Game Start", "alone-296348.mp3");
   	 	this.ioManager.getSoundManager().playSound("Game Start");
        
    }
    

	 

    @Override
    public void render() {
        super.render();
    }
}
