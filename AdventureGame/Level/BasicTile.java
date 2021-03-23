package Level;

import graphics.Screen;

public class BasicTile extends Tile{

    protected int tileId;
    protected int tileColor;
    
    //constructor for basic tile such as grass where player will move through it
    public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, false, false, levelColor);
        this.tileId = x + y *32;
        this.tileColor = tileColor;
}

    
    //renders basic tile to screen
    public void render(Screen screen, Level level, int x, int y) {
            screen.render(x, y, tileId, tileColor,0x00,1);
    }


    @Override
    public void tick() {
        
        
    }

}
