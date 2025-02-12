package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class GameScene extends Scene {
    private CollidableEntity player;
    private CollidableEntity enemy;
    private CollisionManager collisionManager;
    private ShapeRenderer sr;
    private SceneManager sceneManager; // Add this field

    public GameScene(CollidableEntity player, CollidableEntity enemy, CollisionManager collisionManager, ShapeRenderer sr, SceneManager sceneManager) {
        super("Game");
        this.player = player;
        this.enemy = enemy;
        this.collisionManager = collisionManager;
        this.sr = sr;
        this.sceneManager = sceneManager;
    }

    @Override
    public void load() {
        System.out.println("Loading Game Scene...");
    }

    @Override
    public void update() {
    	if (Gdx.input.isKeyPressed(Keys.P)) {
            sceneManager.loadScene("PauseMenu"); // Load PauseScene when 'P' is pressed
        } else if (Gdx.input.isKeyPressed(Keys.Q)) {
            sceneManager.loadScene("MainMenu"); // Load MainMenu when 'Q' is pressed
        } else {
            handlePlayerMovement();
            collisionManager.checkCollisions();
        }
    }
    

    @Override
    public void render() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(sr);
        enemy.draw(sr);
        sr.end();
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
    public void unload() {
        System.out.println("Unloading Game Scene...");
    }
}