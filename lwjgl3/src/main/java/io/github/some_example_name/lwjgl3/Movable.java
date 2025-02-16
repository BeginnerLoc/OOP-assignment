package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.math.Vector2;

public interface Movable {
    void move();                   
    void setDirection(float dx, float dy);  
    void stop();              
}



