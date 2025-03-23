package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameMaster extends ApplicationAdapter {
    private IOManager ioManager;
    private CollisionManager collisionManager;
    private SceneManager sceneManager;
    private EntityManager entityManager;
    private MovementManager movementManager;
   
       
    @Override
    public void create() {    	
    
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        movementManager = new MovementManager();
        sceneManager = new SceneManager();
        
        
        ServiceLocator.register(SpriteBatch.class, new SpriteBatch());
        ServiceLocator.register(ShapeRenderer.class, new ShapeRenderer());
        entityManager = new EntityManager();
        ServiceLocator.register(CollisionManager.class, collisionManager);
        ServiceLocator.register(EntityManager.class, entityManager);
        ServiceLocator.register(MovementManager.class, movementManager);
        ServiceLocator.register(IOManager.class, ioManager);
        ServiceLocator.register(SceneManager.class, sceneManager);
        
        // Register sound effects using broccoli.png as placeholder sound files
        // In a real game, you'd add proper sound files
//        ioManager.getSoundManager().loadSound("pickup", "broccoli.png");
//        ioManager.getSoundManager().loadSound("correct", "broccoli.png");
//        ioManager.getSoundManager().loadSound("wrong", "broccoli.png");
//        ioManager.getSoundManager().loadSound("game_over", "broccoli.png");
        
        
        
        
        //Register Sound
        //Main Menu
        //this.ioManager.getSoundManager().loadSound("background_music_MMS", "MainMenu_Under the Sea - Fearless Flyers.mp3");
        
        //Game
        //this.ioManager.getSoundManager().loadSound("background_music_GS", "GameScene_BGM Dark Maplemas_ O Holy Fright.mp3");
        this.ioManager.getSoundManager().loadSound("game_over", "Game Over Sound Effect.mp3");
        this.ioManager.getSoundManager().loadSound("pickup", "Item Pickup [Sound Effect].mp3");
        this.ioManager.getSoundManager().loadSound("trash_correct", "Correct Answer sound effect.mp3");
        this.ioManager.getSoundManager().loadSound("trash_wrong", "Wrong Answer Sound effect.mp3");
        this.ioManager.getSoundManager().loadSound("Power Up", "powerup.mp3");
        this.ioManager.getSoundManager().loadSound("click", "Mouse Click Sound Effect.mp3");
        
        
        
        // Defining Scenes in Game
        sceneManager.registerScene(MainMenuScene.class, new MainMenuScene("Menu"));
        sceneManager.registerScene(GameScene.class, new GameScene("Game"));
        sceneManager.registerScene(GameOverScene.class, new GameOverScene("GameOver"));
        sceneManager.registerScene(AboutScene.class, new AboutScene("About"));
        sceneManager.registerScene(SettingsScene.class, new SettingsScene("Settings"));
        sceneManager.registerScene(InstructionsScene.class, new InstructionsScene("Instructions"));
        sceneManager.registerScene(PauseScene.class, new PauseScene("Pause"));

        
        
        Gdx.input.setInputProcessor(ioManager.getInputManager());

        // Start with the menu scene
        sceneManager.setScene(MainMenuScene.class);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        sceneManager.render();        
    }

    @Override
    public void dispose() {
        // Clean up resources
        ServiceLocator.get(SpriteBatch.class).dispose();
        ServiceLocator.get(ShapeRenderer.class).dispose();
        ioManager.dispose();
    }
}
