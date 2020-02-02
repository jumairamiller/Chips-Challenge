import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controls the in-game menu.
 * 
 * <br> Creation Date: 25/11/19
 * <br> Last Modification Date: 02/12/19
 * @author Kelsey Pyne(976805), Mariya Ahmed(990306)
 * @version 1.1
 */
public class GameMenuController {

	@FXML
	private Button levelsButton;
	@FXML
	private Button exitGameButton;
	@FXML
	private Button closeButton;

	AlertBox alertBox = new AlertBox();

	@FXML
	/**
	 * Prompts the user if they want to exit the game.
	 * 
	 * @param event
	 */
	public void exitGame(ActionEvent event) {
		boolean answer = alertBox.display("Exit", "Are you sure?");
		if (answer == true) {
			Stage stage = (Stage) exitGameButton.getScene().getWindow();
			stage.close();
			System.exit(0);
		}
	}
	
	// to redirect user to levels and close options box
	@FXML
	private void getLevelMenu(ActionEvent event) throws Exception {
		Stage stage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("LevelMenuFXML.fxml"));
		Scene chooseLevelMenu = new Scene(root);
		stage.setScene(chooseLevelMenu);
		stage.show();
		Stage stagee = (Stage) levelsButton.getScene().getWindow();
		stagee.close();
	}
	
	@FXML
	private void closelevelStart() {
		
	}

	@FXML
	/**
	 * closes down the in-game menu.
	 */
	public void closeMenu() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}