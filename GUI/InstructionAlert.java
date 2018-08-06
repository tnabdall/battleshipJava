package GUI;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.image.*;

/**
 * Displays Game Instructions.
 */
public class InstructionAlert {

    /**
     *
     * @param title Title to display on instructions window.
     * @param message Message to display
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
 
		BackgroundImage instructionBox = new BackgroundImage ( new Image ("InstructionWindow.jpg", 500, 500, false, false),
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        GridPane layout = new GridPane();
		layout.setVgap(10);
		layout.setHgap(10);
		layout.add(closeButton, 20, 40);

		layout.setBackground(new Background (instructionBox));

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout, 500, 500);
        window.setScene(scene);
        window.showAndWait();
    }

}

