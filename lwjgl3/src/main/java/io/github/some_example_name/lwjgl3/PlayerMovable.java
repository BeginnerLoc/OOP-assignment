package io.github.some_example_name.lwjgl3;

public interface PlayerMovable extends Movable {
    void jump();
    void sprint(boolean isSprinting);
    void dash();
}
