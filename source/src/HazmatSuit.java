import javafx.scene.image.Image;

/**
 * Hazmat Suits will prevent the character from getting killed when they move onto acid tiles.
 * The HazmatSuit class is a subclass of the Collectable class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Jumaira Miller (983101)
 * @version 2.0
 */
public class HazmatSuit extends Collectable{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_HAZMAT = ("hazmat");
	
	/**
	 * All HazmatSuit instances need to do is exist so the constructor is empty.
	 */
	public HazmatSuit() {
		super();
	}
	
	/**
	 * Gets the image for the Hazmat Suit.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the Hazmat Suit Image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_COLLECTABLE + IMAGE_FN_HAZMAT + IMAGE_FN_FILETYPE);
	}
}
