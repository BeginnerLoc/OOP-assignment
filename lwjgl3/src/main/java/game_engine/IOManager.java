package game_engine;

import com.badlogic.gdx.Gdx;

public class IOManager {

    private final InputManager inputManager;
    private final SoundManager soundManager;

    public IOManager() {
        this.inputManager = new InputManager();
        this.soundManager = new SoundManager();

        // Make our InputManager the active input processor
        Gdx.input.setInputProcessor(inputManager);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
    public void dispose() {
        soundManager.dispose();
    }
}
