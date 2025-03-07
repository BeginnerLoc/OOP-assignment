package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class MovementManager {
    private final List<Movable> movingEntities = new ArrayList<>();
    private final List<AIMovable> AIentities = new ArrayList<>();

    public MovementManager() {}

    /** Updates the position of all moving entities */
    public void updatePositions() {
        for (Movable entity : movingEntities) {
            entity.move();
        }
    }

    /** Applies gravity to entities */
    public void followWorldRule(int gravity) {
        for (Movable entity : movingEntities) {
            Entity entity1 = (Entity) entity;
            if (entity1.getY() > gravity) {
                entity1.setY(entity1.getY() - (entity1.getSpeed() / 2));
            }
        }
    }

    /** Updates AI-controlled entity movement */
    public void followEntity() {
        for (AIMovable entity : AIentities) {
            entity.followEntity();
        }
    }

    /** Returns all moving entities */
    public List<Movable> getMovingEntities() {
        return movingEntities;
    }

    /** Adds a single moving entity */
    public void addMovingEntity(Movable entity) {
        if (entity != null) {
            movingEntities.add(entity);
        }
    }

    /** Adds multiple moving entities */
    public void addAllMovingEntities(List<Movable> entities) {
        movingEntities.addAll(entities);
    }

 
    /** Adds a single AI-controlled entity */
    public void addAIEntity(AIMovable entity) {
        AIentities.add(entity);
    }

    /** Adds multiple AI-controlled entities */
    public void addAllAIEntities(List<AIMovable> entities) {
        AIentities.addAll(entities);
    }


    /** Removes multiple AI-controlled entities */
    public void removeAllAIEntities(List<AIMovable> entities) {
        AIentities.removeAll(entities);
    }
    
    public void dispose() {
    	AIentities.clear();
    	movingEntities.clear();
    }
    
}
