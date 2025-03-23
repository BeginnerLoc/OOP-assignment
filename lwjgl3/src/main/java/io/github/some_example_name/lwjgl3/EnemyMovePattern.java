package io.github.some_example_name.lwjgl3;

public enum EnemyMovePattern {
    DIRECT,        // Moves directly towards the player
    ZIGZAG,        // Moves in a zigzag pattern towards the player
    FLANKING,      // Tries to approach from the side
    CIRCULAR,      // Moves in a circular pattern while approaching
    RANDOM_ANGLES  // Randomly changes angles while pursuing
}