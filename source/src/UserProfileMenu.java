import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controls the User Profile Selection menu.
 * <br> Creation Date: 21/11/19 
 * <br> Last Modification Date: 07/12/19
 * 
 * @author Kelsey Pyne(976805), Mariya Ahmed(990306), Timothy Roger (977422)
 * @version 1.1
 */
public class UserProfileMenu implements Initializable {

	@FXML
	public Button confirmButton;

	@FXML
	public Button exitButton;

	@FXML
	public TextField userNameEntered;

	@FXML
	public Label labelConfirm;

	@FXML
	public Label levelLabel;

	@FXML
	public ChoiceBox<String> existingUserBox;

	private LevelSelector levelSelectMenu = new LevelSelector();
	
	private static final int GAME_WINDOW_WIDTH = 700;
	private static final int GAME_WINDOW_HEIGHT = 750;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateProfileChoice();
	}
	
	/**
	 * initializes the choice box to add existing users
	 */
	public void updateProfileChoice() {
		try {
			UserProfile[] profiles = FileReader.getProfiles();
			String[] usernames = new String[profiles.length];
			for (int i = 0; i < profiles.length; i++) {
				usernames[i] = profiles[i].getUsername();
			}
			existingUserBox.getItems().setAll(usernames);
		} catch (java.util.NoSuchElementException e) {
			labelConfirm.setText("No Saved Users. Make the first!");
		} 
	}
	
	
	private void levelMenu(String username) {
		
		UserProfile p = FileReader.getUserProfile(username);
		
		Stage stage = new Stage();
		Game g = new Game();
		Pane root = g.buildGUI();
		Scene gameScene = new Scene(root, GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
		stage.setScene(gameScene);
		
		AlertBox a = new AlertBox();
		
		boolean answer = false;
		
		if (FileReader.saveCheck(username)) {
			answer = a.display("Load last save?", "Do you want to load this user's last save?");
		}
		
		if (answer) {
			g.runGame(username, 0, true, 0);
			stage.show();
			close();
		}
		if (!answer) {
			if (p.getHighestLevel() != 0) {
				boolean cont = false;
				int lvlID = 1; //PLACEHOLDING VALUE OF 1
				while (!cont && lvlID != 0) {
					lvlID = levelSelectMenu.display(p.getHighestLevel());
					if (lvlID != 0) {
						cont = a.display("Load Level " + Integer.toString(lvlID), "Do you want to load level " + Integer.toString(lvlID) + "?");
					}
					if (cont) {
						g.runGame(username, lvlID, false, 0);
						stage.show();
						close();
					}
				}
			} else {
				FileReader.addScore(username, 1, 0);
				g.runGame(username, 1, false, 0);
				stage.show();
				close();
			}
		}
	}

	/**
	 * once a user is chosen or created, it confirms the selection and saves.
	 */
	@FXML
	private void confirmSelection() {
		if (!existingUserBox.getSelectionModel().isEmpty()) {
			labelConfirm.setText("User " + existingUserBox.getSelectionModel().getSelectedItem() + " chosen!");
			String chosenUser = existingUserBox.getSelectionModel().getSelectedItem();
			levelMenu(chosenUser);
		} else if (existingUserBox.getSelectionModel().isEmpty() && !userNameEntered.getText().isEmpty()) {
			String username = userNameEntered.getText();
			if (usernameCheck(username)) {
				labelConfirm.setText("User " + username + " added!");
				addUsername(username);
				updateProfileChoice();
			} else {
				labelConfirm.setText("A user already has that name!");
			}
			
		} else {
			labelConfirm.setText("No Username Chosen!");	
		}
	}

	private boolean usernameCheck(String username) {
		boolean b = true;
		for (UserProfile p : FileReader.getProfiles()) {
			if (username.equals(p.getUsername())) {
				b = false;
			}
		}
		return b;
	}

	/**
	 * Deletes a user from all the profiles.
	 */
	@FXML
	private void delete() {
		AlertBox a = new AlertBox();
		boolean answer = a.display("Delete User", "Are you sure you want to delete this user?");
		if (answer == true) {
			if (!existingUserBox.getSelectionModel().isEmpty()) {
				labelConfirm.setText("User " + existingUserBox.getSelectionModel().getSelectedItem() + " deleted");
				String chosenUser = existingUserBox.getSelectionModel().getSelectedItem();
				removeUserProfile(chosenUser);
			} else {
				labelConfirm.setText("No Username Chosen!");	
			}
			updateProfileChoice();
		}
	}

	/**
	 * Returns to the the Main Menu if the user answers yes to a prompt confirming closure of the UserProfileMenu. 
	 * 
	 * @throws IOException - If MainMenuFXML.fxml is not found, an IOException is thrown.
	 */
	@FXML
	private void exit() throws IOException {
		AlertBox a = new AlertBox();
		boolean answer = a.display("Exit", "Are you sure?");
		if (answer == true) {
			Stage stage = new Stage();
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("MainMenuFXML.fxml"));
			Scene chooseUserMenu = new Scene(root);
			stage.setScene(chooseUserMenu);
			stage.show();

			close();
		}
	}
	
	private void close() {
		Stage stage2 = (Stage) exitButton.getScene().getWindow();
		stage2.close();
	}

	/**
	 * saves the new user to user file.
	 * 
	 * @param username String - The username string to add as a new profile.
	 */
	private void addUsername(String username) {
		FileReader.addProfile(username);
	}
	
	private void removeUserProfile(String chosenUser) {
		FileReader.removeProfile(chosenUser);
	}
}