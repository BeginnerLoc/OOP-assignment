package game_engine;

import com.badlogic.gdx.Gdx;

/**
 * IOManager holds both InputManager and SoundManager in one place
 * but keeps them decoupled from each other.
 */
public class IOManager {

    private final InputManager inputManager;
    private final SoundManager soundManager;

    public IOManager() {
        this.inputManager = new InputManager();
        this.soundManager = new SoundManager();

        // Make our InputManager the active input processor
        Gdx.input.setInputProcessor(inputManager);
    }

    /** Provide access to the InputManager if needed. */
    public InputManager getInputManager() {
        return inputManager;
    }

    /** Provide access to the SoundManager if needed. */
    public SoundManager getSoundManager() {
        return soundManager;
    }
    /** Clean up resources when done. */
    public void dispose() {
        soundManager.dispose();
    }
}
