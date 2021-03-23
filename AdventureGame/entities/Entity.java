package entities;

import Level.Level;
import graphics.Screen;
//blueprint class for all sub classes, thats why its abstract
public abstract class Entity {
	
	public int x,y;
	protected Level level;
	
	//constructor for entity
	public Entity(Level level) {
		init(level);
	}
	public final void init(Level level) {
		this.level=level;
	}
	//we just need these methods for all sub classes
	public abstract void tick();
	
	public abstract void render(Screen screen);
}
