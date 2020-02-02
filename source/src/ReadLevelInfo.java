/**
 * Processes the information about a level and constructs a Level object.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422)
 * @version 1.4
 */
public class ReadLevelInfo {
	
	/**
	 * Creates a level object, ready to be played, using the information in the passed list of strings.
	 * @param info String[] - The list of strings containing all the data from a level file. Each element is a line of the file.
	 * @return the initialised and completed Level object, ready to be played.
	 */
	public static Level readConstructInfo(String[] info) {
		//Finds dimensions of map.
		int[] dimensions = readDim(info[0]);
		
		//Populates map.
		Cell[][] tempMap = new Cell[dimensions[1]][dimensions[0]];
		for (int h = 0; h < dimensions[1]; h++) {
			tempMap[h] = readMapLine(info[h+1]);
		}
		
		//Makes character instance.
		Character character = readCharInfo((info[dimensions[1]+1]).split(","));
		//Finds current cumulative passed time in level. 0 unless a saved file.
		int tempTime = Integer.parseInt(((info[dimensions[1]+1]).split(","))[2]);
		//Finds cumulative amount of deaths in level. 0 unless a saved file.
		int deathCount = Integer.parseInt(((info[dimensions[1]+1]).split(","))[3]);
		
		//Processes additional information.
		Enemy[] tempEnemies = new Enemy[0];
		for (int i = dimensions[1]+2; i < info.length; i++) {
			String[] temp = info[i].split(",");
			if (temp[2].equals("ENEMY")) { //Adds enemy to enemy array.
				tempEnemies = addEnemy(tempEnemies, temp);
			} else if (temp[2].equals("TELEPORTER")) { //Deals with teleporter pair.
				tempMap = addTeleInfo(tempMap, temp, info[i+1].split(","));
				i++;
				//Could be more lenient with how the additional information in the level file is written.
			} else { //Any other additional information: Doors, KeyCards
				tempMap = addInfo(tempMap, temp);
			}
		}
		//Creates and returns the Level instance with all the information that has just been processed.
		Level lvl = new Level(character, dimensions, tempMap, tempEnemies, tempTime, deathCount);
		
		if (info[0].split(",").length > 2) {
			lvl.setLevelID(Integer.parseInt(info[0].split(",")[2]));
		}
		return lvl;
	}
	
	/**
	 * Creates an array of strings based on the passed level.
	 * This is used when saving a state of a level.
	 * @param lvl Level - the Level instance to be saved.
	 * @return the finished array of Strings to be written into a file presumably.
	 */
	public static String[] makeConstructInfo(Level lvl) {
		//Adds lines to a String and then at the end it is split at every new line to make the array.
		int[] dim = lvl.getDimensions();
		int lvlID = lvl.getLevelID();
		String infoS = Integer.toString(dim[0]) + "," + Integer.toString(dim[1]) + "," + Integer.toString(lvlID) + "\n";
		
		for (int i = 0; i < dim[1]; i++) {
			infoS += makeMapLine(lvl, i, dim[0]) + "\n";
		}
		
		infoS += makeCharLine(lvl.getCharacter(), lvl.getTime(), lvl.getDeaths());
	
		infoS += makeAddInfo(lvl);
		
		return infoS.split("\n");
	}
	
	
	//READ CONSTRUCT INFO SUBMETHODS
	
	/**
	 * Takes a String containing the dimensions of the map (in number of cells) and convert it into a pair of integer values.
	 * @param dimInfo String - contains the dimensions of the map as "H,W", where H is the height integer and W the width.
	 * @return the array containing the integers of the height and width of the map.
	 */
	private static int[] readDim(String dimInfo) {
		String[] temp = dimInfo.split(",");
		int[] dim = {Integer.parseInt(temp[0]),Integer.parseInt(temp[1])};
		return dim;
	}
	
