package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SoundAdjustScene extends Scene {
    private float musicVolume = 0.5f; // Initial music volume (range: 0.0 to 1.0)
    private float soundVolume = 0.5f; // Initial sound effect volume (range: 0.0 to 1.0)

    public SoundAdjustScene(SpriteBatch batch, BitmapFont font, SceneManager sceneManager) {
    	super("SoundAdjustMenu", batch, font, sceneManager);
    }

    @Override
    public void load() {
        System.out.println("Loading Sound Menu Scene...");
        setLoaded(true); // Mark the scene as loaded
    }

    @Override
    public void update() {
        // Adjust music volume
        if (Gdx.input.isKeyJustPressed(Keys.UP)) {
            musicVolume = Math.min(musicVolume + 0.1f, 1.0f); // Increase music volume
            System.out.println("Music Volume: " + musicVolume);
            // Update the actual music volume here (e.g., using a Music object)
        } else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            musicVolume = Math.max(musicVolume - 0.1f, 0.0f); // Decrease music volume
            System.out.println("Music Volume: " + musicVolume);
            // Update the actual music volume here
        }

        // Adjust sound effect volume
        if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
            soundVolume = Math.min(soundVolume + 0.1f, 1.0f); // Increase sound effect volume
            System.out.println("Sound Effect Volume: " + soundVolume);
            // Update the actual sound effect volume here (e.g., using a Sound object)
        } else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            soundVolume = Math.max(soundVolume - 0.1f, 0.0f); // Decrease sound effect volume
            System.out.println("Sound Effect Volume: " + soundVolume);
            // Update the actual sound effect volume here
        }

        // Return to the previous scene (e.g., MainMenuScene)
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            getSceneManager().loadScene("MainMenu"); // Return to the main menu
        }
    }

    @Override
    public void render() {
        // Clear the screen with a background color
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);

        // Begin rendering with the SpriteBatch
        getSpriteBatch().begin();
        // Draw the sound menu text
        getFont().draw(getSpriteBatch(), "Sound Settings", 200, 400);
        getFont().draw(getSpriteBatch(), "Music Volume: " + String.format("%.1f", musicVolume), 200, 350);
        getFont().draw(getSpriteBatch(), "Press UP/DOWN to adjust.", 200, 330);
        getFont().draw(getSpriteBatch(), "Sound Effect Volume: " + String.format("%.1f", soundVolume), 200, 280);
        getFont().draw(getSpriteBatch(), "Press LEFT/RIGHT to adjust.", 200, 260);
        getFont().draw(getSpriteBatch(), "Press ESCAPE to return.", 200, 200);

        // End rendering with the SpriteBatch
        getSpriteBatch().end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Sound Menu Scene...");
        setLoaded(false); // Mark the scene as unloaded
    }
}