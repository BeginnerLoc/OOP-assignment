package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InstructionsScene extends Scene {
    private BackgroundEntity background;
    private CustomButton dismissButton;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
//    private String[] instructions;
    private Word word;
    
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    public InstructionsScene(String name) {
        super(name);
 
    }

    @Override
    public void create() {
        super.create();
//        spriteBatch = new SpriteBatch();
        
        

        // ✅ Load Background Image
        background = new BackgroundEntity("instructions_bg.png", 0, 0);
        this.entityManager.addEntity(background);

//        // ✅ Define Game Instructions (Bullet Points)
//        instructions = new String[]{
//            "• Use mouse to move",
//            "• Avoid enemy",
//            "• Collect recyclable items and throw in the correct bins",
//            "• Try to score as much before time runs out!"
//        };
     // Adjusted Text Positioning for Centering
        float textX = screenWidth / 2 - 310;  // Center align text
        float textY = screenHeight / 2 - 50;

        word = new Word(textX, textY, 0.1f, Color.BLACK, 
                "OverTrashed is a fun and educational game! \\nAvoid enemies & save the environmen", 2.0f);

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
        
    }

    @Override
    public void render() {
        super.render();
//        spriteBatch.begin();
//
//        // ✅ Draw "Instructions" Title
//        font.getData().setScale(2.0f);
//        font.draw(spriteBatch, "Game Instructions", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 100);
//
//        // ✅ Draw Bullet Points
//        font.getData().setScale(1.5f);
//        float startY = Gdx.graphics.getHeight() - 200;
//        for (String instruction : instructions) {
//            font.draw(spriteBatch, instruction, 100, startY);
//            startY -= 50;  // Move down for next bullet point
//        }
//
//        spriteBatch.end();
    }
}
