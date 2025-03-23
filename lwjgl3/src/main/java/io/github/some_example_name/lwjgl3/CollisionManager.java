package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private final List<Collidable> collidables = new ArrayList<>();

    /** Registers a single collidable entity */
    public void register(Collidable collidable) {
        collidables.add(collidable);
    }

    /** Registers multiple collidable entities */
    public void addAll(List<Collidable> newCollidables) {
        collidables.addAll(newCollidables);
    }

    /** Removes a single collidable entity */
    public void remove(Collidable collidable) {
        collidables.remove(collidable);
    }

    /** Checks for collisions and calls collision handlers */
    public void checkCollisions() {
        for (int i = 0; i < collidables.size(); i++) {
            Collidable a = collidables.get(i);
            for (int j = i + 1; j < collidables.size(); j++) {
                Collidable b = collidables.get(j);
                if (a.getBounds().overlaps(b.getBounds())) {
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
    }
    
    /**
     * Clears all collidable entities
     */
    public void dispose() {
    	collidables.clear();
    }
    
    /**
     * Returns the current list of collidable entities (as a copy for safe iteration)
     */
    public List<Collidable> getCollidables() {
        return new ArrayList<>(collidables);
    }
    
    /**
     * Checks if an entity is registered for collision
     */
    public boolean isRegistered(Collidable collidable) {
        return collidables.contains(collidable);
    }
}
