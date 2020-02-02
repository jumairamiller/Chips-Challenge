import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates an interactive window that the user can use
 * <br> Creation Date: 28/11/19.
 * <br> Last Modification Date: 28/11/19.
 * @author Nihal Goindi (976005)
 * @version 1.1
 */

public class ConfirmBox {

    static boolean answer;

    /**
     *
     * @param title - title of the window
     * @param message - the message displayed to the user
     * @return answer - i.e. the users response, yes or no
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(250);
        Label label1 = new Label();
        label1.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        //creates layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        //adds everything to the scene
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}