package graphics;

public class Colors {
	
	//has information of 4 different colors
	public static int get(int color1, int color2, int color3, int color4) {
		
		//returns colors from darkest to lightest, as with the game class how we are shifting by 8, we are doing the same thing but returning one long number
		return (get(color4)<<24) + (get(color3)<<16) + (get(color2)<<8) + (get(color1));
	}

	private static int get(int color) {
		//prevents it from returning an invisible color
		if(color <0) return 255;
		
		//we want to get the first part of r
		int r =color/100%10;
		int g = color/10%10;
		int b= color%10;
		return r*36 + g*6 + b;		//we are returning 6 becuse we wan to return 6 bits of data
	}
	
}
