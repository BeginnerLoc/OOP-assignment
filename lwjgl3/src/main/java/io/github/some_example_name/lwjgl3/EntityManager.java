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
		for (Entity entity: this.entityList) {
			entity.draw(sb);
			entity.draw(sr);
		}
	}
	
	public void movement() {
		
	}
	
	public void update() {
		
	}
}
