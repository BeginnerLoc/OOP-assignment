package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputManager extends InputAdapter {

    private final List<CustomButton> clickableObjects = new ArrayList<>();

    // Maps for subscribing callbacks to keyDown or keyUp
    private final Map<Integer, Runnable> keyDownCallbacks = new HashMap<>();
    private final Map<Integer, Runnable> keyUpCallbacks   = new HashMap<>();
    private final Set<Integer> activeKeys = new HashSet<>();

    public void registerClickable(CustomButton button) {
        clickableObjects.add(button);
    }

    public void subscribeKeyDown(int key, Runnable callback) {
        keyDownCallbacks.put(key, callback);
    }

    /** Subscribe a callback to a particular key release. */
    public void subscribeKeyUp(int key, Runnable callback) {
        keyUpCallbacks.put(key, callback);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float touchX = screenX;
        float touchY = Gdx.graphics.getHeight() - screenY;

        for (CustomButton clickable : clickableObjects) {
            if (clickable.isClicked(touchX, touchY)) {
                clickable.onMouseClick();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
      activeKeys.add(keycode);
        Runnable callback = keyDownCallbacks.get(keycode);
        if (callback != null) {
            callback.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
      activeKeys.remove(keycode);
        Runnable callback = keyUpCallbacks.get(keycode);
        if (callback != null) {
            callback.run();
            return true;
        }
        return false;
    }
    
    // Call this in your game's update loop
    public void update() {
        for (Integer key : activeKeys) {
            Runnable callback = keyDownCallbacks.get(key);
            if (callback != null) {
                callback.run();
            }
        }
    }
}