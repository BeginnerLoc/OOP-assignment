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
    private Object player;
    private Object nonMovingEnemy;
    private Object movingEnemy;
    private SceneManager sceneManager;
    private Object testEntity;
    
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
        player = new Object(100, 100, 50, 50, Color.BLUE, 0);
        nonMovingEnemy = new Object(300, 300, 50, 50, Color.RED, 0);
        movingEnemy = new Object(370, 370, 50, 50, Color.WHITE, 0);

        testEntity = new Object(500, 50, 50, 50, Color.PINK, 100);
        
        em.addEntity(testEntity);
        em.addEntity(player);
        em.addEntity(nonMovingEnemy);
        em.addEntity(movingEnemy);

        // Register entities for collision detection
        collisionManager.register(player);
        collisionManager.register(nonMovingEnemy);
        collisionManager.register(movingEnemy);
        collisionManager.register(testEntity);

        List<Movable> movingEntities = new ArrayList<>();
        
        movingEntities.add(movingEnemy);  // Example enemy
        movementManager = new MovementManager(movingEntities);
        movementManager.setEntitySpeed(movingEnemy, 200f);
        movementManager.setPlayerSpeed(testEntity, 200f);
        
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
//        End of Scene Manager
        
//        This is to set reaction due to collision
        testEntity.setCollisionAction(Collidable -> {
        	if (Collidable instanceof Object) {
        	sceneManager.loadScene("GameOver");
        	}
        });
   }
    

    

    @Override
    public void render() {
    	
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
     // Update and render the current scene
        sceneManager.update();
        sceneManager.render();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        em.draw(batch, sr);
        em.update();
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
        
        movementManager.moveEntity(testEntity, Gdx.graphics.getDeltaTime(), direction.isZero() ? null : direction);

    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}


