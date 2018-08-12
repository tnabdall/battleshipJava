package GUI;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.BackgroundImage;
import javafx.geometry.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.text.*;	
import javafx.scene.paint.*;
import javafx.scene.shape.*;

/**
 * Runs the GUI Configuration.
 */
public class StartMenu {

    private Stage window;
    private Scene sceneStart, sceneConfig, scenePlayerName, scene4;
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
		buttonMain.setMinHeight(29.0); buttonMain.setPrefHeight(29.0); buttonMain.setMaxHeight(29.0); 
		buttonMain.setMinWidth(120); buttonMain.setPrefWidth(120); buttonMain.setMaxWidth(120);
		// Switches to main game config scene.
        buttonMain.setOnAction(e -> window.setScene(sceneConfig));
		
		// MAIN Image 
		BackgroundImage myBI = new BackgroundImage ( new Image ("Mainpic.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		  
		 //Layout 1 - children laid out in vertical column
        StackPane layout1 = new StackPane();
        layout1.getChildren().add(buttonMain);
		layout1.setBackground(new Background (myBI));
        sceneStart = new Scene(layout1, 500, 500);
     
	 //Button 1 for level 1
        ToggleButton  buttonL1 = new ToggleButton ("L1");
		buttonL1.setMinHeight(29.0); buttonL1.setPrefHeight(29.0); buttonL1.setMaxHeight(29.0);
		buttonL1.setMinWidth(38); buttonL1.setPrefWidth(38); buttonL1.setMaxWidth(38);
		GridPane.setHalignment(buttonL1, HPos.CENTER);

		//Button 2 for level 2
        ToggleButton  buttonL2 = new ToggleButton ("L2");
		buttonL2.setMinHeight(29.0); buttonL2.setPrefHeight(29.0); buttonL2.setMaxHeight(29.0);
		buttonL2.setMinWidth(38); buttonL2.setPrefWidth(38); buttonL2.setMaxWidth(38);
		GridPane.setHalignment(buttonL2, HPos.CENTER);
		
		//Button 3 for level 3
        ToggleButton  buttonL3 = new ToggleButton ("L3");
		buttonL3.setMinHeight(29.0); buttonL3.setPrefHeight(29.0); buttonL3.setMaxHeight(29.0);
		buttonL3.setMinWidth(38); buttonL3.setPrefWidth(38); buttonL3.setMaxWidth(38);
		GridPane.setHalignment(buttonL3, HPos.CENTER);
		
		//Button 4 for level 4
        ToggleButton  buttonL4 = new ToggleButton ("L4");
		buttonL4.setMinHeight(29.0); buttonL4.setPrefHeight(29.0); buttonL4.setMaxHeight(29.0);
		buttonL4.setMinWidth(38); buttonL4.setPrefWidth(38); buttonL4.setMaxWidth(38);
		GridPane.setHalignment(buttonL4, HPos.CENTER);

		//Button 5 for 2 Player
		ToggleButton  button2P = new ToggleButton ("2P");
		button2P.setMinHeight(29.0); button2P.setPrefHeight(29.0); button2P.setMaxHeight(29.0);
		button2P.setMinWidth(38); button2P.setPrefWidth(38); button2P.setMaxWidth(38);
		GridPane.setHalignment(button2P, HPos.CENTER);
		
		// ToggleButton group for level 1-4 and 2P
		ToggleGroup groupLevel = new ToggleGroup();
		buttonL1.setToggleGroup(groupLevel);
		buttonL2.setToggleGroup(groupLevel);
		buttonL3.setToggleGroup(groupLevel);
		buttonL4.setToggleGroup(groupLevel);
		button2P.setToggleGroup(groupLevel);
		
		// Set text for toggle Button
		buttonL1.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 1");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 3;
	
		} } );
		
		buttonL2.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 2");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 0;
		} } );	
		
		buttonL3.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 3");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 1;
		} } );	
		
		buttonL4.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 4");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
				difficulty = 2;
		} } );

		button2P.setOnAction ( new EventHandler<ActionEvent>(){
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
		buttonR.setMinHeight(29.0); buttonR.setPrefHeight(29.0); buttonR.setMaxHeight(29.0); 
		buttonR.setMinWidth(38); buttonR.setPrefWidth(38); buttonR.setMaxWidth(38);
		GridPane.setHalignment(buttonR, HPos.CENTER);
		
		//Button 6 for YELLOW
        ToggleButton  buttonY = new ToggleButton ("Y");
		buttonY.setStyle("-fx-base: yellow ;");
		buttonY.setMinHeight(29.0); buttonY.setPrefHeight(29.0); buttonY.setMaxHeight(29.0);
		buttonY.setMinWidth(38); buttonY.setPrefWidth(38); buttonY.setMaxWidth(38);
		GridPane.setHalignment(buttonY, HPos.CENTER);
		
		//Button 7 for GREEN
        ToggleButton  buttonG = new ToggleButton ("G");
		buttonG.setStyle("-fx-base: lime ;");
		buttonG.setMinHeight(29.0); buttonG.setPrefHeight(29.0); buttonG.setMaxHeight(29.0);
		buttonG.setMinWidth(38); buttonG.setPrefWidth(38); buttonG.setMaxWidth(38);
		GridPane.setHalignment(buttonG, HPos.CENTER);
		
		//Button 8 for PURPLE
        ToggleButton  buttonP = new ToggleButton ("P");
		buttonP.setStyle("-fx-base: purple  ;");
		buttonP.setMinHeight(29.0); buttonP.setPrefHeight(29.0); buttonP.setMaxHeight(29.0);
		buttonP.setMinWidth(38); buttonP.setPrefWidth(38); buttonP.setMaxWidth(38);
		GridPane.setHalignment(buttonP, HPos.CENTER);
		
		//Button 9 for BLUE
        ToggleButton  buttonB	 = new ToggleButton ("B");
		buttonB	.setStyle("-fx-base: cyan ;");
		buttonB	.setMinHeight(29.0); buttonB	.setPrefHeight(29.0); buttonB	.setMaxHeight(29.0);
		buttonB	.setMinWidth(38); buttonB	.setPrefWidth(38); buttonB	.setMaxWidth(38);
		GridPane.setHalignment(buttonB	, HPos.CENTER);
		
		// ToggleButton group for level 1-4
		ToggleGroup groupColor = new ToggleGroup();
		buttonR.setToggleGroup(groupColor);
		buttonY.setToggleGroup(groupColor);
		buttonG.setToggleGroup(groupColor);
		buttonP.setToggleGroup(groupColor);
		buttonB	.setToggleGroup(groupColor);

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
		buttonY.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Yellow");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("YELLOW"));
				playerColor = "YELLOW";

		} } );
		
		// Set text for toggle Button
		buttonG.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Lime");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("LIME"));
				playerColor = "LIME";

		} } );
		// Set text for toggle Button
		buttonP.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Purple");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("PURPLE"));
				playerColor = "PURPLE";

		} } );
		// Set text for toggle Button
		buttonB.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Cyan");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("CYAN"));
				playerColor = "CYAN";

		} } );

		GridPane layoutPlayerName = new GridPane();
		//Button 10 for NEXT SCENE
        Button  buttonToPlayer = new Button ("NEXT");
		buttonToPlayer.setMinHeight(29.0); buttonToPlayer.setPrefHeight(29.0); buttonToPlayer.setMaxHeight(29.0);
		buttonToPlayer.setMinWidth(70); buttonToPlayer.setPrefWidth(70); buttonToPlayer.setMaxWidth(70);
		// Switches to next scene.
		buttonToPlayer.setOnAction(e -> {window.setScene(scenePlayerName); if(difficulty==-1){layoutPlayerName.add(enterName2, 18,24);}});
		GridPane.setHalignment(buttonToPlayer, HPos.RIGHT);
		
		// Button 11 for Instruction Box
		Button buttonINSTRUCTION = new Button ("?");
		buttonINSTRUCTION.setOnAction(e -> InstructionAlert.display("Instruction Window", " "));

		
		// MENU Image 
		BackgroundImage my2BI = new BackgroundImage ( new Image ("FinalMenu.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        //Layout 2 Main Game Config Screen
        GridPane layoutGameConfig = new GridPane();
		layoutGameConfig.setVgap(15);
		layoutGameConfig.setHgap(5);
		layoutGameConfig.setPadding(new Insets(50,0,0,0));
        layoutGameConfig.add(buttonL1, 34, 2);
		layoutGameConfig.add(buttonL2, 34, 3);
		layoutGameConfig.add(buttonL3, 34, 4);
		layoutGameConfig.add(buttonL4, 34, 5);
		layoutGameConfig.add(button2P,35,2);
		layoutGameConfig.add(buttonR, 32, 11);
		layoutGameConfig.add(buttonY, 33, 11);
		layoutGameConfig.add(buttonG, 34, 11);
		layoutGameConfig.add(buttonP, 35, 11);
		layoutGameConfig.add(buttonB	, 36, 11);
		layoutGameConfig.add(buttonToPlayer, 30, 15);
		layoutGameConfig.add(buttonINSTRUCTION, 10, 14);
		
		layoutGameConfig.setBackground(new Background (my2BI));
		sceneConfig = new Scene(layoutGameConfig, 500, 500);
  
		// Button11 for scene 2 to 3
        Button  buttonGo = new Button ("GO");
		buttonGo.setMinHeight(29.0); buttonGo.setPrefHeight(29.0); buttonGo.setMaxHeight(29.0);
		buttonGo.setMinWidth(50); buttonGo.setPrefWidth(50); buttonGo.setMaxWidth(50);
		//button11.setOnAction(e -> window.setScene(scene4));
		buttonGo.setOnAction(e -> {
			playerName = enterName.getText();
			if(difficulty==-1){
				player2Name = enterName2.getText();
			}
			window.close();

		});
		GridPane.setHalignment(buttonGo, HPos.CENTER);

		
		// TextField for Player Name Entry
		enterName = new TextField ("Player"); // Default text of 'Player Name'
		enterName.setPrefHeight(38);
		enterName2 = new TextField("Player 2");
		enterName2.setPrefHeight(38);
		
		// BCKG Image for Name area
		BackgroundImage my3BI = new BackgroundImage ( new Image ("BckgForGrid.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		 //Layout 3 - For entering player Name
		layoutPlayerName.setVgap(10);
		layoutPlayerName.setHgap(10);
		layoutPlayerName.add(buttonGo, 18 , 38);
		layoutPlayerName.add(enterName, 18, 17);
		if (difficulty == -1){
			layoutPlayerName.add(enterName2, 18,24);
		}
		layoutPlayerName.setBackground(new Background (my3BI));
        scenePlayerName = new Scene(layoutPlayerName, 500, 500);
			 
		// Actual bckg of grid 
		BackgroundImage my4BI = new BackgroundImage ( new Image ("ActualBckgGrid.jpg", 500, 500, false, false),
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
			 
		// Rectangle to store info
		Rectangle rect = new Rectangle(100,65);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.BLACK);       
		rect.setStrokeWidth(5);

		

		
			 
        //Display scene 1 at first
        window.setScene(sceneStart);
        window.setTitle("Battleship");
		window.setResizable(false);
        window.showAndWait();
	
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

	/**
	 *
	 * @return Return the desired player colors.
	 */
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
