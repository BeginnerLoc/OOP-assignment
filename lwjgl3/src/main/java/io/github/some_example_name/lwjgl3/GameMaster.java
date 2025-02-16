package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;
    private ShapeRenderer sr;
    private IOManager ioManager;
    private CustomButton playButton;
    private CollisionManager collisionManager;
    private CollidableEntity player;
    private CollidableEntity nonMovingEnemy;
    private CollidableEntity movingEnemy;
    private SceneManager sceneManager;
    
    private EntityManager em;
    
    private MovementManager movementManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        BitmapFont font = new BitmapFont();
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        sceneManager = new SceneManager();
        em = new EntityManager();
        
        // Initialize player and enemy
        player = new CollidableEntity(100, 100, 50, 50, Color.BLUE, 0);
        nonMovingEnemy = new CollidableEntity(300, 300, 50, 50, Color.RED, 0);
        movingEnemy = new CollidableEntity(370, 370, 50, 50, Color.WHITE, 0);
        
        em.addEntity(new Circle(500, 250, 2, 35, Color.RED)); 
        //em.addEntity(new Triangle(500, 400, 1, Color.FOREST)); 
        em.addEntity(player);
        em.addEntity(nonMovingEnemy);
        em.addEntity(movingEnemy);
        // To create Square & Rectangle
        em.addEntity(new Square(40, 80, 50, 50, Color.GREEN, 0));
        //em.addEntity(new Square(120, 350, 120, 50, Color.YELLOW, 0));

        // Register entities for collision detection
        collisionManager.register(player);
        collisionManager.register(nonMovingEnemy);
        collisionManager.register(movingEnemy);

        List<Movable> movingEntities = new ArrayList<>();
        
        movingEntities.add(movingEnemy);  // Example enemy
        movementManager = new MovementManager(movingEntities);
        movementManager.setEntitySpeed(movingEnemy, 0f);
        movementManager.setPlayerSpeed(player, 700f);
        
// Create and add scenes to the SceneManager
        
        MainMenuScene mainMenuScene = new MainMenuScene(batch, font, sceneManager);
        GameScene gameScene = new GameScene(batch, font, sceneManager, sr);
        PauseScene pauseMenuScene = new PauseScene(batch, font, sceneManager);
        GameOverScene gameOverScene = new GameOverScene(batch, font, sceneManager);
        
        //extra scene
        SoundAdjustScene soundAdjustScene = new SoundAdjustScene(batch, font, sceneManager);
        
        sceneManager.addScene("MainMenu", mainMenuScene);
        sceneManager.addScene("Game", gameScene);
        sceneManager.addScene("PauseMenu", pauseMenuScene);
        sceneManager.addScene("GameOver", gameOverScene);
        sceneManager.addScene("SoundAdjustMenu", soundAdjustScene);
        
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
        
        movementManager.updatePositions(Gdx.graphics.getDeltaTime()); 

        // Respawn logic for moving obstacle
        for (Movable entity : movementManager.getMovingEntities()) {
            if (entity.getX() < 0) { // When it moves offscreen to the left
                entity.setX(Gdx.graphics.getWidth()); // Place it at the right edge
            }
        }

        
        // Check for collisions
        collisionManager.checkCollisions();
    }

    private void handlePlayerMovement() {
    	Vector2 direction = new Vector2(0, 0);

    	if (Gdx.input.isKeyPressed(Keys.LEFT)) direction.x = -1;
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) direction.x = 1;
        if (Gdx.input.isKeyPressed(Keys.UP)) direction.y = 1;
        if (Gdx.input.isKeyPressed(Keys.DOWN)) direction.y = -1;
        
        movementManager.moveEntity(player, Gdx.graphics.getDeltaTime(), direction.isZero() ? null : direction);

    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}


