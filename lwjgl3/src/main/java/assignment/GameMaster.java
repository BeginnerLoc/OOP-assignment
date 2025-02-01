package assignment;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;

import assignment.Collidable;
import assignment.CollidableEntity;
import assignment.CollisionManager;
import assignment.CustomButton;
import assignment.IOManager;

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


    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        

    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(sr);
        enemy.draw(sr);
        sr.end();
        
        collisionManager.checkCollisions();
        
        if (Gdx.input.isKeyPressed(Keys.LEFT)) 
            player.setX(player.getX() - 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
            player.setX(player.getX() + 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.UP)) 
            player.setY(player.getY() + 100 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.DOWN)) 
            player.setY(player.getY() - 100 * Gdx.graphics.getDeltaTime());

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

