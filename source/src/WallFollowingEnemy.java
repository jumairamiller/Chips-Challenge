import javafx.scene.image.Image;

/**
 * This is a WallFollowingEnemy class which is used to create an enemy that
 * will only follow walls on either its left or right.
 * <br> Creation Date: 22/11/19.
 * <br> Last Modification Date: 2/12/19.
 * @author Szymon Grzech (988065)
 * @version 2.2
 */
public class WallFollowingEnemy extends Enemy{
	//Final Variables
	//Image:
	private final static String IMAGE_FOLDERNAME_SMART = ("wall/"); //folder name
	
	/**
	 * String wallFollowed states the wall followed by the enemy, either left or right
	 * String direction states one of the four different directions the enemy is walking in.
	 * boolean started states whether it chose the starting direction for the enemy
	 */
	private String wallFollowed;
	private String direction;
	private boolean started = false;
	
	/**
	 * Constructs the WallFollowingEnemy
	 * @param coords (x,y)
	 * @param wallFollowed wall its following
	 */
	public WallFollowingEnemy(int[] coords, String wallFollowed) {
		super(coords);
		this.wallFollowed = wallFollowed;
	}

	/**
	 * Image of the enemy, 4 different images for 
	 * 4 different directions its going to face.
	 */
	public Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_ENEMY + IMAGE_FOLDERNAME_SMART;
		
		if(this.yDirection == -1) {
			filename += IMAGE_FN_ENEMY_SUFFIX_NORTH;
			
		}else if(this.xDirection == 1){
			filename += IMAGE_FN_ENEMY_SUFFIX_EAST;
			
		}else if(this.yDirection == 1){
			filename += IMAGE_FN_ENEMY_SUFFIX_SOUTH;
			
		}else {
			filename += IMAGE_FN_ENEMY_SUFFIX_WEST;
		}
		
