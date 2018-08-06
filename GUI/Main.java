package GUI;

import javafx.application.*;
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

public class Main extends Application {

    Stage window;
    Scene scene1, scene2, scene3, scene4, scene5, scene6;
	TextField enterName, enterNameP1, enterNameP2;
	Label message = new Label (" ");
	Label messageColor = new Label (" ");
	

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        //MAIN Button 
        Button buttonMain = new Button("Click to Start");
		buttonMain.setMinHeight(30.0); buttonMain.setPrefHeight(30.0); buttonMain.setMaxHeight(30.0); 
		buttonMain.setMinWidth(90.0); buttonMain.setPrefWidth(90.0); buttonMain.setMaxWidth(90.0);
        buttonMain.setOnAction(e -> window.setScene(scene5));
		
		// MAIN Image 
		BackgroundImage myBI = new BackgroundImage ( new Image ("Mainpic.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		  
		 //Layout 1 - children laid out in vertical column
        StackPane layout1 = new StackPane();
        layout1.getChildren().add(buttonMain);
		layout1.setBackground(new Background (myBI));
        scene1 = new Scene(layout1, 500, 500);
     
	 
		//GAME TYPE Button 
        Button GameTypePP = new Button("PLAYER VS. PLAYER");
		GameTypePP.setMinHeight(30.0); GameTypePP.setPrefHeight(30.0); GameTypePP.setMaxHeight(30.0); 
		GameTypePP.setMinWidth(140); GameTypePP.setPrefWidth(140); GameTypePP.setMaxWidth(140);
       GameTypePP.setOnAction(e -> window.setScene(scene6));
		
		Button GameTypePC = new Button("PLAYER VS. Main.AI");
		GameTypePC.setMinHeight(30.0); GameTypePC.setPrefHeight(30.0); GameTypePC.setMaxHeight(30.0); 
		GameTypePC.setMinWidth(140); GameTypePC.setPrefWidth(140); GameTypePC.setMaxWidth(140);
        GameTypePC.setOnAction(e -> window.setScene(scene2));
		
		
		// TextField
		enterNameP1 = new TextField ("Player 1 Name"); // Default text of 'Player Name'
		enterNameP1.setPrefHeight(30);
		enterNameP1.setMinWidth(100);
		
		// TextField
		enterNameP2 = new TextField ("Player 2 Name"); // Default text of 'Player Name'
		enterNameP2.setPrefHeight(30);
		enterNameP2.setMinWidth(100);

		//Button 5 for RED
        ToggleButton buttonP1R = new ToggleButton ("R");
		buttonP1R.setStyle("-fx-base: red ;");
		buttonP1R.setMinHeight(30.0); buttonP1R.setPrefHeight(30.0); buttonP1R.setMaxHeight(30.0); 
		buttonP1R.setMinWidth(30.0); buttonP1R.setPrefWidth(30); buttonP1R.setMaxWidth(30);
		GridPane.setHalignment(buttonP1R, HPos.CENTER);
		
		//Button 6 for YELLOW
        ToggleButton buttonP1Y = new ToggleButton ("Y");
		buttonP1Y.setStyle("-fx-base: yellow ;");
		buttonP1Y.setMinHeight(30.0); buttonP1Y.setPrefHeight(30.0); buttonP1Y.setMaxHeight(30.0); 
		buttonP1Y.setMinWidth(30); buttonP1Y.setPrefWidth(30); buttonP1Y.setMaxWidth(30);
		GridPane.setHalignment(buttonP1Y, HPos.CENTER);
		
		//Button 7 for GREEN
        ToggleButton  buttonP1G = new ToggleButton ("G");
		buttonP1G.setStyle("-fx-base: lime ;");
		buttonP1G.setMinHeight(30.0); buttonP1G.setPrefHeight(30.0); buttonP1G.setMaxHeight(30.0); 
		buttonP1G.setMinWidth(30); buttonP1G.setPrefWidth(30); buttonP1G.setMaxWidth(30);
		GridPane.setHalignment(buttonP1G, HPos.CENTER);
		
		//Button 8 for PURPLE
        ToggleButton  buttonP1P = new ToggleButton ("P");
		buttonP1P.setStyle("-fx-base: purple  ;");
		buttonP1P.setMinHeight(30.0); buttonP1P.setPrefHeight(30.0); buttonP1P.setMaxHeight(30.0); 
		buttonP1P.setMinWidth(30); buttonP1P.setPrefWidth(30); buttonP1P.setMaxWidth(30);
		GridPane.setHalignment(buttonP1P, HPos.CENTER);
		
		//Button 9 for BLUE
        ToggleButton  buttonP1B = new ToggleButton ("B");
		buttonP1B.setStyle("-fx-base: cyan ;");
		buttonP1B.setMinHeight(30.0); buttonP1B.setPrefHeight(30.0); buttonP1B.setMaxHeight(30.0); 
		buttonP1B.setMinWidth(30); buttonP1B.setPrefWidth(30); buttonP1B.setMaxWidth(30);
		GridPane.setHalignment(buttonP1B, HPos.CENTER);
		
		// ToggleButton group for level 1-4
		ToggleGroup groupColor1 = new ToggleGroup();
		buttonP1R.setToggleGroup(groupColor1);
		buttonP1Y.setToggleGroup(groupColor1);
		buttonP1G.setToggleGroup(groupColor1);
		buttonP1P.setToggleGroup(groupColor1);
		buttonP1B.setToggleGroup(groupColor1);

		// MAIN Image 
		BackgroundImage my6BI = new BackgroundImage ( new Image ("playervsplayer.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		  
		 //Layout 5 - children laid out in vertical column
        GridPane layout6 = new GridPane();
		layout6.setVgap(10);
		layout6.setHgap(10);
		layout6.setBackground(new Background (my6BI));
		layout6.add(buttonP1R, 17, 21);
		layout6.add(buttonP1Y, 18, 21);
		layout6.add(buttonP1G, 19, 21);
		layout6.add(buttonP1P, 20, 21);
		layout6.add(buttonP1B, 21, 21);
		layout6.add(enterNameP1, 4, 15);
		layout6.add(enterNameP2, 16, 15);
        scene6 = new Scene(layout6, 500, 500);
		
		
		// MAIN Image 
		BackgroundImage my5BI = new BackgroundImage ( new Image ("GameType.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		  
		 //Layout 5 - children laid out in vertical column
        GridPane layout5 = new GridPane();
		layout5.setVgap(10);
		layout5.setHgap(10);
		layout5.setBackground(new Background (my5BI));
		layout5.add(GameTypePP, 5, 30);
		layout5.add(GameTypePC, 17, 30);
        scene5 = new Scene(layout5, 500, 500);
		

	
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
		
		// ToggleButton group for level 1-4
		ToggleGroup groupLevel = new ToggleGroup();
		button1.setToggleGroup(groupLevel);
		button2.setToggleGroup(groupLevel);
		button3.setToggleGroup(groupLevel);
		button4.setToggleGroup(groupLevel);
		
		// Set text for toggle Button
		button1.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 1");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
	
		} } );
		
		button2.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 2");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
		} } );	
		
