package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;



public class  MainMenuScene extends Scene {

    public MainMenuScene(String name) {
		super(name);
	}

	@Override
    public void create() {
        Gdx.app.log("MenuScene", "Menu Scene Created");
        super.create();;
    }

    @Override
    public void update(float delta) {
        // Handle menu logic
    }

    @Override
    public void render() {


    }

    @Override
    public void dispose() {
    }
}