	/**
	 * Creates a list of Cell objects based on a string of characters.
	 * @param lineInfo String - has characters corresponding to a certain type of cell, making up one row of cells in the map.
	 * @return the created list of Cell objects.
	 */
	private static Cell[] readMapLine(String lineInfo) {
		Cell[] lineList = new Cell[lineInfo.length()];
		for (int w = 0; w < lineInfo.length(); w++) {
			lineList[w] = createCell(lineInfo.charAt(w));
		}
		return lineList;
	}
	
	/**
	 * Creates a Cell object based on the character passed to it.
	 * @param cellSymb char - determines the subclass of Cell to be created.
	 * @return the created object masked as a Cell object.
	 */
	private static Cell createCell(char cellSymb) {
		Cell cell = null;
		if (cellSymb == '#') {
			cell = (Cell) new Wall();
		} else if (cellSymb == ',') {
			cell = (Cell) new Wall(true);
		} else if (cellSymb == 'G') {
			cell = (Cell) new Goal();
		} else if (cellSymb == 'A') {
			cell = (Cell) new Acid();
		} else if (cellSymb == 'W') {
			cell = (Cell) new Wires();
		} else if (cellSymb == '-') {
			cell = (Cell) new ColouredDoor();
		} else if (cellSymb == '=') {
			cell = (Cell) new PoweredDoor();
		} else if (cellSymb == 'T') {
			cell = (Cell) new Teleporter();
		} else if (cellSymb == 'H') {
			cell = (Cell) new Floor((Collectable) new HazmatSuit());
		} else if (cellSymb == 'R') {
			cell = (Cell) new Floor((Collectable) new RubberBoots());
		} else if (cellSymb == 'O') {
			cell = (Cell) new Floor((Collectable) new Energy());
		} else if (cellSymb == 'X') {
			cell = (Cell) new Floor((Collectable) new Treasure());
		} else {
			cell = (Cell) new Floor(null); // ' ' (Floor), 'E' (Enemy) or 'K' (Keycard)
		}
		return cell;
	}
	
	/**
	 * Creates a Character instance based on an array of strings. This includes saved inventory.
	 * @param charInfo String[] - Contains the information needed to create the Character instance.
	 * @return the constructed Character instance to be used.
	 */
	private static Character readCharInfo(String[] charInfo) {
		int[] charcoords = { Integer.parseInt(charInfo[0]) , Integer.parseInt(charInfo[1]) };
		Character character = new Character(charcoords);
		for (int i = 4; i < charInfo.length; i++) {
			character.addCollectable(createColl(charInfo[i]));
		}
		return character;
	}
	
	/**
	 * Creates a Collectable instance based of a small String passed to the method.
	 * @param collInfo String - Contains all information for the Collectable instance.
	 * @return The constructed Collectable.
	 */
	private static Collectable createColl(String collInfo) {
		Collectable tempColl = null;
		if (collInfo.length() == 1) {
			if (collInfo.equals("H")) {
				tempColl = new HazmatSuit();
			} else if (collInfo.equals("R")) {
				tempColl = new RubberBoots();
			} else if (collInfo.equals("O")) {
				tempColl = new Energy();
			} else if (collInfo.equals("X")) {
				tempColl = new Treasure();
			}
		} else {
			String[] collArr = collInfo.split("");
			if (collArr[0].equals("K")) {
				if (collArr[1].equals("B")) {
					tempColl = new KeyCard("BLUE");
				} else if (collArr[1].equals("R")) {
					tempColl = new KeyCard("RED");
				} else if (collArr[1].equals("G")) {
					tempColl = new KeyCard("GREEN");
				} else if (collArr[1].equals("P")) {
					tempColl = new KeyCard("PURPLE");
				}
			} //In case of any new Collectables being added that have additional information.
		}
		return tempColl;
	}
	
