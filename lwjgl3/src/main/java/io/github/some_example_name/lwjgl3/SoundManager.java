package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager loads and plays sounds, no references to InputManager
 */
//public class SoundManager {
//
//    private final Map<String, Sound> sounds = new HashMap<>();
//
//    public void loadSound(String id, String filePath) {
//        FileHandle fileHandle = Gdx.files.internal(filePath);
//        Sound sound = Gdx.audio.newSound(fileHandle);
//        sounds.put(id, sound);
//    }
//
//    public void playSound(String id) {
//        Sound sound = sounds.get(id);
//        if (sound != null) {
//            sound.play(1.0f);
//        }
//    }
//
//    public void dispose() {
//        for (Sound s : sounds.values()) {
//            s.dispose();
//        }
//        sounds.clear();
//    }
//    
//    public void disposeSound(String id) {
//        Sound sound = sounds.get(id);
//        if (sound != null) {
//            sound.dispose();
//            sounds.remove(id); // Remove the sound from the map after disposing
//        }
//    }
//}


public class SoundManager {
    private final Map<String, Sound> sounds = new HashMap<>();
    private String currentBackgroundMusicId = null; // Track the currently playing background music ID
    private boolean isBackgroundMusicPlaying = false;

    public void loadSound(String id, String filePath) {
        FileHandle fileHandle = Gdx.files.internal(filePath);
        Sound sound = Gdx.audio.newSound(fileHandle);
        sounds.put(id, sound);
    }

    public void playSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            sound.play(1.0f); // Play the sound with default volume
        }
    }

    public void playBackgroundMusic(String id) {
        // If the requested music is already playing, do nothing
        if (currentBackgroundMusicId != null && currentBackgroundMusicId.equals(id) && isBackgroundMusicPlaying) {
            return;
        }

        // Stop the currently playing background music if it's different
        if (currentBackgroundMusicId != null && !currentBackgroundMusicId.equals(id)) {
            stopSound(currentBackgroundMusicId);
        }

        // Play the requested background music
        Sound sound = sounds.get(id);
        if (sound != null) {
            sound.loop(1.0f); // Loop the background music
            currentBackgroundMusicId = id; // Update the current background music ID
            isBackgroundMusicPlaying = true; // Mark the music as playing
        }
    }

    public void stopSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            sound.stop();
            if (id.equals(currentBackgroundMusicId)) {
                isBackgroundMusicPlaying = false; // Reset the music playing flag for background music
            }
        }
    }

    public void dispose() {
        for (Sound s : sounds.values()) {
            s.dispose();
        }
        sounds.clear();
        currentBackgroundMusicId = null; // Reset the current background music ID
        isBackgroundMusicPlaying = false; // Reset the music playing flag
    }

    public void disposeSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            sound.dispose();
            sounds.remove(id);
        }
        if (currentBackgroundMusicId != null && currentBackgroundMusicId.equals(id)) {
            currentBackgroundMusicId = null; // Reset the current background music ID
            isBackgroundMusicPlaying = false; // Reset the music playing flag
        }
    }
 // New method to check if background music is playing
    public boolean isBackgroundMusicPlaying() {
        return isBackgroundMusicPlaying;
    }
}