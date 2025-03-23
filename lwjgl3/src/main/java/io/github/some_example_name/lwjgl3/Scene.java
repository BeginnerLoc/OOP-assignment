package io.github.some_example_name.lwjgl3;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class Scene {
    protected String name;
    protected IOManager ioManager;
    protected CollisionManager collisionManager;
    protected SceneManager sceneManager;
    protected EntityManager entityManager;
    protected MovementManager movementManager;

    public Scene(String name) {
        this.name = name;
        this.collisionManager = ServiceLocator.get(CollisionManager.class);
        this.ioManager = ServiceLocator.get(IOManager.class);
        this.movementManager = ServiceLocator.get(MovementManager.class);
        this.sceneManager = ServiceLocator.get(SceneManager.class);
        this.entityManager = ServiceLocator.get(EntityManager.class);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

 
    public void create() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    };

    public void render()
    {
    	this.entityManager.draw();
    	this.collisionManager.checkCollisions();
    	this.movementManager.updatePositions();
    	this.movementManager.followEntity();
    	this.ioManager.getInputManager().update();
    }
    
    /**
     * Called when the screen is resized
     * @param width The new width
     * @param height The new height
     */
    public void resize(int width, int height) {
        // Base implementation does nothing
        // Subclasses can override this to handle resize events
    }
    
    public void dispose() {
    	this.entityManager.dispose();
    	this.collisionManager.dispose();
    	this.movementManager.dispose();
    	this.ioManager.getInputManager().dispose();
    	 
    }
}
