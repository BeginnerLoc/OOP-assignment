package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class EntityManager {
	private final List<Entity> entityList = new ArrayList<Entity>();
	
    
	public void addEntity(Entity entity) {
		entityList.add(entity);		
		
	}
	
	public void draw(SpriteBatch sb, ShapeRenderer sr) {
		sb.begin();
        for (Entity entity : entityList) {
            entity.draw(sb);
        }
        sb.end();
        
        sr.begin(ShapeRenderer.ShapeType.Filled); 
        for (Entity entity : entityList) {
            entity.draw(sr);
        }
        sr.end();
	}
	
	public void update() {
		
	}
}
