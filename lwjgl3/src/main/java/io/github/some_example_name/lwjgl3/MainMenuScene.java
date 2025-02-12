package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScene extends Scene {
    private BitmapFont font;
    private SpriteBatch batch;

    public MainMenuScene(SpriteBatch batch) {
        super("MainMenu");
        this.batch = batch;
        font = new BitmapFont();
    }

    @Override
    public void load() {
        System.out.println("Loading Main Menu...");
        setLoaded(true);
    }

    @Override
    public void update() {
        System.out.println("Updating Main Menu...");
    }

    @Override
    public void render() {
    	ScreenUtils.clear(0, 2, 0.2f, 10);
        batch.begin();
        font.draw(batch, "Main Menu Screen", 200, 350);
        font.draw(batch, "Press Space to play.", 200, 300);
        font.draw(batch, "Press O to adjust Sound.", 200, 250);
        
        //font.draw(batch, "Press P to pause.", 200, 250);
        //font.draw(batch, "Press Q to quit to main menu.", 200, 200);
        batch.end();
    }

    @Override
    public void unload() {
        System.out.println("Unloading Main Menu...");
        setLoaded(false);
    }
}