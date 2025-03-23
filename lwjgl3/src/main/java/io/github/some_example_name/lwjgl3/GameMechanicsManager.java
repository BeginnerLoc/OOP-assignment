package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMechanicsManager {
    private Player player;
    private List<Enemy> enemies;
    private List<TrashBin> bins;
    private List<Trash> trashItems;
    private List<PowerUp> powerUps;
    private float speedBoostTimer;
    private float speedPenaltyTimer;
    private boolean isSpeedBoosted;
    private boolean isSpeedPenalized;
    private float trashCooldown;
    private int level;
    private float levelTimer;
    private int playerHealth;
    private float powerUpSpawnTimer;

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
        this.level = 1;
        this.playerHealth = 3;
        
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

        for (int i = enemies.size(); i < enemyCount; i++) {
            // Spawn enemy at random position away from the player
            float x, y;
            do {
                x = (float) (Math.random() * (virtualWidth - 100));
                y = (float) (Math.random() * (virtualHeight - 100));
            } while (isNearPlayer(x, y, 200)); // Keep enemies at least 200 units away from player initially
            
            Enemy enemy = new Enemy(x, y, "grandmother.png", GameState.getEnemySpeed(), 65f, 90f);
            enemy.setTarget(player);
            
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
        powerUps.add(powerUp);
        entityManager.addEntity(powerUp);
        collisionManager.register(powerUp);
    }

    private void setupCollisions() {
        player.setCollisionAction(other -> handleCollision((Entity)other));
    }

    private void handleCollision(Entity other) {
        if (other instanceof Enemy) {
            handleEnemyCollision();
        } else if (other instanceof Trash) {
            handleTrashCollision((Trash) other);
        } else if (other instanceof TrashBin && trashCooldown <= 0) {
            handleTrashBinCollision((TrashBin) other);
        } else if (other instanceof PowerUp) {
            handlePowerUpCollision((PowerUp) other);
        }
    }

    private void handleEnemyCollision() {
        // Immediately end game on enemy collision
        if (!isSpeedPenalized) {
            // Play game over sound and end the game
            if (ioManager.getSoundManager() != null) {
                ioManager.getSoundManager().playSound("game_over");
            }
            cleanupGameResources();
            ServiceLocator.get(SceneManager.class).setScene(GameOverScene.class);
        }
    }
    
    /**
     * Cleans up all game resources and resets the game state when the game is over
     */
    public void cleanupGameResources() {
        // Reset GameState static data
        GameState.reset();
        
        // Clean up all entities
        cleanupEntities();
    }
    
    /**
     * Cleans up all game entities to prevent memory leaks and state persistence
     */
    private void cleanupEntities() {
        try {
            // Use safe iteration with a copy of the list when possible
            List<Enemy> enemiesCopy = new ArrayList<>(enemies);
            for (Enemy enemy : enemiesCopy) {
                entityManager.removeEntity(enemy);
                collisionManager.remove(enemy);
                movementManager.removeMovingEntity(enemy);
                movementManager.removeAIEntity(enemy);
                enemy.dispose(); // Properly dispose of textures
            }
            enemies.clear();
            
            // Clean up trash items
            List<Trash> trashCopy = new ArrayList<>(trashItems);
            for (Trash trash : trashCopy) {
                entityManager.removeEntity(trash);
                collisionManager.remove(trash);
                trash.dispose(); // Properly dispose of textures
            }
            trashItems.clear();
            
            // Clean up power-ups
            List<PowerUp> powerUpsCopy = new ArrayList<>(powerUps);
            for (PowerUp powerUp : powerUpsCopy) {
                entityManager.removeEntity(powerUp);
                collisionManager.remove(powerUp);
                powerUp.dispose(); // Properly dispose of textures
            }
            powerUps.clear();
            
            // Clean up bins
            List<TrashBin> binsCopy = new ArrayList<>(bins);
            for (TrashBin bin : binsCopy) {
                entityManager.removeEntity(bin);
                collisionManager.remove(bin);
                // TrashBin should also have a dispose method if it has resources
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
            playerHealth = 3;
            powerUpSpawnTimer = 0;
            
            // Clean up player resources if necessary
            if (player != null && player.getHeldTrash() != null) {
                Trash heldTrash = player.getHeldTrash();
                entityManager.removeEntity(heldTrash);
                collisionManager.remove(heldTrash);
                heldTrash.dispose();
            }
        } catch (Exception e) {
            // Log any errors but don't crash
            System.err.println("Error during entity cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleTrashCollision(Trash trash) {
        if (!trash.isPickedUp() && player.getHeldTrash() == null && player.droppedTrash() != trash) {
            trash.setPickedUp(true);
            player.pickupTrash(trash);
            ioManager.getSoundManager().playSound("pickup");
        }
    }

    private void handleTrashBinCollision(TrashBin bin) {
        Trash heldTrash = player.getHeldTrash();
        if (heldTrash != null) {
            boolean correct = bin.getAcceptedType() == heldTrash.getType();
            GameState.updateScore(correct);
            if (correct) {
                applySpeedBoost();
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

    private void handlePowerUpCollision(PowerUp powerUp) {
        powerUp.applyEffect(player);
        powerUps.remove(powerUp);
        entityManager.removeEntity(powerUp);
        collisionManager.remove(powerUp);
        ioManager.getSoundManager().playSound("Power Up");
    }

    public void applySpeedBoost() {
        player.changeTexture("car.png");
        isSpeedBoosted = true;
        isSpeedPenalized = false;
        speedBoostTimer = 5;
        player.setSpeed(GameState.getPlayerSpeed() * 1.5f);
    }

    public void applySpeedPenalty() {
        player.changeTexture("turtle.png");
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
                
                player.changeTexture("mrbean.png");

            }
        }
        
        if (isSpeedPenalized) {
            speedPenaltyTimer -= delta;
            if (speedPenaltyTimer <= 0) {
                isSpeedPenalized = false;
                player.changeTexture("mrbean.png");
                player.setSpeed(GameState.getPlayerSpeed());
            }
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

    // Getters for UI display
    public int getPlayerHealth() { return playerHealth; }
    public int getLevel() { return level; }
    public boolean isSpeedBoosted() { return isSpeedBoosted; }
    public boolean isSpeedPenalized() { return isSpeedPenalized; }
    public float getSpeedBoostTimer() { return speedBoostTimer; }
    public float getSpeedPenaltyTimer() { return speedPenaltyTimer; }
    public Player getPlayer() { return player; }
}