package game_engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity {
    private float x;
    private float y;
    private float width;   
    private float height;   
    private float speed;
    private Color color;
    private int renderPriority; // Higher number means render later (on top)
    
    public Entity() {
        this.renderPriority = 0;
        this.width = 0;
        this.height = 0;
    }
    
    public Entity(float x, float y, Color color, float speed, int renderPriority) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.renderPriority = renderPriority;
        this.width = 0;
        this.height = 0;
    }

    public Entity(float x, float y, float width, float height, Color color, float speed, int renderPriority) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
        this.renderPriority = renderPriority;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRenderPriority() {
        return renderPriority;
    }

    public void setRenderPriority(int priority) {
        this.renderPriority = priority;
    }
    
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    
    public void draw(ShapeRenderer rd) {
    }
    
    public void draw(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
