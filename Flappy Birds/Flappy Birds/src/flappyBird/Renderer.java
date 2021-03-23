package flappyBird;

import java.awt.Graphics;

import javax.swing.JPanel;

//create a renderer
public class Renderer extends JPanel {

	/*
	 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void paintComponent(Graphics g) {
		
		//super calls the parent of the class called paintComponent
		super.paintComponent(g);
		
		FlappyBird.flappyBird.repaint(g);
	}

	

}
