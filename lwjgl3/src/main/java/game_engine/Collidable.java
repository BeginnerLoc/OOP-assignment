package game_engine;

import java.util.function.Consumer;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getBounds();
	void onCollision(Collidable other);
	void setCollisionAction(Consumer<Collidable> action);
}
