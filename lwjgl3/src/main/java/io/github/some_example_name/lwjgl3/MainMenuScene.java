package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScene extends Scene {

    public MainMenuScene(SpriteBatch batch, BitmapFont font, SceneManager sceneManager) {
    	super("MainMenu", batch, font, sceneManager);
    }

    @Override
    public void load() {
        System.out.println("Loading Main Menu...");
        setLoaded(true); // Mark the scene as loaded
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            getSceneManager().loadScene("Game"); // Load the "Game" scene when SPACE is pressed
        } else if (Gdx.input.isKeyJustPressed(Keys.O)) {
            System.out.println("Adjusting sound settings...");
            getSceneManager().loadScene("SoundAdjustMenu"); // Load the SoundAdjustMenu scene
        } else if (Gdx.input.isKeyJustPressed(Keys.P)) {
            System.out.println("Pausing game...");
            // Add logic for pausing the game here
        } else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            System.out.println("Main Menu");
            getSceneManager().loadScene("MainMenu"); // Reload the main menu
        } else if (Gdx.input.isKeyJustPressed(Keys.L)) {
            System.out.println("Go to Game Over TEST");
            getSceneManager().loadScene("GameOver"); // Load the GameOver scene
        }
    }

    @Override
    public void render() {
        // Clear the screen with a background color
        ScreenUtils.clear(0, 0.2f, 0.2f, 1);

        // Begin rendering with the SpriteBatch
        getSpriteBatch().begin();

        // Draw the main menu text
        getFont().draw(getSpriteBatch(), "Main Menu Screen", 200, 400);
        getFont().draw(getSpriteBatch(), "Press SPACE to play.", 200, 350);
        getFont().draw(getSpriteBatch(), "Press O to adjust Sound.", 200, 300);
        getFont().draw(getSpriteBatch(), "Press P to pause.", 200, 250);
        getFont().draw(getSpriteBatch(), "Press Q to quit to main menu.", 200, 200);

        // End rendering with the SpriteBatch
        getSpriteBatch().end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Main Menu...");
        setLoaded(false); // Mark the scene as unloaded
    }
}