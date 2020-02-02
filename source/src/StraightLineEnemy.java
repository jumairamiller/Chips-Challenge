import javafx.scene.image.Image;

/**
 * This is a SmartTargetingEnemy class which is used to create an enemy that
 * is able to only move in a straight line, either horizontally or vertically.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 30/11/19.
 * @author Szymon Grzech (988065)
 * @version 1.2
 */
public class StraightLineEnemy extends Enemy {
	//Final Variables
	//Image:
	private final static String IMAGE_FOLDERNAME_LINE = ("line/"); //folder name
	
	/**
	 * The direction it starts moving in.
	 */
	private String direction;

	/**
	 * Constructs the StraightLineEnemy
	 * @param coords the enemies coordinates (x,y)
	 * @param direction, the direction its doing to start with.
	 */
	public StraightLineEnemy(int[] coords,String direction) {
		super(coords);
		this.setDirection(direction);
		direction();
	}

	/**
	 * Image of the enemy, 4 different images for 
	 * 4 different directions its going to face.
	 */
	public Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_ENEMY + IMAGE_FOLDERNAME_LINE;
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
	 * This method moves the enemy by 1 block in either vertical or horizontal direction,
	 * if there is a wall or a collectible or a door in front of the enemy, then
	 * the enemy will bounce and walk in the opposite direction.
	 */
	public void move(Character ch, Cell[][] map) {
		
		//If xDirection is not 0 then it will move horizontally
	   	if(this.xDirection == 1 || this.xDirection == -1) {
	   		// can only walk on an instance of a floor that has no collectables present.
	   		if(map[this.coords[1]][this.coords[0] + this.xDirection] instanceof Floor) {
	   			
	   			if(!((Floor) map[this.coords[1]][this.coords[0] + this.xDirection]).collPresent()){
	   				this.coords[0] += this.xDirection;
	   			
	   			}else {
	   				//if it hits a wall or a collectable its direction will be multiplied by -1 so it will change 
	   				//the direction its moving in.
	   				changeDirection();
		   			this.coords[0] += this.xDirection;
	   			}
	 
	   		}else {
	   			changeDirection();
	   			this.coords[0] += this.xDirection;
	   		}
	   		
	   		
	   	
	   	//else if yDirection is not 0 then it will move vertically.
	   	}else if(this.yDirection == 1 || this.yDirection == -1) {
	   		
	   		if(map[this.coords[1] + this.yDirection][this.coords[0]] instanceof Floor) {
	   			
	   			if(!((Floor) map[this.coords[1] + this.yDirection][this.coords[0]]).collPresent()){
	   				this.coords[1] += this.yDirection;
	   			
	   			}else {
	   				changeDirection();
	   				this.coords[1] += this.yDirection;
	   			}
	   			
	   		}else {
	   			changeDirection();
	   			this.coords[1] += this.yDirection;
	   		}
	   	}
	   	
	   	this.characterCheck(ch);
	}
	
	/**
	 * Gets the string which states the starting direction.
	 * @return string direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Sets the starting direction
	 * @param direction String - new direction value to set to the enemy's direction.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	//changes the direction by multiplying the coordinates by -1
	private void changeDirection() {
		this.xDirection *= -1;
		this.yDirection *= -1;
	}
	
	/**
	 * Sets the xDirection and the yDirection values depending on the 
	 * starting direction of the enemy.
	 * @return the xDirection and the yDirection
	 */
	private int direction() {
		switch(this.direction) {
		case "UP":
			this.xDirection = 0;
			return this.yDirection = -1;
		case "DOWN":
			this.xDirection = 0;
			return this.yDirection = 1;
		case "RIGHT":
			this.yDirection = 0;
			return this.xDirection = 1;
		case "LEFT":
			this.yDirection = 0;
			return this.xDirection = -1;
		default:
			return 0;
		}
	}
}
