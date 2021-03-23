package AdventureGame;
/*Wasim Khan
* This is a 2D game I have created for my mini-project
* */

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.*;
import Level.Level;
import entities.Player;
import entities.playerStats;
import entities.treasureBox;
import graphics.Colors;
import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Font;

public class Game extends Canvas implements Runnable{

	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=150, HEIGHT =WIDTH/12*9,SCALE=3;
	public static final String NAME="Game";
	public int tickCount=0;
	public boolean running = false;
	
	//we have a pixel variable which we can update whenever we want, an the variable will update that image
	//all variables set are going back into the image here
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);  //basic RGB image
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();  //gives an array of int which are pixels, updates that pixel array wih whatever we want, and it will continue to update
	private int [] colors = new int[6*6*6];  //enables only 6 different shades, herefore 6*6*6 corresponding to RGB values
	public Graphics g;
	public BufferStrategy bs;
	
	//create neccessary objects of different types for the programs
	private Screen screen;
	public Level level;
	public InputHandler input;
	public Player player;
	public playerStats play;
	public treasureBox box;
	private JFrame jframe;
	public boolean imageDrawn;
	
	//class constructor to set up the JFrame
	public Game() {
		
		//set the minimum,maximum and preferred size of the canvas
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		//create the JFrame object for the game
		jframe= new JFrame();
		jframe.setSize( WIDTH, HEIGHT);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setLayout(new BorderLayout());
		jframe.setVisible(true);
		
		jframe.add(this, BorderLayout.CENTER); //we want the Canvas to be centred in the JFrame
		jframe.pack(); //set the frame so they are at or set at the preferred size
		jframe.setLocationRelativeTo(null); //I do not want the  location to be relative to anything
	}
	//Useful for creating an Applet, place it in a browser and make it downloadable
	//create a start thread
	private synchronized void start() {
		//needs to be an instance of runnable
		running=true;
		new Thread(this).start();
	}
	
	//create a stop thread
	private synchronized void stop() {
		//needs to be an instance of runnable
		running=false;
	}
	
	@Override
	//game engine
	public void run() {
		 long lastTime = System.nanoTime();
         long lastTimer = System.currentTimeMillis();
         double nsPerTick = 1000000000D / 60D;
         double delta = 0;
         int ticks = 0;
         int frames = 0;
         
         //initialises everything
         init();
         
         //ensures everything in this loop continues to run until the window closes
         while (running) {
                 long now = System.nanoTime();
                 delta += (now - lastTime) / nsPerTick;
                 lastTime = now;
                 boolean shouldRender = true;

                 while (delta >= 1) {
                         ticks++;
                         tick();
                         delta -= 1;
                         shouldRender = true;
                 }
                 try {
                         Thread.sleep(2);
                 } catch (InterruptedException e) {
                         e.printStackTrace();
                 }
                 if (shouldRender) {
                         frames++;
                         render();
                 }

                 if (System.currentTimeMillis() - lastTimer >= 1000) {
                         lastTimer += 1000;
                         System.out.println(ticks + " ticks , " + frames
                                         + " frames per second");
                         frames = 0;
                         ticks = 0;
                 }
         }
	}
	
	//initialises all variables
	public void init() {
		
		//fills the color array, providing numbers from 0 to 5 providing shades
		int index=0;
		for(int r=0;r<6;r++) {
			for (int g=0; g<6; g++) {
				for(int b=0; b<6; b++) {
					// we put 255 becuse we do not want some colors to render
					int rr=(r*255/5);
					int gg=(g*255/5);
					int bb=(b*255/5);
					
					//theres going to be 2^8 bits for red blue and green,thats why its shifting by 8 all the time
					colors[index++] = rr <<16| gg << 8| bb;
				}
			}
		}
		
		//create screen, input control system, level,set player stats and add player to the screen
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/res/spriteSheet.png"));
		input = new InputHandler(this);
		level = new Level("/res/Levels/water.png");
		play = new  playerStats(0,"super",0,0);
		player = new Player(level,20,20,input,JOptionPane.showInputDialog(this, "Please enter a username"),play);//enables the users name to apear on screen
		level.addEntity(player);
		
	}
	
	
	//all variables are being set in tick
	public void tick() {
		//identifies current tick count
		tickCount++;
		
		//updates the game constantly importing the pixels from level.tick which calls all tick functions for all mob chraacters
		level.tick();
	}
	//for an image, we use render to overlay the canvas with an image
	
	
	//we are doing triple buffering, the higher the number, the better it will be at reducing image tearing and cross image pixelation
	public void render() {
		//organise image we are buffering
		bs = getBufferStrategy();
		
		//checks if the image even exists
            if (bs == null) {
                    createBufferStrategy(3);
                    return;
            }
            
            //enables the camera to move along the player
            int xOffset = player.x - (screen.width / 2);
            int yOffset = player.y - (screen.height / 2);
            
            //renders tiles and player to screen
            level.renderTiles(screen, xOffset, yOffset);
            level.renderEntities(screen);
            
            //ensures pixels are within bound
            for (int y = 0; y < screen.height; y++) {
                    for (int x = 0; x < screen.width; x++) {
                            int ColourCode = screen.pixels[x + y * screen.width];
                            if (ColourCode < 255) {
                                    pixels[x + y * WIDTH] = colors[ColourCode];
    
                            }
                    }
            }
            
            //displays the image, puts content on screen
            g = bs.getDrawGraphics();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            g.dispose();
            bs.show();
	}
	//begins the program
	public static void main(String[]args) {
		new Game().start();
	}
}
