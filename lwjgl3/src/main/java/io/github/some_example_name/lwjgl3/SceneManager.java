package io.github.some_example_name.lwjgl3;

import java.util.HashMap;
import java.util.Map;

// Scene Manager class
public class SceneManager {
    private Scene currentScene;
    private Map<String, Scene> scenes;

    public SceneManager() {
        scenes = new HashMap<>();
        currentScene = null; // Explicitly initialize to null for clarity
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void loadScene(String name) {
        if (scenes.containsKey(name)) {
            if (currentScene != null) {
                currentScene.unload();
            }
            currentScene = scenes.get(name);
            if (currentScene != null) { // Additional check to avoid NPE
                currentScene.load();
            }
        } else {
            System.out.println("Scene not found: " + name);
        }
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
}