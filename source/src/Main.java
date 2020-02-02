import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;

/**
 * Starts the game Menu.
 * 
 * <br> Creation Date: 20/11/19
 * <br> Last Modification Date: 07/12/19
 * @author Kelsey Pyne(976805), Mariya Ahmed(990306), Timothy Roger (977422)
 * @version 1.1
 */
public class Main extends Application {


	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Loads the initial Menu.
	 */
	public void start(Stage primaryStage) throws Exception {

		Pane menuRoot = (Pane) FXMLLoader.load(getClass().getResource("MainMenuFXML.fxml"));
		
		primaryStage.setTitle("Lost In Space");
		primaryStage.setScene(new Scene(menuRoot));
		primaryStage.show();

		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			primaryStage.close();
		});
	}
}