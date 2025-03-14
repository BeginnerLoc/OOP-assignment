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

import com.badlogic.gdx.Gdx;

public class MainMenuScene extends Scene {
    private BackgroundEntity background;
    private CustomButton playButton;
    private CustomButton aboutButton;
    private CustomButton settingsButton;


    public MainMenuScene(String name) {
        super(name);
    }

    @Override
    public void create() {
        super.create();

        // Get screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Load Background Image
        background = new BackgroundEntity("OVERTRASHED.png", 0, 0);
        this.entityManager.addEntity(background);

        // Play Button - Dimensions and Position as Percentage of Screen
        float buttonWidth = screenWidth * 0.20f;  
        float buttonHeight = screenHeight * 0.07f;  
        float buttonX = screenWidth * 0.5f - (buttonWidth)/2;  
        float buttonY = screenHeight * 0.5f - (buttonHeight / 2);  

        playButton = new CustomButton("play_button.png", buttonX, buttonY, buttonWidth, buttonHeight);
        playButton.setOnClickAction(() -> {
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(playButton);
        this.ioManager.getInputManager().registerClickable(playButton);

        // Additional buttons can be defined similarly using relative dimensions.
        
        // About Button
        aboutButton = new CustomButton("about_button.png", buttonX, buttonY - 100f, buttonWidth, buttonHeight);
        aboutButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(AboutScene.class);
            }
        });
        this.entityManager.addEntity(aboutButton);
        this.ioManager.getInputManager().registerClickable(aboutButton);
        
        // Settings Button
        settingsButton = new CustomButton("settings_button.png", buttonX, buttonY - 200f, buttonWidth, buttonHeight);
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
    }

}
