package game.utils;

public enum EnemyMovePattern {
    DIRECT,        // Moves directly towards the player
    ZIGZAG,        // Moves in a zigzag pattern towards the player
    FLANKING,      // Tries to approach from the side
    CIRCULAR,      // Moves in a circular pattern while approaching
    RANDOM_ANGLES  // Randomly changes angles while pursuing
}