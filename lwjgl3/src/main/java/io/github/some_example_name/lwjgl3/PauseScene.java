package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScene extends Scene {
    private BitmapFont font;
    private SpriteBatch batch;
    private SceneManager sceneManager; // Add this field

    public PauseScene(SpriteBatch batch) {
        super("PauseMenu");
        this.batch = batch;
        font = new BitmapFont(); // Default font
    }

    @Override
    public void load() {
        System.out.println("Loading Pause Menu...");
        setLoaded(true);
    }

    @Override
    public void update() {
    	if (Gdx.input.isKeyPressed(Keys.P)) {
           sceneManager.loadScene("Game"); // Use the sceneManager field directly
        } else if (Gdx.input.isKeyPressed(Keys.Q)) {
            sceneManager.loadScene("MainMenu"); // Use the sceneManager field directly
        }
    }

    @Override
    public void render() {
        batch.begin();
        font.draw(batch, "Paused", 300, 400);
        font.draw(batch, "Press P to resume.", 200, 300);
        font.draw(batch, "Press Q to quit to main menu.", 200, 250);
        batch.end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Pause Menu...");
        setLoaded(false);
    }
}