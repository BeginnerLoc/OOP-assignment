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
        
        // Defining Scenes in Game
        sceneManager.registerScene(MainMenuScene.class, new MainMenuScene("Menu"));
        sceneManager.registerScene(GameScene.class, new GameScene("Game"));
        sceneManager.registerScene(GameOverScene.class, new GameOverScene("GameOver"));
        sceneManager.registerScene(AboutScene.class, new AboutScene("About"));
        sceneManager.registerScene(SettingsScene.class, new SettingsScene("Settings"));
        
        
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

    }
    
}
