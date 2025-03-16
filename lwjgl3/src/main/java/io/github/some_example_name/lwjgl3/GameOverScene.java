package io.github.some_example_name.lwjgl3;
//import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScene extends Scene{
    private BackgroundEntity background;
    
    private CustomButton yesButton;
    private CustomButton noButton;

//	private Word word;
//	private Word word1;
//	private Word word2;
	

	public GameOverScene(String name) {
		super(name);
	}
	
	@Override
    public void create() {
		super.create();
		

        
//        word = new Word(200, 350, 0.1f, Color.WHITE, "Game Over!!!");
//        word1 = new Word(200, 300, 0.1f, Color.WHITE, "Press R to restart the game.");
//        word2 = new Word(200, 250, 0.1f, Color.WHITE, "Press Q to go main Menu .");
//        
//        this.entityManager.addWord(word);
//        this.entityManager.addWord(word1);
//        this.entityManager.addWord(word2);
//        
//        this.ioManager.getInputManager().subscribeKeyDown(Keys.R, () -> 
//        ServiceLocator.get(SceneManager.class).setScene(GameScene.class));
//      
//        this.ioManager.getInputManager().subscribeKeyDown(Keys.Q, () -> 
//        ServiceLocator.get(SceneManager.class).setScene(MainMenuScene.class));
        
      // Get screen dimensions
      float screenWidth = Gdx.graphics.getWidth();
      float screenHeight = Gdx.graphics.getHeight();

   // Load Background Image with a scaling factor
      float backgroundScale = .95f; // Adjust this value to scale the background image
      background = new BackgroundEntity("gameover_bg.png", -10, -5, backgroundScale);
      this.entityManager.addEntity(background);
      
      // YES Button 
      float buttonWidth = screenWidth * 0.15f;  
      float buttonHeight = screenHeight * 0.10f;  
      float buttonX = screenWidth * 0.3f - (buttonWidth)/2;  
      float buttonY = screenHeight * 0.3f - (buttonHeight / 2);  

      yesButton = new CustomButton("yes_button.png", buttonX, buttonY, buttonWidth, buttonHeight);
      yesButton.setOnClickAction(() -> {
          this.sceneManager.setScene(GameScene.class);
      });
      this.entityManager.addEntity(yesButton);
      this.ioManager.getInputManager().registerClickable(yesButton);
      
      // NO Button
      noButton = new CustomButton("no_button.png", buttonX + 400f, buttonY, buttonWidth, buttonHeight);
      noButton.setOnClickAction(() -> {
          this.sceneManager.setScene(MainMenuScene.class);
      });
      this.entityManager.addEntity(noButton);
      this.ioManager.getInputManager().registerClickable(noButton);
      
    }

 
    @Override
    public void render() {
    	 ScreenUtils.clear(0.5f, 0, 0, 1);
    	 super.render();
    
       
    }

}
