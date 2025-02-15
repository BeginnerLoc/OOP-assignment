package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScene extends Scene {


    public PauseScene(SpriteBatch batch, BitmapFont font, SceneManager sceneManager) {
    	super("PauseMenu", batch, font, sceneManager);
        
    }

    @Override
    public void load() {
        System.out.println("Loading Pause Scene...");
        setLoaded(true);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            System.out.println("Resuming Game...");
            Scene gameScene = getSceneManager().getScene("Game");
            if (gameScene instanceof GameScene) {
                ((GameScene) gameScene).resumeGame(); // Reset the isPaused flag
            }
            getSceneManager().setCurrentScene("Game"); // Switch back to GameScene without reloading
        } else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            System.out.println("Returning to Main Menu...");
            getSceneManager().loadScene("MainMenu"); // Load the MainMenuScene
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.5f);

        // Render the GameScene's content in the background
        Scene currentGameScene = getSceneManager().getScene("Game");
        if (currentGameScene instanceof GameScene && currentGameScene.isLoaded()) {
            ((GameScene) currentGameScene).render();
        }

        getSpriteBatch().begin();
        getFont().draw(getSpriteBatch(), "Game Paused", 200, 400);
        getFont().draw(getSpriteBatch(), "Press SPACE to resume the game.", 200, 350);
        getFont().draw(getSpriteBatch(), "Press Q to return to Main Menu.", 200, 300);
        getSpriteBatch().end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Pause Scene...");
        setLoaded(false);
    }
}