/**
 * Characters will die if they do not have a specific collectable when moving onto a hazard.
 * The Hazard class is an abstract subclass of the Cell class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 25/11/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.3
 */
public abstract class Hazard extends Cell {
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_HAZARD = ("hazard/"); //folder name
	
	/**
	 * Constructs a Hazard instance. Nothing is needed for it.
	 */
	public Hazard() {
		super();
	}
	
	/**
	 * Should kill a character if they do not have a required collectable.
	 * @param c Character - The Character instance is passed to the method in order to use it's information.
	 */
	@Override
	public void doAction(Character c) {
		if (!checkCol(c.getInventory())) {
			c.killCharacter();
		}
	}

	/**
	 * Checks if the passed Collectable is an instance of the required subclass.
	 * Abstract so that all subclasses of Hazard have to implement this with their own required instance of Collectable
	 * @param c Collectable - The instance of Collectable to check against
	 * @return a boolean value based on result of the check.
	 */
	protected abstract boolean checkCollectable(Collectable c);
	
	/**
	 * Checks if an instance of a particular type of Collectable is present in a list of Collectable instances.
	 * @param col Collectable[] - The array of Collectable instances to check through.
	 * @return a boolean value based on the result of the check.
	 */
	private boolean checkCol(Collectable[] col) {
		boolean b = false;
		for (Collectable c: col) {
			if (checkCollectable(c)) {
				b = true;
			}
		}
		return b;
	}
}
