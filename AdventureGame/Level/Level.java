package Level;


import java.util.List;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import entities.Entity;
import graphics.Screen;

public class Level {
    
    //tiles is an array of IDs for a specific coordinate that resides in it, as well as the width and height of tile screen
    private byte[] tiles;
    public int width;
    public int height;
    private String imagePath;
    private BufferedImage image;
    
    public List<Entity> entities = new ArrayList<Entity>();
    
    //constructor for the level class
    public Level(String imagePath) {
        if(imagePath != null) {
            this.imagePath =imagePath;
            this.loadLevelFromFile();
        } else {
            //creates an array of new board size
             tiles = new byte[width * height];
             this.width = 64;
             this.height = 64;
             this.generateLevel();
        }
           
    }
    //loads the level image to the screen and uses it to genrate a level
    private void loadLevelFromFile() {
        try {
            this.image = ImageIO.read(Level.class.getResource(this.imagePath));
            this.width = image.getWidth();
            this.height = image.getHeight();
            tiles = new byte[width*height];
            this.generateLevel();
            this.loadTiles();
        }   catch(IOException e){
            e.printStackTrace();
        }
        
    }
    //loads the tiles to the screeen
    private void loadTiles() {
        int [] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                tileCheck :for(Tile t: Tile.tiles) {
                    if(t !=null && t.getLevelColor() == tileColors[x + y *width]) {
                        this.tiles[x + y *width] =t.getId();
                        break tileCheck;
                    }
                }
            }
        }
    }
    
    //method to create content for level such as stones and grass
    public void generateLevel() {
      for (int y = 0; y < height; y++) {
           for (int x = 0; x < width; x++) {
               tiles[x + y * width] = Tile.GRASS.getId();
               
               if( (x==2 && y==10) || (x==3 && y==10)) {
                   tiles[x + y * width] = Tile.treasure.getId();
                   Tile.treasureID[0]=false;
               }
               if( (x==6 && y==4) || (x==7 && y==4)) {
                   tiles[x + y * width] = Tile.treasure.getId();
                   Tile.treasureID[1]=false;
               }
               
           }
       }
    }
    
    
    //will be used later on
    public void alterTile(int x, int y, Tile newTile) {
        this.tiles[x + y * width] = newTile.getId();
        image.setRGB(x, y, newTile.getLevelColor());
    }
    
    //enables the player to appear on screen and move, this method calls the tick method from the player class subclass of mob subclass of entities
    public void tick() {
        for( Entity e: entities) {
            e.tick();
        }
        for(Tile t: Tile.tiles) {
            if(t==null) {break;}
            t.tick();
        }
    }
    //xOffset and yOffset are the players position rendered on screen
    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        //checks if the positions of the player does not go out of bounds, if near the boundry
        //this is done by causing the screen camera from moving
        //player is stopped
      if (xOffset < 0)
          xOffset = 0;
      if (xOffset > ((width << 3) - screen.width))
          xOffset = ((width << 3) - screen.width);
      if (yOffset < 0)
          yOffset = 0;
      if (yOffset > ((height << 3) - screen.height))
          yOffset = ((height << 3) - screen.height);

          screen.setOffset(xOffset, yOffset);
      //creates the tile on screen      
          
          
          for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
              for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
                      getTile(x, y).render(screen, this, x << 3, y << 3);
              }
          }
    }
    
    //enable the rendering of entities, or objects
    public void renderEntities(Screen screen) {
        for( Entity e: entities) {
            e.render(screen);
        }
    }
    //checks if tile is beyond the screen if so gives a void tile
    public Tile getTile(int x, int y) {
        if (0 > x || x >= width || 0 > y || y >= height)
            return Tile.VOID;
        return Tile.tiles[tiles[x + y * width]];
    }
    
    //enables entities to be rendered on screen
    public void addEntity(Entity entity) {
        this.entities.add(entity);
        
    }
}