		button3.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 3");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
		} } );	
		
		button4.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				message.setText("Level 4");
				message.setFont(Font.font ("Verdana", 15));
				message.setTextFill(Color.web("WHITE"));
		} } );
		
		//Button 5 for RED
        ToggleButton  button5 = new ToggleButton ("R");
		button5.setStyle("-fx-base: red ;");
		button5.setMinHeight(30.0); button5.setPrefHeight(30.0); button5.setMaxHeight(30.0); 
		button5.setMinWidth(30.0); button5.setPrefWidth(30); button5.setMaxWidth(30);
		GridPane.setHalignment(button5, HPos.CENTER);
		
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
		button5.setToggleGroup(groupColor);
		button6.setToggleGroup(groupColor);
		button7.setToggleGroup(groupColor);
		button8.setToggleGroup(groupColor);
		button9.setToggleGroup(groupColor);

		// Set text for toggle Button
		button5.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Red");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("RED"));

		} } );
		
		// Set text for toggle Button
		button6.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Yellow");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("YELLOW"));

		} } );
		
		// Set text for toggle Button
		button7.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Lime");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("LIME"));

		} } );
		// Set text for toggle Button
		button8.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Purple");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("PURPLE"));

		} } );
		// Set text for toggle Button
		button9.setOnAction ( new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){
				messageColor.setText("Cyan");
				messageColor.setFont(Font.font ("Verdana", 15));
				messageColor.setTextFill(Color.web("CYAN"));

		} } );
		//Button 10 for NEXT SCENE
        Button  button10 = new Button ("NEXT");
		button10.setMinHeight(30.0); button10.setPrefHeight(30.0); button10.setMaxHeight(30.0); 
		button10.setMinWidth(50); button10.setPrefWidth(50); button10.setMaxWidth(50);
		button10.setOnAction(e -> window.setScene(scene3));
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
		layout2.add(button5, 49, 11);
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
		button11.setOnAction(e -> window.setScene(scene4));
		GridPane.setHalignment(button11, HPos.CENTER);

		
		// TextField
		enterName = new TextField ("Player Name"); // Default text of 'Player Name'
		enterName.setPrefHeight(30);
		
		// BCKG Image for Name area
		BackgroundImage my3BI = new BackgroundImage ( new Image ("BckgForGrid.jpg", 500, 500, false, false),
			 BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		 //Layout 3
		GridPane layout3 = new GridPane();
		layout3.setVgap(10);
		layout3.setHgap(10);
		layout3.add(button11, 18 , 30);
		layout3.add(enterName, 18, 17);
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
        window.setTitle("Battleship -- Demo2 Version");
		window.setResizable(false);
        window.show();
	
    }

}