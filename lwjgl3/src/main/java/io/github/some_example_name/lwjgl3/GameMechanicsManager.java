package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import game_engine.BackgroundRenderer;
import game_engine.Collidable;
import game_engine.CollisionManager;
import game_engine.Entity;
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
    

    // Trash image arrays
    private final String[] plasticTrashImages = {"bottle_waste.png", "jug1_waste.png", "jug2_waste.png"};
    private final String[] paperTrashImages = {"newspaper_waste.png", "cup_waste.png", "paperbag_waste.png", "parcelbox_waste.png", "pizzabox_waste.png"};
    private final String[] metalTrashImages = {"ColaCan_waste.png", "canfood_waste.png", "SprayCan_waste.png", "catfoodCan_waste.png"};
    private final String[] glassTrashImages = {"glassbottle1_waste.png", "glassbottle2_waste.png", "glassjar_waste.png"};

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
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        float centerX = virtualWidth / 2f;
        float centerY = virtualHeight / 2f;
        Random random = new Random();

        spawnTrashOfType(Trash.TrashType.PLASTIC, plasticTrashImages, 2, centerX, centerY, random);
        spawnTrashOfType(Trash.TrashType.PAPER, paperTrashImages, 2, centerX, centerY, random);
        spawnTrashOfType(Trash.TrashType.METAL, metalTrashImages, 2, centerX, centerY, random);
        spawnTrashOfType(Trash.TrashType.GLASS, glassTrashImages, 2, centerX, centerY, random);
    }

    private void spawnTrashOfType(Trash.TrashType type, String[] images, int count, float centerX, float centerY, Random random) {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        
        for (int i = 0; i < count; i++) {
            String imageName = images[random.nextInt(images.length)];
            float x = centerX + (random.nextFloat() - 0.5f) * virtualWidth * 0.4f;
            float y = centerY + (random.nextFloat() - 0.5f) * virtualHeight * 0.4f;
            Trash trash = new Trash(x, y, type, imageName);
            trashItems.add(trash);
            entityManager.addEntity(trash);
            collisionManager.register(trash);
        }
    }

    public void spawnNewTrash() {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        float centerX = virtualWidth / 2f + (float)(Math.random() * 100 - 50);
        float centerY = virtualHeight / 2f + (float)(Math.random() * 100 - 50);
        Trash.TrashType randomType = Trash.TrashType.values()[(int)(Math.random() * 4)];
        
        String imageName = getRandomImageForType(randomType);
        Trash newTrash = new Trash(centerX, centerY, randomType, imageName);
        
        trashItems.add(newTrash);
        entityManager.addEntity(newTrash);
        collisionManager.register(newTrash);
    }

    private String getRandomImageForType(Trash.TrashType type) {
        Random random = new Random();
        switch (type) {
            case PLASTIC: return plasticTrashImages[random.nextInt(plasticTrashImages.length)];
            case PAPER: return paperTrashImages[random.nextInt(paperTrashImages.length)];
            case METAL: return metalTrashImages[random.nextInt(metalTrashImages.length)];
            case GLASS: return glassTrashImages[random.nextInt(glassTrashImages.length)];
            default: return "";
        }
    }

    public void spawnEnemies() {
        // Clear existing enemies except the first one
        if (enemies.size() > 1) {
            for (int i = enemies.size() - 1; i > 0; i--) {
                Enemy enemy = enemies.remove(i);
                entityManager.removeEntity(enemy);
                collisionManager.remove(enemy);
                movementManager.getMovingEntities().remove(enemy);           
            }
        }

        // Create new enemies based on current level
        int enemyCount = Math.min(level + 1, 5); // Cap at 5 enemies maximum
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        Random random = new Random();

        for (int i = enemies.size(); i < enemyCount; i++) {
            // Spawn enemy at random position away from the player
            float x, y;
            do {
                x = (float) (Math.random() * (virtualWidth - 100));
                y = (float) (Math.random() * (virtualHeight - 100));
            } while (isNearPlayer(x, y, 200)); // Keep enemies at least 200 units away from player initially
            
            // Get a random movement pattern
            EnemyMovePattern[] patterns = EnemyMovePattern.values();
            EnemyMovePattern randomPattern = patterns[random.nextInt(patterns.length)];
            
            // Create enemy using the factory instead of direct instantiation
            Enemy enemy = EnemyFactory.createEnemy(x, y, "grandmother.png", 
                                                GameState.getEnemySpeed(), 
                                                randomPattern, 65f, 90f);
            enemy.setTarget(player);
            
            // Set up collision handling within the Enemy class
            configureEnemyCollisions(enemy);
            
            enemies.add(enemy);
            entityManager.addEntity(enemy);
            collisionManager.register(enemy);
            movementManager.addMovingEntity(enemy);
            movementManager.addAIEntity(enemy);
        }
    }

    private boolean isNearPlayer(float x, float y, float minDistance) {
        if (player == null) return false;
        float dx = x - player.getX();
        float dy = y - player.getY();
        return Math.sqrt(dx * dx + dy * dy) < minDistance;
    }

    public void spawnPowerUp() {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        float x = (float) (Math.random() * (virtualWidth - 32));
        float y = (float) (Math.random() * (virtualHeight - 32));
        PowerUp powerUp = new PowerUp(x, y, "teddy.png");
        
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
                handlePushFromStaticObject(enemy, bin);
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
        handlePushFromStaticObject(player, bin);
        
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
    
    /**
     * Physics-like collision handling that pushes a moving entity away from a static object
     */
    private void handlePushFromStaticObject(Collidable movingEntity, Collidable staticEntity) {
        // Calculate centers
        float movingCenterX = ((Entity)movingEntity).getX() + movingEntity.getBounds().width / 2;
        float movingCenterY = ((Entity)movingEntity).getY() + movingEntity.getBounds().height / 2;
        float staticCenterX = ((Entity)staticEntity).getX() + staticEntity.getBounds().width / 2;
        float staticCenterY = ((Entity)staticEntity).getY() + staticEntity.getBounds().height / 2;
        
        // Calculate direction vector from static center to moving center
        float dx = movingCenterX - staticCenterX;
        float dy = movingCenterY - staticCenterY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        
        if (distance < 0.1f) {
            // If centers are too close, push directly right
            dx = 1;
            dy = 0;
            distance = 1;
        }
        
        // Calculate minimum distance needed between centers
        float minDistance = (movingEntity.getBounds().width + staticEntity.getBounds().width) / 2;
        
        if (distance < minDistance) {
            // Calculate new position maintaining minimum distance
            float pushX = (dx / distance) * (minDistance - distance);
            float pushY = (dy / distance) * (minDistance - distance);
            
            ((Entity)movingEntity).setX(((Entity)movingEntity).getX() + pushX);
            ((Entity)movingEntity).setY(((Entity)movingEntity).getY() + pushY);
        }
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
    
    /**
     * Cleans up all game resources when the game is over
     */
    public void cleanupGameResources() {
        unregisterEntities();
    }
    

    private void unregisterEntities() {
        try {
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