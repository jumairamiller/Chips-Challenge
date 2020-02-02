import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LeaderBoardController {

	@FXML
	public Button level1;
	@FXML
	public Button level2;
	@FXML
	public Button level3;
	@FXML
	public Button level4;
	@FXML
	public Button level5;
	@FXML
	public Button level6;
	@FXML
	public Button level7;
	@FXML
	public Button level8;
	@FXML
	public Button level9;
	@FXML
	public Button level10;
	@FXML
	public Button MainMenu;

	private AlertBox alertBox = new AlertBox();
	private Stage window;
	
	@FXML
	private void closeLBMenu(ActionEvent event) throws IOException {
		boolean answer = alertBox.display("Exit", "Are you sure?");
		if (answer == true) {
			Stage stage = new Stage();
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("MainMenuFXML.fxml"));
			Scene chooseUserMenu = new Scene(root);
			stage.setScene(chooseUserMenu);
			stage.show();
			
			Stage stage2 = (Stage) MainMenu.getScene().getWindow();
			stage2.close();
		}
	}
	
	private void boardSelect(int levelID) {
		Stage stage = new Stage();
		window = stage;
		window.setTitle("Leaderboard");
		window.setMinWidth(600);

		// creates the grid
		GridPane leaderboard = new GridPane();
		leaderboard.setVgap(40);
		leaderboard.setHgap(40);

		Label position = new Label("Position");
		position.setStyle("-fx-font-weight: bold;");
		Label userName = new Label("User name");
		userName.setStyle("-fx-font-weight: bold;");
		Label highScore = new Label("High Score");
		highScore.setStyle("-fx-font-weight: bold;");

		Button closeButton = new Button("Close Leaderboard");
		closeButton.setOnAction(e -> stage.close());

		leaderboard.add(position, 0, 0, 1, 1);
		leaderboard.add(userName, 1, 0, 1, 1);
		leaderboard.add(highScore, 2, 0, 1, 1);
		leaderboard.add(closeButton, 3, 0, 1, 1);

		ArrayList<UserProfile> profileList = new ArrayList<UserProfile>(Arrays.asList(FileReader.getProfiles()));
		Map<String, Integer> sortedLBoard = LeaderBoard.getBoard(profileList, levelID);

		// iterates through the sorted leaderboard and displays the top 3
		int count = 1;
		for (Map.Entry<String, Integer> entry : sortedLBoard.entrySet()) {
			if (count <= 3) {
				Label positionLabel = new Label(Integer.toString(count));
				leaderboard.add(positionLabel, 0, count, 1, 1);

				Label userNameLabel = new Label(entry.getKey());
				leaderboard.add(userNameLabel, 1, count, 1, 1);

				Label scoreLabel = new Label(Integer.toString(entry.getValue()));
				leaderboard.add(scoreLabel, 2, count, 1, 1);
			}
			count += 1;
		}

		Scene scene = new Scene(leaderboard, 410, 200);

		window.setScene(scene);
		window.show();
	}

	@FXML
	private void level1Select(ActionEvent event) {
		boardSelect(1);
	}

	@FXML
	private void level2Select(ActionEvent event) {
		boardSelect(2);
	}

	@FXML
	private void level3Select(ActionEvent event) {
		boardSelect(3);
	}
	
	@FXML
	private void level4Select(ActionEvent event) {
		boardSelect(4);
	}
	
	@FXML
	private void level5Select(ActionEvent event) {
		boardSelect(5);
	}
	
	@FXML
	private void level6Select(ActionEvent event) {
		boardSelect(6);
	}
	
	@FXML
	private void level7Select(ActionEvent event) {
		boardSelect(7);
	}
	
	@FXML
	private void level8Select(ActionEvent event) {
		boardSelect(8);
	}
	
	@FXML
	private void level9Select(ActionEvent event) {
		boardSelect(9);
	}
	
	@FXML
	private void level10Select(ActionEvent event) {
		boardSelect(10);
	}
}
