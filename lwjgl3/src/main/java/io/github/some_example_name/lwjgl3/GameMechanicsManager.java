package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import game.entities.CatToy;
import game.entities.Enemy;
import game.entities.EnemyFactory;
import game.entities.Player;
import game.entities.PowerUp;
import game.entities.Trash;
import game.entities.TrashBin;
import game.scenes.GameOverScene;
import game.utils.EnemyMovePattern;
import game.utils.GameState;
import game.utils.GameUtils;
import game_engine.BackgroundRenderer;
import game_engine.CollisionManager;
import game_engine.EntityManager;
import game_engine.IOManager;
import game_engine.MovementManager;
import game_engine.SceneManager;
import game_engine.ServiceLocator;

public class GameMechanicsManager {
    private Player player;
    private List<Enemy> enemies;
    private List<TrashBin> bins;
    private List<Trash> trashItems;
    private List<PowerUp> powerUps;
    private List<CatToy> catToys;
    private float speedBoostTimer;
    private float speedPenaltyTimer;
    private boolean isSpeedBoosted;
    private boolean isSpeedPenalized;
    private float trashCooldown;
    private int level;
    private float levelTimer;
    private float powerUpSpawnTimer;
    private Map<Enemy, Float> stunnedEnemies;
    private int catCharges; 
    private float catCooldown = 0; 

    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private IOManager ioManager;
    private MovementManager movementManager;

    public GameMechanicsManager() {
        this.enemies = new ArrayList<>();
        this.bins = new ArrayList<>();
        this.trashItems = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.catToys = new ArrayList<>();
        this.stunnedEnemies = new HashMap<>();
        this.level = 1;
        this.catCharges = 0; // Initialize cat charges
        
        // Get manager instances
        this.entityManager = ServiceLocator.get(EntityManager.class);
        this.collisionManager = ServiceLocator.get(CollisionManager.class);
        this.ioManager = ServiceLocator.get(IOManager.class);
        this.movementManager = ServiceLocator.get(MovementManager.class);
    }

    public void initializeGame(Player player) {
        this.player = player;
        createTrashBins();
        spawnTrash();
        spawnEnemies();
        setupCollisions();
        setupControls();
    }

    public void createTrashBins() {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        bins.add(new TrashBin(0, 0, Trash.TrashType.PLASTIC, "plastic_bin_updated.png"));
        bins.add(new TrashBin(virtualWidth - 100, 0, Trash.TrashType.METAL, "metal_bin_updated.png"));
        bins.add(new TrashBin(0, virtualHeight - 100, Trash.TrashType.PAPER, "paper_bin_updated.png"));
        bins.add(new TrashBin(virtualWidth - 100, virtualHeight - 100, Trash.TrashType.GLASS, "glass_bin_updated.png"));
        
        for (TrashBin bin : bins) {
            entityManager.addEntity(bin);
            collisionManager.register(bin);
        }
    }

    public void spawnTrash() {
        float[] center = GameUtils.getScreenCenter();
        float centerX = center[0];
        float centerY = center[1];
        Random random = new Random();

        spawnTrashOfType(Trash.TrashType.PLASTIC, 2, centerX, centerY, random);
        spawnTrashOfType(Trash.TrashType.PAPER, 2, centerX, centerY, random);
        spawnTrashOfType(Trash.TrashType.METAL, 2, centerX, centerY, random);
        spawnTrashOfType(Trash.TrashType.GLASS, 2, centerX, centerY, random);
    }

    private void spawnTrashOfType(Trash.TrashType type, int count, float centerX, float centerY, Random random) {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        for (int i = 0; i < count; i++) {
            String imageName = GameUtils.getRandomImageForType(type);
            float x = centerX + (random.nextFloat() - 0.5f) * virtualWidth * 0.4f;
            float y = centerY + (random.nextFloat() - 0.5f) * virtualHeight * 0.4f;
            Trash trash = new Trash(x, y, type, imageName);
            trashItems.add(trash);
            entityManager.addEntity(trash);
            collisionManager.register(trash);
        }
    }

