package io.github.some_example_name.lwjgl3;
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
        }
    }

    /** Update the active scene */
    public void update(float delta) {
        if (currentScene != null) {
            currentScene.update(delta);
        }
    }

    /** Render the active scene */
    public void render() {
        if (this.currentScene != null) {
            this.currentScene.render();
        }
    }
}
