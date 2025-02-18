package io.github.some_example_name.lwjgl3;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    private String name;

    public Scene(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    // Abstract methods to be implemented by subclasses
    public void create() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    };
    public abstract void update(float delta);
    public abstract void render();
    public abstract void dispose();
}
