package io.github.some_example_name.lwjgl3;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScene extends Scene {
   
    public GameOverScene(SpriteBatch batch, BitmapFont font, SceneManager sceneManager) {
        super("GameOver", batch, font, sceneManager); // Pass the name and SpriteBatch to the superclass constructor
    }

    @Override
    public void load() {
        System.out.println("Loading GameOver Scene...");
        setLoaded(true); // Mark the scene as loaded
    }

    @Override
    public void update() {
        // Handle input for transitioning back to the game or main menu
        if (Gdx.input.isKeyJustPressed(Keys.R)) {
            getSceneManager().loadScene("Game"); // Restart the game when R is pressed
        } else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            getSceneManager().loadScene("MainMenu"); // Return to the MainMenuScene when Q is pressed
        }
    }

    @Override
    public void render() {
        // Clear the screen with a dark red background color to indicate game over
        ScreenUtils.clear(0.5f, 0, 0, 1);

        // Begin rendering with the SpriteBatch
        getSpriteBatch().begin();

        // Draw the game over scene text
        getFont().draw(getSpriteBatch(), "GAME OVER", 200, 400);
        getFont().draw(getSpriteBatch(), "Press R to restart the game.", 200, 350);
        getFont().draw(getSpriteBatch(), "Press Q to return to Main Menu.", 200, 300);

        // End rendering with the SpriteBatch
        getSpriteBatch().end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading GameOver Scene...");
        setLoaded(false); // Mark the scene as unloaded
    }
}