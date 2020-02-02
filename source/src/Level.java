import java.util.Arrays;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The Level Class will hold any and all information for a Level being played in the game.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 8/12/19.
 * @author Timothy Roger (977422), Nihal Goindi (976005)
 * @version 1.6
 */
public class Level {
	//Final variables for javafx drawing.
	private static final int GRID_CELL_WIDTH = 100; //cell dimensions
	private static final int GRID_CELL_HEIGHT = 100;
	private static final int GRID_DIM_X = 7; //viewable grid dimensions
	private static final int GRID_DIM_Y = 7; //please KEEP AS ODD number so that player is centered
	//int value for base font size.
	private final static int IMAGE_FONT_SIZE = 56;
	
	//Maximum number of levels.
	private static final int MAX_LEVELID = 10;

	//For working out playtime
	private final static int NANO_SEC_DIVIDER = 1000000000;
	//int values for final score
	private final static int LEVEL_COMPLETION_SCORE = 500;
	private final static int BASE_TIME_SCORE = 1000;
	private final static int TREASURE_SCORE = 230;
	private final static double DEATH_MULTIPLIER_SCORE = 0.5;
	private final static double BASE_TIME_SCORE_MULTIPLIER = 0.025;
	
	//Level instance information.
	private int levelID;
	private Character character;
	private int[] dimensions;
	private Cell[][] map;
	private Enemy[] enemies;
	private int startTime;
	private int time;
	private int prevLoadTime; //the cumulative play-time of the level at the time of the last save.
	private int deathCount;
	
	
	/**
	 * Constructs a Level instance ready to be played.
	 * @param chara Character - the instance of Character to be referenced by the Level object.
	 * @param dim int[] - the dimensions of the two-dimensional array, map.
	 * @param map Cell[][] - holds all instances of Cell that make up the map in a grid-like form.
	 * @param enem Enemy[] - the array of Enemy instances that are on the level.
	 * @param prevTime int - the total time spent during the previous load of the level.
	 * @param deathCount int - the amount of deaths from the previous load of the level.
	 */
	public Level(Character chara, int[] dim, Cell[][] map, Enemy[] enem, int prevTime, int deathCount) {
		this.character = chara;
		this.dimensions = dim;
		this.map = map;
		this.enemies = enem;
		this.prevLoadTime = prevTime;
		this.startTime = Math.round(System.nanoTime()/NANO_SEC_DIVIDER); // Makes note of time at which this Level instance was constructed.
		this.deathCount = deathCount;
	}
	
	/**
	 * Runs a whole turn of the level based on the passed direction.
	 * @param direction String - indicates the direction in which the character is to try and move.
	 */
	public void runTurn(String direction) {
		//Direction of character is changed and new, temporary coordinates are created.
		this.character.setDirection(direction);
		int[] newC = getNewCharacterCoords(direction);
		
		//Checks is the move is possible and sets the character's coordinates to the new ones if so.
		if (validMove(newC)) {
			this.character.setCoords(newC);
		}
		
		//Does the action of the cell the character has just moved onto.
		getCell(this.character.getCoords()).doAction(this.character);
		
		//Each enemy on the level makes moves.
		for (Enemy e: this.enemies) {
			
			//this part of the code re creates the smart enemy as the smart enemy breaks on the 
			//second turn without this. This happens because it inserts the cells it already 
			//visited inside a list so that it will not search them again and simply clearing 
			//those lists inside the enemy class doesn't work.
			if (e instanceof SmartTargetingEnemy) {
				e = new SmartTargetingEnemy(e.getEnemyCoords());
			}
			e.move(this.character, this.map);
		}
		
		//Checks if they have moved onto the character, and therefore if they have killed them.
		for (Enemy e: this.enemies) {
			e.characterCheck(this.character);
		}
	}
	
