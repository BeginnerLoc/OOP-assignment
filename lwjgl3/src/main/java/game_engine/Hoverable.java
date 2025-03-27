package game_engine;

public interface Hoverable {
    void onHoverEnter();
    void onHoverExit();
    boolean isHovered(float worldX, float worldY);
}