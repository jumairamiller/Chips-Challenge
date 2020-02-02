import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The Collectable class is an abstract class of the collectables that will be used in levels.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Jumaira Miller (983101)
 * @version 2.1
 */
public abstract class Collectable {
	//Final Variables
	//Image:
	protected final static String IMAGE_FOLDERNAME_COLLECTABLE = ("collectable/"); //folder name
	//file type
	protected final static String IMAGE_FN_FILETYPE = (".png");
	
	/**
	 * Constructs a Collectable instance. Nothing is needed for it at all.
	 */
	public Collectable() {}
	
	/**
	 * A method which will get a Collectable to draw itself.
	 * @param IMAGE_FOLDER String - the path towards the folder containing all the images.
	 * @param gc GraphicsContext - where anything is drawn to.
	 * @param x int - the x coordinate to draw to.
	 * @param y int - the y coordinate to draw to.
	 */
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		gc.drawImage(image(IMAGE_FOLDER), x, y);
	}
	
	/**
	 * Every non-abstract child of Collectable should have it's own Image.
	 * @param IMAGE_FOLDER String - the path towards the folder containing all the images.
	 * @return the Collectable's Image.
	 */
	protected abstract Image image(String IMAGE_FOLDER);
}
