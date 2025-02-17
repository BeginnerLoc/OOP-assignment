package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class MovementManager {
    private final List<Movable> movingEntities = new ArrayList<>();
    private final List<AIMovable> AIentities = new ArrayList<>();

    public MovementManager() {}

    public void updatePositions() {
        for (Movable entity : movingEntities) {
            entity.move();
        }
    }
    
    public void followWorldRule(int gravity) {
    	for (Movable entity: movingEntities) {
    		Entity entity1 =  (Entity) entity;
    		if(entity1.getY() > gravity) {
    			entity1.setY(entity1.getY() - (entity1.getSpeed()/10));
    		}
    	}
    }
    
    public void followEntity() {
        for (AIMovable entity : AIentities) {
        	entity.followEntity();
        }
    }

    public List<Movable> getMovingEntities() {
        return movingEntities;
    }

    public void addMovingEntity(Movable entity) {
        if (entity != null) {
            movingEntities.add(entity);
        }
    }

    public void removeMovingEntity(Movable entity) {
        movingEntities.remove(entity);
    }
    
    public void addAIEntities(AIMovable entity) {
    	AIentities.add(entity);
    }
}
