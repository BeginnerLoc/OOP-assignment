package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;
    private ShapeRenderer sr;
    private IOManager ioManager;
    private CustomButton playButton;
    private CollisionManager collisionManager;

    private SceneManager sceneManager;
    
    private EntityManager em;
    
    private MovementManager movementManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        sceneManager = new SceneManager();
        em = new EntityManager();
       
        
     }

    @Override
    public void render() {
    	
    	
        ScreenUtils.clear(0, 0, 0.2f, 1);
        sceneManager.update();
        sceneManager.render();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        em.draw(batch, sr);
        sr.end();        
    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}