    public void spawnNewTrash() {
        float[] center = GameUtils.getScreenCenter();
        float centerX = center[0] + (float)(Math.random() * 100 - 50);
        float centerY = center[1] + (float)(Math.random() * 100 - 50);
        Trash.TrashType randomType = Trash.TrashType.values()[(int)(Math.random() * 4)];
        
        String imageName = GameUtils.getRandomImageForType(randomType);
        Trash newTrash = new Trash(centerX, centerY, randomType, imageName);
        
        trashItems.add(newTrash);
        entityManager.addEntity(newTrash);
        collisionManager.register(newTrash);
    }

    public void spawnEnemies() {
        // Early return if we already have enough enemies for the current level
        if (enemies.size() >= level + 1) {
            return; 
        }

        // Determine how many enemies to spawn based on the current level
        int enemyCount = determineEnemyCount();
        
        // Only add new enemies to reach the target count
        spawnAdditionalEnemies(enemyCount);
    }
    
    /**
     * Determines the target number of enemies based on the current level
     * @return the number of enemies that should be present
     */
    private int determineEnemyCount() {
        // Number of enemies equals the current level
        return level;
    }
    
    /**
     * Spawns additional enemies until the target count is reached
     * @param targetCount the total number of enemies that should be present
     */
    private void spawnAdditionalEnemies(int targetCount) {
        Random random = new Random();
        
        for (int i = enemies.size(); i < targetCount; i++) {
            // Spawn enemy at random position away from the player
            float x, y;
            do {
                float[] position = GameUtils.getRandomPosition(100);
                x = position[0];
                y = position[1];
            } while (GameUtils.isNearEntity(x, y, player, 200)); // Keep enemies at least 200 units away from player initially
            
            // Get a random movement pattern
            EnemyMovePattern randomPattern = getRandomMovementPattern(random);
            
            // Create and configure enemy
            Enemy enemy = createAndConfigureEnemy(x, y, randomPattern);
            
            // Register enemy with various managers
            registerEnemy(enemy);
        }
    }
    
    /**
     * Selects a random movement pattern for an enemy
     * @param random Random number generator
     * @return A randomly selected enemy movement pattern
     */
    private EnemyMovePattern getRandomMovementPattern(Random random) {
        EnemyMovePattern[] patterns = EnemyMovePattern.values();
        return patterns[random.nextInt(patterns.length)];
    }
    
    /**
     * Creates and configures a new enemy entity
     * @param x X-coordinate for spawn position
     * @param y Y-coordinate for spawn position
     * @param movePattern Movement pattern to assign to the enemy
     * @return The configured enemy entity
     */
    private Enemy createAndConfigureEnemy(float x, float y, EnemyMovePattern movePattern) {
        // Create enemy using the factory 
        Enemy enemy = EnemyFactory.createEnemy(x, y, "grandmother.png", 
                                            GameState.getEnemySpeed(), 
                                            movePattern, 65f, 90f);
        
        // Configure target and collision handling
        enemy.setTarget(player);
        configureEnemyCollisions(enemy);
        
        return enemy;
    }
    
    /**
     * Registers an enemy with all required managers and collections
     * @param enemy The enemy to register
     */
    private void registerEnemy(Enemy enemy) {
        enemies.add(enemy);
        entityManager.addEntity(enemy);
        collisionManager.register(enemy);
        movementManager.addMovingEntity(enemy);
        movementManager.addAIEntity(enemy);
    }

    public void spawnPowerUp() {
        float[] position = GameUtils.getRandomPosition(32);
        PowerUp powerUp = new PowerUp(position[0], position[1], "teddy.png");
        
        // Configure PowerUp collision handling
        configureCollisionForPowerUp(powerUp);
        
        powerUps.add(powerUp);
        entityManager.addEntity(powerUp);
        collisionManager.register(powerUp);
    }

