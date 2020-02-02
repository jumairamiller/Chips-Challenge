import javafx.scene.image.Image;

/**
 * Characters will be teleported when they move onto a teleporter tile.
 * The arrival location should be one tile further past the teleporter's pair based on the direction the character moved onto the teleporter.
 * The Teleporter class is a subclass of the Cell class.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.4
 */
public class Teleporter extends Cell{
	//Final Variables
	//Image filenames:
	private final static String IMAGE_FN_TELEPORTER = ("teleporter");
	
	//attributes of a Teleporter instance.
	private Teleporter teleMatch;
	int[] coords;
	
	/**
	 * Initial construction requires no values, these are added later.
	 */
	public Teleporter() {
		super();
	}
	
	/**
	 * Sets the teleporter's information to the passed instances.
	 * @param coords int[] - the teleporter's own coordinates.
	 * @param teleMatch Teleporter - the reference to it's pair.
	 */
	public void setInfo(int[] coords, Teleporter teleMatch) {
		this.coords = coords;
		this.teleMatch = teleMatch;
	}
	
	/**
	 * Characters will be teleported just past this teleporter's pair if they move onto the tile.
	 * @param c Character - The Character instance is passed to the method in order to use it's information.
	 */
	@Override
	public void doAction(Character c) {
		int xChange = 0;
		int yChange = 0;
		switch (c.getDirection()) {
			case "NORTH":
				xChange--;
				break;
			case "EAST":
				yChange++;
				break;
			case "SOUTH":
				xChange++;
				break;
			case "WEST":
				yChange--;
				break;	
		}
		int[] teleMatchCoords = this.teleMatch.getCoords();
		c.setCoords(new int[] {teleMatchCoords[0] + yChange, teleMatchCoords[1] + xChange});
	}
	
	/**
	 * Returns the teleporter instances' coordinates on the map.
	 * @return the small array of int values corresponding to the x and y coordinates.
	 */
	public int[] getCoords() {
		return this.coords;
	}
	
	/**
	 * Returns the teleporter's pair's coordinates on the map.
	 * @return the array of int values corresponding to the x and y coordinates of this teleporter's pair.
	 */
	public int[] getPairCoords() {
		return this.teleMatch.getCoords();
	}
	
	/**
	 * Gets the image of the teleporter.
	 * @param IMAGE_FOLDER String - the path to the correct folder of images.
	 * @return the Image of the teleporter tile.
	 */
	protected Image image(String IMAGE_FOLDER) {
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_CELL + IMAGE_FN_TELEPORTER + IMAGE_FN_FILETYPE);
	}
}
