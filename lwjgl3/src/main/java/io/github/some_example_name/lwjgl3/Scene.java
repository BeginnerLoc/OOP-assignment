package io.github.some_example_name.lwjgl3;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scene {
    private String name;
    

    public Scene(String name) {
        this.name = name;
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
    	ServiceLocator.get(EntityManager.class).draw();
        ServiceLocator.get(CollisionManager.class).checkCollisions();
        ServiceLocator.get(MovementManager.class).updatePositions();
        ServiceLocator.get(MovementManager.class).followEntity();
        ServiceLocator.get(IOManager.class).getInputManager().update();
    }
    
    public void dispose() {
    	ServiceLocator.get(CollisionManager.class).dispose();
    	 ServiceLocator.get(MovementManager.class).dispose();
    	 ServiceLocator.get(EntityManager.class).dispose();
    	 
    }
}
