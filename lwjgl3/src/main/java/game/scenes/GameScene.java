package game.scenes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import game.entities.Player;
import game.utils.GameState;
import game_engine.BackgroundRenderer;
import game_engine.Scene;
import game_engine.ServiceLocator;
import io.github.some_example_name.lwjgl3.GameMechanicsManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.GL20;

public class GameScene extends Scene {
    // Rendering priority constants
    private static final int CHARACTER_PRIORITY = 3;
    
    private GameMechanicsManager mechanics;
    private BitmapFont font;
    private BackgroundRenderer backgroundRenderer;
    private ShapeRenderer shapeRenderer;
    
    public GameScene(String name) {
        super(name);
        mechanics = new GameMechanicsManager();
    }
    
    @Override
    public void create() {
        super.create();
        System.out.println("Game Scene");
        
        // Load and play background music
        this.ioManager.getSoundManager().loadSound("background_music_GS", "GameScene_BGM Dark Maplemas_ O Holy Fright.mp3");
        this.ioManager.getSoundManager().playBackgroundMusic("background_music_GS");
    	
        // Load essential sound effects
        this.ioManager.getSoundManager().loadSound("enemy_hit", "Wrong Answer Sound effect.mp3");
        this.ioManager.getSoundManager().loadSound("game_over", "Game Over Sound Effect.mp3");
        this.ioManager.getSoundManager().loadSound("trash_correct", "Correct Answer sound effect.mp3");
        this.ioManager.getSoundManager().loadSound("trash_wrong", "Wrong Answer Sound effect.mp3");
        this.ioManager.getSoundManager().loadSound("pickup", "Item Pickup [Sound Effect].mp3");
        this.ioManager.getSoundManager().loadSound("Power Up", "8-Bit Powerup Sound Effects (Copyright Free).mp3");
        
        // Reset the entity system to clear any lingering entities
        this.entityManager.clearAllEntities();
        this.collisionManager.dispose();
        this.movementManager.clearAll();
        
        // Initialize background with fixed resolution
        backgroundRenderer = new BackgroundRenderer("brickwall.png");
        
        // Pass the viewport to managers
        this.ioManager.getInputManager().setViewport(backgroundRenderer.getViewport());
        this.entityManager.setViewport(backgroundRenderer.getViewport());
        
        // Use virtual coordinates for game objects
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        // Create player with proper scaling relative to virtual screen size
        Player player = new Player(
            virtualWidth * 0.1f,    // X position: 10% from left
            virtualHeight * 0.4f,   // Y position: 40% from bottom
            "mr_bean.png",
            3.0f,                   // Speed multiplier
            virtualWidth * 0.08f,   // Width: 8% of virtual width
            virtualHeight * 0.15f // Height: 15% of virtual height
        );
        this.entityManager.addEntity(player, CHARACTER_PRIORITY);
        this.collisionManager.register(player);
        this.movementManager.addMovingEntity(player);
        
        // Initialize game mechanics with prioritized rendering
        mechanics = new GameMechanicsManager();
        mechanics.initializeGame(player);
        
        // Initialize UI elements with proper scaling and priority
        font = new BitmapFont();
        font.getData().setScale(virtualWidth / 700f); // Scale font relative to virtual width
        font.setColor(Color.WHITE);
        
        shapeRenderer = new ShapeRenderer();
        
        setupControls();
    }
    
