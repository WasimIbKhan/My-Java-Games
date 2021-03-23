package graphics;


public class Screen {
	
	//gives us the maps width and height, as well as imformation on colors on bits of data in arrays and an array of tiles
	public static final int MAP_WIDTH=64,MAP_WIDTH_MASK=MAP_WIDTH-1;
	public int[] tiles = new int[MAP_WIDTH*MAP_WIDTH];
	public int[] colors = new int[MAP_WIDTH*MAP_WIDTH*4];
	
	public static final byte BIT_MIRROR_X = 0x01,BIT_MIRROR_Y = 0x02;
	
	public int[] pixels;
	
	//have offset values to offset the screen
	public int xOffset=0;
	public int yOffset=0;
	
	//declare variables for the width and height of the screen
	public int width;
	public int height;
	
	public SpriteSheet sheet;
	
	//constructor for the data, to set the data
	public Screen(int width,int height, SpriteSheet sheet) {
		this.width=width;
		this.height=height;
		this.sheet=sheet;
		
		pixels = new int[height*width];
	}
	
	//sets the offSets for our screen
	 public void setOffset(int xOffset, int yOffset) {
	        this.xOffset = xOffset;
	        this.yOffset = yOffset;
	    }
	
	 //just a method that needs to be there
	 public void render(int xPos, int yPos, int tile, int color) {
		 render(xPos,yPos,tile,color,0x00,1);
	 }
	
	 //method to render image on screen
	public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {
		//keeps the screen on bounds
		xPos -= xOffset;
		yPos -=yOffset;
		
		//enables mirroring of sprites, for bit mapping
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		
		//provides position and scale
		int scaleMap = scale -1;
		int xTile = tile % 32;	//ensures the number will always be between 0 to 32
		int yTile = tile/32;		//for y we will divide by 32 as each different width is percent 32
		int tileOffset =(xTile<<3) +(yTile <<3) *sheet.width;		//we shift each 8 pixel tile by 3, as each tile piexel is 8 and 2^3 is 8.
		
		for(int y=0; y<8; y++) {
			int ySheet=y;
			if(mirrorY) ySheet =7-y; //enables mirroring in the y axis
			int yPixel = y +yPos + (y* scaleMap) -((scaleMap << 3)/2);	//centres the pixels
			for(int x=0; x<8; x++) {
				int xSheet=x;
				if(mirrorX) xSheet =7-x;	//enables mirroring on the x axis
				int xPixel = x+xPos +(x*scaleMap) -((scaleMap <<3)/2);	//centres the pixels
				int col =(color >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset]*8)) &255;		//identifies tile and provides color data and where in the sheet we are
				if(col <255) {
					for(int yScale =0; yScale < scale; yScale++) {
						if(yPixel + yScale <0 || yPixel + yScale>=height) continue;		//ensures in bounds of the actual screen
						for(int xScale =0; xScale < scale; xScale++) {
							if(xPixel + xScale <0 || xPixel + xScale>=width) continue;		//ensures i bounds of the actual screen
							pixels[(xPixel+ xScale) +(yPixel +yScale)*width]=col;	//thats going to set the pixel data up top
						}
					}
				}
			}
		}
	}
}

