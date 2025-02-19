package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScene extends Scene {

	private Player player;
	private Enemy enemy;
	private CustomButton customButton;
	
    public GameScene(String name) {
		super(name);
	}


    @Override
    public void create() {
    	
    	 super.create();
       
        player = new Player(100, 0, Color.PINK, 3.0f);
        enemy = new Enemy(500, 0, 1.5f, Color.RED);
        enemy.setTarget(player);
        
        ServiceLocator.get(CollisionManager.class).register(enemy);
        ServiceLocator.get(CollisionManager.class).register(player);

        
        ServiceLocator.get(EntityManager.class).addEntity(player);
        ServiceLocator.get(EntityManager.class).addEntity(enemy);


        ServiceLocator.get(MovementManager.class).addMovingEntity(player);
        ServiceLocator.get(MovementManager.class).addMovingEntity(enemy);
        ServiceLocator.get(MovementManager.class).addAIEntity(enemy);


        // Subscribe keyDown events for player movement
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.W, () -> player.setDirection(0, 1));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.S, () -> player.setDirection(0, -1));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.A, () -> player.setDirection(-1, 0));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.D, () -> player.setDirection(1, 0));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.SPACE, () -> player.jump(8));

        
        player.setCollisionAction(Collidable -> {
        	if(Collidable.getClass() == enemy.getClass()) {
        		ServiceLocator.get(IOManager.class).getSoundManager().dispose();
        		ServiceLocator.get(SceneManager.class).setScene(GameOverScene.class);
        	}
        });
        
        ServiceLocator.get(IOManager.class).getSoundManager().loadSound("Game Start", "alone-296348.mp3");
        ServiceLocator.get(IOManager.class).getSoundManager().playSound("Game Start");
        
        
    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render();


    }
}
