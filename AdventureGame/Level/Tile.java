package Level;



import Level.Level;
import graphics.Colors;
import graphics.Screen;

public abstract class Tile {
	
	//current maximum number of tiles and also set up the contents inside the tile
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(0, 0,0, 0),0xFF000000);//gives it a clear color
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(-1, 333, -1, -1),0xFF555555);//tile gives a grey color
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, -1),0xFF00FF00);//gren color
	public static final Tile WATER = new AnimatedTile(3,new int[][] {{0,5},{1,5},{2,5},{1,5}},Colors.get(-1, 004, 115, -1),0xFF0000FF,500);
	public static final Tile treasure = new collideTile(4,3,0,Colors.get(-1, 111, 654321, 541), 0xFF00FF00);
	//all objects we may include in the future
    protected byte id;
    protected boolean solid;
    protected boolean tresure;
    private int levelColor;
    public static boolean  treasureID []= new boolean [10];
    
    public Tile(int id, boolean isSolid, boolean isTresure, int levelColor) {
            this.id = (byte) id;
            //checks if there is a tile with that specific ID
            if (tiles[id] != null) {
            	//if there is we throw an exception
                    throw new RuntimeException("Duplicate tile id on " + id);
            }
            //initialise variables
            solid = isSolid;
            tresure = isTresure;
            this.levelColor=levelColor;
            tiles[id] = this;
    }
    
    //all neccesary method we will need in the sub class
    public byte getId() {
            return id;
    }

    public boolean isSolid() {
            return solid;
    }

    public boolean isTresure() {
            return tresure;
    }
    
    public int getLevelColor() {
    	return levelColor;
    }
    
    //blueprint for subclass BasicTile
    public abstract void render(Screen screen, Level level, int x, int y);
    
    public abstract void tick();
	
}
