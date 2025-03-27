package game.utils;

public enum EnemyMovePattern {
    /**
     * Moves directly toward the player
     */
    DIRECT,
    
    /**
     * Moves in a zigzag pattern toward the player
     */
    ZIGZAG,
    
    /**
     * Tries to approach from the side
     */
    FLANKING,
    
    /**
     * Moves in a circular pattern while approaching
     */
    CIRCULAR,
    
    /**
     * Randomly changes angles while pursuing
     */
    RANDOM_ANGLES
}