    /**
     * Sets up collision handling for all entities through their Collidable interface
     */
    private void setupCollisions() {
        // Set up player collision handling
        configurePlayerCollisions();
        
        // Set up enemy collision handling
        for (Enemy enemy : enemies) {
            configureEnemyCollisions(enemy);
        }
        
        // Set up trash bin collision handling for all bins
        for (TrashBin bin : bins) {
            configureBinCollisions(bin);
        }
        
        // Set up trash collision handling
        for (Trash trash : trashItems) {
            configureTrashCollisions(trash);
        }
    }

    /**
     * Configure player collision handling
     */
    private void configurePlayerCollisions() {
        player.setCollisionAction(other -> {
            if (other instanceof Enemy) {
                handlePlayerEnemyCollision((Enemy) other);
            } else if (other instanceof Trash) {
                handlePlayerTrashCollision((Trash) other);
            } else if (other instanceof TrashBin) {
                handlePlayerTrashBinCollision((TrashBin) other);
            } else if (other instanceof PowerUp) {
                handlePlayerPowerUpCollision((PowerUp) other);
            }
        });
    }
    
    /**
     * Configure enemy collision handling
     */
    private void configureEnemyCollisions(Enemy enemy) {
        enemy.setCollisionAction(other -> {
            if (other instanceof TrashBin) {
                // Handle enemy-bin collision with physics-like pushing
                TrashBin bin = (TrashBin) other;
                GameUtils.handlePushFromStaticObject(enemy, bin);
            } else if (other instanceof CatToy) {
                // Cat toy stuns the enemy
                handleEnemyCatToyCollision(enemy, (CatToy) other);
            }
        });
    }
    
    /**
     * Configure trash bin collision handling
     */
    private void configureBinCollisions(TrashBin bin) {
        // Bins don't need active collision handling,
        // they are passive in collisions
    }
    
    /**
     * Configure trash item collision handling
     */
    private void configureTrashCollisions(Trash trash) {
        // Trash items don't need active collision handling,
        // they are passive in collisions
    }
    
    /**
     * Configure power-up collision handling
     */
    private void configureCollisionForPowerUp(PowerUp powerUp) {
        // Power-ups don't need active collision handling,
        // they are passive in collisions
    }
    
    /**
     * Configure cat toy collision handling
     */
    private void configureCatToyCollisions(CatToy toy) {
        toy.setCollisionAction(other -> {
            if (other instanceof Enemy) {
                handleEnemyCatToyCollision((Enemy) other, toy);
            }
        });
    }

    /**
     * Handles collision between player and enemy
     */
    private void handlePlayerEnemyCollision(Enemy enemy) {
        // End game on enemy collision if not penalized
        if (!isSpeedPenalized) {
            if (ioManager.getSoundManager() != null) {
                ioManager.getSoundManager().playSound("game_over");
            }
            cleanupGameResources();
            ServiceLocator.get(SceneManager.class).setScene(GameOverScene.class);
        }
    }
    
    /**
     * Handles collision between player and trash
     */
    private void handlePlayerTrashCollision(Trash trash) {
        if (!trash.isPickedUp() && player.getHeldTrash() == null && player.droppedTrash() != trash) {
            trash.setPickedUp(true);
            player.pickupTrash(trash);
            ioManager.getSoundManager().playSound("pickup");
        }
    }
    
    /**
     * Handles collision between player and trash bin
     */
    private void handlePlayerTrashBinCollision(TrashBin bin) {
        // Physics-like handling to push player away from bin
        GameUtils.handlePushFromStaticObject(player, bin);
        
        // Game logic for depositing trash in bin
        Trash heldTrash = player.getHeldTrash();
        if (heldTrash != null) {
            boolean correct = bin.getAcceptedType() == heldTrash.getType();
            GameState.updateScore(correct);
            if (correct) {
                applySpeedBoost();
                catCharges++; // Add cat charge for correct trash disposal
                ioManager.getSoundManager().playSound("trash_correct");
            } else {
                applySpeedPenalty();
                ioManager.getSoundManager().playSound("trash_wrong");
            }
            trashCooldown = 0.2f;
            player.dropTrash();
            entityManager.removeEntity(heldTrash);
            spawnNewTrash();
        }
    }
    
