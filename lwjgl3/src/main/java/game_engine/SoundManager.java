package game_engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoundManager {
    private final Map<String, Sound> sounds = new HashMap<>();
    private String currentBackgroundMusicId = null;
    private boolean isBackgroundMusicPlaying = false;
    private float currentVolume = 1.0f;
    
    // Track the ID of the currently playing background music instance
    private long currentBackgroundMusicInstanceId = -1;
    
    // Track active sound instances to avoid memory leaks
    private final Map<String, Long> activeSoundInstances = new ConcurrentHashMap<>();

    public void loadSound(String id, String filePath) {
        // Avoid reloading if sound is already loaded
        if (sounds.containsKey(id)) {
            return;
        }
        
        try {
            FileHandle fileHandle = Gdx.files.internal(filePath);
            Sound sound = Gdx.audio.newSound(fileHandle);
            sounds.put(id, sound);
        } catch (Exception e) {
            Gdx.app.error("SoundManager", "Error loading sound: " + filePath, e);
        }
    }

    public long playSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            try {
                long soundId = sound.play(currentVolume);
                // Store the instance ID for potential later control
                activeSoundInstances.put(id + "_" + soundId, soundId);
                return soundId;
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error playing sound: " + id, e);
            }
        }
        return -1;
    }

    public void playBackgroundMusic(String id) {
        // If the requested music is already playing, do nothing
        if (currentBackgroundMusicId != null && currentBackgroundMusicId.equals(id) && isBackgroundMusicPlaying) {
            return;
        }

        // Stop the currently playing background music
        stopBackgroundMusic();

        // Play the requested background music
        Sound sound = sounds.get(id);
        if (sound != null) {
            try {
                currentBackgroundMusicInstanceId = sound.loop(currentVolume);
                currentBackgroundMusicId = id;
                isBackgroundMusicPlaying = true;
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error playing background music: " + id, e);
            }
        }
    }

    public void stopBackgroundMusic() {
        if (currentBackgroundMusicId != null && isBackgroundMusicPlaying) {
            Sound bgMusic = sounds.get(currentBackgroundMusicId);
            if (bgMusic != null) {
                try {
                    bgMusic.stop();
                } catch (Exception e) {
                    Gdx.app.error("SoundManager", "Error stopping background music", e);
                }
            }
            currentBackgroundMusicId = null;
            currentBackgroundMusicInstanceId = -1;
            isBackgroundMusicPlaying = false;
        }
    }

    public void stopSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            try {
                sound.stop();
                
                // Remove any stored instance IDs for this sound
                activeSoundInstances.entrySet().removeIf(entry -> entry.getKey().startsWith(id + "_"));
                
                // Also handle background music if it's this sound
                if (id.equals(currentBackgroundMusicId)) {
                    currentBackgroundMusicId = null;
                    currentBackgroundMusicInstanceId = -1;
                    isBackgroundMusicPlaying = false;
                }
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error stopping sound: " + id, e);
            }
        }
    }

    public void dispose() {
        for (Sound s : sounds.values()) {
            try {
                s.dispose();
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error disposing sound", e);
            }
        }
        sounds.clear();
        activeSoundInstances.clear();
        currentBackgroundMusicId = null;
        currentBackgroundMusicInstanceId = -1;
        isBackgroundMusicPlaying = false;
    }

    public void disposeSound(String id) {
        Sound sound = sounds.get(id);
        if (sound != null) {
            try {
                sound.stop(); // Stop all instances first
                sound.dispose();
                sounds.remove(id);
                
                // Clean up any instance IDs
                activeSoundInstances.entrySet().removeIf(entry -> entry.getKey().startsWith(id + "_"));
                
                if (currentBackgroundMusicId != null && currentBackgroundMusicId.equals(id)) {
                    currentBackgroundMusicId = null;
                    currentBackgroundMusicInstanceId = -1;
                    isBackgroundMusicPlaying = false;
                }
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error disposing specific sound: " + id, e);
            }
        }
    }

    public void setVolume(float volume) {
        // Clamp volume between 0 and 1
        currentVolume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Update volume for currently playing background music
        if (currentBackgroundMusicId != null && isBackgroundMusicPlaying && currentBackgroundMusicInstanceId != -1) {
            Sound bgMusic = sounds.get(currentBackgroundMusicId);
            if (bgMusic != null) {
                try {
                    bgMusic.setVolume(currentBackgroundMusicInstanceId, currentVolume);
                } catch (Exception e) {
                    Gdx.app.error("SoundManager", "Error setting volume for background music", e);
                }
            }
        }
    }

    public float getVolume() {
        return currentVolume;
    }

    public boolean isBackgroundMusicPlaying() {
        return isBackgroundMusicPlaying;
    }
    
    // Method to pause all sounds (useful when game pauses)
    public void pauseAllSounds() {
        for (Sound sound : sounds.values()) {
            try {
                sound.pause();
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error pausing sounds", e);
            }
        }
    }
    
    // Method to resume all sounds (useful when game resumes)
    public void resumeAllSounds() {
        for (Sound sound : sounds.values()) {
            try {
                sound.resume();
            } catch (Exception e) {
                Gdx.app.error("SoundManager", "Error resuming sounds", e);
            }
        }
        
        // If we had background music playing, make sure it's still playing
        if (currentBackgroundMusicId != null && isBackgroundMusicPlaying) {
            Sound bgMusic = sounds.get(currentBackgroundMusicId);
            if (bgMusic != null) {
                try {
                    currentBackgroundMusicInstanceId = bgMusic.loop(currentVolume);
                } catch (Exception e) {
                    Gdx.app.error("SoundManager", "Error resuming background music", e);
                }
            }
        }
    }
}