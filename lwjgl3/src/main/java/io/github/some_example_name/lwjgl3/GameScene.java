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
        Gdx.app.log("GameScene", "Game Scene Created");

        customButton = new CustomButton("click_me.png", 400.0f, 400.0f, 200.0f, 100.0f);
        
        customButton.setOnClickAction(() -> {
            ServiceLocator.get(SceneManager.class).setScene(MainMenuScene.class);
        });
        
        player = new Player(100, 0, Color.PINK, 4.0f);
        enemy = new Enemy(500, 0, 2.0f, Color.RED);
        enemy.setTarget(player);
        
        ServiceLocator.get(CollisionManager.class).register(enemy);
        ServiceLocator.get(CollisionManager.class).register(player);

        
        ServiceLocator.get(EntityManager.class).addEntity(player);
        ServiceLocator.get(EntityManager.class).addEntity(enemy);
        ServiceLocator.get(EntityManager.class).addEntity(customButton);

        ServiceLocator.get(MovementManager.class).addMovingEntity(player);
        ServiceLocator.get(MovementManager.class).addMovingEntity(enemy);
        ServiceLocator.get(MovementManager.class).addAIEntity(enemy);


        // Subscribe keyDown events for player movement
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.W, () -> player.setDirection(0, 1));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.S, () -> player.setDirection(0, -1));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.A, () -> player.setDirection(-1, 0));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.D, () -> player.setDirection(1, 0));
        ServiceLocator.get(IOManager.class).getInputManager().subscribeKeyDown(Keys.SPACE, () -> player.jump(30));
        ServiceLocator.get(IOManager.class).getInputManager().registerClickable(customButton);
        
        player.setCollisionAction(Collidable -> {
        	if(Collidable.getClass() == enemy.getClass()) {
        	}
        });
        
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);

        
        ServiceLocator.get(EntityManager.class).draw();
        ServiceLocator.get(CollisionManager.class).checkCollisions();
        ServiceLocator.get(MovementManager.class).updatePositions();
        ServiceLocator.get(MovementManager.class).followEntity();
        ServiceLocator.get(IOManager.class).getInputManager().update();

    }


    @Override
    public void dispose() {
    }
}
