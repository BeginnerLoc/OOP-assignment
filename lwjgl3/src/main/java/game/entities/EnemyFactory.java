package game.entities;

import game.utils.EnemyMovePattern;

public class EnemyFactory {

    public static Enemy createEnemy(float x, float y, String texturePath, float speed, 
                                  EnemyMovePattern pattern, Float width, Float height) {
        if (width != null && height != null) {
            return new Enemy(x, y, texturePath, speed, width, height, pattern);
        } else {
            return new Enemy(x, y, texturePath, speed, pattern);
        }
    }
    
    /**
     * Convenience method for creating an enemy with default dimensions
     */
    public static Enemy createEnemy(float x, float y, String texturePath, float speed, EnemyMovePattern pattern) {
        return createEnemy(x, y, texturePath, speed, pattern, null, null);
    }
}