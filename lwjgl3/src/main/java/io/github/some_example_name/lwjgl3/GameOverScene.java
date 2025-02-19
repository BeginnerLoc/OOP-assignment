package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScene extends Scene{

	private Word word;
	private Word word1;
	private Word word2;
	

	public GameOverScene(String name) {
		super(name);
	}
	
	@Override
    public void create() {
		super.create();
        
        word = new Word(200, 350, 0.1f, Color.WHITE, "Game Over!!!");
        word1 = new Word(200, 300, 0.1f, Color.WHITE, "Press R to restart the game.");
        word2 = new Word(200, 250, 0.1f, Color.WHITE, "Press Q to go main Menu .");
        
        ServiceLocator.get(EntityManager.class).addWord(word);
        ServiceLocator.get(EntityManager.class).addWord(word1);
        ServiceLocator.get(EntityManager.class).addWord(word2);
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.R, () -> 
        ServiceLocator.get(SceneManager.class).setScene(GameScene.class));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.Q, () -> 
        ServiceLocator.get(SceneManager.class).setScene(MainMenuScene.class));

    }

 
    @Override
    public void render() {
    	 ScreenUtils.clear(0.5f, 0, 0, 1);
    	 super.render();
    
       
    }

}
