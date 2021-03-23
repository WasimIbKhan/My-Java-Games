package Level;
/*
 * This method is created to prevent the user from moving past some boxes
 * It states that it is solid
 * */



public class BasicSolidTile extends BasicTile {

    
    //constructor for basic solid tile used to prevent player from moving off screen and from the user to be unable to move past some tiles
    public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor ) {
        super(id, x, y , tileColor,levelColor);
        //for collision detection
        this.solid=true;
    }

    

	

}
