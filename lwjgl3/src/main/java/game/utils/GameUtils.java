package game.utils;

import java.util.Random;

import game.entities.Trash;
import game_engine.BackgroundRenderer;
import game_engine.Collidable;
import game_engine.Entity;

/**
 * Utility class for game-related helper functions.
 */
public class GameUtils {
    // Image arrays for different trash types
    private static final String[] PLASTIC_TRASH_IMAGES = {"bottle_waste.png", "jug1_waste.png", "jug2_waste.png"};
    private static final String[] PAPER_TRASH_IMAGES = {"newspaper_waste.png", "cup_waste.png", "paperbag_waste.png", "parcelbox_waste.png", "pizzabox_waste.png"};
    private static final String[] METAL_TRASH_IMAGES = {"ColaCan_waste.png", "canfood_waste.png", "SprayCan_waste.png", "catfoodCan_waste.png"};
    private static final String[] GLASS_TRASH_IMAGES = {"glassbottle1_waste.png", "glassbottle2_waste.png", "glassjar_waste.png"};
    
    private static final Random RANDOM = new Random();

    public static String getRandomImageForType(Trash.TrashType type) {
        switch (type) {
            case PLASTIC: return PLASTIC_TRASH_IMAGES[RANDOM.nextInt(PLASTIC_TRASH_IMAGES.length)];
            case PAPER: return PAPER_TRASH_IMAGES[RANDOM.nextInt(PAPER_TRASH_IMAGES.length)];
            case METAL: return METAL_TRASH_IMAGES[RANDOM.nextInt(METAL_TRASH_IMAGES.length)];
            case GLASS: return GLASS_TRASH_IMAGES[RANDOM.nextInt(GLASS_TRASH_IMAGES.length)];
            default: return "";
        }
    }
    
    public static boolean isNearEntity(float x, float y, Entity entity, float minDistance) {
        if (entity == null) return false;
        float dx = x - entity.getX();
        float dy = y - entity.getY();
        return Math.sqrt(dx * dx + dy * dy) < minDistance;
    }

    public static void handlePushFromStaticObject(Collidable movingEntity, Collidable staticEntity) {
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
    
    public static float[] getRandomPosition(float margin) {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        float x = (float) (Math.random() * (virtualWidth - 2 * margin)) + margin;
        float y = (float) (Math.random() * (virtualHeight - 2 * margin)) + margin;
        
        return new float[]{x, y};
    }
    
    public static float[] getScreenCenter() {
        float virtualWidth = BackgroundRenderer.VIRTUAL_WIDTH;
        float virtualHeight = BackgroundRenderer.VIRTUAL_HEIGHT;
        return new float[]{virtualWidth / 2f, virtualHeight / 2f};
    }
}
