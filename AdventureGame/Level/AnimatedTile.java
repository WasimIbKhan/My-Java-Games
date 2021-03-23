package Level;



public class AnimatedTile extends BasicTile {
	
	private int [][] animationTileCoordinates;//coordinates of tiles on screen
	private int currentAnimationIndex;//tile to be animated
	private long lastIterationTime;//the previous time before the delay
	private int animationSwitchDelay;//delay between the switch of tiles
	
	//initialise the instance variables
	public AnimatedTile(int id, int [][] animationCoordination, int tileColor, int levelColor, int animationSwitchDelay) {
		super(id, animationCoordination[0][0], animationCoordination[0][1], tileColor,levelColor);
		this.animationTileCoordinates=animationCoordination;
		this.currentAnimationIndex=0;
		this.lastIterationTime=System.currentTimeMillis();
		this.animationSwitchDelay=animationSwitchDelay;
	}
	
	public void tick() {
		//continues to update the animation
		//moves from one position to another and back in the spriteSheet
		if((System.currentTimeMillis() -lastIterationTime) >= (animationSwitchDelay)) {
			lastIterationTime = System.currentTimeMillis(); //resets the timer
			currentAnimationIndex=(currentAnimationIndex +1) % animationTileCoordinates.length; //keeps it within the bounds of the animated tiles
			this.tileId =(animationTileCoordinates[currentAnimationIndex][0] + (animationTileCoordinates[currentAnimationIndex][1] *32));
			
		}
	}
	
	

}