	/**
	 * Based on information in an array of strings, values are added to certain Cell instances in the map.
	 * @param tempMap Cell[][] - The temporary map to be changed.
	 * @param info String[] - Holds information about the change that needs to be made.
	 * @return the temporary map that has been changed.
	 */
	private static Cell[][] addInfo(Cell[][] tempMap, String[] info) {
		int[] coords = {Integer.parseInt(info[0]),Integer.parseInt(info[1])};
		if (info[2].equals("DOOR")) {
			if (info[3].equals("TOKEN")) {
				((PoweredDoor)tempMap[coords[1]][coords[0]]).setReqEnergy(Integer.parseInt(info[4]));
			} else if (info[3].equals("KEY")) {
				((ColouredDoor)tempMap[coords[1]][coords[0]]).setColour(info[4]);
			}
			if (info.length > 5) {
				((Door)tempMap[coords[1]][coords[0]]).setStateInteger(Integer.parseInt(info[5]));
			}
		} else if (info[2].equals("KEY")){
			((Floor)tempMap[coords[1]][coords[0]]).setCollectable((Collectable) new KeyCard(info[3]));
		}
		return tempMap;
	}
	
	/**
	 * Adds information to a Teleporter pair using two lines of the level file as String arrays.
	 * @param tempMap Cell[][] - the temporary map in which the Teleporter instances reside.
	 * @param tele1Info String[] - Holds all needed information for the first of the pair.
	 * @param tele2Info String[] - Holds all needed information for the second of the pair.
	 * @return the temporary map with the new information added.
	 */
	private static Cell[][] addTeleInfo(Cell[][] tempMap, String[] tele1Info, String[] tele2Info) {
		int[] coords1 = {Integer.parseInt(tele1Info[0]),Integer.parseInt(tele1Info[1])};
		int[] coords2 = {Integer.parseInt(tele2Info[0]),Integer.parseInt(tele2Info[1])};
		Teleporter tele1 = (Teleporter) tempMap[coords1[1]][coords1[0]];
		Teleporter tele2 = (Teleporter) tempMap[coords2[1]][coords2[0]];
		tele1.setInfo(coords1, tele2);
		tele2.setInfo(coords2, tele1);
		tempMap[coords1[1]][coords1[0]] = (Cell) tele1;
		tempMap[coords2[1]][coords2[0]] = (Cell) tele2;
		return tempMap;
	}
	
	/**
	 * Adds an Enemy to the passed array of Enemies.
	 * @param tempEnemies Enemy[] - the current array of Enemy instances.
	 * @param info String[] - holds all information about the enemy to be added.
	 * @return the array of Enemy instances which should now include one more.
	 */
	private static Enemy[] addEnemy(Enemy[] tempEnemies, String[] info) {
		int[] coords = {Integer.parseInt(info[0]),Integer.parseInt(info[1])};
		Enemy[] evenTemperEnemies = new Enemy[tempEnemies.length+1];
		Enemy tempEnemy = null;
		if (info[3].equals("LINE")) {
			tempEnemy = new StraightLineEnemy(coords, info[4]);
		} else if (info[3].equals("WALL")) {
			tempEnemy = new WallFollowingEnemy(coords, info[4]);
		} else if (info[3].equals("DUMB")) {
			tempEnemy = new DumbTargetingEnemy(coords);
		} else if (info[3].equals("SMART")) {
			tempEnemy = new SmartTargetingEnemy(coords);
		}
		
		//New enemy is added onto the existing array of enemies.
		for (int i = 0; i < tempEnemies.length; i++) {
			evenTemperEnemies[i] = tempEnemies[i];
		}
		evenTemperEnemies[tempEnemies.length] = tempEnemy;
		return evenTemperEnemies;
	}	
	
	
	// MAKE CONSTRUCT INFO SUBMETHODS
	