	/**
	 * Draws the current state of the level to the canvas for the palyer to see.
	 * @param IMAGE_FOLDER String - The path to get to the folder where the images that need to be used are.
	 * @param canvas Canvas - the canvas upon which everything must be drawn.
	 */
	public void draw(String IMAGE_FOLDER, Canvas canvas) {
		//Creates a new GraphicsContext instance to be used and clears the canvas of previous drawing.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//New 2D array of cells which will consist of those the player will be able to see.
		Cell[][] viewMap = new Cell[GRID_DIM_Y][GRID_DIM_X];
		//Character's coordinates indicate the centre of the view.
		int[] center = this.character.getCoords();
		
		//finds the boundries of the for loops to populate the viewable map 2D array.
		int startX = center[0] - (GRID_DIM_X/2);
		int endX = center[0] + (GRID_DIM_X/2);
		int startY = center[1] - (GRID_DIM_Y/2);
		int endY = center[1] + (GRID_DIM_Y/2);
		
		int currViewX = 0;
		int currViewY = 0;
		
		//populate viewMap with correct cells or null values.
		//Empty Space Wall instances will fill out tiles that dont exist in the map.
		for (int i = startY; i <= endY; i++) {
			currViewX = 0;
			if ((i >= 0) && (i < this.map.length)) {
				for (int j = startX; j <= endX; j++) {
					if ((j >= 0) && (j < this.map[0].length)) {
						viewMap[currViewY][currViewX] = this.map[i][j];
					} else {
						viewMap[currViewY][currViewX] = new Wall(true);
					}
					currViewX++;
				}
			} else {
				Arrays.fill(viewMap[currViewY], new Wall(true));
			}
			currViewY++;
		}
		
		//Draws each cell of the viewable map now that it is populated.
		for (int y = 0; y < GRID_DIM_Y; y++) {
			for (int x = 0; x < GRID_DIM_X; x++) {
				if (viewMap[y][x] != null) {
					//Draws the cell.
					viewMap[y][x].draw(IMAGE_FOLDER, gc, x*GRID_CELL_WIDTH, y*GRID_CELL_HEIGHT);
				} 			
			}
		}
		
		//The character is drawn.
		this.character.draw(IMAGE_FOLDER, gc, ((GRID_DIM_X/2))*GRID_CELL_WIDTH, ((GRID_DIM_Y/2))*GRID_CELL_HEIGHT);
		
		//The enemies inside the boundries of the viewable map are drawn.
		int[] tempCoords;
		for (Enemy e: this.enemies) {
			tempCoords = e.getEnemyCoords();
			if (	(tempCoords[0] >= startX) && (tempCoords[0] <= endX) &&
					(tempCoords[1] >= startY) && (tempCoords[1] <= endY)) {
				e.draw(IMAGE_FOLDER, gc, (tempCoords[0]-startX)*GRID_CELL_WIDTH, (tempCoords[1]-startY)*GRID_CELL_HEIGHT);
			}
		}
		
		//The token count of the character is displayed.
		String tokenCount = "Energy: " + Integer.toString(this.character.countEnergy());
		gc.setFill(Color.WHITE);
		gc.setFont(new Font("TechnicLite", IMAGE_FONT_SIZE/2));
		gc.fillText(tokenCount, GRID_CELL_WIDTH/4, GRID_CELL_HEIGHT/2);
		
		//Text has to be displayed if the character has won or died, this is so that the player knows what has happend.
		if (this.character.winCheck()) {
			this.time = getTime();
			//"COMPLETED" is displayed if the character has just won, along with a prompt to press any key to continue.
			gc.setFill(Color.LIMEGREEN);
			gc.setFont(new Font("TechnicLite", IMAGE_FONT_SIZE));
			if (this.levelID == MAX_LEVELID) {
				gc.fillText("THE END", ((GRID_DIM_X/2)-1)*GRID_CELL_WIDTH, ((GRID_DIM_Y/2)-0.5)*GRID_CELL_HEIGHT);
				gc.fillText("Thanks for playing!", ((GRID_DIM_X/2)-2)*GRID_CELL_WIDTH, ((GRID_DIM_Y/2))*GRID_CELL_HEIGHT);
			} else {
				gc.fillText("COMPLETED", ((GRID_DIM_X/2)-1)*GRID_CELL_WIDTH, ((GRID_DIM_Y/2))*GRID_CELL_HEIGHT);
			}
			gc.setFont(new Font("TechnicLite", IMAGE_FONT_SIZE/2.5));
			gc.fillText("   Press Any Key To Continue", ((GRID_DIM_X/2)-1)*GRID_CELL_WIDTH, (((GRID_DIM_Y/2))*GRID_CELL_HEIGHT)+(IMAGE_FONT_SIZE/2.5));
			gc.fillText("Score: "+Integer.toString(getScore()), ((GRID_DIM_X/2))*GRID_CELL_WIDTH, (((GRID_DIM_Y/2)+1)*GRID_CELL_HEIGHT)+(IMAGE_FONT_SIZE/2.5));
			gc.fillText("Time: "+Integer.toString(this.time), ((GRID_DIM_X/2))*GRID_CELL_WIDTH, (((GRID_DIM_Y/2)+1.2)*GRID_CELL_HEIGHT)+(IMAGE_FONT_SIZE/2.5));
		} else if (!this.character.aliveCheck()) {
			//"YOU DIED" is displayed if the character has just been killed, along with a prompt to press any key to restart the level.
			gc.setLineWidth(2.0);
			gc.setStroke(Color.LIMEGREEN);
			gc.setFont(new Font("TechnicLite", IMAGE_FONT_SIZE));
			gc.strokeText("YOU DIED", ((GRID_DIM_X/2)-1)*GRID_CELL_WIDTH, ((GRID_DIM_Y/2))*GRID_CELL_HEIGHT);
			gc.setFill(Color.LIMEGREEN);
			gc.setFont(new Font("TechnicLite", IMAGE_FONT_SIZE/2.5));
			gc.fillText("Press Any Key To Restart", ((GRID_DIM_X/2)-1)*GRID_CELL_WIDTH, (((GRID_DIM_Y/2))*GRID_CELL_HEIGHT)+(IMAGE_FONT_SIZE/2.5));
		}
	}
	
