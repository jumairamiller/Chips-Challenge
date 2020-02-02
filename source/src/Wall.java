import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Characters and enemies are not allowed to walk onto Wall tiles.
 * The Wall class is a subclass of the Cell class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 6/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005), Joseph Pugh (975656)
 * @version 2.2
 */
public class Wall extends Cell {
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FOLDERNAME_WALL = ("wall/");
	private final static String IMAGE_FN_WALL = ("wall");
	private final static String IMAGE_FOLDERNAME_SPACE = ("space/");
	private final static String IMAGE_FN_SPACE = ("space");
	private final static int IMAGE_SPACE_DETAIL_NUM = 5; //Highest detail image possible
	
	private boolean isEmptySpace; //indicates if the wall tile is meant to be an space.
	
	/**
	 * Constructs a Wall instance. If this is called it means the Wall is not empty space.
	 */
	public Wall() {
		super();
		this.isEmptySpace = false;
	}
	
	/**
	 * Constructs a Wall instance. This version of the constructor allows setting of the Wall instance being an space.
	 * @param b boolean - needs to be true if the Wall instance being constructed is an empty space.
	 */
	public Wall(boolean b) {
		super();
		this.isEmptySpace = b;
	}

	/**
	 * Since wall tiles can never be moved onto by the player, this method is Overriden from Cell and the return value is set to false.
	 * @param c Character - the character instance being used in the level.
	 */
	public boolean accessCheck(Character c) {
		return false;
	}
	
	/**
	 * Returns the indicator of empty space of the wall cell.
	 * @return the boolean value indicating if the Wall is an empty space.
	 */
	public boolean isEmptySpace() {
		return this.isEmptySpace;
	}
	
	/**
	 * Draws the wall tile.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @param gc GraphicsContext - where to draw to.
	 * @param x int - the x coordinate at which to draw.
	 * @param y int - the y coordinate at which to draw.
	 */
	@Override
	public void draw(String IMAGE_FOLDER, GraphicsContext gc, int x, int y) {
		gc.drawImage(image(IMAGE_FOLDER), x, y);
		if (isEmptySpace) {
			gc.drawImage(detailImage(IMAGE_FOLDER), x, y);
		}
	}
	
	/**
	 * Returns the Image of the Cell. Can be either a wall image or an space image.
	 * @param IMAGE_FOLDER String - the path to the correct image folder.
	 * @return the found wall or empty space image.
	 */
	protected Image image(String IMAGE_FOLDER) {
		String filename = IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL;
		//adds the path to the correct image.
		if (isEmptySpace) {
			filename += IMAGE_FOLDERNAME_SPACE + IMAGE_FN_SPACE;
		} else {
			filename += IMAGE_FOLDERNAME_WALL + IMAGE_FN_WALL;
		}
		return new Image(filename + IMAGE_FN_FILETYPE);
	}
	
	/**
	 * Returns the image of details to be drawn ontop of the space tile.
	 * @param IMAGE_FOLDER String - the path to the image folder to be used.
	 * @return the found detail Image.
	 */
	private Image detailImage(String IMAGE_FOLDER) {
		Random rd = new Random();
		int detailNum = rd.nextInt(IMAGE_SPACE_DETAIL_NUM)+1;
		return new Image(	IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FOLDERNAME_SPACE  +
							Integer.toString(detailNum) + IMAGE_FN_FILETYPE);
	}

}
