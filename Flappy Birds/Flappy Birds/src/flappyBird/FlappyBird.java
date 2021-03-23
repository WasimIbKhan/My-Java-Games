package flappyBird;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;//abstract class for all graphics context that allows an application to draw onto components 
import java.awt.Rectangle;//enables importing rectangle
import java.awt.event.ActionEvent;//indicates that a component-defined action has occurred
import java.awt.event.ActionListener;//parent class responsible in the handling of all action events
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;//imports arraylists
import java.util.Random;

import javax.swing.JFrame;//provides a window on the screen
import javax.swing.Timer;//runs a block of code in a regular interval provided

public class FlappyBird implements ActionListener, MouseListener,KeyListener  {

	public static FlappyBird flappyBird;
	
	//create a control space, that can be accessed from anywhere
	public final int width = 800, height = 800;
	//create a rectangle instance called bird
	public Rectangle bird;
	//create an instance of variable renderer, currently null
	public Renderer renderer;
	//create an instance of variable renderer, currently null
	public int ticks,yMotion,score;
	//enable the bird to have motion in it, and keep track of score
	public Random rand;
	//class constructor to initialise objects flappyBird, to create instances
	public ArrayList<Rectangle>columns;
	//array list which will contain rectangle
	public boolean gameOver,started;
	//variable for game over
	public FlappyBird() {
		
		//create a new instance of jFrame timer and renderer
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20,this);
        renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//prevent it from continuously running
		jframe.setSize(width, height);//set size
		jframe.setVisible(true);//enable it to be visible
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);//prevent it from being resized
		
		bird = new Rectangle(width/2-10, height/2-10,20,20);//create a rectangle
		columns =new ArrayList<Rectangle>();
		
		//create four columns
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int speed = 10;
		
		ticks++;
		
		if(started) {
			for(int i =0; i<columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x-=speed;
			}
			//adds y motion to the bird.
			if ((ticks % 2 == 0) && yMotion<15) {
				yMotion+=2;
				
				/*
				 * if the remainder of ticks is 
				 * equal to 0, and  ymotion is 
				 * less than 15 * then call everything 
				 * inside these curly brackets*/
				
			}
			
			
			for (int i=0; i<columns.size(); i++) {
				Rectangle column = columns.get(i);
				if(column.x + column.width< 0) {
					columns.remove(column);
					if(column.y==0) {
						addColumn(false);
					}
					
				}
				//this means if it's the last column add another column
			}
			
			bird.y += yMotion;//causes the bird to fall
			
			for( Rectangle column : columns) {
				if (column.y==0 && bird.x + bird.width/2 > column.x + column.width/2-10 && bird.x +bird.width /2 < column.x +column.width/2+10) {
					score++;
				}
				if (column.intersects(bird)) {
					gameOver=true;
					bird.x = column.x-bird.width;
				}
			}
			if (bird.y > height-120|| bird.y<0) {
				gameOver = true;
			}
			
			if (bird.y +yMotion >= height -120) {
				bird.y = height-120-bird.height;
			}
			renderer.repaint();
		}
	}
	
	//method to create columns
	public void addColumn(boolean start) {
		int space =300;
		int WIDTH=100;
		int HEIGHT = 50 + rand.nextInt(300);
		
		if(start) {
			columns.add(new Rectangle(width + WIDTH +columns.size()*300,height - HEIGHT-120, WIDTH, HEIGHT));
			columns.add(new Rectangle(width + WIDTH +(columns.size()-1)*300,0, WIDTH, height-HEIGHT-space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600,height - HEIGHT-120, WIDTH, HEIGHT));
			columns.add(new Rectangle(columns.get(columns.size()-2).x+600,0, WIDTH, height-HEIGHT-space));
		}
		
	}
	//create image
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x,column.y,column.width,column.height);
	}
	//create image
	public void jump() {
		if(gameOver) {
			gameOver=false;
			
			bird = new Rectangle(width/2-10, height/2-10,20,20);//create a rectangle
			columns.clear();
			yMotion = 0;
			score=0;
			//create four columns
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
		}
		if(!started) {
			started = true;
		}
		else if (!gameOver){
			if (yMotion >0) {
				yMotion=0;
			}
			yMotion-=10;
		}
	}
	public void repaint(Graphics g) {
		//retangles and their individual colours are displayed
		g.setColor(Color.cyan);
		g.fillRect(0,0, width, height);
		
		g.setColor(Color.orange);
		g.fillRect(0, height-120, width, 120);
		
		g.setColor(Color.green);
		g.fillRect(0, height-120, width, 20);
		
		g.setColor(Color.pink);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		//columns are being painted and iterated so that its infinite
		for(Rectangle column: columns) {
			paintColumn(g, column);
		}
		//creates fonts and title when game is over
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		if(gameOver) {
			g.drawString("Game Over!", 100, height/2-50);
		}
		if(!started) {
			g.drawString("Click to Start!", 75, height/2-50);
		}
		if(!gameOver && started) {
			g.drawString(String.valueOf(score), width/2-25, 100);
		}
	}
	public static void main(String[] args) {
		flappyBird = new FlappyBird();//setting a new instance of flappyBird
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	
}

