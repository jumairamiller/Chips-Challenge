import javafx.scene.image.Image;

/**
 * Acid tiles kill a character when they move onto it if they do not have a hazmat suit.
 * Acid is a subclass of the abstract Hazard class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 2.2
 */
public class Acid extends Hazard{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_ACID = ("acid");
	
	/**
     * Constructs an Acid instance. Nothing is needed for it.
     */
	public Acid() {
		super();
	}
	
	/**
	 * Checks if the instance of Collectable passed to the method is an instance of the subclass HazmatSuit.
	 * @param c Collectable - The Collectable instance to check.
	 * @return a boolean value response to the result of the check.
	 */
	@Override
	protected boolean checkCollectable(Collectable c) {
		boolean b = false;
		if (c instanceof HazmatSuit) {
			b = true;
		}
		return b;
	}
	
	/**
	 * Returns the Image for the Acid tiles.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_HAZARD + IMAGE_FN_ACID + IMAGE_FN_FILETYPE);
	}
}