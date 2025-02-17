package io.github.some_example_name.lwjgl3;

public interface PlayerMovable extends Movable {
    void jump(int height);
    void sprint(boolean isSprinting);
    void dash();
}
