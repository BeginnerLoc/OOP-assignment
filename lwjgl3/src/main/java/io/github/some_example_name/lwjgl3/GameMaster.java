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
    private CollisionManager collisionManager;
    private SceneManager sceneManager;
    private EntityManager entityManager;
    private MovementManager movementManager;
    private Player player;
    private Enemy enemy;
    
    private int gravity;
    
    @Override
    public void create() {
    	
    	
    	gravity = 0;
    	
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        sceneManager = new SceneManager();
        entityManager = new EntityManager();
        movementManager = new MovementManager();

        player = new Player(100, 0, Color.BLUE, 10.0f);
        enemy = new Enemy(300, 0, 2.0f, Color.RED);
        enemy.setTarget(player);

        
        //entity
        entityManager.addEntity(player);
        entityManager.addEntity(enemy);
        
        //movement
        movementManager.addMovingEntity(player);
        movementManager.addMovingEntity(enemy);
        movementManager.addAIEntities(enemy);

        
        //collision
        collisionManager.register(player);
        collisionManager.register(enemy);
      
        player.setCollisionAction(Collidable -> {
        	if(Collidable.getClass() == enemy.getClass()) {
            	player.setColor(Color.PINK);
        	}
        });
        

        // Subscribe keyDown events for player movement
        ioManager.getInputManager().subscribeKeyDown(Keys.W, () -> player.setDirection(0, 1));
        ioManager.getInputManager().subscribeKeyDown(Keys.S, () -> player.setDirection(0, -1));
        ioManager.getInputManager().subscribeKeyDown(Keys.A, () -> player.setDirection(-1, 0));
        ioManager.getInputManager().subscribeKeyDown(Keys.D, () -> player.setDirection(1, 0));
        ioManager.getInputManager().subscribeKeyDown(Keys.SPACE, () -> player.jump(30));

        // Register InputManager
        Gdx.input.setInputProcessor(ioManager.getInputManager());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        sceneManager.update();
        sceneManager.render();

               
        collisionManager.checkCollisions();
        movementManager.followEntity();
        movementManager.updatePositions();
        entityManager.draw(batch, sr);
        movementManager.followWorldRule(gravity);
    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}
