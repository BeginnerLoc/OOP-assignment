package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.math.Vector2;

public interface Movable {
	
	void movement(float deltaTime, Vector2 direction);

	float getX();
    void setX(float x);
    float getY();
    void setY(float y);
    void translate(float dx, float dy);

}




