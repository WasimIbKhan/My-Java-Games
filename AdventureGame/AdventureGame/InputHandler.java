package AdventureGame;

//import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.ArrayList;


/*
 * every time a key is pressed
 * This class is called
 */
public class InputHandler implements KeyListener{
	private int numTimesPressed=0;
	//
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public class Key {
		public boolean pressed=false;
		
		//getter method
		public boolean isPressed() {
			return pressed;
		}
		public void toggle(boolean isPressed) {
			pressed=isPressed;
			if(isPressed)numTimesPressed++;//later to be used for double jump
		}
	}
	
	
	
	//4 registered keys
	public Key up= new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	//enables actions to be performed if the key is pressed
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(),true);
	}
	
	//prevents actions from occuring if key is not pressed
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(),false);
		
	}


	public void keyTyped(KeyEvent e) {
		
	}

	//toggles key enables movement
	public void toggleKey(int keyCode, boolean isPressed) {
		if(keyCode ==KeyEvent.VK_W || keyCode ==KeyEvent.VK_UP) {
			up.toggle(isPressed);
		}
		if(keyCode ==KeyEvent.VK_S || keyCode ==KeyEvent.VK_DOWN) {
			down.toggle(isPressed);
		}
		if(keyCode ==KeyEvent.VK_A || keyCode ==KeyEvent.VK_LEFT) {
			left.toggle(isPressed);
		}
		if(keyCode ==KeyEvent.VK_D || keyCode ==KeyEvent.VK_RIGHT) {
			right.toggle(isPressed);
		}
	}


}

	