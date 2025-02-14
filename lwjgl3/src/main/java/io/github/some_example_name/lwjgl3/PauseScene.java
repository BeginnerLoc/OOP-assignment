package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScene extends Scene {
    private BitmapFont font;
    private SpriteBatch batch;
    private SceneManager sceneManager; // Add this field

    public PauseScene(SpriteBatch batch, SceneManager sceneManager) { // Pass sceneManager
        super("PauseMenu");
        this.batch = batch;
        this.sceneManager = sceneManager; // Initialize the field
        font = new BitmapFont(); // Default font
    }

    @Override
    public void load() {
        System.out.println("Loading Pause Menu...");
        setLoaded(true);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            sceneManager.loadScene("Game"); // Load GameScene when 'P' is pressed
        } else if (Gdx.input.isKeyPressed(Keys.Q)) {
            sceneManager.loadScene("MainMenu"); // Load MainMenu when 'Q' is pressed
        }
    }

    @Override
    public void render() {
    	ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        font.draw(batch, "Paused", 300, 400);
        font.draw(batch, "Press SPACE to resume.", 200, 300);
        font.draw(batch, "Press Q to quit to main menu.", 200, 250);
        batch.end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Pause Menu...");
        setLoaded(false);
    }
}