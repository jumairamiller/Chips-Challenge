import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * AN instance of Character will hold any data about a character, including it's state, it's coordiantes on the map and it's inventory.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 6/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.7
 */
public class Character {
	
	//Final Variables
	//Image:
	private final static String IMAGE_FOLDERNAME_CHAR = ("char/"); //folder name
	//Filename structure
	//Filename prefix
	private final static String IMAGE_FN_CHAR_PREFIX_BACK = ("back_");
	private final static String IMAGE_FN_CHAR_PREFIX_TOP = ("top_");
	//filename suffix
	private final static String IMAGE_FN_CHAR_SUFFIX_NORMAL = ("normal");
	private final static String IMAGE_FN_CHAR_SUFFIX_HAZMAT = ("hazmat");
	private final static String IMAGE_FN_CHAR_SUFFIX_BOOTS = ("boots");
	private final static String IMAGE_FN_CHAR_SUFFIX_NORTH = ("north");
	private final static String IMAGE_FN_CHAR_SUFFIX_EAST = ("east");
	private final static String IMAGE_FN_CHAR_SUFFIX_WEST = ("west");
	private final static String IMAGE_FN_CHAR_SUFFIX_SOUTH = ("south");
	//file type
	private final static String IMAGE_FN_FILETYPE = (".png");
	
	
	// X and Y coordinates of player.
	private int[] coords;
	//Array of collected collectables.
	private Collectable[] inventory;
	private String latestMoveDir;
	private boolean state; //alive > true, dead > false.
	private boolean winState; //won > true, not won > false
	
	/**
	 * Constructor that initialises the character
	 * @param coords - reference the positions of the character on the map
	 */
	public Character(int[] coords) {
	   this.coords = coords;
	   this.latestMoveDir = "SOUTH";
	   this.inventory = new Collectable[0];
	   this.state = true;
	   this.winState = false;
	}
	
