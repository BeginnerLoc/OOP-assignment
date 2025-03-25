package game_engine;

import com.badlogic.gdx.math.Vector2;

public interface Movable {
    void move();                   
    void setDirection(float dx, float dy);  
    void stop();              
}