    private void setupControls() {
        this.ioManager.getInputManager().subscribeKeyDown(Keys.W, () -> mechanics.getPlayer().setDirection(0, 1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.S, () -> mechanics.getPlayer().setDirection(0, -1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.A, () -> mechanics.getPlayer().setDirection(-1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.D, () -> mechanics.getPlayer().setDirection(1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SPACE, () -> mechanics.throwBananaPeel());
        
        // Add sprint control with Shift key
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SHIFT_LEFT, () -> mechanics.getPlayer().sprint(true));
        this.ioManager.getInputManager().subscribeKeyUp(Keys.SHIFT_LEFT, () -> mechanics.getPlayer().sprint(false));
        
        // Stop movement when keys are released
        this.ioManager.getInputManager().subscribeKeyUp(Keys.W, () -> checkStopMovement());
        this.ioManager.getInputManager().subscribeKeyUp(Keys.S, () -> checkStopMovement());
        this.ioManager.getInputManager().subscribeKeyUp(Keys.A, () -> checkStopMovement());
        this.ioManager.getInputManager().subscribeKeyUp(Keys.D, () -> checkStopMovement());
    }

    private void checkStopMovement() {
        // Check if any movement keys are still pressed
        boolean wPressed = Gdx.input.isKeyPressed(Keys.W);
        boolean sPressed = Gdx.input.isKeyPressed(Keys.S);
        boolean aPressed = Gdx.input.isKeyPressed(Keys.A);
        boolean dPressed = Gdx.input.isKeyPressed(Keys.D);

        // If no movement keys are pressed, stop the player
        if (!wPressed && !sPressed && !aPressed && !dPressed) {
            mechanics.getPlayer().stop();
        }
    }
    
    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
        // Update game mechanics
        mechanics.update(Gdx.graphics.getDeltaTime());
        
        // Get the SpriteBatch from ServiceLocator
        SpriteBatch batch = ServiceLocator.get(SpriteBatch.class);
        
        if (backgroundRenderer != null && backgroundRenderer.getViewport() != null) {
            // Update viewport
            backgroundRenderer.getViewport().apply();
            
            // Render background with proper viewport
            if (batch != null) {
                batch.begin();
                backgroundRenderer.render(batch);
                batch.end();
            }
            
            // Set projection matrices for consistent rendering
            if (backgroundRenderer.getCamera() != null) {
                batch.setProjectionMatrix(backgroundRenderer.getCamera().combined);
                shapeRenderer.setProjectionMatrix(backgroundRenderer.getCamera().combined);
            }
            
            // Render game entities
            super.render();
            
            // Draw UI elements with proper blending
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            drawProgressBars();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            
            // Draw UI text
            if (batch != null) {
                batch.begin();
                drawUI(batch);
                batch.end();
            }
        }
    }
    
    private void drawProgressBars() {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        float barWidth = virtualWidth * 0.2f;    // 20% of screen width
        float barHeight = virtualHeight * 0.03f;  // 3% of screen height
        float barX = virtualWidth * 0.1f;        // 10% from left edge
        float barY = virtualHeight * 0.88f;        // 88% from bottom edge
        
        shapeRenderer.begin(ShapeType.Filled);
        
        // Draw speed boost bar if active
        if (mechanics.isSpeedBoosted()) {
            // Border
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(barX - 2, barY - 2, barWidth + 4, barHeight + 4);
            
            // Background
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(barX, barY, barWidth, barHeight);
            
            // Progress
            float progress = mechanics.getSpeedBoostTimer() / 5.0f;
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);
        }
        
        // Draw speed penalty bar if active
        if (mechanics.isSpeedPenalized()) {
            float penaltyBarY = barY - barHeight - virtualHeight * 0.02f; // 2% spacing
            
            // Border
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(barX - 2, penaltyBarY - 2, barWidth + 4, barHeight + 4);
            
            // Background
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(barX, penaltyBarY, barWidth, barHeight);
            
            // Progress
            float progress = mechanics.getSpeedPenaltyTimer() / 2.0f;
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(barX, penaltyBarY, barWidth * progress, barHeight);
        }
        
        shapeRenderer.end();
    }
    
    private void drawUI(SpriteBatch batch) {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        float textX = virtualWidth * 0.01f;      // 1% from left
        float baseTextY = virtualHeight * 0.85f;  // 95% from bottom
        float lineSpacing = virtualHeight * 0.03f; // 3% of screen height
        
        font.draw(batch, "Score: " + GameState.getScore(), textX, baseTextY);
        font.draw(batch, "Level: " + mechanics.getLevel(), textX, baseTextY - lineSpacing);
        
        Player player = mechanics.getPlayer();
        if (player.getHeldTrash() != null) {
            float iconSize = virtualHeight * 0.05f;
            float iconX = textX + virtualWidth * 0.1f;
            float iconY = baseTextY - lineSpacing * 2 - iconSize/2;
            
            font.draw(batch, "Holding:", textX, baseTextY - lineSpacing * 2);
            batch.draw(player.getHeldTrash().getImage(), iconX, iconY, iconSize, iconSize);
        }
        
        float statusY = baseTextY - lineSpacing * 3;
        if (mechanics.getBananaCharges() > 0) {
            font.draw(batch, "Grandma's Cat: " + mechanics.getBananaCharges() + " - Press SPACE to drop", textX, statusY);
            statusY -= lineSpacing;
        }
        if (mechanics.isSpeedBoosted()) {
            font.draw(batch, "Speed Boost Active", textX, statusY);
            statusY -= lineSpacing;
        }
        if (mechanics.isSpeedPenalized()) {
            font.draw(batch, "Speed Penalty", textX, statusY);
        }
    }
    
    @Override
    public void resize(int width, int height) {
        if (backgroundRenderer != null) {
            // Update viewport on resize
            backgroundRenderer.resize(width, height);
            // Update viewport references in managers
            this.ioManager.getInputManager().setViewport(backgroundRenderer.getViewport());
            this.entityManager.setViewport(backgroundRenderer.getViewport());
        }
    }
    
    @Override
    public void dispose() {
        if (backgroundRenderer != null) {
            backgroundRenderer.dispose();
        }
        if (font != null) {
            font.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        
        // Stop and dispose all game-specific sounds to prevent memory leaks
        this.ioManager.getSoundManager().stopSound("background_music_GS");
        this.ioManager.getSoundManager().disposeSound("background_music_GS");
        this.ioManager.getSoundManager().disposeSound("enemy_hit");
        this.ioManager.getSoundManager().disposeSound("game_over");
        this.ioManager.getSoundManager().disposeSound("trash_correct");
        this.ioManager.getSoundManager().disposeSound("trash_wrong");
        this.ioManager.getSoundManager().disposeSound("pickup");
        
        // Don't dispose background_music_MMS as it's shared with other scenes
        // Just stop it if it's playing
        this.ioManager.getSoundManager().stopSound("background_music_MMS");
        
        super.dispose();
    }
}


