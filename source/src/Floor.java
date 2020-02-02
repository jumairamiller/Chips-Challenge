import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Characters will collect any Collectables on a floor tile they move on if there is one.
 * The Floor class is a subclass of the Cell class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 6/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 2.2
 */
public class Floor extends Cell{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FOLDERNAME_FLOOR = ("floor/");
	private final static String IMAGE_FN_FLOOR = ("floor");
	private final static int IMAGE_FLOOR_DETAIL_NUM = 4; //Highest detail image possible
	
	//collSlot Collectable - Potential reference to a Collectable instance which the character could collect.
	private Collectable collSlot;
	private int detailNum;
	
	/**
	 * The constructor will take a Collectable instance to be referenced in collSlot.
	 * @param coll Collectable - the collectable to be referenced by the Floor instance.
	 */
	public Floor(Collectable coll) {
		super();
		this.collSlot = coll;
		findDetailNum();
	}
	
	/**
	 * The character will collect any collectable on the floor tile it moves on.
	 * @param c Character - The instance of Character to be used.
	 */
	@Override
	public void doAction(Character c) {
		if (collPresent()) {
			c.addCollectable(collSlot);
			collSlot = null;
		}		
	}
	
	/**
	 * Checks if there is an actual instance of Collectable referenced by the calling Floor instance.
	 * @return boolean value equal to the result of the check.
	 */
	public boolean collPresent() {
		boolean b = true;
		if (collSlot == null) {
			b = false;
		} 
		return b;
	}
	
	/**
	 * Gets the Collectable referneced by the Floor cell.
	 * @return the Collectable instance.
	 */
	public Collectable getCollectable() {
		return collSlot;
	}
	
	/**
	 * Sets the collSlot reference to the passed instance of Collectable.
	 * @param coll Collectable - the instance to now be referenced.
	 */
	public void setCollectable(Collectable coll) {
		this.collSlot = coll;
	}
	
	/**
	 * Draws the Floor cell image and gets the Collectable instance in collSlot to draw itself it there is one too.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @param gc GraphicsContext - Where to draw the Images.
	 * @param x int - X coordinate in pixels of where to draw the Images on the pane.
	 * @param y int - Y coordinate in pixels of where to draw the Images on the pane.
	 */
	@Override
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		gc.drawImage(image(IMAGE_FOLDER), x, y);
		if (this.detailNum != 0) {
			gc.drawImage(detailImage(IMAGE_FOLDER), x, y);
		}
		if (collPresent()) {
			collSlot.draw(IMAGE_FOLDER, gc, x, y);
		}
	}

	/**
	 * Gets the image for the floor tile.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the found floor image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_FLOOR + IMAGE_FN_FLOOR + IMAGE_FN_FILETYPE);
	}
	
	/**
	 * Gets the image for the floor detail to be shown.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the found detail image.
	 */
	private Image detailImage(String IMAGE_FOLDER) {
		return new Image(	IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_FLOOR +
							Integer.toString(this.detailNum) + IMAGE_FN_FILETYPE);
	}
	
	/**
	 * Sets the number value for the random details added to the floor tile.
	 */
	private void findDetailNum() {
		Random rd = new Random();
		if (rd.nextInt(4) == 0) {
			this.detailNum = rd.nextInt(IMAGE_FLOOR_DETAIL_NUM)+1;
		} else {
			this.detailNum = 0;
		}
		
	}

}
