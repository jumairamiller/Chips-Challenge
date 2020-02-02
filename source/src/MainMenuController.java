import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controls the main menu displayed when first loaded.
 * 
 * <br>
 * Creation Date: 20/11/19 <br>
 * Last Modification Date: 06/12/19
 * 
 * @author Kelsey Pyne(976805), Mariya Ahmed(990306)
 * @version 1.1
 */
public class MainMenuController {
	@FXML
	public Button exitButton;
	@FXML
	public Button startButton;
	@FXML
	public Label userChosenLabel;
	@FXML
	public Label messageDay;
	@FXML
	public Button messageButton;

	private static String messageURL = "http://cswebcat.swan.ac.uk/message?solution=";

	AlertBox alertBox = new AlertBox();
	Stage window;

	@FXML
	private void displayMessage(ActionEvent event) throws Exception {
		messageDay.setText(MessageOfTheDay.getRequest(messageURL + MessageOfTheDay.getDecodedText()));
	}

	/**
	 * Starts the game.
	 * @throws IOException - If "UserProfileMenuFXML.fxml" doesn't exist then an IOException is thown.
	 */
	@FXML
	private void playGame() throws IOException {
		Stage stage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("UserProfileMenuFXML.fxml"));
		Scene chooseUserMenu = new Scene(root);
		stage.setScene(chooseUserMenu);
		stage.show();
		closeMenu();
	}

	/**
	 * Opens the leaderboard menu.
	 * @throws IOException - If LeaderBoardFXML.fxml doesn't exist then an IOException will be thrown.
	 */
	@FXML
	private void getLeaderboard() throws IOException {
		Stage stage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("LeaderBoardFXML.fxml"));
		Scene chooseLeaderboard = new Scene(root);
		stage.setScene(chooseLeaderboard);
		stage.show();
		closeMenu();
	}

	/**
	 * Closes the menu once pressed.
	 */
	@FXML
	private void closeProgram() {
		boolean answer = alertBox.display("Exit", "Are you sure?");
		if (answer == true) {
			closeMenu();
		}
	}

	/**
	 * closes the menu.
	 */
	private void closeMenu() {
		Stage stage = (Stage) startButton.getScene().getWindow();
		stage.close();
	}
}