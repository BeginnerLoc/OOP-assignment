package assignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager loads and plays sounds, no references to InputManager
 */
public class SoundManager {

    private final Map<String, Sound> sounds = new HashMap<>();

    public void loadSound(String id, String filePath) {
        FileHandle fileHandle = Gdx.files.internal(filePath);
        Sound sound = Gdx.audio.newSound(fileHandle);
        sounds.put(id, sound);
    }

    public void playSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            sound.play(1.0f);
        }
    }

    public void dispose() {
        for (Sound s : sounds.values()) {
            s.dispose();
        }
        sounds.clear();
    }
}
