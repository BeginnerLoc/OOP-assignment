package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScene extends Scene {

	private Player player;
	private Enemy enemy;
	
	private BackgroundEntity background;
	
    public GameScene(String name) {
		super(name);
	}


    @Override
    public void create() {
    	
    	super.create();
       
    	System.out.println("Game Scene");
    	
    	// Load Background Image
        background = new BackgroundEntity("brickwall.png", 0, 0);
        this.entityManager.addEntity(background);
    	
        // Creation of player & enemy
        player = new Player(100, 300, "mrbean.png", 3.0f, 65f, 90f);
        enemy = new Enemy(500, 0, "grandmother.png", 1.5f, 75f, 85f);
        enemy.setTarget(player);
        
        this.collisionManager.register(enemy);
        this.collisionManager.register(player);
        
        this.entityManager.addEntity(enemy);
        this.entityManager.addEntity(player);
        
        
        this.movementManager.addMovingEntity(player);
        this.movementManager.addMovingEntity(enemy);
        this.movementManager.addAIEntity(enemy);
    

        // Subscribe keyDown events for player movement
        this.ioManager.getInputManager().subscribeKeyDown(Keys.W, () -> player.setDirection(0, 1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.S, () -> player.setDirection(0, -1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.A, () -> player.setDirection(-1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.D, () -> player.setDirection(1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SPACE, () -> player.jump(2));


        
        player.setCollisionAction(Collidable -> {
        	if(Collidable.getClass() == enemy.getClass()) {
        		 this.ioManager.getSoundManager().dispose();
        		 this.sceneManager.setScene(GameOverScene.class);
        	}
        });
        
        
		 this.ioManager.getSoundManager().loadSound("Game Start", "alone-296348.mp3");
		 this.ioManager.getSoundManager().playSound("Game Start");
        
        
    }


    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render();


    }
}
