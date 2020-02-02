import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Game Class will deal with the construction of, running of and the changes in between levels of the game.
 * <br> Creation Date: 26/11/19.
 * <br> Last Modification Date: 4/12/19.
 * @author Timothy Roger (977422), Szymon Grzech (988065)
 * @version 1.4
 */
public class Game {
	//Final Variables to do with the window.
	//Canvas dimensions in pixels.
	private static final int CANVAS_WIDTH = 700;
	private static final int CANVAS_HEIGHT = 700;
	
	//Image file path + skin information
	private static final int MAX_SKIN_NUM = 2; //current maximum number of skins for the game
	private static final String IMAGE_FOLDER_START = ("img");
	private static final String IMAGE_FOLDER_END = ("/");
	
	//Maximum number of levels.
	private static final int MAX_LEVELID = 10;
	
	//Private Attributes
	private Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT); // the canvas for the game to be drawn on.
	private InGameMenu menu = new InGameMenu(); // only one instance of the in game menu is needed.
	private AlertBox confirmBox = new AlertBox(); // only one instance of the alert box is needed (for "are you sure?" prompts).
	
	private Level level;
	private String username;
	private int levelID;
	private int skinNum; // indicates current skin of game to be used.
	
	/**
	 * Constructs a Game instance
	 */
	public Game() {
		this.skinNum = 1; // when a game instance is first initialised, the skin indicator is set to 1, which is the default.
	}
	
	/**
	 * Starts the game for the player from a point based on the parameters handed to the method.
	 * @param username String - the username of the player playing the game, will be used if saving a position in a level.
	 * @param levelID int - indicates the level the player will be playing in the game.
	 * @param isSaveFile boolean - will be true if the player is going to play from a saved location.
	 * @param deathCount int - the current cumulative deathcount the player has on the level.
	 */
	public void runGame(String username, int levelID, boolean isSaveFile, int deathCount) {
		//sets some of Game's attributes to the right values.
		this.username = username;
		this.levelID = levelID;
		//creates the correct level instance and makes the level attribute refer to it.
		this.level = ReadLevelInfo.readConstructInfo(FileReader.readFile(username, this.levelID, isSaveFile));
		this.level.setDeaths(deathCount);
		//draws the initial state of the loaded level instance.
		if (this.levelID == 0) {
			this.levelID = this.level.getLevelID();
		} else {
			this.level.setLevelID(this.levelID);
		}
		drawGame();
	}
	
	/**
	 * Creates a Pane instance for the game. 
	 * This includes a button to open the in game menu and space for the display of the gameplay itself.
	 * @return the created Pane object.
	 */
	public Pane buildGUI() {
		BorderPane root = new BorderPane();
		root.setCenter(canvas);
		
		VBox toolbar = new VBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10,10,10,10));
		root.setTop(toolbar);

		// Binds the arrow keys with the character to make it move.
		root.addEventFilter(KeyEvent.KEY_PRESSED, event ->	processKeyEvent(event));
		
		// Menu button for the in-game menu.
		Button menuButton = new Button("Menu");
		menuButton.setOnAction(e ->{
			// Deals with the results from clicks in the in-game menu.
			int i = menu.display();
			switch(i) {
				case 1: //Restarts the level
					if (confirmBox.display("Restart The Level", "Are you sure you want to restart the level?") == true) {
						runGame(this.username, this.levelID, false, this.level.getDeaths()+1);
					}
					break;
					
				case 2: //Saves player's level state and exits the game.
					if (confirmBox.display("Save and Exit Game", "Are you sure you want to save and leave the game?") == true) {
						FileReader.writeFile(this.username, ReadLevelInfo.makeConstructInfo(this.level));
						Stage stage = new Stage();
						Pane menuPane;
						try {
							menuPane = (Pane) FXMLLoader.load(getClass().getResource("MainMenuFXML.fxml"));
							Scene chooseUserMenu = new Scene(menuPane);
							stage.setScene(chooseUserMenu);
							stage.show();
						} catch (IOException e1) {
							e1.printStackTrace();
							System.exit(0);
						}
						Stage stage2 = (Stage) menuButton.getScene().getWindow();
						stage2.close();
					}
					break;
					
				case 3: //Changes the skin of the game.
					if (confirmBox.display("Change Skin", "Do you want to change the skin of the game?") == true) {
						changeSkin();
						drawGame();
					}
					break;
						
				default:
					//nothing is done.
					break;
			}
		});
		
		//adds the menu button to the toolbar.
		toolbar.getChildren().addAll(menuButton);
		
		return root;
	}
	
	private void returnToMain() {
		Stage stage = new Stage();
		Pane menuPane;
		try {
			menuPane = (Pane) FXMLLoader.load(getClass().getResource("MainMenuFXML.fxml"));
			Scene chooseUserMenu = new Scene(menuPane);
			stage.setScene(chooseUserMenu);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		Stage stage2 = (Stage) canvas.getScene().getWindow();
		stage2.close();
	}
	
	/**
	 * Process a key event due to a key being pressed, e.g., to the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event) {
		
		if (level.winCheck()) {//If the player has just gotten to the goal of the level, the next level is started upon a keypress.
			
			FileReader.addScore(this.username, this.levelID, this.level.getScore());
			if (this.levelID == MAX_LEVELID) {
				returnToMain();
			} else {
				FileReader.addScore(this.username, this.levelID+1, 0);
				runGame(this.username, this.levelID + 1, false, 0); //increments the levelID so that the next level is run.#
			}
		} else if (level.restartCheck()) { //If the player has just died, the level is restarted upon a keypress.
			runGame(this.username, this.levelID, false, this.level.getDeaths()+1); //will run the game from the start of the level the player died on.
		} else { //Deal with the 4 possible directions the player can input using the arrow keys.
			String direction = null;
			switch (event.getCode()) {
				
			    case RIGHT:
			    	direction = "EAST";
		        	break;
		        	
			    case LEFT:
			    	direction = "WEST";
			    	break;
			    	
			    case UP:
			    	direction = "NORTH";
			    	break;
			    	
			    case DOWN:
			    	direction = "SOUTH";
			    	break;
			    	
		        default:
		        	direction = "."; //default value, avoids errors.
		        	break;
			}
			
			level.runTurn(direction); //Runs the next turn of the level based on the inputed direction.
		}
		
		// Redraw game with the new level state.
		drawGame();
		
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
	
	}
	
	/**
	 * Draws the game for the player.
	 */
	private void drawGame() {
		//Creates the a string containing the path for images use based on the current skin indicator being used.
		String IMAGE_FOLDER = IMAGE_FOLDER_START + Integer.toString(this.skinNum) + IMAGE_FOLDER_END;
		//Draws the level in it's current state.
		level.draw(IMAGE_FOLDER, canvas);
	}
	
	/**
	 * Changes the skin of the game, will cycle through the possible choices with each click.
	 */
	private void changeSkin() {
		//skinNum cannot be more than the maximum, indicated by a final variable.
		if ((this.skinNum + 1) > MAX_SKIN_NUM) { 
			this.skinNum = 1;
		} else {
			this.skinNum++;
		}
	}
}
