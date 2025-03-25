package game.entities;

import com.badlogic.gdx.graphics.Color;

public enum RecyclingItemType {
    METAL(Color.YELLOW, "metal"),
    PAPER(Color.BLUE, "paper"),
    PLASTIC(Color.RED, "plastic"),
    GENERAL_WASTE(Color.GREEN, "general");

    private final Color color;
    private final String name;

    RecyclingItemType(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public static RecyclingItemType getRandomType() {
        RecyclingItemType[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}
