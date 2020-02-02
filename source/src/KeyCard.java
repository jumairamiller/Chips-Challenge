import javafx.scene.image.Image;

/**
 * KeyCards are collected by the character, they will match with a coloured door based on colour and will let the character open them if collected..
 * The KeyCard class is a subclass of the Collectable class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Jumaira Miller (983101)
 * @version 2.1
 */
public class KeyCard extends Collectable{
	//Final Variables
	//Image:
	private final static String IMAGE_FOLDERNAME_KEYCARD = ("keycard/"); //folder name
	//colour path:
	private final static String IMAGE_FN_BLUE = ("blue");
	private final static String IMAGE_FN_GREEN = ("green");
	private final static String IMAGE_FN_PURPLE = ("purple");
	private final static String IMAGE_FN_RED = ("red");
	
	//String that signals the colour of the KeyCard
	String colour;
	
	/**
	 * KeyCards need a colour
	 * @param colour String - The colour to set the KeyCards to.
	 */
	public KeyCard(String colour) {
		super();
		this.colour = colour;
	}
	
	/**
	 * Retrieve the KeyCard's colour value.
	 * @return the String value signifying the colour of the KeyCard.
	 */
	public String getColour() {
		return this.colour;
	}
	
	/**
	 * Returns the correct Image based on the KeyCard's colour.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the image for the KeyCard instance.
	 */
	protected Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_COLLECTABLE + IMAGE_FOLDERNAME_KEYCARD;
		//add path to colour
		switch (colour) {
			case "BLUE":
				filename += IMAGE_FN_BLUE;
				break;
			case "GREEN":
				filename += IMAGE_FN_GREEN;
				break;
			case "PURPLE":
				filename += IMAGE_FN_PURPLE;
				break;
			case "RED":
				filename += IMAGE_FN_RED;
				break;
			default:
				break;
				
		}
		
		return new Image(filename + IMAGE_FN_FILETYPE);
	}
}
