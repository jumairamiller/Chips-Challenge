import javafx.scene.image.Image;

/**
 * Characters will be stopped from moving onto a Coloured Door tile if it is closed and they do not have a keycard of the correct colour.
 * The ColouredDoor class is a subclass of the abstract Door class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.3
 */
public class ColouredDoor extends Door {
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_COLOURED = ("coloured/"); //folder name
	//structure:
	//door colour
	protected final static String IMAGE_FOLDERNAME_BLUE = ("blue/");
	protected final static String IMAGE_FOLDERNAME_GREEN = ("green/");
	protected final static String IMAGE_FOLDERNAME_PURPLE = ("purple/");
	protected final static String IMAGE_FOLDERNAME_RED = ("red/");
	//openness
	protected final static String IMAGE_FN_OPEN = ("open");
	protected final static String IMAGE_FN_CLOSED = ("closed");

	
	//colour String - colour of the door. Can be stuff like 'BLUE', 'GREEN', 'PURPLE' or 'RED'.
	private String colour;
	
	/**
	 * Constructs a ColouredDoor instance. Nothing is needed for it. 
	 */
	public ColouredDoor() {
		super();
	}
	
	/**
	 * Sets the colour of the Coloured Door to the passed String value.
	 * @param c String - the new colour of the door.
	 */
	public void setColour(String c) {
		this.colour = c;
	}
	
	/**
	 * Gets the colour of the Coloured Door.
	 * @return the String containing the Colour of the door.
	 */
	public String getColour() {
		return this.colour;
	}
	
	/**
	 * Returns a boolean value based on if the passed Character instance's array of Collectable instances contains a KeyCard of a matching colour to the door in it.
	 * If the character does have the matching KeyCard, it should be removed afterwards.
	 * @param c Character - The Character instance which has the array of Collectables to check through.
	 * @return a boolean value based on the result of the check.
	 */
	@Override
	protected boolean doorCheck(Character c) {
		boolean b = false;
		for (Collectable col: c.getInventory()) {
			if (col instanceof KeyCard) {
				if (this.colour.equals(((KeyCard) col).getColour())) {
					this.openState = true;
					b = true;
					c.setInventory(removeKeyCard(c.getInventory(), (KeyCard) col));
				}
			}
		}
		return b;
	}
	
	/**
	 * Retrieves the correct image based on colour and openness of the coloured door instance.
	 * @return the Image object with the correct image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_DOOR + IMAGE_FOLDERNAME_COLOURED; //img1/cell/door/coloured/....
		
		//adds the correct path for colour
		switch (this.colour) {
			case "BLUE":
				filename += IMAGE_FOLDERNAME_BLUE;
				break;
			case "GREEN":
				filename += IMAGE_FOLDERNAME_GREEN;
				break;
			case "PURPLE":
				filename += IMAGE_FOLDERNAME_PURPLE;
				break;
			case "RED":
				filename += IMAGE_FOLDERNAME_RED;
				break;
		}
		
		//adds the correct path for openness
		if (openState == true) {
			filename += IMAGE_FN_OPEN;
		} else {
			filename += IMAGE_FN_CLOSED;
		}
		
		return new Image(filename + IMAGE_FN_FILETYPE); //adds the correct filetype, makes the new Image object and returns it.
	}
	
	/**
	 * Removes a specific instance of KeyCard from a passed array of Collectable instances.
	 * @param c Collectable[] - the array of Collectable instances to change.
	 * @param k KeyCard - the KeyCard instance to be removed.
	 * @return the new Collectable array without the KeyCard instance.
	 */
	private Collectable[] removeKeyCard(Collectable[] c, KeyCard k) {
		Collectable[] newC = new Collectable[c.length-1];
		int j = 0;
		for (int i = 0; i < c.length; i++) {
			if (!(c[i].equals((Collectable)k))) {
				newC[j] = c[i];
				j++;
			} 
		}
		return newC;
	}
}
