package io.github.some_example_name.lwjgl3;

public class GameState {
    private static int score = 0;
    private static float playerBaseSpeed = 3.0f;
    private static float enemyBaseSpeed = 0.5f;
    private static float timeElapsed = 0;

    public static void reset() {
        score = 0;
        playerBaseSpeed = 3.0f;
        enemyBaseSpeed = 0.5f;
        timeElapsed = 0;
    }

    public static void updateScore(boolean correct) {
        score += correct ? 10 : -5;
        if (score < 0) score = 0;
    }

    public static int getScore() {
        return score;
    }

    public static float getPlayerSpeed() {
        return playerBaseSpeed;
    }

    public static float getEnemySpeed() {
        return Math.min(enemyBaseSpeed + (timeElapsed / 30), playerBaseSpeed - 1);
    }

    public static void updateTime(float delta) {
        timeElapsed += delta;
    }
}
