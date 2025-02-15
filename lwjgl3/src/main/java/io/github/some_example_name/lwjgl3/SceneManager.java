package io.github.some_example_name.lwjgl3;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SceneManager {
    private Scene currentScene;
    private Map<String, Scene> scenes;

    public SceneManager() {
        scenes = new HashMap<>();
        currentScene = null;
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void loadScene(String sceneName) {
        Scene newScene = scenes.get(sceneName);
        if (newScene == null) {
            System.err.println("Error: Scene '" + sceneName + "' does not exist.");
            return;
        }

        if (currentScene != null && !currentScene.getName().equals(sceneName)) {
            currentScene.unload();
        }
        
     // Reload the scene only if it's not already loaded or if it's the MainMenuScene
        if (!newScene.isLoaded() || sceneName.equals("MainMenu")) {
            newScene.load(); // Reload the scene to reset its state
        }

        currentScene = newScene;
    }

    public void setCurrentScene(String sceneName) {
        Scene newScene = scenes.get(sceneName);
        if (newScene == null) {
            System.err.println("Error: Scene '" + sceneName + "' does not exist.");
            return;
        }

        if (currentScene != null && !currentScene.getName().equals(sceneName)) {
            currentScene.unload();
        }

        currentScene = newScene;
    }

    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public void render() {
        if (currentScene != null) {
            currentScene.render();
        }
    }

    public Scene getScene(String name) {
        return scenes.get(name);
    }

    public String getCurrentSceneName() {
        for (Map.Entry<String, Scene> entry : scenes.entrySet()) {
            if (entry.getValue() == currentScene) {
                return entry.getKey();
            }
        }
        return null;
    }
}