import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The Cell class is an abstract class of the cells that will be used in levels
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 27/11/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.3
 */
public abstract class Cell {
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_CELL = ("cell/"); //folder name
	//file type
	protected final static String IMAGE_FN_FILETYPE = (".png");
	
	/**
	 * Constructs a Cell instance. Nothing is needed for it at all.
	 */
	public Cell() {}
	
	/**
	 * An method for how the cell's response to the character moving onto them.
	 * Needs to be overridden for any subclass of Cell that will actually do anything upon being moved onto.
	 * @param c Character - implementations of doAction may need the character's information and therefore the Character instance is given to all.
	 */
	public void doAction(Character c) {};
	
	/**
	 * Checks if the Cell in question is available to move onto.
	 * Should be overridden by subclasses if there is a possibility of being false.
	 * @param c Character - Some sub-classes may need to check against character information.
	 * @return the boolean value relating to the Cell's accessibility.
	 */
	public boolean accessCheck(Character c) {
		return true;
	}
	
	/**
	 * A method which will get a Cell to draw itself.
	 * @param IMAGE_FOLDER String - the path to the folder containing the image to be drawn.
	 * @param gc GraphicsContext - the area to draw the image onto.
	 * @param x int - the x coordinate location to draw the cell at.
	 * @param y int - the y coordinate location to draw the cell at.
	 */
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		gc.drawImage(image(IMAGE_FOLDER), x, y);
	}
	
	/**
	 * Every non-abstract child of Cell should have it's own Image.
	 * @param IMAGE_FOLDER String - the path to the folder containing all images for the level.
	 * @return the Cell's Image.
	 */
	protected abstract Image image(String IMAGE_FOLDER);
	
	
	
	
	
	
}