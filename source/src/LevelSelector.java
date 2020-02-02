import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LevelSelector {

		private int x;
		
		public int display(int upToLevelID) {
			x = 0;
			
			Stage stage = new Stage();

			//this line states that you have to deal with this window before doing anything else
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.setTitle("Level Select");
			stage.setMinWidth(250);
			
			Label menu = new Label("Choose Level:");
			
			VBox root = new VBox(10);
			root.setSpacing(10);
			root.setPadding(new Insets(10,10,10,10));
			root.setAlignment(Pos.CENTER);
			root.getChildren().add(menu);
			
			for(int i = 1; i <= upToLevelID; i++) {
				Button levelButton = new Button(Integer.toString(i));
				levelButton.setOnAction(e -> {
					x = Integer.parseInt(levelButton.getText());
					stage.close();
				});
				root.getChildren().add(levelButton);
			}

			Button closeButton = new Button("Close");
			closeButton.setOnAction(e -> stage.close());

			root.getChildren().add(closeButton);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
			
			return x;
	}

}