	/**
	 * Checks if the level has been won.
	 * Will be true once the character has reached a goal tile.
	 * @return a boolean value based on result of the check.
	 */
	public boolean winCheck() {
		return this.character.winCheck();
	}
	
	/**
	 * Checks if the level needs to be restarted.
	 * This is true if the level's referenced character is dead.
	 * @return a boolean value based on result of the check.
	 */
	public boolean restartCheck() {
		return !this.character.aliveCheck();
	}
	
	/**
	 * Sets the levelID of the level to a new value.
	 * @param lvlID int - the new value of levelID.
	 */
	public void setLevelID(int lvlID) {
		this.levelID = lvlID;
	}
	
	/**
	 * Gets the levelID value of the Level instance.
	 * @return the int value of levelID.
	 */
	public int getLevelID() {
		return this.levelID;
	}
	
	/**
	 * Retrieves the Cell instance at a certain position in the map.
	 * @param c int[] - The coordinates of the Cell to return.
	 * @return the found Cell instance. 
	 */
	public Cell getCell(int[] c) {
		return this.map[c[1]][c[0]];
	}
	
	/**
	 * Gets the dimensions of the level.
	 * @return the small int array containing the width and length of the map.
	 */
	public int[] getDimensions() {
		return this.dimensions;
	}
	
	/**
	 * Gets the character the level is referencing.
	 * @return the Character instance being used.
	 */
	public Character getCharacter() {
		return this.character;
	}
	
	/**
	 * Works out and returns the cumulative play time of the level.
	 * @return the cumulative time the player has spent alive.
	 */
	public int getTime() {
		return ((Math.round(System.nanoTime()/NANO_SEC_DIVIDER)-this.startTime) + this.prevLoadTime);
	}
	
	/**
	 * Retrieves the current death count of the level instance.
	 * @return the integer value of the death count.
	 */
	public int getDeaths() {
		return this.deathCount;
	}
	
	/**
	 * Sets the number of deaths in the level instance to the passed value.
	 * @param d int - the value to set the death count to.
	 */
	public void setDeaths(int d) {
		this.deathCount = d;
	}
	
	/**
	 * Get the player's score for the level which they have completed.
	 * @return the integer value of score.
	 */
	public int getScore() {
		return (int) (	LEVEL_COMPLETION_SCORE + //Base Score
						(BASE_TIME_SCORE/(this.time*(BASE_TIME_SCORE_MULTIPLIER+(DEATH_MULTIPLIER_SCORE*this.deathCount)))) + //Score for how well level was done (relates to time and no. deaths)
						(TREASURE_SCORE * this.character.countTreasure()) //Treasure adds score!
						);
	}
	
	/**
	 * Gets the array of enemies on the level.
	 * @return the array of Enemy instances being used in the level.
	 */
	public Enemy[] getEnemies() {
		return this.enemies;
	}
	
	/**
	 * Checks if the passed coordinates are outside the dimensional bounds of the map.
	 * @param c int[] - The coordinates to be checked.
	 * @return the response as a boolean variable.
	 */
	private boolean outOfBoundsCheck(int[] c) {
		return (!((c[0] >= this.dimensions[0]) || (c[0] < 0) ||
				(c[1] >= this.dimensions[1]) || (c[1] < 0)));
	}
	
	/**
	 * Finds the map coordinates of where the character will want to move to based on passed direction.
	 * @param direction String - Signifies direction of movement on the map. Should be one of the four directions of a compass. Anything else wont change the characte's current coordinates.
	 * @return The wanted character coordinates.
	 */
	private int[] getNewCharacterCoords(String direction) {
		int[] oldC = this.character.getCoords();
		int[] newC = new int[2];
		switch (direction) {
			case "NORTH":
				newC = new int[] {oldC[0], oldC[1]-1};
				break;
			case "EAST":
				newC = new int[] {oldC[0]+1, oldC[1]};
				break;
			case "SOUTH":
				newC = new int[] {oldC[0], oldC[1]+1};
				break;	
			case "WEST":
				newC = new int[] {oldC[0]-1, oldC[1]};
				break;
			default:
				//do nothing.
				break;		
		}
		return newC;
	}
	
	/**
	 * Returns a boolean value based on the combined results on checks for the validity of the character's move attempt.
	 * @param c int[] - The coordinates to be checked.
	 * @return the response as a boolean value.
	 */
	private boolean validMove(int[] c) {
		boolean b = false;
		if (outOfBoundsCheck(c)) {
			b = getCell(c).accessCheck(this.character);
		}
		return b;
	}
}
