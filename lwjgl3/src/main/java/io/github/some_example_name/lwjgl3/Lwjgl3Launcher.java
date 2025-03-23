package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new GameMaster(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("INF10009");
        
        // Enable Vsync
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        
        // Get the primary monitor's resolution
        DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        
        // Set window size to 80% of the screen size or 800x600, whichever is larger
        int windowWidth = Math.max(800, (int)(primaryMode.width * 0.8f));
        int windowHeight = Math.max(600, (int)(primaryMode.height * 0.8f));
        
        // Make sure the window isn't larger than the screen
        windowWidth = Math.min(windowWidth, primaryMode.width);
        windowHeight = Math.min(windowHeight, primaryMode.height);
        
        configuration.setWindowedMode(windowWidth, windowHeight);
        
        // Enable window resizing
        configuration.setResizable(true);
        
        // Set window icons
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        
        return configuration;
    }
}