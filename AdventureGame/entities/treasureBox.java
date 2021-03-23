package entities;

import Level.Level;
import graphics.Screen;

//this will store only positions
public abstract class treasureBox extends Entity {
	
	//position of the treasure box sprite
	public static int xTileClose=8;
	public static int yTileClose=28;
	
	public static int xTileOpen=10;
	public static int yTileOpen=28;
	
	public treasureBox(Level level) {super(level);}
	public abstract void tick();
	public abstract void render(Screen screen);
	
	
		
	
	

}
