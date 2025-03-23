package game_engine;

public interface PlayerMovable extends Movable {
    void jump(int height);
    void sprint(boolean isSprinting);
    void dash();
}
