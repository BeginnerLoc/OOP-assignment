package assignment;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private final List<Collidable> collidables = new ArrayList<>();

    public void register(Collidable collidable) {
        collidables.add(collidable);
    }

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
}
