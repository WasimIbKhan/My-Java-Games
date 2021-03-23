package entities;
import Level.Level;
import Level.Tile;
import Level.collideTile;
import graphics.Colors;
import graphics.Font;
import graphics.Screen;
import AdventureGame.InputHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Player extends Mob {

	//we want the player to accept an input
private InputHandler input;
private int color = Colors.get(-1, 111, 145, 543);
//private int color2 = Colors.get(-1, 111, 654321, 541);
private int scale =1;
protected boolean isSwimming =false;
private int tickCount=0;
private String username;
private playerStats player;
public Player(Level level, int x, int y, InputHandler input, String username, playerStats play) {
	super(level,"Player", x, y, 1);
	this.input=input;
	this.username=username;
	this.player=play;
}

@Override
//function that causes the user to move
public void tick() {
	int xa=0;
	int ya=0;
	
	if(input.up.isPressed()) {
		ya--;
	}
	if(input.down.isPressed()) {
		ya++;
	}
	if(input.left.isPressed()) {
		xa--;
	}
	if(input.right.isPressed()) {
		xa++;
	}

	if(xa!=0 || ya !=0) {
		move(xa, ya);
		isMoving=true;
	}
	else {
		isMoving = false;
	}
	if((level.getTile(this.x>>3,this.y>>3)).getId() ==3) {
		isSwimming=true;
	}
	if(isSwimming && level.getTile(this.x >> 3, this.y >>3).getId()!=3) {
		isSwimming=false;
	}
	tickCount++;
}

@Override
public void render(Screen screen) {
    int xTile=0;
    int yTile=28;
    int walkingSpeed=3;
    
    //shows the display walking speed
    //gets a number
    int flipTop = (numSteps >> walkingSpeed)&1;
    int flipBottom =(numSteps >> walkingSpeed) &1;
    
    //shifts the tiles to pick sprite
    if(movingDir ==1) {
    	xTile +=2;
    } 
    //changes only a specific flip depending on the situation
    else if (movingDir >1) {
    	//makes this our defult sprite
    	xTile +=4 + ((numSteps >> walkingSpeed) &1)*2;
    	//gets a number between 0 and 1, giving only two phases
    	flipTop = (movingDir -1)%2;
    }
    
    //enable the individual to be placed in the middle of the screen
    int modifier =8*scale;
    int xOffset = x-modifier/2;
    int yOffset = y-modifier/2 -4;
    if(isSwimming) {
    	int waterColor=0;
    	yOffset+=4;
    	if(tickCount%60 <15) {
    		yOffset -=1;
    		waterColor= Colors.get(-1, -1, 225, -1);
    	}else if(15 <=tickCount %60 && tickCount%60 <30) {
    		yOffset -=1;
    		waterColor= Colors.get(-1, 225, 115, -1);
    	} else if(30 <= tickCount %60 && tickCount %60 <45) {
    		yOffset -=1;
    		waterColor= Colors.get(-1, 115, -1, 225);
    	} else {
    		yOffset -=1;
    		waterColor= Colors.get(-1, 225, 115, -1);
    	}
    	screen.render(xOffset, yOffset +3, 0+27 *32, waterColor, 0x00, 1);
    	screen.render(xOffset +8, yOffset +3, 0+27 *32, waterColor, 0x01, 1);
    }
    
    
    //renders sprite to screen, this is the upper body
    screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile *32, color,flipTop,scale);
    screen.render(xOffset + modifier -(modifier * flipTop), yOffset, (xTile + 1) + yTile *32, color ,flipTop, scale);
	
    if(!isSwimming) {
    	//this is the rendering of the lower body
    	screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) *32, color, flipBottom, scale);
    	screen.render(xOffset  + modifier -(modifier * flipBottom), yOffset  + modifier, (xTile + 1) + (yTile +1) *32, color, flipBottom, scale);
    	
    	}
    	//renders user name onto the player
    if(username != null ) {
    	Font.render(username,screen,xOffset -(username.length()/2*8), yOffset-10,Colors.get(-1, -1, -1, 555),1 );
    }
    //checks if the user has colided with the treasure box tile
    if(!collideTile.tresureBoxOpened && collideTile.tresureFound ) {
    	Font.render(player.currentWealth() +"",screen,xOffset +60, yOffset-40,Colors.get(-1, -1, -1, 555),1 );//displays wealth on screen
    	player.increaseWealth(30);//increases wealth of the user
    	collideTile.tresureBoxOpened=true;//acesss the records and changes it to true
    	try {
    	BufferedWriter score = new BufferedWriter(new FileWriter("C:/Users/wasim/Documents/Java Games/AdventureGame/Score.txt",true));
    	score.write(""+player.currentWealth());//Write out a string to the file
    	score.close();//flushes and closes the stream 
    	} catch (IOException e) {
    		 e.printStackTrace();
    	} 
    	
    } else {
    	Font.render(player.currentWealth() +"",screen,xOffset +60, yOffset-40,Colors.get(-1, -1, -1, 555),1 );//renders the users wealth to screen
    	try {
    	BufferedWriter score = new BufferedWriter(new FileWriter("C:/Users/wasim/Documents/Java Games/AdventureGame/Score.txt",true));
    	score.write(player.currentWealth());//Write out a string to the file
    	score.close();//flushes and closes the stream 
    	} catch (IOException e) {
    		 e.printStackTrace();
    	} 
    }

	

}

@Override
//method for collision
public boolean hasCollided(int xa, int ya) {
	int xMin =0;
	int xMax= 7;
	int yMin =3;
	int yMax= 7;
	
	//uses pixel position on sprite for collision detection
	//checks if its a treasure tile if not checks if its a solid tile
	for( int x =xMin; x < xMax; x++) {
		if(isTresureTile(xa,ya, x, yMin)) {
			collideTile.tresureFound=true;
			return true;
		}
		if(isSolidTile(xa,ya, x, yMin)) {
			return true;
		}
	}
	for( int x =xMin; x < xMax; x++) {
		if(isTresureTile(xa,ya, x, yMax)) {
			collideTile.tresureFound=true;
			return true;
		}
		if(isSolidTile(xa,ya, x, yMax)) {
			return true;
		}
	}
	for( int y=yMin; y< yMax; y++) {
		
		if(isTresureTile(xa,ya, xMin, y)) {
			collideTile.tresureFound=true;
			return true;
		}
		if(isSolidTile(xa,ya, xMin, y)) {
			return true;
		}
	}
	for( int y =yMin; y < yMax; y++) {
		if(isTresureTile(xa,ya, xMax, y)) {
			collideTile.tresureFound=true;
			return true;
		}
		if(isSolidTile(xa,ya, xMax, y)) {
			return true;
		}
	}
	
	
	return false;
}



   
}
