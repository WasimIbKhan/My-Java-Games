package graphics;


public class Font {
	
	//all characters that may be displayed on screen
	private static String chars ="ABCDEFGHIJKLMNOPQRSTUVWXYZ      "
								+"0123456789.,:;\"!?$%()-=+/      ";
	
	//renders text to screen
	public static void render(String msg, Screen screen, int x, int y, int color, int scale) {
		msg=msg.toUpperCase();
		
		//loops through all characters and renders to screen
		for(int i=0; i<msg.length(); i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			if(charIndex >=0) screen.render(x +(i*8), y, charIndex +30*32, color);
		}
	}
}