	/**
	 * Makes a line of characters based on the cells in a row of the level's map.
	 * @param lvl Level - the instance of Level the row will be from.
	 * @param rowN int - the index of the row to be converted.
	 * @param rowLen int - the length of the row, which is the same value as the width of the map.
	 * @return The String containing the line of characters.
	 */
	private static String makeMapLine(Level lvl, int rowN, int rowLen) {
		String line = "";
		for (int i = 0; i < rowLen; i++) {
			line += getCellChar(lvl.getCell(new int[]{i,rowN}));
		}
		return line;
	}
	
	/**
	 * Returns a character based on the Cell passed to the method.
	 * @param c Cell - the cell instance to be used.
	 * @return the char value signifying the Cell type.
	 */
	private static char getCellChar(Cell c) {
		char ch;
		if (c instanceof Wall) {
			if (((Wall)c).isEmptySpace()) {
				ch = ',';
			} else {
				ch = '#';
			}
		} else if (c instanceof Goal) {
			ch = 'G';
		} else if (c instanceof Acid) {
			ch = 'A';
		} else if (c instanceof Wires) {
			ch = 'W';
		} else if (c instanceof ColouredDoor) {
			ch = '-';
		} else if (c instanceof PoweredDoor) {
			ch = '=';
		} else if (c instanceof Teleporter) {
			ch = 'T';
		} else if (c instanceof Floor) {
			if (((Floor)c).collPresent()) {
				Collectable col = ((Floor)c).getCollectable();
				if (col instanceof HazmatSuit) {
					ch = 'H';
				} else if (col instanceof RubberBoots) {
					ch = 'R';
				} else if (col instanceof Energy) {
					ch = 'O';
				} else if (col instanceof KeyCard) {
					ch = 'K';
				} else if (col instanceof Treasure) {
					ch = 'X';
				} else {
					ch = ' ';
				}
			} else {
				ch = ' ';
			}
		} else {
			ch = ',';
			//a default value (not really needed)
		}
		return ch;
	}
	
	/**
	 * Makes the line with all the character's information, which also includes the level's current cumulative time.
	 * @param c Character - the Character instance to be used.
	 * @param time int - the current cumulative playtime of the level to be added.
	 * @param deathCount int - the current cumulative deaths of the level to be added.
	 * @return the String with the finished line.
	 */
	private static String makeCharLine(Character c, int time, int deathCount) {
		int[] crds = c.getCoords();
		String charLine = 	Integer.toString(crds[0]) + "," + Integer.toString(crds[1]) + "," + Integer.toString(time) + "," + Integer.toString(deathCount);
		//The collectables in the character's inventory are added to the line.
		Collectable[] colls = c.getInventory();
		for (Collectable coll : colls) {
			charLine += "," + getCollectableString(coll);
		}
		return charLine + "\n";
	}
	
	/**
	 * Creates a String based on the passed Collectable instance.
	 * @param c Collectable - the Collectable instance to be used.
	 * @return the finished String.
	 */
	private static String getCollectableString(Collectable c) {
		String s;
		if (c instanceof HazmatSuit) {
			s = "H";
		} else if (c instanceof RubberBoots) {
			s = "R";
		} else if (c instanceof Energy) {
			s = "O";
		} else if (c instanceof Treasure) {
			s = "X";
		} else if (c instanceof KeyCard) {
			s = "K";
			//KeyCards need extra information.
			String colour = ((KeyCard)c).getColour();
			switch (colour) {
				case "BLUE":
					s += "B";
					break;
				case "GREEN":
					s += "G";
					break;
				case "PURPLE":
					s += "P";
					break;
				case "RED":
					s += "R";
					break;
			}
		} else {
			s = ""; ///Should never be the case
		}
		return s;
	}
	
