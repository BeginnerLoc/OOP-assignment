package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.GL20;

public class GameScene extends Scene {
    private GameMechanicsManager mechanics;
    private BitmapFont font;
    private BackgroundEntity background;
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
        
        // Setup background
        float backgroundScale = .95f;
        background = new BackgroundEntity("brickwall.png", -10, -5, backgroundScale);
        this.entityManager.addEntity(background);
    	
        // Create and initialize player
        Player player = new Player(100, 300, "mrbean.png", 3.0f, 65f, 90f);
        this.entityManager.addEntity(player);
        this.collisionManager.register(player);
        this.movementManager.addMovingEntity(player);
        
        // Initialize game mechanics
        mechanics.initializeGame(player);
        
        // Initialize UI elements
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        shapeRenderer = new ShapeRenderer();
        
        setupControls();
    }

    private void setupControls() {
        this.ioManager.getInputManager().subscribeKeyDown(Keys.W, () -> mechanics.getPlayer().setDirection(0, 1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.S, () -> mechanics.getPlayer().setDirection(0, -1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.A, () -> mechanics.getPlayer().setDirection(-1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.D, () -> mechanics.getPlayer().setDirection(1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SPACE, () -> mechanics.getPlayer().dropTrash());
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
        // Update game mechanics
        mechanics.update(Gdx.graphics.getDeltaTime());
        
        // 1. Render game entities first (this already uses proper OpenGL state management now)
        super.render();
        
        // 2. Draw progress bars with proper state management
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(ServiceLocator.get(SpriteBatch.class).getProjectionMatrix());
        drawProgressBars();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        // 3. Draw UI text last (uses SpriteBatch)
        SpriteBatch batch = ServiceLocator.get(SpriteBatch.class);
        batch.begin();
        drawUI(batch);
        batch.end();
    }

    private void drawProgressBars() {
        float barWidth = 200;
        float barHeight = 20;
        float barX = 10;
        float barY = 370;
        
        shapeRenderer.begin(ShapeType.Filled);
        
        // Draw speed boost bar if active
        if (mechanics.isSpeedBoosted()) {
            // Draw border
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(barX - 2, barY - 2, barWidth + 4, barHeight + 4);
            
            // Draw background
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(barX, barY, barWidth, barHeight);
            
            // Draw progress
            float progress = mechanics.getSpeedBoostTimer() / 5.0f; // 5.0f is the total boost duration
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);
        }
        
        // Draw speed penalty bar if active
        if (mechanics.isSpeedPenalized()) {
            // Draw border
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(barX - 2, barY - 27, barWidth + 4, barHeight + 4);
            
            // Draw background
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(barX, barY - 25, barWidth, barHeight);
            
            // Draw progress
            float progress = mechanics.getSpeedPenaltyTimer() / 2.0f; // 2.0f is the total penalty duration
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(barX, barY - 25, barWidth * progress, barHeight);
        }
        
        shapeRenderer.end();
    }

    private void drawUI(SpriteBatch batch) {
        font.draw(batch, "Score: " + GameState.getScore(), 10, 470);
        font.draw(batch, "Health: " + mechanics.getPlayerHealth(), 10, 450);
        font.draw(batch, "Level: " + mechanics.getLevel(), 10, 430);
        
        Player player = mechanics.getPlayer();
        if (player.getHeldTrash() != null) {
            float textX = 10;
            float textY = 410;
            float iconX = textX + 50;
            float iconY = 390;
            float iconSize = 32;

            font.draw(batch, "Holding:", textX, textY);
            batch.draw(player.getHeldTrash().getImage(), iconX, iconY, iconSize, iconSize);
        }
        
        if (mechanics.isSpeedBoosted()) {
            font.draw(batch, "Speed Boost", 10, 405);
        }
        if (mechanics.isSpeedPenalized()) {
            font.draw(batch, "Speed Penalty", 10, 380);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        shapeRenderer.dispose();
        this.ioManager.getSoundManager().stopSound("background_music_MMS");
        this.ioManager.getSoundManager().stopSound("background_music_GS");
        this.ioManager.getSoundManager().disposeSound("background_music_GS");
    }
}


