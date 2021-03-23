package Level;

import entities.treasureBox;
import graphics.Colors;
import graphics.Screen;

public class collideTile extends BasicSolidTile{
	
	public collideTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, x, y, tileColor, levelColor);
		this.tresure=true;
		this.solid=true;
		
	}
	//class variables to check if the trasure box has been found
	public static boolean tresureFound =false;
	public static boolean tresureBoxOpened =false;
	private int color = Colors.get(-1, 111, 654321, 541);
	//initialise the instance variables

	//renders entities to screenthat require collision detection	
	public void render(Screen screen, Level level, int x, int y) {
		
		int modifier =8;
		if(tresureFound) {
			screen.render(15 , 73,treasureBox.xTileOpen + treasureBox.yTileOpen *32, color,0x00,1);
			screen.render(15  + modifier, 73, (treasureBox.xTileOpen +1) + treasureBox.yTileOpen *32, color, 0x00,1);
			screen.render(15 , 73 + modifier, treasureBox.xTileOpen + (treasureBox.yTileOpen +1) *32, color,0x00,1);
			screen.render(15  + modifier, 73 + modifier, (treasureBox.xTileOpen+1)+ (treasureBox.yTileOpen+1) *32, color, 0x00,1);
		} else{
			screen.render(15 , 73,treasureBox.xTileClose + treasureBox.yTileClose *32, color,0x00,1);
			screen.render(15  + modifier, 73, (treasureBox.xTileClose +1) + treasureBox.yTileClose *32, color, 0x00,1);
			screen.render(15 , 73 + modifier, treasureBox.xTileClose + (treasureBox.yTileClose +1) *32, color,0x00,1);
			screen.render(15  + modifier, 73 + modifier, (treasureBox.xTileClose+1)+ (treasureBox.yTileClose+1) *32, color, 0x00,1);
		}
		
		if(tresureFound) {
			screen.render(47 , 27,treasureBox.xTileOpen + treasureBox.yTileOpen *32, color,0x00,1);
			screen.render(47  + modifier, 27, (treasureBox.xTileOpen +1) + treasureBox.yTileOpen *32, color, 0x00,1);
			screen.render(47 , 27 + modifier, treasureBox.xTileOpen + (treasureBox.yTileOpen +1) *32, color,0x00,1);
			screen.render(47  + modifier, 27 + modifier, (treasureBox.xTileOpen+1)+ (treasureBox.yTileOpen+1) *32, color, 0x00,1);
		} else{
			screen.render(47 , 27,treasureBox.xTileClose + treasureBox.yTileClose *32, color,0x00,1);
			screen.render(47  + modifier, 27, (treasureBox.xTileClose +1) + treasureBox.yTileClose *32, color, 0x00,1);
			screen.render(47 , 27 + modifier, treasureBox.xTileClose + (treasureBox.yTileClose +1) *32, color,0x00,1);
			screen.render(47  + modifier, 27 + modifier, (treasureBox.xTileClose+1)+ (treasureBox.yTileClose+1) *32, color, 0x00,1);
		}
		
		
		
		
}
}
