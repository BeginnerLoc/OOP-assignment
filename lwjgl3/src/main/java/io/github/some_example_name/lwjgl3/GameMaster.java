package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;
    private ShapeRenderer sr;
    private IOManager ioManager;
    private CustomButton playButton;
    private CollisionManager collisionManager;
    private CollidableEntity player;
    private CollidableEntity enemy;
    private SceneManager sceneManager;
    
    private EntityManager em;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        sceneManager = new SceneManager();
        em = new EntityManager();
        
        // Initialize player and enemy
        player = new CollidableEntity(100, 100, 50, 50, Color.BLUE, 0);
        enemy = new CollidableEntity(300, 300, 50, 50, Color.RED, 0);
        
        em.addEntity(new Circle(500, 250, 2, 35, Color.RED)); 
        em.addEntity(new Triangle(500, 400, 1, Color.FOREST)); 
        em.addEntity(player);
        em.addEntity(enemy);
        
        // To create Square & Rectangle
        em.addEntity(new Square(40, 80, 50, 50, Color.GREEN, 0));
        em.addEntity(new Square(120, 350, 120, 50, Color.YELLOW, 0));

        // Register entities for collision detection
        collisionManager.register(player);
        collisionManager.register(enemy);
        
        
     // Create and add scenes to the SceneManager
        MainMenuScene mainMenuScene = new MainMenuScene(batch);
        GameScene gameScene = new GameScene(player, enemy, collisionManager, sr);
        PauseScene pauseMenuScene = new PauseScene(batch);
        sceneManager.addScene("MainMenu", mainMenuScene);
        sceneManager.addScene("Game", gameScene);
        //sceneManager.addScene("PauseMenu", pauseMenuScene);
        
        // Load the initial scene (e.g., main menu)
        sceneManager.loadScene("MainMenu");
    }

    @Override
    public void render() {
    	
    	
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
     // Update and render the current scene
        sceneManager.update();
        sceneManager.render();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        	em.draw(batch, sr);
        sr.end();

        // Handle player movement
        handlePlayerMovement();

        // Check for collisions
        collisionManager.checkCollisions();
    }

    private void handlePlayerMovement() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) 
            player.setX(player.getX() - 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
            player.setX(player.getX() + 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.UP)) 
            player.setY(player.getY() + 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.DOWN)) 
            player.setY(player.getY() - 100 * Gdx.graphics.getDeltaTime());
        
        
        //scene
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            sceneManager.loadScene("Game");
        } else if (Gdx.input.isKeyPressed(Keys.Q)) {
            sceneManager.loadScene("MainMenu");
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}


