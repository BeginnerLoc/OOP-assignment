package game.movement;

import game.utils.EnemyMovePattern;

/**
 * Factory for creating movement strategies based on the movement pattern
 */
public class MovementStrategyFactory {
    
    /**
     * Creates a movement strategy instance based on the given pattern
     * 
     * @param pattern The movement pattern to create a strategy for
     * @return The corresponding movement strategy implementation
     */
    public static MovementStrategy createStrategy(EnemyMovePattern pattern) {
        switch (pattern) {
            case DIRECT:
                return new DirectMovementStrategy();
            case ZIGZAG:
                return new ZigzagMovementStrategy();
            case FLANKING:
                return new FlankingMovementStrategy();
            case CIRCULAR:
                return new CircularMovementStrategy();
            case RANDOM_ANGLES:
                return new RandomAnglesMovementStrategy();
            default:
                // Default to direct movement if pattern is unrecognized
                return new DirectMovementStrategy();
        }
    }
}