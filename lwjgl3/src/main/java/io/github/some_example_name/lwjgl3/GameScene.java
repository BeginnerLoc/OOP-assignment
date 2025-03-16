package io.github.some_example_name.lwjgl3;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameScene extends Scene {
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<TrashBin> bins = new ArrayList<>();
    private List<Trash> trashItems = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private float speedBoostTimer = 0;
    private float speedPenaltyTimer = 0;
    private boolean isSpeedBoosted = false;
    private boolean isSpeedPenalized = false;
    private BitmapFont font;
    private Trash lastTrashDropped = null;
    private float trashCooldown = 0;
    private int level = 1;
    private float levelTimer = 0;
    private int playerHealth = 3;
    private float powerUpSpawnTimer = 0;
	private BackgroundEntity background;
	
    public GameScene(String name) {
        super(name);
    }

    @Override
    public void create() {
    	super.create();
       
    	System.out.println("Game Scene");
    	
    	// Load Background Image
    	// Load Background Image with a scaling factor
        float backgroundScale = .95f; // Adjust this value to scale the background image
        background = new BackgroundEntity("brickwall.png", -10, -5, backgroundScale);
        this.entityManager.addEntity(background);
    	
        // Creation of player
        player = new Player(100, 300, "mrbean.png", 3.0f, 65f, 90f);
        this.entityManager.addEntity(player);
        this.collisionManager.register(player);
        this.movementManager.addMovingEntity(player);
        
        // Create initial enemy (grandmother)
        Enemy firstEnemy = new Enemy(500, 0, "grandmother.png", 1.5f, 75f, 85f);
        firstEnemy.setTarget(player);
        enemies.add(firstEnemy);
        this.entityManager.addEntity(firstEnemy);
        this.collisionManager.register(firstEnemy);
        this.movementManager.addMovingEntity(firstEnemy);
        this.movementManager.addAIEntity(firstEnemy);

        // Create trash bins at corners
        createTrashBins();
        
        // Create initial trash
        spawnTrash();

        // Create additional enemies based on level
        spawnEnemies();

        setupCollisions();
        setupControls();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    private void createTrashBins() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        
        bins.add(new TrashBin(0, 0, Trash.TrashType.PLASTIC, "plastic_bin.png"));
        bins.add(new TrashBin(screenWidth - 100, 0, Trash.TrashType.METAL, "metal_bin.png"));
        bins.add(new TrashBin(0, screenHeight - 100, Trash.TrashType.PAPER, "paper_bin.png"));
        bins.add(new TrashBin(screenWidth - 100, screenHeight - 100, Trash.TrashType.GLASS, "general_bin.png"));
        
        // Register bins with managers
        for (TrashBin bin : bins) {
            this.collisionManager.register(bin);
            this.entityManager.addEntity(bin);
        }
    }

    private void spawnTrash() {
        // Spawn one of each type in middle area
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        
        trashItems.add(new Trash(centerX - 50, centerY, Trash.TrashType.PLASTIC, "bottle_waste.png"));
        trashItems.add(new Trash(centerX + 50, centerY, Trash.TrashType.PAPER, "newspaper_waste.png"));
        trashItems.add(new Trash(centerX, centerY - 50, Trash.TrashType.METAL, "ColaCan_waste.png"));
        trashItems.add(new Trash(centerX, centerY + 50, Trash.TrashType.GLASS, "glassbottle1_waste.png"));
        
        // Register trash items with managers
        for (Trash trash : trashItems) {
            this.entityManager.addEntity(trash);
            this.collisionManager.register(trash);
            
            
        }
    }

    private void spawnEnemies() {
        // Clear all but the first enemy (grandmother)
        if (enemies.size() > 1) {
            for (int i = enemies.size() - 1; i > 0; i--) {
                Enemy enemy = enemies.remove(i);
                this.entityManager.removeEntity(enemy);
                this.collisionManager.remove(enemy);            
            }
        }
        
//        // Add new enemies based on level
//        for (int i = 0; i < level-1; i++) {
//            float x = (float) (Math.random() * Gdx.graphics.getWidth());
//            float y = (float) (Math.random() * Gdx.graphics.getHeight());
//            Enemy enemy = new Enemy(x, y, "grandmother.png", GameState.getEnemySpeed(), 75f, 85f);
//            enemy.setTarget(player);
//            enemies.add(enemy);
//            
//            // Register with managers
//            this.entityManager.addEntity(enemy);
//            this.collisionManager.register(enemy);
//            this.movementManager.addMovingEntity(enemy);
//            this.movementManager.addAIEntity(enemy);
//        }
    }

    private void spawnPowerUp() {
        float x = (float) (Math.random() * Gdx.graphics.getWidth());
        float y = (float) (Math.random() * Gdx.graphics.getHeight());
        PowerUp powerUp = new PowerUp(x, y, "broccoli.png");
        powerUps.add(powerUp);
        this.entityManager.addEntity(powerUp);
        this.collisionManager.register(powerUp);
    }

    private void setupCollisions() {
        player.setCollisionAction(other -> {
            if (other instanceof Enemy) {
                playerHealth--;
                if (playerHealth <= 0) {
                    this.ioManager.getSoundManager().playSound("game_over");
                    this.sceneManager.setScene(GameOverScene.class);
                }
            }
            else if (other instanceof Trash) {
                Trash trash = (Trash) other;
                System.out.println("Trash Detected");
                if (!trash.isPickedUp() && player.getHeldTrash() == null && player.droppedTrash() != trash) {
                    trash.setPickedUp(true);
                    player.pickupTrash(trash);
                    this.ioManager.getSoundManager().playSound("pickup");
                }
            }
            else if (other instanceof TrashBin && trashCooldown <= 0) {
                TrashBin bin = (TrashBin) other;
                Trash heldTrash = player.getHeldTrash();
                if (heldTrash != null ) {
                    boolean correct = bin.getAcceptedType() == heldTrash.getType();
                    GameState.updateScore(correct);
                    if (correct) {
                        applySpeedBoost();
                        System.out.println("Trash placed correct");
//                        this.ioManager.getSoundManager().playSound("correct");
                    } else {
                        applySpeedPenalty();
                        System.out.println("Trash placed Wrongly this is the " + bin.getAcceptedType() + " Bin");
//                        this.ioManager.getSoundManager().playSound("wrong");
                    }
                    lastTrashDropped = heldTrash;
                    trashCooldown = 0.2f; // Prevent multiple triggers
                    player.dropTrash();
                    this.entityManager.removeEntity((Entity) heldTrash);
                    spawnNewTrash();
                }
            }
            else if (other instanceof PowerUp) {
                PowerUp powerUp = (PowerUp) other;
                powerUp.applyEffect(player);
                powerUps.remove(powerUp);
                this.entityManager.removeEntity(powerUp);
                this.collisionManager.remove(powerUp);
            }
        });
    }

    private void setupControls() {
        this.ioManager.getInputManager().subscribeKeyDown(Keys.W, () -> player.setDirection(0, 1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.S, () -> player.setDirection(0, -1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.A, () -> player.setDirection(-1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.D, () -> player.setDirection(1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SPACE, () -> player.dropTrash());
    }

    private void applySpeedBoost() {
        isSpeedBoosted = true;
        isSpeedPenalized = false; // Cancel penalty if active
        speedBoostTimer = 5; // 5 seconds
        player.setSpeed(GameState.getPlayerSpeed() * 1.5f);
    }

    private void applySpeedPenalty() {
        isSpeedPenalized = true;
        isSpeedBoosted = false; // Cancel boost if active
        speedPenaltyTimer = 2; // 2 seconds
        player.setSpeed(GameState.getPlayerSpeed() * 0.6f);
    }

    
    private void spawnNewTrash() {
        float centerX = Gdx.graphics.getWidth() / 2f + (float)(Math.random() * 100 - 50);
        float centerY = Gdx.graphics.getHeight() / 2f + (float)(Math.random() * 100 - 50);
        Trash newTrash = new Trash(centerX, centerY, 
            Trash.TrashType.values()[(int)(Math.random() * 4)], "broccoli.png");
        trashItems.add(newTrash);
        this.entityManager.addEntity(newTrash);
        this.collisionManager.register(newTrash);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
        // Update timers
        float delta = Gdx.graphics.getDeltaTime();
        GameState.updateTime(delta);
        
        if (isSpeedBoosted) {
            speedBoostTimer -= delta;
            if (speedBoostTimer <= 0) {
                isSpeedBoosted = false;
                player.setSpeed(GameState.getPlayerSpeed());
            }
        }
        
        if (isSpeedPenalized) {
            speedPenaltyTimer -= delta;
            if (speedPenaltyTimer <= 0) {
                isSpeedPenalized = false;
                player.setSpeed(GameState.getPlayerSpeed());
            }
        }

        // Update enemy speeds
        float currentEnemySpeed = GameState.getEnemySpeed();
        enemies.forEach(enemy -> enemy.setSpeed(currentEnemySpeed));

        // Update cooldown
        if (trashCooldown > 0) {
            trashCooldown -= Gdx.graphics.getDeltaTime();
        }

        // Update level timer
        levelTimer += delta;
        if (levelTimer >= 30) {
            level++;
            levelTimer = 0;
            spawnEnemies();
        }

        // Spawn power-ups periodically
        powerUpSpawnTimer += delta;
        if (powerUpSpawnTimer >= 10) {
            powerUpSpawnTimer = 0;
            spawnPowerUp();
        }
        
        super.render();

        // Draw score, health, and current trash type
        SpriteBatch batch = ServiceLocator.get(SpriteBatch.class);
        batch.begin();
        font.draw(batch, "Score: " + GameState.getScore(), 10, 470);
        font.draw(batch, "Health: " + playerHealth, 10, 450);
        font.draw(batch, "Level: " + level, 10, 430);
        Trash held = player.getHeldTrash();
        if (held != null) {
            font.draw(batch, "Holding: " + held.getType(), 10, 410);
        }
        if (isSpeedBoosted) {
            font.draw(batch, "Speed Boost: " + String.format("%.1f", speedBoostTimer), 10, 390);
        }
        if (isSpeedPenalized) {
            font.draw(batch, "Speed Penalty: " + String.format("%.1f", speedPenaltyTimer), 10, 390);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}


