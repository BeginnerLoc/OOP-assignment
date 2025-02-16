package io.github.some_example_name.lwjgl3;

import java.util.function.Consumer;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getBounds();
	void onCollision(Collidable other);
	void setCollisionAction(Consumer<Collidable> action);
}
