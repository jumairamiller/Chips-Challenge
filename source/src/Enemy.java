import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The Enemy class is an abstract class of the enemies that will be used in levels
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 30/11/19.
 * @author  Szymon Grzech (988065), Timothy Roger (977422)
 * @version 1.2
 */

public abstract class Enemy {
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_ENEMY = ("enemy/"); //folder name
	//file type
	protected final static String IMAGE_FN_FILETYPE = (".png");
	//direction suffix
	protected final static String IMAGE_FN_ENEMY_SUFFIX_NORTH = ("north");
	protected final static String IMAGE_FN_ENEMY_SUFFIX_EAST = ("east");
	protected final static String IMAGE_FN_ENEMY_SUFFIX_WEST = ("west");
	protected final static String IMAGE_FN_ENEMY_SUFFIX_SOUTH = ("south");
	
	/**
	 * The x and the y directions that the enemy is moving in,
	 * the can be either 1, -1 or 0. 
	 */
	protected int xDirection;
	protected int yDirection;
	
	/**
	 * Stores enemies coordinates in (x,y) format
	 */
	int[] coords;
	
	/**
	 * Constructs an enemy.
	 * @param coords its (x,y) coordinates on the map.
	 */
	public Enemy(int[] coords) {
		this.coords = coords;
	}
	
	/**
	 * This is a move method for the enemies which is responsible for
	 * the enemies moving around the map, each enemy will have a different 
	 * set of movements. 
	 * @param ch the Character, used to get the characters coordinates and 
	 * 		  is used to kill the character if they are on the same coordinates.
	 * @param map a 2D array of Cells on which the enemies move around.
	 */
	public abstract void move(Character ch, Cell[][] map);
	
	/**
	 * This method is used to kill the character if the enemy
	 * and the character are on the same spot.
	 * @param ch the Character.
	 */
	public void characterCheck(Character ch) {
		if(this.coords[0] == ch.getCoords()[0]
				&&this.coords[1] == ch.getCoords()[1]) {
			ch.killCharacter();
		}
	}
	
	/**
	 * A method to get the enemy to draw itself.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @param gc the GraphicsContext.
	 * @param x its X coordinate.
	 * @param y its Y coordinate.
	 */
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		gc.drawImage(image(IMAGE_FOLDER), x, y);
	}
	
	/**
	 * Every child of the enemy should have its own image.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the image.
	 */
	public abstract Image image(String IMAGE_FOLDER);

	/**
	 * Returns the enemies coordinates in (x,y) format.
	 * @return the enemy coordinates in (x,y) format.
	 */
	public int[] getEnemyCoords() {
		return this.coords;
	}
	
	/**
	 * Allows you to set the enemies coordinates.
	 * @param coords (x,y).
	 */
	public void setEnemyCoords(int[] coords) {
		this.coords = coords;
	}
	
	/**
	 * Get the xDirection that the enemy is moving in
	 * @return int xDirection
	 */
	public int getxDirection() {
		return xDirection;
	}

	/**
	 * Sets the xDirection 
	 * @param xDirection the direction the enemy is moving in.
	 */
	public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	/**
	 * Get the yDirection that the enemy is moving in
	 * @return int yDirection
	 */
	public int getyDirection() {
		return yDirection;
	}

	/**
	 * Sets the yDirection 
	 * @param yDirection the direction the enemy is moving in.
	 */
	public void setyDirection(int yDirection) {
		this.yDirection = yDirection;
	}
}
