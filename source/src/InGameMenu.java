import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InGameMenu {

	private int x;
	
	public int display() {
		x = 0;
		
		Stage stage = new Stage();

		//this line states that you have to deal with this window before doing anything else
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.setTitle("Menu");
		stage.setMinWidth(250);
		
		Label menu = new Label("Menu");
		
		Button restartButton = new Button("Restart");
		restartButton.setOnAction(e -> {
			x = 1;
			stage.close();
		});
		
		Button exitButton = new Button("Save and Exit Level");
		exitButton.setOnAction(e -> {
			x = 2;
			stage.close();
		});
		
		Button swapSkinButton = new Button("Swap Game Skin");
		swapSkinButton.setOnAction(e -> {
			x = 3;
			stage.close();
		});

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> stage.close());
		
		VBox root = new VBox(10);
		root.setSpacing(10);
		root.setPadding(new Insets(10,10,10,10));
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(menu, restartButton, exitButton, swapSkinButton, closeButton);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.showAndWait();
		
		return x;
	}
}
