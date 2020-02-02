import javafx.scene.image.Image;

/**
 * Treasure is collected by the character, this allowes the character to get a higher score.
 * The Treasure class is a subclass of the Collectable class.
 * <br> Creation Date: 06/12/19.
 * <br> Last Modification Date: 6/12/19.
 * @author Szymon Grzech (988065)
 * @version 1.0
 */
public class Treasure extends Collectable{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_TREASURE = ("treasure");
	
	/**
	 * All Treasure instances need to do is exist so the constructor is empty.
	 */
	public Treasure(){
		super();
	}

	/**
	 * Gets the image for the Treasure collectable.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the found Treasure Image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_COLLECTABLE + IMAGE_FN_TREASURE + IMAGE_FN_FILETYPE);
	}
	
}
