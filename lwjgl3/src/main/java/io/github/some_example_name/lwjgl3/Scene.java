package io.github.some_example_name.lwjgl3;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    private String name;
    private boolean isLoaded;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private SceneManager sceneManager;

    // Constructor
    public Scene(String name, SpriteBatch spriteBatch, BitmapFont font, SceneManager sceneManager) {
        if (spriteBatch == null || font == null || sceneManager == null) {
            throw new IllegalArgumentException("Dependencies (spriteBatch, font, sceneManager) cannot be null.");
        }
        this.name = name;
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.sceneManager = sceneManager;
        this.isLoaded = false;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public void setFont(BitmapFont font) {
        if (font == null) {
            throw new IllegalArgumentException("Font cannot be null.");
        }
        this.font = font;
    }

    public void setSceneManager(SceneManager sceneManager) {
        if (sceneManager == null) {
            throw new IllegalArgumentException("SceneManager cannot be null.");
        }
        this.sceneManager = sceneManager;
    }

    // Abstract methods to be implemented by subclasses
    public abstract void load();
    public abstract void update();
    public abstract void render();
    public abstract void unload();
}
