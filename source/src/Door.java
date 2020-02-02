/**
 * Characters will be stopped from moving onto a Door tile if it is closed and they do not meet the conditions to open it.
 * The Door class is an abstract subclass of the Cell class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 27/11/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.2
 */
public abstract class Door extends Cell{
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_DOOR = ("door/"); //folder name
	
	
	//openState boolean - true if the door is open, false if it is closed.
	protected boolean openState;
	
	/**
	 * Constructs a Hazard instance. Nothing is needed for it.
	 */
	public Door() {
		super();
	}
	
	/**
	 * The accessibility of a Door tile is based on if it is open or not.
	 * @param c Character - reference to the Character instance used in the level.
	 * @return the boolean result of the check.
	 */
	public boolean accessCheck(Character c) {
		boolean b = true;
		if (!this.openState) {
			b = doorCheck(c);
		}
		return b;
	}
	
	public int getStateInteger() {
		if (this.openState) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public void setStateInteger(int state) {
		if (state == 1) {
			this.openState = true;
		} else {
			this.openState = false;
		}
	}
	
	/**
	 * Checks if it should open itself based on a array of Collectable instances in character.
	 * @param c Character - The Character instance to check against.
	 * @return a boolean value based on the result of the check.
	 */
	protected abstract boolean doorCheck(Character c);
}