	/**
	 * Draws the character onto the passed GraphicsContext at the passed coordinates
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @param gc GraphicsContext - used to draw the Images.
	 * @param x int - X coordinate.
	 * @param y int - Y coordinate
	 */
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		//draws hands under the back under the top
		gc.drawImage(imageBack(IMAGE_FOLDER), x, y);
		gc.drawImage(imageTop(IMAGE_FOLDER), x, y);
		gc.drawImage(imageDirection(IMAGE_FOLDER), x, y);
	}
	
	/**
	 * Adds a new Collectable instance to the character's inventory.
	 * @param c Collectable - the new instance to add.
	 */
	public void addCollectable(Collectable c) {
		Collectable[] tempInv = this.inventory;
		Collectable[] newInv = new Collectable[(tempInv.length)+1];
		for (int i = 0; i < tempInv.length; i++) {
			newInv[i] = tempInv[i];
		}
		newInv[(tempInv.length)] = c;//tempInv.length will be 1 more than the index so no increment or decrement is needed.
		this.inventory = newInv;
	}
	
	/**
	 * Gets the inventory of the character.
	 * @return the array of Collectable instances that make up the inventory.
	 */
	public Collectable[] getInventory() {
		return this.inventory;
	}
	
	/**
	 * Sets the inventory to be equal to a new array of Collectable instances.
	 * @param cs Collectable[] - the new array of instances.
	 */
	public void setInventory(Collectable[] cs) {
		this.inventory = cs;
	}
	
	/**
	 * Get the coordinates of the character.
	 * @return the small array of integers that contain the coordinates.
	 */
	public int[] getCoords() {
	   return this.coords;
	}
	
	/**
	 * Sets the character's coordinates to the passed value.
	 * @param coords int[] - array of two integers which are the new X and Y coordinates.
	 */
	public void setCoords(int[] coords) {
	   this.coords = coords;
	}
	
	/**
	 * Sets the character's direction to something new.
	 * @param direction String - string which signifies the new direction of the character.
	 */
	public void setDirection (String direction) {
		if (direction.equals("NORTH") || direction.equals("EAST") ||
			direction.equals("SOUTH") || direction.equals("WEST")) {
			this.latestMoveDir = direction;
		}
	}
	
	/**
	 * Returns the latest move direction of the character.
	 * @return the String corresponding to direction of the character.
	 */
	public String getDirection () {
		return this.latestMoveDir;
	}
	
	/**
	 * "Kills" the character by setting it's state to false, which signals it's death.
	 */
	public void killCharacter() {
		this.state = false;
	}
	
	/**
	 * Make the character win the level, by changing it's win state to true, which singals it has reached the goal.
	 */
	public void winLevel() {
		this.winState = true;
	}
	
	public boolean winCheck() {
		return this.winState;
	}
	
	/**
	 * Checks if the character is alive and can continue.
	 * @return the character's state as a boolean variable.
	 */
	public boolean aliveCheck() {
		return this.state;
	}
	
	/**
	 * Counts the energy the character has in their inventory.
	 * @return the integer value of the amount the character has.
	 */
	public int countEnergy() {
		int count = 0;
		for (Collectable c: this.inventory) {
			if (c instanceof Energy) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Counts the energy the character has in their inventory.
	 * @return the integer value of the amount the character has.
	 */
	public int countTreasure() {
		int count = 0;
		for (Collectable c: this.inventory) {
			if (c instanceof Treasure) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Check to see if the character has a hazmat suit.
	 * @return boolean based on result.
	 */
	private boolean hazmatCheck() {
		boolean b = false;
		for (Collectable c: this.inventory) {
			if (c instanceof HazmatSuit) {
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * Check to see if the character has rubber boots.
	 * @return boolean based on result.
	 */
	private boolean bootsCheck() {
		boolean b = false;
		for (Collectable c: this.inventory) {
			if (c instanceof RubberBoots) {
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * Retrieves an Image object of the character's hands in the correct direction.
	 * @param IMAGE_FOLDER String - the path to the correct image file. 
	 * @return the Image object with the correct image.
	 */
	private Image imageDirection(String IMAGE_FOLDER) {
		//The correct filename needs to be found.
		String filename = (IMAGE_FOLDER + IMAGE_FOLDERNAME_CHAR);// "img1/char/"
		//finds correct direction.
		switch (latestMoveDir) {
		case "NORTH":
			filename += IMAGE_FN_CHAR_SUFFIX_NORTH;
			break;
		case "EAST":
			filename += IMAGE_FN_CHAR_SUFFIX_EAST;
			break;
		case "SOUTH":
			filename += IMAGE_FN_CHAR_SUFFIX_SOUTH;
			break;
		case "WEST":
			filename += IMAGE_FN_CHAR_SUFFIX_WEST;
			break;
		}
		return (new Image(filename + IMAGE_FN_FILETYPE)); //".png" is added and the image is made and returned.
	}
	
	/**
	 * Retrieves an Image object of the character's "top" based on what items they do and don't have.
	 * @param IMAGE_FOLDER String - the path to the correct image file.
	 * @return the Image object with the correct image.
	 */
	private Image imageTop(String IMAGE_FOLDER) {
		//Correct filename needs to be found.
		String filename = (IMAGE_FOLDER + IMAGE_FOLDERNAME_CHAR + IMAGE_FN_CHAR_PREFIX_TOP);// "img/char/top_"
		if (bootsCheck()) { //character will turn yellow when they own a hazmat suit.
			filename += IMAGE_FN_CHAR_SUFFIX_BOOTS;
		} else {
			filename += IMAGE_FN_CHAR_SUFFIX_NORMAL;
		}
		return (new Image(filename + IMAGE_FN_FILETYPE)); //".png" is added and the image is made and returned.
	}
	
	/**
	 * Retrieves an Image object of the character's "back" based on what items they do and don't have.
	 * @param IMAGE_FOLDER String - the path to the correct image file. 
	 * @return the Image object with the correct image.
	 */
	private Image imageBack(String IMAGE_FOLDER) {
		String filename = (IMAGE_FOLDER + IMAGE_FOLDERNAME_CHAR + IMAGE_FN_CHAR_PREFIX_BACK);// "img/char/back_"
		if (hazmatCheck()) { //character will have rusty border when they own rubber boots.
			filename += IMAGE_FN_CHAR_SUFFIX_HAZMAT;
		} else {
			filename += IMAGE_FN_CHAR_SUFFIX_NORMAL;
		}
		return (new Image(filename + IMAGE_FN_FILETYPE)); //".png" is added and the image is made and returned.
	}
   
}