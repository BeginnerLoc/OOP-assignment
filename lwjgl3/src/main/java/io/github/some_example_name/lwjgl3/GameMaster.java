//package io.github.some_example_name.lwjgl3;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//
//import java.util.Random;
//
//public class GameMaster extends ApplicationAdapter {
//    private SpriteBatch batch;
//    private ShapeRenderer sr;
//    private EntityManager entityManager;
//    private TextureObject bucket;
//
//    @Override
//    public void create() {
//        batch = new SpriteBatch();
//        sr = new ShapeRenderer();
//        entityManager = new EntityManager();
//        Random random = new Random();
//
//        // Create bucket (player-controlled)
//        bucket = new TextureObject("bucket.png", 600, 0, 0);
//        entityManager.addEntity(bucket);
//
//        // Create falling droplets (AI-controlled)
//        for (int i = 0; i < 10; i++) {
//            int randomSpeed = 2 + random.nextInt(5);
//            TextureObject drop = new TextureObject("droplet.png", 50 * i, 125 * i, randomSpeed);
//            drop.setAIControlled(true);  // Mark drop as AI-controlled
//            entityManager.addEntity(drop);
//        }
//
//        // Create player-controlled circle and triangle
//        entityManager.addEntity(new Circle(100, 100, 2, Color.RED, 50));
//        entityManager.addEntity(new Triangle(200, 300, Color.BLUE, 2));
//    }
//
//    @Override
//    public void render() {
//        ScreenUtils.clear(0, 0, 0.2f, 1); // Clear the screen
//
//        // Handle bucket movement (user-controlled)
//        handleBucketMovement();
//
//        // Update all entities
//        entityManager.movement();
//        entityManager.update();
//
//        // Draw all entities
//        entityManager.draw(batch, sr);
//    }
//
//    /** Handles the player-controlled bucket movement */
//    private void handleBucketMovement() {
//        if (Gdx.input.isKeyPressed(Keys.LEFT)) 
//            bucket.setX(bucket.getX() - 100 * Gdx.graphics.getDeltaTime());
//        if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
//            bucket.setX(bucket.getX() + 100 * Gdx.graphics.getDeltaTime());
//        if (Gdx.input.isKeyPressed(Keys.UP)) 
//            bucket.setY(bucket.getY() + 100 * Gdx.graphics.getDeltaTime());
//        if (Gdx.input.isKeyPressed(Keys.DOWN)) 
//            bucket.setY(bucket.getY() - 100 * Gdx.graphics.getDeltaTime());
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        sr.dispose();
//    }
//}

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
    private CustomButton playButton;
    private CollisionManager collisionManager;
    private CollidableEntity player;
    private CollidableEntity enemy;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        collisionManager = new CollisionManager();
        ioManager = new IOManager();

        // Initialize player and enemy
        player = new CollidableEntity(100, 100, 50, 50, Color.GREEN, 0);
        enemy = new CollidableEntity(300, 300, 50, 50, Color.RED, 0);

        // Register entities for collision detection
        collisionManager.register(player);

        collisionManager.register(enemy);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(sr);
        enemy.draw(sr);
        sr.end();

        // Handle player movement
        handlePlayerMovement();

        // Check for collisions
        collisionManager.checkCollisions();
    }

    private void handlePlayerMovement() {
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
        sr.dispose();
    }
}


