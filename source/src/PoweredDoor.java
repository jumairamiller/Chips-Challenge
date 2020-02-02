import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Characters will be stopped from moving onto a Powered Door tile if it is closed and they do not have enough energy to open it.
 * The PoweredDoor class is a subclass of the abstract Door class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.4
 */
public class PoweredDoor extends Door {
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_POWERED = ("powered/"); //folder name
	//Structure:
	//openness
	protected final static String IMAGE_FN_OPEN = ("open");
	protected final static String IMAGE_FN_CLOSED = ("closed");
	
	//reqTokes int - the amount of energy required to open the powered door.
	private int reqEnergy;
	
	/**
	 * Constructs a PoweredDoor instance. Nothing is needed for it. 
	 */
	public PoweredDoor() {
		super();
	}
	
	/**
	 * Sets the required energy to the passed value.
	 * @param r int - the new value of the required energy.
	 */
	public void  setReqEnergy(int r) {
		this.reqEnergy = r;
	}
	
	/**
	 * Retrieves the value of energy required to open the instance of Powered Door.
	 * @return the int value of required energy.
	 */
	public int getReqEnergy() {
		return this.reqEnergy;
	}
	
	/**
	 * Draws the Powered Door cell image and the amount of energy needed to open to door on top of it.
	 * @param gc GraphicsContext - Where to draw the Images.
	 * @param x int - X coordinate in pixels of where to draw the Images on the pane.
	 * @param y int - Y coordinate in pixels of where to draw the Images on the pane.
	 */
	@Override
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		gc.drawImage(image(IMAGE_FOLDER), x, y);
		if (!this.openState) {
			gc.setFill(Color.GRAY);
			gc.setFont(new Font("TechnicLite", 28));
			gc.fillText(Integer.toString(this.reqEnergy), x + (1.5*gc.getFont().getSize()), y + (2*gc.getFont().getSize()));
		}
	}

	/**
	 * Returns a boolean value based on if the passed Character instance's array of Collectable instances contains enough Energy.
	 * @param c Character - The Character instance with the array of Collectables to check against.
	 * @return a boolean value based on the result of the check.
	 */
	@Override
	protected boolean doorCheck(Character c) {
		boolean b = false;
		int count = 0;
		for (Collectable col: c.getInventory()) {
			if (col instanceof Energy) {
				count++;
			}
		}
		if (count >= this.reqEnergy) {
			b = true;
			this.openState = true;
			c.setInventory(removeEnergy(c.getInventory()));
		}
		return b;
	}
	
	/**
	 * Retrieves the correct image based openness of the powered door instance.
	 * @return the Image object with the correct image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_DOOR + IMAGE_FOLDERNAME_POWERED; //img/cell/door/powered/....
		
		//adds the correct path for openness
		if (openState == true) {
			filename += IMAGE_FN_OPEN;
		} else {
			filename += IMAGE_FN_CLOSED;
		}
		
		return new Image(filename + IMAGE_FN_FILETYPE); //adds the correct filetype, makes the new Image object and returns it.
	}
	
	/**
	 * Remove the required amount of Energy instances from a passed array of Collectable instances.
	 * @param c Collectable[] - the array of Collectable instances to change.
	 * @return the new Collectable array without the KeyCard instance.
	 */
	private Collectable[] removeEnergy(Collectable[] c) {
		Collectable[] newC = new Collectable[c.length-this.reqEnergy];
		int count = this.reqEnergy;
		int j = 0;
		for (int i = 0; i < c.length; i++) {
			if (!(c[i] instanceof Energy) || count == 0) {
				newC[j] = c[i];
				j++;
			} else {
				count--;
			}
		}
		return newC;
	}
}