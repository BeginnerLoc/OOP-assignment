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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScene extends Scene {
    private BackgroundEntity background;
    private CustomButton playButton;
    private CustomButton aboutButton;
    private CustomButton settingsButton;
    private SceneManager sceneManager;
    private SpriteBatch spriteBatch;

    // ✅ Alternative Fix: Constructor without SceneManager
    public MainMenuScene(String name) {
        super(name);
        this.sceneManager = null; // Can be assigned later
    }

    // ✅ Original Constructor with SceneManager
    public MainMenuScene(String name, SceneManager sceneManager) {
        super(name);
        this.sceneManager = sceneManager;
    }

    @Override
    public void create() {
        super.create();
        spriteBatch = new SpriteBatch(); // ✅ Initialize SpriteBatch

        //Load Background Image
        background = new BackgroundEntity("OVERTRASHED3.png", 0, 0);
        this.entityManager.addEntity(background);

//     // Play Button
//        playButton = new CustomButton("play_button.png", 200.0f, 200.0f, 200.0f, 50.0f);
//        playButton.setOnClickAction(() -> {
//        	this.sceneManager.setScene(GameScene.class);
//        });
//        this.entityManager.addEntity(playButton);
//        this.ioManager.getInputManager().registerClickable(playButton);
        

        // ✅ Optional Buttons (Uncomment if needed)
        /*
        // About Button
        aboutButton = new CustomButton("assets/about_button.png", 300.0f, 320.0f, 200.0f, 50.0f);
        aboutButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(AboutScene.class);
            }
        });
        this.entityManager.addEntity(aboutButton);
        this.ioManager.getInputManager().registerClickable(aboutButton);

        // Settings Button
        settingsButton = new CustomButton("assets/settings_button.png", 300.0f, 390.0f, 200.0f, 50.0f);
        settingsButton.setOnClickAction(() -> {
            if (sceneManager != null) {
                sceneManager.setScene(SettingsScene.class);
            }
        });
        this.entityManager.addEntity(settingsButton);
        this.ioManager.getInputManager().registerClickable(settingsButton);
        */
    }

    @Override
    public void render() {
        super.render();

        if (!spriteBatch.isDrawing()) { // ✅ Prevent multiple begin() calls
            spriteBatch.begin();
        }

        if (background != null) {
            background.draw(spriteBatch); // ✅ Background is drawn correctly
        }

        spriteBatch.end(); // ✅ Ensure end() is called only once
    }

}