    /**
     * Handles collision between player and power-up
     */
    private void handlePlayerPowerUpCollision(PowerUp powerUp) {
        powerUps.remove(powerUp);
        entityManager.removeEntity(powerUp);
        collisionManager.remove(powerUp);
        catCharges++; // Add cat charge for power-up collection
        ioManager.getSoundManager().playSound("Power Up");
    }
    
    /**
     * Handles collision between enemy and cat toy
     */
    private void handleEnemyCatToyCollision(Enemy enemy, CatToy toy) {
        // Stun the enemy
        stunnedEnemies.put(enemy, toy.getStunDuration());
        enemy.setTexture("grandmother_happy.png");
        enemy.setStunned(true);
        enemy.setSpeed(0);
        
        // Remove the cat toy
        catToys.remove(toy);
        entityManager.removeEntity(toy);
        collisionManager.remove(toy);
        toy.dispose();
    }

    public void throwCatToy() {
        if (catCharges > 0 && catCooldown <= 0) { // Only throw if cooldown is up
            float x = player.getX();
            float y = player.getY();
            CatToy toy = new CatToy(x, y, "cat.png", 52, 52);
            
            // Set up collision handling for the toy
            configureCatToyCollisions(toy);
            
            catToys.add(toy);
            entityManager.addEntity(toy);
            collisionManager.register(toy);
            catCharges--; // Use up one charge
            catCooldown = 0.5f; // Set cooldown to 0.5 seconds
        }
    }

    public void applySpeedBoost() {
        player.changeTexture("mrbean_car.png");
        isSpeedBoosted = true;
        isSpeedPenalized = false;
        speedBoostTimer = 5;
        player.setSpeed(GameState.getPlayerSpeed() * 1.5f);
    }

    public void applySpeedPenalty() {
        player.changeTexture("mrbean_slow.png");
        isSpeedPenalized = true;
        isSpeedBoosted = false;
        speedPenaltyTimer = 2;
        player.setSpeed(GameState.getPlayerSpeed() * 0.6f);
    }

    public void update(float delta) {
        updateTimers(delta);
        updateEnemySpeeds();
        checkLevelProgress(delta);
        checkPowerUpSpawn(delta);
        
    }

    private void updateTimers(float delta) {
        GameState.updateTime(delta);
        
        if (isSpeedBoosted) {
            speedBoostTimer -= delta;
            if (speedBoostTimer <= 0) {
                isSpeedBoosted = false;
                player.setSpeed(GameState.getPlayerSpeed());
                player.changeTexture("mr_bean.png");
            }
        }
        
        if (isSpeedPenalized) {
            speedPenaltyTimer -= delta;
            if (speedPenaltyTimer <= 0) {
                isSpeedPenalized = false;
                player.changeTexture("mr_bean.png");
                player.setSpeed(GameState.getPlayerSpeed());
            }
        }

        // Update stunned enemies
        Iterator<Map.Entry<Enemy, Float>> it = stunnedEnemies.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Enemy, Float> entry = it.next();
            Enemy enemy = entry.getKey();
            float remainingStunTime = entry.getValue() - delta;
            
            if (remainingStunTime <= 0) {
                enemy.setStunned(false);
                enemy.setTexture("grandmother.png");
                enemy.setSpeed(GameState.getEnemySpeed());
                it.remove();
            } else {
                entry.setValue(remainingStunTime);
            }
        }

        // Update cat cooldown
        if (catCooldown > 0) {
            catCooldown -= delta;
        }

