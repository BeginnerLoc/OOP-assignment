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

     // Load Background Image with a scaling factor
        float backgroundScale = 1.01f; // Adjust this value to scale the background image
        background = new BackgroundEntity("instructions_bg.png", -35, 1, backgroundScale);
        this.entityManager.addEntity(background);

//        // ✅ Define Game Instructions (Bullet Points)
//        instructions = new String[]{
//            "• Use mouse to move",
//            "• Avoid enemy",
//            "• Collect recyclable items and throw in the correct bins",
//            "• Try to score as much before time runs out!"
//        };

        float textX = screenWidth / 2 - 200;  // Center align text
        float textY = screenHeight / 2 + 100;
        
        word = new Word(textX, textY, 0.1f, Color.BLACK, 
                "Use W,A,S,D to move\nAvoid enemy\nCollect recyclable items and throw in the correct bins\nTry to score as much before time runs out! ", 1f);
        this.entityManager.addWord(word);
        
        float buttonWidth = screenWidth * 0.06f;  
        float buttonHeight = screenHeight * 0.06f;  
        float buttonX = screenWidth * 0.95f - (buttonWidth)/2;  
        float buttonY = screenHeight * 0.95f - (buttonHeight / 2);

        dismissButton = new CustomButton("dismiss_button.png", buttonX, buttonY, buttonWidth, buttonHeight);
        dismissButton.setOnClickAction(() -> {
        	this.ioManager.getSoundManager().dispose();
            this.sceneManager.setScene(MainMenuScene.class);
        });
        this.entityManager.addEntity(dismissButton);
        this.ioManager.getInputManager().registerClickable(dismissButton);
        
//        this.ioManager.getSoundManager().loadSound("Game Start", "alone-296348.mp3");
//   	 	this.ioManager.getSoundManager().playSound("Game Start");
        
    }
    

	 

    @Override
    public void render() {
        super.render();
    }
}
