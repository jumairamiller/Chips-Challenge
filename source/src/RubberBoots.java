import javafx.scene.image.Image;

/**
 * Rubber Boots will prevent the character from getting killed when they move onto tiles of wires.
 * The RubberBoots class is a subclass of the Collectable class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Jumaira Miller (983101)
 * @version 2.0
 */
public class RubberBoots extends Collectable{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_BOOTS = ("boots");
	
	/**
	 * All RubberBoots instances need to do is exist so the constructor is empty.
	 */
	public RubberBoots() {
		super();
	}
	
	/**
	 * Gets the Image for Rubber Boots.
	 * @param IMAGE_FOLDER String - the path the the correct image folder.
	 * @return the Image of the rubber boots.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_COLLECTABLE + IMAGE_FN_BOOTS + IMAGE_FN_FILETYPE);
	}
}
