package game_engine;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private final Map<Class<? extends Scene>, Scene> scenes = new HashMap<>();
    private Scene currentScene;

    /** Registers a scene */
    public void registerScene(Class<? extends Scene> sceneClass, Scene scene) {
        scenes.put(sceneClass, scene);
    }

    /** Switch to a different scene */
    public void setScene(Class<? extends Scene> sceneClass) {
        if (currentScene != null) {
            currentScene.dispose();
        }
        currentScene = scenes.get(sceneClass);
        if (currentScene != null) {
            currentScene.create();
            // Pass the current screen size to the new scene
            int width = com.badlogic.gdx.Gdx.graphics.getWidth();
            int height = com.badlogic.gdx.Gdx.graphics.getHeight();
            currentScene.resize(width, height);
        }
    }

    /** Render the active scene */
    public void render() {
        if (this.currentScene != null) {
            this.currentScene.render();
        }
    }
    
    /** Handle resize events and pass them to the current scene */
    public void resize(int width, int height) {
        if (this.currentScene != null) {
            this.currentScene.resize(width, height);
        }
    }
}
