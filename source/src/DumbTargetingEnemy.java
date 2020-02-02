import javafx.scene.image.Image;

/**
 * This is a DumbTargetingEnemy class which is used to create an enemy that
 * is able to follow the player but, gets stuck easily.
 * <br> Creation Date: 23/11/19.
 * <br> Last Modification Date: 30/11/19.
 * @author Szymon Grzech (988065)
 * @version 1.2
 */
public class DumbTargetingEnemy extends Enemy{
	//Final Variables
	//Image:
	private final static String IMAGE_FOLDERNAME_DUMB = ("dumb/"); //folder name
	

	/**
	 * Constructs the DumbTargetingEnemy
	 * @param coords the enemies coordinates (x,y)
	 */
	public DumbTargetingEnemy(int[] coords) {
		super(coords);
	}

	/**
	 * Image of the enemy, 4 different images for 
	 * 4 different directions its going to face.
	 */
	public Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_ENEMY + IMAGE_FOLDERNAME_DUMB;
		if(this.yDirection == -1) {
			filename += IMAGE_FN_ENEMY_SUFFIX_NORTH;
			
		}else if(this.xDirection == 1){
			filename += IMAGE_FN_ENEMY_SUFFIX_EAST;
			
		}else if(this.yDirection == 1){
			filename += IMAGE_FN_ENEMY_SUFFIX_SOUTH;
			
		} else {
			filename += IMAGE_FN_ENEMY_SUFFIX_WEST;
		}
		
		return new Image(filename + IMAGE_FN_FILETYPE);
	}
	
	/**
	 * This move method gets the magnitude of the distance between enemies x and characters x
	 * coordinates and compares it to the magnitude of the distance between
	 * enemies y and characters y coordinates. Whichever one is bigger thats the
	 * direction the enemy will move in.
	 */
	public void move(Character ch, Cell[][] map) {
		
		// Gets the magnitude of the difference between enemies and characters coordinates.
		// if the magnitude between x coordinate is bigger then it will move in x direction,
		// else it will move in the y direction.
		if(Math.abs(ch.getCoords()[0] - this.coords[0]) >=  Math.abs(ch.getCoords()[1] - this.coords[1])) {
			
			// Can only move on a cell that is an instance of a floor and doesnt have a collectable on it
			if(map[this.coords[1]][this.coords[0] + 1] instanceof Floor 
					&& this.coords[0] <= ch.getCoords()[0]) {
				
				if(!((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
					this.xDirection = 1;
					this.yDirection = 0;
					this.coords[0] += this.xDirection;
				}
				
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Floor 
					&& this.coords[0] >= ch.getCoords()[0]){
				
				if(!((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
					this.xDirection = -1;
					this.yDirection = 0;
					this.coords[0] += this.xDirection;
				}
			}
			
		}else if(Math.abs(ch.getCoords()[0] - this.coords[0]) <  Math.abs(ch.getCoords()[1] - this.coords[1])) {
			
			if(map[this.coords[1] + 1][this.coords[0]] instanceof Floor 
					&& this.coords[1] <= ch.getCoords()[1]) {
				
				if(!((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
					this.yDirection = 1;
					this.xDirection = 0;
					this.coords[1] += this.yDirection;
				}
				
			}else if(map[this.coords[1] - 1][this.coords[0]] instanceof Floor 
					&& this.coords[1] >= ch.getCoords()[1]){
				
				if(!((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
					this.yDirection = -1;
					this.xDirection = 0;
					this.coords[1] += this.yDirection;
				}
			}
		}
		
		this.characterCheck(ch);
	}
}