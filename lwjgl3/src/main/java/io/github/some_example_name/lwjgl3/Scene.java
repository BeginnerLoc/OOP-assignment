package io.github.some_example_name.lwjgl3;
import java.util.Map;
import java.util.HashMap;

abstract class Scene {
	private String name;
    private boolean isLoaded;
    
    public Scene(String name) {
        this.name = name;
        this.isLoaded = false;
    }
    
    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
    public abstract void load();
    public abstract void update();
    public abstract void render();
    public abstract void unload();
    
}

