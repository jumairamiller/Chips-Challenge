import javafx.scene.image.Image;

/**
 * Energy is collected by the character, having enough will allow the character to open powered doors.
 * The Energy class is a subclass of the Collectable class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Jumaira Miller (983101)
 * @version 2.1
 */
public class Energy extends Collectable{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_ENERGY = ("energy");
	
	/**
	 * All Energy instances need to do is exist so the constructor is empty.
	 */
	public Energy() {
		super();
	}
	
	/**
	 * Gets the image for the Energy collectable.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the found Energy Image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_COLLECTABLE + IMAGE_FN_ENERGY + IMAGE_FN_FILETYPE);
	}
}