		return new Image(filename + IMAGE_FN_FILETYPE);
	}
	
	/**
	 * Follows either the left or the right wall all around the map.
	 */
	public void move(Character ch, Cell[][] map) {
		
		wallHugged(map);
		
		if(this.wallFollowed.equals("LEFT")) {
			
			left(map);
			
		}else if(this.wallFollowed.equals("RIGHT")) {

			right(map);
			
		}
		
		characterCheck(ch);
		
	}

	/**
	 * Gets the string stating which wall is being followed
	 * @return String
	 */
	public String getWallFollowed() {
		return wallFollowed;
	}

	/**
	 * Sets the wall followed by the enemy 
	 * @param wallFollowed String - side on which the wall to follow is.
	 */
	public void setWallFollowed(String wallFollowed) {
		this.wallFollowed = wallFollowed;
	}

	/**
	 * Direction the enemy is walking in
	 * @return string stating the direction the enemy is walking in
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * sets the direction the enemy is walking in
	 * @param direction String - direction to set wall following enemy to.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * gets the isStarted boolean
	 * @return boolean 
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * sets the started boolean
	 * @param started boolean - value to set started to.
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}

	/**
	 * Sets the wall hugged by the enemy
	 * @param map the game map of Cells
	 */
	private void wallHugged(Cell[][] map) {
		
//		when the enemy first moves it looks for its starting wall and sets its starting direction
//		after this runs once it'll not run again for the same level unless the level restarts

		if(this.started == false) {
			
			if(this.wallFollowed.equals("LEFT")) {
				
				if(map[this.coords[1] - 1][this.coords[0]] instanceof Wall) {
					this.xDirection = 1;
					this.yDirection = 0;
				
				}else if(map[this.coords[1] + 1][this.coords[0]] instanceof Wall) {
					this.xDirection = -1;
					this.yDirection = 0;
				
				}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Wall) {
					this.xDirection = 0;
					this.yDirection = 1;
				
				}else {
					this.xDirection = 0;
					this.yDirection = -1;
				}
				
				this.started = true;
				
			}else if(this.wallFollowed.equals("RIGHT")) {
			
				if(map[this.coords[1] - 1][this.coords[0]] instanceof Wall) {
					this.xDirection = -1;
					this.yDirection = 0;
				
				}else if(map[this.coords[1] + 1][this.coords[0]] instanceof Wall) {
					this.xDirection = 1;
					this.yDirection = 0;
				
				}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Wall) {
					this.xDirection = 0;
					this.yDirection = 1;
				
				}else {
					this.xDirection = 0;
					this.yDirection = -1;
				}
				
				this.started = true;
			}
		}	
	}
	
	/**
	 * sets the direction that the enemy is walking in
	 */
	private void switchDirectionLeft() {
		switch(direction) {
		case "LEFT":
			this.yDirection = 0;
			this.xDirection = -1;
			this.coords[0] += this.xDirection;	
			break;
		
		case "RIGHT":
			this.yDirection = 0;
			this.xDirection = 1;
			this.coords[0] += this.xDirection;
			break;
		
		case "UP":
			this.yDirection = -1;
			this.xDirection = 0;
			this.coords[1] += this.yDirection;
			break;
		
		case "DOWN":
			this.yDirection = 1;
			this.xDirection = 0;
			this.coords[1] += this.yDirection;
			break;
		}
	}
	
	/**
	 * If left wall followed
	 * Those are hard coded scenarios for one of the four possible outcomes
	 * on each occasion on each direction followed.
	 * Does not pass through collectables.
	 * @param map of Cells
	 */
	private void left(Cell[][] map) {
		
		if(this.xDirection == 1) {
			
			if(map[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1] - 1][this.coords[0] - 1] instanceof Wall
					&& !((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				
				this.direction = "UP";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				
				this.direction = "RIGHT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Wall
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& !((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
				
				this.direction = "DOWN";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()){
				this.direction = "LEFT";
				switchDirectionLeft();
			}
			
		}else if(this.xDirection == -1) {
			
			if(map[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1] + 1][this.coords[0] + 1] instanceof Wall
					&& !((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
				
				this.direction = "DOWN";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				
				this.direction = "LEFT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Wall
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& !((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				
				this.direction = "UP";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()){
				this.direction = "RIGHT";
				switchDirectionLeft();
			}
			
		}else if(this.yDirection == 1) {
			
			if(map[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& map[this.coords[1] - 1][this.coords[0] + 1] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				
				this.direction = "RIGHT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1]][this.coords[0] + 1] instanceof Wall
					&& !((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
				
				this.direction = "DOWN";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Wall
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& !((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				
				this.direction = "LEFT";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()){
				this.direction = "UP";
				switchDirectionLeft();
			}
			
		}else if(this.yDirection == -1) {
			
			if(map[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& map[this.coords[1] + 1][this.coords[0] - 1] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				
				this.direction = "LEFT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1]][this.coords[0] - 1] instanceof Wall
					&& !((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				
				this.direction = "UP";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Wall
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& !((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				
				this.direction = "RIGHT";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()){
				this.direction = "DOWN";
				switchDirectionLeft();
			}	
		}
	}
	
	/**
	 * If right wall followed
	 * Those are hard coded scenarios for one of the four possible outcomes
	 * on each occasion on each direction followed.
	 * Does not pass through collectables.
	 * @param map of Cells
	 */
	private void right(Cell[][] map) {
		
		if(this.xDirection == 1) {
			
			if(map[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1] + 1][this.coords[0] - 1] instanceof Wall
					&& !((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
					
				this.direction = "DOWN";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				
				this.direction = "RIGHT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Wall
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& !((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				
				this.direction = "UP";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()){
				this.direction = "LEFT";
				switchDirectionLeft();
			}
			
		}else if(this.xDirection == -1) {
			
			if(map[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1] - 1][this.coords[0] + 1] instanceof Wall
					&& !((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				
				this.direction = "UP";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				
				this.direction = "LEFT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Wall
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& !((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
				
				this.direction = "DOWN";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()){
				this.direction = "RIGHT";
				switchDirectionLeft();
			}
			
		}else if(this.yDirection == 1) {
			
			if(map[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& map[this.coords[1] - 1][this.coords[0] - 1] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				
				this.direction = "LEFT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1]][this.coords[0] - 1] instanceof Wall
					&& !((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()) {
				
				this.direction = "DOWN";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] - 1] instanceof Wall
					&& map[this.coords[1] + 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& !((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				
				this.direction = "RIGHT";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()){
				this.direction = "UP";
				switchDirectionLeft();
			}
			
		}else if(this.yDirection == -1) {
			
			if(map[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& map[this.coords[1] + 1][this.coords[0] + 1] instanceof Wall
					&& !((Floor) map[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				
				this.direction = "RIGHT";
				switchDirectionLeft();
			
			}else if(map[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& map[this.coords[1]][this.coords[0] + 1] instanceof Wall
					&& !((Floor) map[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				
				this.direction = "UP";
				switchDirectionLeft();
			
			}else if(map[this.coords[1]][this.coords[0] + 1] instanceof Wall
					&& map[this.coords[1] - 1][this.coords[0]] instanceof Wall
					&& map[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& !((Floor) map[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				
				this.direction = "LEFT";
				switchDirectionLeft();
			
			}else if(!((Floor) map[this.coords[1] + 1][this.coords[0]]).collPresent()){
				this.direction = "DOWN";
				switchDirectionLeft();
			}
			
		}
		
	}
}
