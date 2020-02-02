import javafx.scene.image.Image;

/**
 * Goal tiles will make the character win when they move onto it.
 * The Goal class is a subclass of the Cell class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422)
 * @version 1.7
 */
public class Goal extends Cell{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_GOAL = ("goal");
	
	/**
	 * Constructs a Goal instance. Nothing is needed for it.
	 */
	public Goal() {
		super();
	}
	
	/**
	 * When the character moves onto goal they will win the level.
	 * @param c Character - the character instance being used in the level.
	 */
	@Override
	public void doAction(Character c) {
		c.winLevel();
	}
	
	
	/**
	 * Returns the Image for the Goal cell.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FN_GOAL + IMAGE_FN_FILETYPE);
	}
}
