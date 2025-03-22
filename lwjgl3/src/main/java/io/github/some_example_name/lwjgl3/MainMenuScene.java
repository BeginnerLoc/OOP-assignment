//package io.github.some_example_name.lwjgl3;
//
//public class  MainMenuScene extends Scene {
//	private CustomButton customButton;
//	
//	private CustomButton playButton;
//	private CustomButton aboutButton;
//	private CustomButton settingsButton;
//	
//	//private mainMenuBg mainMenuBg;
//
//    public MainMenuScene(String name) {
//		super(name);
//	}
//
//	@Override
//    public void create() {
//        super.create();
//        
//        // DELETE ME CustomButton(String imagePath, float x, float y, float width, float height)
//        
//        // Play Button
//        playButton = new CustomButton("play_button.png", 200.0f, 200.0f, 200.0f, 50.0f);
//        playButton.setOnClickAction(() -> {
//        	this.sceneManager.setScene(GameScene.class);
//        });
//        this.entityManager.addEntity(playButton);
//        this.ioManager.getInputManager().registerClickable(playButton);
//        
//        /**
//        // Settings Button
//        settingsButton = new CustomButton("settings_button.png", 200.0f, 100.0f, 200.0f, 50.0f);
//        settingsButton.setOnClickAction(() -> {
//            this.sceneManager.setScene(SettingsScene.class);
//        });
//        this.entityManager.addEntity(settingsButton);
//        this.ioManager.getInputManager().registerClickable(settingsButton);
//
//        
//        // Exit Button
//        exitButton = new CustomButton("exit_button.png", 200.0f, 400.0f, 200.0f, 50.0f);
//        exitButton.setOnClickAction(() -> {
//            System.exit(0);
//        });
//        this.entityManager.addEntity(exitButton);
//        this.ioManager.getInputManager().registerClickable(exitButton);
//        **/
//        
//        //mainMenuBg = new mainMenuBg("waste_screenshot.png", 200.0f, 200.0f, 200.0f, 100.0f);
//
//    
//    }
//
// 
//    @Override
//    public void render() {
//    	super.render();
//
//    }
//    
//
//}

package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.Gdx;

public class MainMenuScene extends Scene {
    private BackgroundEntity background;
    private CustomButton playButton;
    private CustomButton aboutButton;
    private CustomButton settingsButton;
    private CustomButton instructionsButton;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    public MainMenuScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();
     
     // Load and play shared background music for MainMenuScene and AboutScene
        if (!this.ioManager.getSoundManager().isBackgroundMusicPlaying()) {
            this.ioManager.getSoundManager().loadSound("background_music_MMS", "MainMenu_Under the Sea - Fearless Flyers.mp3");
            this.ioManager.getSoundManager().playBackgroundMusic("background_music_MMS");
        }
        
        // Get screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

     // Load Background Image with a scaling factor
        float backgroundScale = .95f; // Adjust this value to scale the background image
        background = new BackgroundEntity("OVERTRASHED.png", -5, 0, backgroundScale);
        this.entityManager.addEntity(background);

     // Play Button - Set specific dimensions
        float playButtonWidth = screenWidth * 0.3f; 
        float playButtonHeight = screenHeight * 0.1f;
        float playButtonX = screenWidth * 0.5f - (playButtonWidth) / 2;
        float playButtonY = screenHeight - (screenHeight * 0.55f);

        playButton = new CustomButton("play_button.png", playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        playButton.setOnClickAction(() -> {
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(playButton);
        this.ioManager.getInputManager().registerClickable(playButton);
   

        // About Button - Set specific dimensions
        float aboutButtonWidth = screenWidth * 0.3f; 
        float aboutButtonHeight = screenHeight * 0.1f;
        float aboutButtonX = screenWidth * 0.5f - (aboutButtonWidth) / 2;
        float aboutButtonY = screenHeight * 0.38f - (aboutButtonHeight / 2);

        aboutButton = new CustomButton("about_button.png", aboutButtonX, aboutButtonY, aboutButtonWidth, aboutButtonHeight);
        aboutButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(AboutScene.class);
            }
        });
        this.entityManager.addEntity(aboutButton);
        this.ioManager.getInputManager().registerClickable(aboutButton);
        
        
        // Instruction Button - Set specific dimensions
        float InstructionButtonWidth = screenWidth * 0.3f; 
        float InstructionButtonHeight = screenHeight * 0.1f;
        float InstructionButtonX = screenWidth * 0.5f - (InstructionButtonWidth) / 2;
        float InstructionButtonY = screenHeight * 0.26f - (InstructionButtonHeight / 2);

        instructionsButton = new CustomButton("instructions_button.png", InstructionButtonX, InstructionButtonY, InstructionButtonWidth, InstructionButtonHeight);
        instructionsButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(InstructionsScene.class);
            }
        });
        this.entityManager.addEntity(instructionsButton);
        this.ioManager.getInputManager().registerClickable(instructionsButton);   
        
        // Settings Button - Set specific dimensions
        float settingsButtonWidth = screenWidth * 0.3f; 
        float settingsButtonHeight = screenHeight * 0.1f;
        float settingsButtonX = screenWidth * 0.5f - (settingsButtonWidth) / 2;
        float settingsButtonY = screenHeight * 0.14f - (settingsButtonHeight / 2);


        settingsButton = new CustomButton("settings_button.png", settingsButtonX, settingsButtonY, settingsButtonWidth, settingsButtonHeight);
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
        super.render();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color for bounding boxes

        // Draw bounding boxes for each button
        drawButtonBounds(playButton);
        drawButtonBounds(aboutButton);
        drawButtonBounds(instructionsButton);
        drawButtonBounds(settingsButton);

        shapeRenderer.end();


        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color for bounding boxes

        // Draw bounding boxes for each button
        drawButtonBounds(playButton);
        drawButtonBounds(aboutButton);
        drawButtonBounds(instructionsButton);
        drawButtonBounds(settingsButton);

        shapeRenderer.end();

    }
    
    private void drawButtonBounds(CustomButton button) {
        if (button != null) {
            shapeRenderer.rect(button.getBounds().x, button.getBounds().y, button.getBounds().width, button.getBounds().height);
        }


}
    @Override
    public void dispose() {
        super.dispose();
    }

    
}
