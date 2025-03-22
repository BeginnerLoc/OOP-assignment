package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
    private final List<Entity> entityList = new ArrayList<>();
    private final List<Word> wordList = new ArrayList<>();
    private final SpriteBatch sb;
    private final ShapeRenderer sr;
    private BitmapFont font;

    public EntityManager() {
        this.sb = ServiceLocator.get(SpriteBatch.class);
        this.sr = ServiceLocator.get(ShapeRenderer.class);
        this.font = new BitmapFont();
        if (sb == null || sr == null) {
            throw new IllegalStateException("SpriteBatch or ShapeRenderer not found in ServiceLocator. Make sure they are registered before using EntityManager.");
        }
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }
    
    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }
    
    public void addWord (Word word) {
    	wordList.add(word);
    }

    public void draw() {
        // First draw shapes
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity : entityList) {
            entity.draw(sr);
        }
        sr.end();
        
        // Then draw sprites and text
        sb.begin();
        // Draw entities that use SpriteBatch
        for (Entity entity : entityList) {
            entity.draw(sb);
        }
        
        // Draw text
        for (Word word : wordList) {
            float scale = word.getScale();
            if (scale <= 0) scale = 1;
            font.getData().setScale(scale);
            font.setColor(word.getColor());
            font.draw(sb, word.getWord(), word.getX(), word.getY());
        }
        sb.end();
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void update() {
        // Add logic if needed
    }

    public void dispose() {
        entityList.clear();
        wordList.clear();
    }
    
}
