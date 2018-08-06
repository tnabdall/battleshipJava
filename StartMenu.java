import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.BackgroundImage;
import javafx.geometry.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.text.*;	
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class StartMenu {

    private Stage window;
    private Scene scene1, scene2, scene3, scene4;
	private TextField enterName, enterName2;
	private String playerName, player2Name; //Player's name
	private Label message = new Label (" ");
	private Label messageColor = new Label (" "); //Stores color for game configuration



	private String playerColor = "CYAN";
	private int difficulty = 3;

	/**
	 * Method that handles all the windows for game configuration.
	 */
    public void start() {
        window = new Stage();

        //MAIN Button 
        Button buttonMain = new Button("Click to Start");
		buttonMain.setMinHeight(30.0); buttonMain.setPrefHeight(30.0); buttonMain.setMaxHeight(30.0); 
		buttonMain.setMinWidth(90.0); buttonMain.setPrefWidth(90.0); buttonMain.setMaxWidth(90.0);
        buttonMain.setOnAction(e -> window.setScene(scene2));
		
		// MAIN Image 
		BackgroundImage myBI = new BackgroundImage ( new Image ("Mainpic.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		  
		 //Layout 1 - children laid out in vertical column
        StackPane layout1 = new StackPane();
        layout1.getChildren().add(buttonMain);
		layout1.setBackground(new Background (myBI));
        scene1 = new Scene(layout1, 500, 500);
     
	 //Button 1 for level 1
        ToggleButton  button1 = new ToggleButton ("L1");
		button1.setMinHeight(30.0); button1.setPrefHeight(30.0); button1.setMaxHeight(30.0); 
		button1.setMinWidth(30); button1.setPrefWidth(30); button1.setMaxWidth(30);
		GridPane.setHalignment(button1, HPos.CENTER);

		//Button 2 for level 2
        ToggleButton  button2 = new ToggleButton ("L2");
		button2.setMinHeight(30.0); button2.setPrefHeight(30.0); button2.setMaxHeight(30.0); 
		button2.setMinWidth(30); button2.setPrefWidth(30); button2.setMaxWidth(30);
		GridPane.setHalignment(button2, HPos.CENTER);
		
		//Button 3 for level 3
        ToggleButton  button3 = new ToggleButton ("L3");
		button3.setMinHeight(30.0); button3.setPrefHeight(30.0); button3.setMaxHeight(30.0); 
		button3.setMinWidth(30); button3.setPrefWidth(30); button3.setMaxWidth(30);
		GridPane.setHalignment(button3, HPos.CENTER);
		
		//Button 4 for level 4
        ToggleButton  button4 = new ToggleButton ("L4");
		button4.setMinHeight(30.0); button4.setPrefHeight(30.0); button4.setMaxHeight(30.0); 
		button4.setMinWidth(30); button4.setPrefWidth(30); button4.setMaxWidth(30);
		GridPane.setHalignment(button4, HPos.CENTER);

		//Button 5 for 2 Player
		ToggleButton  button5 = new ToggleButton ("2P");
		button5.setMinHeight(30.0); button5.setPrefHeight(30.0); button5.setMaxHeight(30.0);
		button5.setMinWidth(30); button5.setPrefWidth(30); button5.setMaxWidth(30);
		GridPane.setHalignment(button5, HPos.CENTER);
		
		// ToggleButton group for level 1-4 and 2P
		ToggleGroup groupLevel = new ToggleGroup();
		button1.setToggleGroup(groupLevel);
		button2.setToggleGroup(groupLevel);
		button3.setToggleGroup(groupLevel);
		button4.setToggleGroup(groupLevel);
		button5.setToggleGroup(groupLevel);
		
		// Set text for toggle Button
		button1.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 1");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 3;
	
		} } );
		
		button2.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 2");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 0;
		} } );	
		
		button3.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 3");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 1;
		} } );	
		
		button4.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 4");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 2;
		} } );

		button5.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("2 Player");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = -1; // 2 Player

			} } );
		
		//Button 5 for RED
        ToggleButton  buttonR = new ToggleButton ("R");
		buttonR.setStyle("-fx-base: red ;");
		buttonR.setMinHeight(30.0); buttonR.setPrefHeight(30.0); buttonR.setMaxHeight(30.0); 
		buttonR.setMinWidth(30.0); buttonR.setPrefWidth(30); buttonR.setMaxWidth(30);
		GridPane.setHalignment(buttonR, HPos.CENTER);
		
		//Button 6 for YELLOW
        ToggleButton  button6 = new ToggleButton ("Y");
		button6.setStyle("-fx-base: yellow ;");
		button6.setMinHeight(30.0); button6.setPrefHeight(30.0); button6.setMaxHeight(30.0); 
		button6.setMinWidth(30); button6.setPrefWidth(30); button6.setMaxWidth(30);
		GridPane.setHalignment(button6, HPos.CENTER);
		
		//Button 7 for GREEN
        ToggleButton  button7 = new ToggleButton ("G");
		button7.setStyle("-fx-base: lime ;");
		button7.setMinHeight(30.0); button7.setPrefHeight(30.0); button7.setMaxHeight(30.0); 
		button7.setMinWidth(30); button7.setPrefWidth(30); button7.setMaxWidth(30);
		GridPane.setHalignment(button7, HPos.CENTER);
		
		//Button 8 for PURPLE
        ToggleButton  button8 = new ToggleButton ("P");
		button8.setStyle("-fx-base: purple  ;");
		button8.setMinHeight(30.0); button8.setPrefHeight(30.0); button8.setMaxHeight(30.0); 
		button8.setMinWidth(30); button8.setPrefWidth(30); button8.setMaxWidth(30);
		GridPane.setHalignment(button8, HPos.CENTER);
		
		//Button 9 for BLUE
        ToggleButton  button9 = new ToggleButton ("B");
		button9.setStyle("-fx-base: cyan ;");
		button9.setMinHeight(30.0); button9.setPrefHeight(30.0); button9.setMaxHeight(30.0); 
		button9.setMinWidth(30); button9.setPrefWidth(30); button9.setMaxWidth(30);
		GridPane.setHalignment(button9, HPos.CENTER);
		
		// ToggleButton group for level 1-4
		ToggleGroup groupColor = new ToggleGroup();
		buttonR.setToggleGroup(groupColor);
		button6.setToggleGroup(groupColor);
		button7.setToggleGroup(groupColor);
		button8.setToggleGroup(groupColor);
		button9.setToggleGroup(groupColor);

		// Set text for toggle Button
		buttonR.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Red");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("RED"));
				playerColor = "RED";

		} } );
		
		// Set text for toggle Button
		button6.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Yellow");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("YELLOW"));
				playerColor = "YELLOW";

		} } );
		
		// Set text for toggle Button
		button7.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Lime");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("LIME"));
				playerColor = "LIME";

		} } );
		// Set text for toggle Button
		button8.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Purple");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("PURPLE"));
				playerColor = "PURPLE";

		} } );
		// Set text for toggle Button
		button9.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Cyan");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("CYAN"));
				playerColor = "CYAN";

		} } );

		GridPane layout3 = new GridPane();
		//Button 10 for NEXT SCENE
        Button  button10 = new Button ("NEXT");
		button10.setMinHeight(30.0); button10.setPrefHeight(30.0); button10.setMaxHeight(30.0); 
		button10.setMinWidth(50); button10.setPrefWidth(50); button10.setMaxWidth(50);
		button10.setOnAction(e -> {window.setScene(scene3); if(difficulty==-1){layout3.add(enterName2, 18,24);}});
		GridPane.setHalignment(button10, HPos.RIGHT);
		
		// Button 11 for Instruction Box
		Button buttonINSTRUCTION = new Button ("?");
		buttonINSTRUCTION.setOnAction(e -> InstructionAlert.display("Instruction Window", " "));

		
		// MENU Image 
		BackgroundImage my2BI = new BackgroundImage ( new Image ("FinalMenu.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        //Layout 2
        GridPane layout2 = new GridPane();
		layout2.setVgap(15);
		layout2.setHgap(5);
		layout2.setPadding(new Insets(50,0,0,0));
        layout2.add(button1, 51, 2);
		layout2.add(button2, 51, 3);
		layout2.add(button3, 51, 4);
		layout2.add(button4, 51, 5);
		layout2.add(button5,52,2);
		layout2.add(buttonR, 49, 11);
		layout2.add(button6, 50, 11);
		layout2.add(button7, 51, 11);
		layout2.add(button8, 52, 11);
		layout2.add(button9, 53, 11);
		layout2.add(button10, 54, 14);
		layout2.add(buttonINSTRUCTION, 10, 14);
		
		layout2.setBackground(new Background (my2BI));
		scene2 = new Scene(layout2, 500, 500);
  
		// Button11 for scene 3 to 2
        Button  button11 = new Button ("GO");
		button11.setMinHeight(30.0); button11.setPrefHeight(30.0); button11.setMaxHeight(30.0); 
		button11.setMinWidth(50); button11.setPrefWidth(50); button11.setMaxWidth(50);
		//button11.setOnAction(e -> window.setScene(scene4));
		button11.setOnAction(e -> {
			playerName = enterName.getText();
			if(difficulty==-1){
				player2Name = enterName2.getText();
			}
			window.close();

		});
		GridPane.setHalignment(button11, HPos.CENTER);

		
		// TextField
		enterName = new TextField ("Player"); // Default text of 'Player Name'
		enterName.setPrefHeight(30);
		enterName2 = new TextField("Player 2");
		enterName2.setPrefHeight(30);
		
		// BCKG Image for Name area
		BackgroundImage my3BI = new BackgroundImage ( new Image ("BckgForGrid.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		 //Layout 3

		layout3.setVgap(10);
		layout3.setHgap(10);
		layout3.add(button11, 18 , 30);
		layout3.add(enterName, 18, 17);
		if (difficulty == -1){
			layout3.add(enterName2, 18,24);
		}
		layout3.setBackground(new Background (my3BI));
        scene3 = new Scene(layout3, 500, 500);
			 
		// Actual bckg of grid 
		BackgroundImage my4BI = new BackgroundImage ( new Image ("ActualBckgGrid.jpg", 500, 500, false, false),
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
			 
		// Rectangle to store info
		Rectangle rect = new Rectangle(100,65);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.BLACK);       
		rect.setStrokeWidth(5);

		
		//Layout 4
		GridPane layout4 = new GridPane();
		layout4.setVgap(10);
		layout4.setHgap(10);
		layout4.setBackground(new Background (my4BI));
	//	message.setFill(Color.'messageColor');
		layout4.add(message, 10, 0);
		layout4.add(messageColor, 10, 1);
		layout4.add(rect, 32, 0);
        scene4 = new Scene(layout4, 500, 500);
		
			 
        //Display scene 1 at first
        window.setScene(scene1);
        window.setTitle("Battleship");
		window.setResizable(false);
        window.showAndWait();
	
    }

    /**

	 @return Returns the level chosen.
     */
	public Label getMessage() {
		return message;
	}

	/**
	 * @return Returns the color chosen.
	 */
	public Label getMessageColor() {
		return messageColor;
	}

	/**
	 *
	 * @return Returns the player's name.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 *
	 * @return Returns the 2nd players name
	 */
	public String getPlayer2Name() {
		return player2Name;
	}

	public String getPlayerColor() {
		return playerColor;
	}

	/**
	 *
	 * @return Returns the game difficulty (-1 for 2 Player, 0 for normal, 1 for challenge, 2 for impossible, 3 for random)
	 */
	public int getDifficulty() {
		return difficulty;
	}
}
