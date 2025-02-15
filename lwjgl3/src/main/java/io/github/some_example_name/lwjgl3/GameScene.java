
package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the game scene where the main gameplay occurs.
 */

public class GameScene extends Scene {
    private ShapeRenderer shapeRenderer;
    private float rectX = 0; // X-coordinate of the moving rectangle
    private float rectY = 300; // Y-coordinate of the moving rectangle
    private float rectWidth = 50; // Width of the rectangle
    private float rectHeight = 50; // Height of the rectangle
    private float speed = 200; // Speed of the rectangle (pixels per second)
    private boolean isPaused = false; // Tracks whether the game is paused

    // Target object (e.g., an obstacle)
    private float targetX = 500; // X-coordinate of the target
    private float targetY = 300; // Y-coordinate of the target
    private float targetWidth = 50; // Width of the target
    private float targetHeight = 50; // Height of the target

    public GameScene(SpriteBatch batch, BitmapFont font, SceneManager sceneManager, ShapeRenderer shapeRenderer) {
        super("Game", batch, font, sceneManager);
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void load() {
        System.out.println("Loading Game Scene...");
        setLoaded(true);
        isPaused = false; // Reset the pause state when loading the scene
        resetObjectPosition(); // Reset the object's position when the scene is loaded
    }

    @Override
    public void update() {
        if (!isPaused) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            rectX += speed * deltaTime;

            // Check if the rectangle goes out of bounds
            if (rectX > Gdx.graphics.getWidth()) {
                rectX = -rectWidth;
            }

             //Check for collision with the target can be commented off : test GameOverScene
            if (checkCollision(rectX, rectY, rectWidth, rectHeight, targetX, targetY, targetWidth, targetHeight)) {
                System.out.println("Collision detected! Game Over.");
                getSceneManager().loadScene("GameOver"); // Transition to GameOverScene
            }
        }

        // Handle input for pausing the game or returning to the main menu
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            isPaused = true; // Pause the game
            getSceneManager().loadScene("PauseMenu"); // Transition to the PauseScene
        } else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            getSceneManager().loadScene("MainMenu"); // Return to the MainMenuScene
        }
    }

    @Override
    public void render() {
        // Clear the screen with a background color
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1);

        // Render the moving rectangle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);

        // Render the target object
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(targetX, targetY, targetWidth, targetHeight);
        shapeRenderer.end();

        // Render text
        getSpriteBatch().begin();
        getFont().draw(getSpriteBatch(), "Game Scene", 200, 400);
        getFont().draw(getSpriteBatch(), "Press Q to return to Main Menu.", 200, 350);
        getFont().draw(getSpriteBatch(), "Press P to pause the game.", 200, 300);
        getSpriteBatch().end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Game Scene...");
        setLoaded(false);
    }

    public void resumeGame() {
        isPaused = false; // Unpause the game
    }
    //testing Object
    private void resetObjectPosition() {
        rectX = 0; // Reset the object's X position
    }

    // Collision detection method, can be commented off : test GameOverScenes
    private boolean checkCollision(float rectX1, float rectY1, float width1, float height1,
                                   float rectX2, float rectY2, float width2, float height2) {
        return rectX1 < rectX2 + width2 &&
               rectX1 + width1 > rectX2 &&
               rectY1 < rectY2 + height2 &&
               rectY1 + height1 > rectY2;
    }
}