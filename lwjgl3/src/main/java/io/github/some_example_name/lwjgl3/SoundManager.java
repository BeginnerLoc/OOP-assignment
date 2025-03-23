package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;


public class SoundManager {
    private final Map<String, Sound> sounds = new HashMap<>();
    private String currentBackgroundMusicId = null; // Track the currently playing background music ID
    private boolean isBackgroundMusicPlaying = false;
    private float currentVolume = 1.0f; // Store current volume level

    public void loadSound(String id, String filePath) {
        FileHandle fileHandle = Gdx.files.internal(filePath);
        Sound sound = Gdx.audio.newSound(fileHandle);
        sounds.put(id, sound);
    }

    public void playSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            sound.play(currentVolume); // Play the sound with current volume
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
            sound.loop(currentVolume); // Loop the background music with current volume
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

    public void setVolume(float volume) {
        // Clamp volume between 0 and 1
        currentVolume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Update volume for currently playing background music
        if (currentBackgroundMusicId != null && isBackgroundMusicPlaying) {
            Sound bgMusic = sounds.get(currentBackgroundMusicId);
            if (bgMusic != null) {
                bgMusic.setVolume(0, currentVolume); // Update volume for the looping sound
            }
        }
    }

    public float getVolume() {
        return currentVolume;
    }

    // New method to check if background music is playing
    public boolean isBackgroundMusicPlaying() {
        return isBackgroundMusicPlaying;
    }
}