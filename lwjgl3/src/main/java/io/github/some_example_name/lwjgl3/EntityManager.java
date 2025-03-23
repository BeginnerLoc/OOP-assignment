package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EntityManager {
    private final List<Entity> entityList = new ArrayList<>();
    private final List<Word> wordList = new ArrayList<>();
    private final SpriteBatch sb;
    private final ShapeRenderer sr;
    private BitmapFont font;
    private Viewport viewport;

    public EntityManager() {
        this.sb = ServiceLocator.get(SpriteBatch.class);
        this.sr = ServiceLocator.get(ShapeRenderer.class);
        this.font = new BitmapFont();
        if (sb == null || sr == null) {
            throw new IllegalStateException("SpriteBatch or ShapeRenderer not found in ServiceLocator. Make sure they are registered before using EntityManager.");
        }
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void addEntity(Entity entity) {
        if (entity != null) {
            entityList.add(entity);
        }
    }
    
    public void removeEntity(Entity entity) {
        if (entity != null) {
            entityList.remove(entity);
        }
    }
    
    public void addWord(Word word) {
        if (word != null) {
            wordList.add(word);
        }
    }

    public void draw() {
        if (viewport == null || viewport.getCamera() == null) {
            System.out.println("Warning: Viewport or camera is null in EntityManager.draw()");
            return;
        }

        viewport.apply();

        // Set projection matrices for consistent rendering
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sb.setProjectionMatrix(viewport.getCamera().combined);

        // First draw shapes
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity : new ArrayList<>(entityList)) {
            entity.draw(sr);
        }
        sr.end();
        
        // Then draw sprites and text
        sb.begin();
        // Draw entities that use SpriteBatch
        for (Entity entity : new ArrayList<>(entityList)) {
            entity.draw(sb);
        }
        
        // Draw text - now the Word class handles its own scaling
        for (Word word : new ArrayList<>(wordList)) {
            word.draw(sb);
        }
        sb.end();
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void update() {
        // Add logic if needed
    }

    public void dispose() {
        // Dispose of each entity's resources
        for (Entity entity : new ArrayList<>(entityList)) {
            try {
                entity.dispose();
            } catch (Exception e) {
                // Log error but continue cleanup
                System.err.println("Error disposing entity: " + e.getMessage());
            }
        }
        entityList.clear();

        // Dispose of word resources
        for (Word word : new ArrayList<>(wordList)) {
            try {
                word.dispose();
            } catch (Exception e) {
                System.err.println("Error disposing word: " + e.getMessage());
            }
        }
        wordList.clear();

        // Dispose of font
        if (font != null) {
            font.dispose();
            font = null;
        }
    }
    
    public void clearAllEntities() {
        entityList.clear();
        wordList.clear();
    }
    
    public List<Entity> getEntities() {
        return new ArrayList<>(entityList);
    }
}
