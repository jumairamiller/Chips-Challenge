import javafx.scene.image.Image;

/**
 * Any 'Wires' tile will kill a character when they move onto it if they do not have rubber boots.
 * Wires is a subclass of the abstract Hazard class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422)
 * @version 2.0
 */
public class Wires extends Hazard {
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_WIRES = ("wires");
	
	/**
     * Constructs an Wires instance. Nothing is needed for it.
     */
	public Wires() {
		super();
	}
	
	/**
	 * Checks if the instance of Collectable passed to the method is an instance of the subclass RubberBoots.
	 * @param c Collectable - The Collectable instance to check.
	 * @return a boolean value response to the result of the check.
	 */
	@Override
	protected boolean checkCollectable(Collectable c) {
		boolean b = false;
		if (c instanceof RubberBoots) {
			b = true;
		}
		return b;
	}
	
	/**
	 * Retrieves the Image for the Wires tile.
	 * @param IMAGE_FOLDER String - the path towards the folder containing all the images.
	 * @return the Image of a Wires tile.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_HAZARD + IMAGE_FN_WIRES + IMAGE_FN_FILETYPE);
	}
    
}