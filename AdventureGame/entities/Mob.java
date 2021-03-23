package entities;

import Level.Level;
import Level.Tile;
/*
 * We want to have all entities to have the following methods for sure however we
 * 
 */
import Level.collideTile;


public abstract class Mob extends Entity{

	protected String name;
	protected int speed;
	protected int numSteps;
	protected boolean isMoving;
	protected int movingDir =1;
	protected int scale =1;
	
	
	//constructor for player
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name=name;
		this.x=x;
		this.y=y;
		this.speed=speed;
	}
	
	//function used to move the player
	public void move(int xa, int ya) {
		if(xa !=0 && ya !=0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		//directions north,east,south and west
		if (!hasCollided(xa, ya)) {
			if (ya <0) movingDir=0;
			if (ya >0) movingDir=1;
			if (xa <0) movingDir=2;
			if (xa >0) movingDir=3; 
			x +=xa*speed;
			y +=ya*speed;
		}
	}
	
	//mobs can be different pixels therefore we do not want it to be linked to all mobs, however all mobs must have a hasColided function
	public abstract boolean hasCollided(int xa, int ya);
	
	//relative movement method to see if the tile is solid
	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if(level ==null) {
			return false;
		}
		//then checks if the previous tile the sprite was on and the current tile is different
		Tile lastTile =level.getTile((this.x +x)>>3, (this.y +y) >> 3); //finds the position of the last tile the player is standing on
		Tile newTile = level.getTile((this.x + x +xa) >> 3, (this.y + y +ya) >>3);	//finds the current position the player is standingon
		if(!lastTile.equals(newTile) && newTile.isSolid()) {
			return true; //verifies new tile is solid
		}
		return false;
	}
	
	//relative movement method to see if the tile is solid
	protected boolean isTresureTile(int xa, int ya, int x, int y) {
		if(level ==null) {
			return false;
		}
		//then checks if the previous tile the sprite was on and the current tile is different
		Tile lastTile =level.getTile((this.x +x)>>3, (this.y +y) >> 3); //finds the position of the last tile the player is standing on
		Tile newTile = level.getTile((this.x + x +xa) >> 3, (this.y + y +ya) >>3);	//finds the current position the player is standingon
		if(!lastTile.equals(newTile) && newTile.isSolid() && newTile.isTresure()) {
			collideTile.tresureFound =true;//verifies if its a tile used for collision detection
			return true; //verifies new tile is solid
		}
		return false;
	}
	//returns name of the user
	public String getName() {
		return name;
	}
}
