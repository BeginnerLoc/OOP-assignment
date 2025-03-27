package game_engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
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

    // Reference to the current scene's viewport
    private Viewport currentViewport;

    // Vector for coordinate transformation
    private final Vector3 touchPoint = new Vector3();
    private CustomButton lastHoveredButton = null;

    public void setViewport(Viewport viewport) {
        this.currentViewport = viewport;
    }

    public Viewport getViewport() {
        return currentViewport;
    }

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
        if (currentViewport != null) {
            // Convert screen coordinates to world coordinates
            touchPoint.set(screenX, screenY, 0);
            currentViewport.unproject(touchPoint);
            
            for (CustomButton clickable : clickableObjects) {
                if (clickable.isClicked(touchPoint.x, touchPoint.y)) {
                    Gdx.app.log("InputManager", String.format("Button clicked at world coords: (%.2f, %.2f)", touchPoint.x, touchPoint.y));
                    clickable.onMouseClick();
                    return true;
                }
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
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (currentViewport != null) {
            touchPoint.set(screenX, screenY, 0);
            currentViewport.unproject(touchPoint);
            
            CustomButton currentHovered = null;
            
            // Check for new hover
            for (CustomButton button : clickableObjects) {
                if (button.isHovered(touchPoint.x, touchPoint.y)) {
                    currentHovered = button;
                    if (lastHoveredButton != button) {
                        button.onHoverEnter();
                    }
                    break;
                }
            }
            
            // Handle hover exit
            if (lastHoveredButton != null && lastHoveredButton != currentHovered) {
                lastHoveredButton.onHoverExit();
            }
            
            lastHoveredButton = currentHovered;
            return currentHovered != null;
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
    
    public void dispose() {
    	clickableObjects.clear();
    	keyDownCallbacks.clear();
    	activeKeys.clear();
    	
    }

    public void clearClickables() {
        clickableObjects.clear();
    }
}