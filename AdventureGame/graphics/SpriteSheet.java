package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	
	//declaring variables for the path of the image, width and height of the image
	public String path;
	public int width;
	public int height;
	
	//pixel data of the actual sprite sheet
	public int[] pixels;
	
	public SpriteSheet(String path) {
		BufferedImage image=null;
		//checks if there is no image
		if(SpriteSheet.class.getResourceAsStream(path)==null){
			System.out.println("doesnt exist!");
		}
		//there will be exceptions
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));  //obtains image content from file
		} catch (IOException e) {
			 e.printStackTrace();
		} 
		
		
		//sets the class variables
		this.path=path;
		this.width=image.getWidth();
		this.height=image.getHeight();
		
		//sets the pixls variables, this returns the RGB, we now have the color data imported into here and is going to be stored in te RGB array called imaged
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		//set the pixels to the color we are dealing with,
		for(int i=0; i<pixels.length; i++) {
			pixels[i]=(pixels[i] &0xff)/(255/4);
		}
		//prints the first 8 bits
		for(int i=0; i<8; i++) {
			System.out.println(pixels[i]);
		}
	}
}
