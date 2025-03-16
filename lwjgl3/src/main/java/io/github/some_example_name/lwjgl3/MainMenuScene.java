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

        // Load Background Image with a scaling factor
        float backgroundScale = .95f; // Adjust this value to scale the background image
        background = new BackgroundEntity("OVERTRASHED.png", -10, -5, backgroundScale);
        this.entityManager.addEntity(background);

        
     // Play Button - Set specific dimensions
        float playButtonWidth = 450f;  
        float playButtonHeight = 250f;  
        float playButtonX = screenWidth * 0.5f - (playButtonWidth)/2;  
        float playButtonY = screenHeight * 0.55f - (playButtonHeight / 2);  

        playButton = new CustomButton("play_button.png", playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        playButton.setOnClickAction(() -> {
            this.sceneManager.setScene(GameScene.class);
        });
        this.entityManager.addEntity(playButton);
        this.ioManager.getInputManager().registerClickable(playButton);

        // About Button - Set specific dimensions
        float aboutButtonWidth = 660f;  
        float aboutButtonHeight = 350f;  
        float aboutButtonX = screenWidth * 0.5f - (aboutButtonWidth)/2;  
        float aboutButtonY = playButtonY - 150f;  

        aboutButton = new CustomButton("about_button.png", aboutButtonX, aboutButtonY, aboutButtonWidth, aboutButtonHeight);
        aboutButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(AboutScene.class);
            }
        });
        this.entityManager.addEntity(aboutButton);
        this.ioManager.getInputManager().registerClickable(aboutButton);

        // Settings Button - Set specific dimensions
        float settingsButtonWidth = 330f;  
        float settingsButtonHeight = 180f;  
        float settingsButtonX = screenWidth * 0.5f - (settingsButtonWidth)/2;  
        float settingsButtonY = aboutButtonY - 30f;  

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
    }

}