	/**
	 * Creates a String including all the lines of additional information needed to save the Level state.
	 * @param lvl Level - the level instance to be used.
	 * @return the finished String.
	 */
	private static String makeAddInfo(Level lvl) {
		String addInfo = "";
		
		//Enemys are converted and added to the String.
		Enemy[] enems = lvl.getEnemies();
		for (Enemy e: enems) {
			addInfo += makeEnemyLine(e);
		}
		
		//The map is gone through again and the additional information for certain cells are added to the String.
		int[] dim = lvl.getDimensions();
		int[][] finishedTeleCoords = {}; //Array of coordinates of Teleporter cells whose information has already been added.
		for (int y = 0; y < dim[1]; y++) {
			for (int x = 0; x < dim[0]; x++) {
				Cell c = lvl.getCell(new int[]{x,y});
				if (c instanceof Door) { //Doors can be Key (Coloured) or Token (Powered).
					addInfo += Integer.toString(x) + "," + Integer.toString(y) + "," + "DOOR,";
					if (c instanceof ColouredDoor) {
						addInfo += "KEY," + ((ColouredDoor) c).getColour() + "," + Integer.toString(((ColouredDoor) c).getStateInteger()); //Key Doors have a Colour.
					} else if (c instanceof PoweredDoor) {
						addInfo += "TOKEN," + Integer.toString(((PoweredDoor) c).getReqEnergy()) + "," + Integer.toString(((PoweredDoor) c).getStateInteger()); //Token Doors have a required Energy.
					}
					addInfo += "\n";
				} else if (c instanceof Floor) {
					if ((((Floor) c).getCollectable()) instanceof KeyCard) {
						KeyCard col = (KeyCard)(((Floor) c).getCollectable());
						addInfo += 	Integer.toString(x) + "," + Integer.toString(y) + "," + 
									"KEY," + col.getColour() + "\n";
					}
				} else if (c instanceof Teleporter) {
					int[] teleCoords = ((Teleporter)c).getCoords();
					boolean cont = true;
					//Checks if the teleporter has already been added.
					for (int[] crd: finishedTeleCoords) {
						if (teleCoords[0] == crd[0] && teleCoords[1] == crd[1]) {
							cont = false;
						}
					}
					
					if (cont) {
						//Both Teleporters' information is added, because they need to be next to each other in the file.
						int[] teleMatchCoords = ((Teleporter)c).getPairCoords();
						
						int[][] tempfinishedTeleCoords = new int[finishedTeleCoords.length+2][2];
						for (int i = 0; i < finishedTeleCoords.length; i++) {
							tempfinishedTeleCoords[i] = finishedTeleCoords[i];
						}
						tempfinishedTeleCoords[finishedTeleCoords.length] = teleCoords;
						tempfinishedTeleCoords[finishedTeleCoords.length+1] = teleMatchCoords;
						finishedTeleCoords = tempfinishedTeleCoords;
						addInfo += 	Integer.toString(teleCoords[0]) + "," + Integer.toString(teleCoords[1]) + "," + 
								"TELEPORTER,\n";
						addInfo += 	Integer.toString(teleMatchCoords[0]) + "," + Integer.toString(teleMatchCoords[1]) + "," + 
								"TELEPORTER,\n";
					}
				}
			}
		}
		return addInfo;
	}
	
	/**
	 * A String is created based on the passed Enemy's information.
	 * @param e Enemy - the enemy instance to be used.
	 * @return the finished String.
	 */
	private static String makeEnemyLine(Enemy e) {
		int[] crds = e.getEnemyCoords();
		String s = Integer.toString(crds[0]) + "," + Integer.toString(crds[1]) + ",ENEMY,";
		if (e instanceof StraightLineEnemy) {
			s += "LINE," + ((StraightLineEnemy) e).getDirection(); //Line enemies have a direction.
		} else if (e instanceof WallFollowingEnemy) {
			s += "WALL," + ((WallFollowingEnemy) e).getDirection(); //Wall enemies have a direction.
		} else if (e instanceof DumbTargetingEnemy) {
			s += "DUMB";
		} else {
			s += "SMART";
		}
		return s + "\n";
	}
	
	
}