        if (trashCooldown > 0) {
            trashCooldown -= delta;
        }
    }

    private void updateEnemySpeeds() {
        float currentEnemySpeed = GameState.getEnemySpeed();
        enemies.forEach(enemy -> enemy.setSpeed(currentEnemySpeed));
    }

    private void checkLevelProgress(float delta) {
        levelTimer += delta;
        if (levelTimer >= 30) {
            level++;
            levelTimer = 0;
            spawnEnemies();
        }
    }

    private void checkPowerUpSpawn(float delta) {
        powerUpSpawnTimer += delta;
        if (powerUpSpawnTimer >= 10) {
            powerUpSpawnTimer = 0;
            spawnPowerUp();
        }
    }

        
    private void setupControls() {
        this.ioManager.getInputManager().subscribeKeyDown(Keys.W, () -> this.getPlayer().setDirection(0, 1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.S, () -> this.getPlayer().setDirection(0, -1));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.A, () -> this.getPlayer().setDirection(-1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.D, () -> this.getPlayer().setDirection(1, 0));
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SPACE, () -> this.throwCatToy());
        
        // Add sprint control with Shift key
        this.ioManager.getInputManager().subscribeKeyDown(Keys.SHIFT_LEFT, () -> this.getPlayer().sprint(true));
        this.ioManager.getInputManager().subscribeKeyUp(Keys.SHIFT_LEFT, () -> this.getPlayer().sprint(false));
        
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
            this.getPlayer().stop();
        }
    }
    
    /**
     * Cleans up all game resources when the game is over
     */
    public void cleanupGameResources() {
        unregisterEntities();
    }
    
    private void unregisterEntities() {
        try {
            // Use safe iteration with a copy of the list when possible
            List<Enemy> enemiesCopy = new ArrayList<>(enemies);
            for (Enemy enemy : enemiesCopy) {
                entityManager.removeEntity(enemy);
                collisionManager.remove(enemy);
                movementManager.removeMovingEntity(enemy);
                movementManager.removeAIEntity(enemy);
            }
            enemies.clear();
            
            // Clean up trash items
            List<Trash> trashCopy = new ArrayList<>(trashItems);
            for (Trash trash : trashCopy) {
                entityManager.removeEntity(trash);
                collisionManager.remove(trash);
            }
            trashItems.clear();
            
            // Clean up power-ups
            List<PowerUp> powerUpsCopy = new ArrayList<>(powerUps);
            for (PowerUp powerUp : powerUpsCopy) {
                entityManager.removeEntity(powerUp);
                collisionManager.remove(powerUp);
            }
            powerUps.clear();
            
            // Clean up bins
            List<TrashBin> binsCopy = new ArrayList<>(bins);
            for (TrashBin bin : binsCopy) {
                entityManager.removeEntity(bin);
                collisionManager.remove(bin);
            }
            bins.clear();
            
            // Reset all flags and timers
            speedBoostTimer = 0;
            speedPenaltyTimer = 0;
            isSpeedBoosted = false;
            isSpeedPenalized = false;
            trashCooldown = 0;
            level = 1;
            levelTimer = 0;
            powerUpSpawnTimer = 0;
            
            // Clean up player resources if necessary
            if (player != null && player.getHeldTrash() != null) {
                Trash heldTrash = player.getHeldTrash();
                entityManager.removeEntity(heldTrash);
                collisionManager.remove(heldTrash);
            }
        } catch (Exception e) {
            System.err.println("Error during entity cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters for UI display
    public int getLevel() { return level; }
    public boolean isSpeedBoosted() { return isSpeedBoosted; }
    public boolean isSpeedPenalized() { return isSpeedPenalized; }
    public float getSpeedBoostTimer() { return speedBoostTimer; }
    public float getSpeedPenaltyTimer() { return speedPenaltyTimer; }
    public Player getPlayer() { return player; }
    public int getCatCharges() { return catCharges; }
}