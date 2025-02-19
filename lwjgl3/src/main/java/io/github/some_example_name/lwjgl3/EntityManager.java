package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

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
        this.font =  new BitmapFont();
        if (sb == null || sr == null) {
            throw new IllegalStateException("SpriteBatch or ShapeRenderer not found in ServiceLocator. Make sure they are registered before using EntityManager.");
        }
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }
    
    public void addWord (Word word) {
    	wordList.add(word);
    }

    public void draw() {
        sb.begin();
        for (Entity entity : entityList) {
            entity.draw(sb);
        }
        for (Word word : wordList) {
        	font.draw(sb, word.getWord(), word.getX(), word.getY());
        }
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity : entityList) {
            entity.draw(sr);
            
        }
       
       
        sr.end();
        
    }

    public void update() {
        // Add logic if needed
    }

    public void dispose() {
        entityList.clear();
        wordList.clear();
    }
    
}
