import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Prompts the user for a boolean answer to a question. Used commonly for validation requests from the user.
 * <br> Creation Date: 20/11/19.
 * <br> Last Modification Date: 8/12/19.
 * @author Szymon Grzech (988065)
 * @version 1.0
 */
public class AlertBox {
	
	//The Strings displayed in the choice buttons for the user.
	final String YES = "YES";
	final String NO = "NO";
	
	//The current response from the user.
	private boolean answer;
	
	/**
	 * Displays the "alert box" to the user and returns their answer.
	 * The prompt should be a yes or no question for which the user can choose to click one of the two buttons as their response.
	 * @param title String - What the title of the window is to be set to.
	 * @param message String - The prompt to be displayed to the user.
	 * @return the boolean response. If the user clicked "Yes", this will be true. If "No", it will be false.
	 */
	public boolean display(String title, String message) {
		
		Stage window = new Stage();
		//this line states that you have to deal with this window before doing anything else
		window.initModality(Modality.APPLICATION_MODAL);
		
		window.setTitle(title);
		window.setMinWidth(250);
		Label l = new Label(message);
		
		//answer buttons created.
		Button yes = new Button(YES);
		Button no = new Button(NO);
		
		yes.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		no.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(l, yes, no);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	
}