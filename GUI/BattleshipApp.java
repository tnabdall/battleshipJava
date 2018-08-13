package GUI;
import Main.*;


import java.util.Vector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Runs the battleship GUI.
 */
public class BattleshipApp extends Application {

    // Variables to help run game.
    private static PlayerBoard playerBoard = new PlayerBoard();
    private static EnemyBoard enemyBoard = new EnemyBoard();
    private static AI enemyAI = new AI(playerBoard);


    // Enemy grid root
    private BorderPane root = new BorderPane();
    private GridPane enemyGridPane = new GridPane();
    private static Button[][] enemyGrid = new Button[10][10];
	
    // Player grid root
    private BorderPane root2 = new BorderPane();
    private GridPane playerGridPane = new GridPane();
    private static Button[][] playerGrid = new Button[10][10];
    // Labels for the end game result and hit/miss messages
	private static Label result = new Label("");
	private static Label hit = new Label("");

	// Labels for the enemy and game board
	private Label enemyBoardLabel = new Label("Enemy Board");
	private Label playerBoardLabel = new Label("Player Board");

	// A-J and 1-10 labels for enemy and player boards.
	private Label[] columnsAJ = new Label[10];
	private Label[] rows1to10 = new Label[10];
	private Label[] columnsAJ2 = new Label[10];
	private Label[] rows1to10n2 = new Label[10];

	// Bridges together the player and enemy boards.
	private VBox playerEnemyGridBridge = new VBox();

	// Variables to create the scene for placing ships.
	private BorderPane placeShipRoot = new BorderPane();
	private GridPane placeShipGrid = new GridPane();
	private Label[][] placeShipGridElements = new Label[10][10];

	// For second player
	private BorderPane placeShipRoot2 = new BorderPane();
	private GridPane placeShipGrid2 = new GridPane();
	private Label[][] placeShipGridElements2 = new Label[10][10];

    // Holds the coordinates of the ship that is trying to be placed.
	private Vector<int[]> shipVector = new Vector<int[]>();
	// The array of ships from player board to be placed.
	private Ship[] ships = playerBoard.getShips();
	private Ship[] ships2 = pBoard2.getShips();
	// A counter to go through the ships array. Helps with ending ship placement.
	private int shipCounter = 0;
	private int shipCounter2 =0; //for Player 2

	// Label to be placed on top of Placing Ships scene
	private Label placeShipLabel;


	private StartMenu startMenu = new StartMenu(); //For Game Configuration

	private static PlayerBoard pBoard2 = new PlayerBoard(); // Used to setup player 2 board.
	private boolean player2Turn = false; // Turn logic for 2 players.

    private Scene placeShips = new Scene(placeShipRoot,500,400); // First scene to place the player's ships
    private Scene placeShips2 = new Scene(placeShipRoot2, 500, 400); //Optional second scene to set up the second player's ships.
    private Scene mainGame = new Scene(root, 400, 680); // Second/Third scene to run the battleship game



    public static void main(String[] args){
        launch(args);
    }

	/**
	 * Runs the GUI for the game.
	 * @param primaryStage The main stage being shown
     */
	public void start(Stage primaryStage) {

		// For game configuration
		startMenu.start();
		placeShipLabel = new Label(startMenu.getPlayerName()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[0].getName() + " with length of " + ships[0].getLength());
		playerBoardLabel.setText(startMenu.getPlayerName()+"'s Board");
		int difficulty = startMenu.getDifficulty(); // Gets difficulty

        // Checks to see if its a 2 player game
		boolean p2 = false;
		if (difficulty  == -1){
			p2 = true;
		}

		// Sets player 2 label
		if(p2){
			enemyBoardLabel.setText(startMenu.getPlayer2Name()+"'s Board");
		}

		// Sets up enemyAI difficulty
		if (!p2) {
			enemyAI.setDifficulty(startMenu.getDifficulty());
		}

		// Sets color scheme
		playerBoardLabel.setTextFill(Color.web(startMenu.getPlayerColor()));
		if(p2){
            enemyBoardLabel.setTextFill(Color.web(startMenu.getPlayerColor()));
        }


		if(!p2) { //If one player, makes random board for enemy.
			enemyBoard.makeRandomBoard();
		}

    	placeShipRoot.setCenter(placeShipGrid); // Puts the grid at the center of the scene
		placeShipRoot.setTop(placeShipLabel); // Lets you know how and what ships to place

        placeShipRoot2.setCenter(placeShipGrid2);




        // Iterate through all the grid elements in both player and enemy boards to set them up.
        if(p2){
            placeShips(primaryStage, true, 1);
            placeShips(primaryStage, true, 2);
        }
        else {
            placeShips(primaryStage, false, 1);
        }

        // Sets some padding for the game.
        root.setPadding(new Insets(10,10,10,10));
		
		// Sets up A-J and 1-10 Labels for each board.
		for (int i = 0; i<10; i++){
			columnsAJ[i] = new Label(Character.toString((char)(i+65)));
			rows1to10[i] = new Label(Integer.toString(i+1));
			columnsAJ2[i] = new Label(Character.toString((char)(i+65)));
			rows1to10n2[i] = new Label(Integer.toString(i+1));
			enemyGridPane.add(columnsAJ[i],i+1,0);
			enemyGridPane.add(rows1to10[i],0,i+1);
			playerGridPane.add(columnsAJ2[i],i+1,0);
			playerGridPane.add(rows1to10n2[i],0,i+1);
		}

        // Sets up player and (enemy or player 2 grids)
        for (int i = 0; i< enemyGrid.length; i++){

			
			// Creates new buttons and adds some spacing.
            for (int j = 0; j< enemyGrid.length; j++){
				playerGrid[i][j] = new Button();
				enemyGrid[i][j] = new Button();
				playerGrid[i][j].setPadding(new Insets(0,0,0,0));
				enemyGrid[i][j].setPadding(new Insets(0,0,0,0));
                GridPane.setMargin(playerGrid[i][j], new Insets(1, 1, 1, 1));
                GridPane.setMargin(enemyGrid[i][j], new Insets(1, 1, 1, 1));


                int rowi = i;
                int colj = j;
                
				// Method that runs the game logic when you click on the enemy board
                enemyGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                    	if (difficulty==-1) {
							fire(actionEvent, rowi, colj,true,1 ); // sets up grid for player vs computer
						}
						else{
                    		fire(actionEvent, rowi, colj, false,1); // sets up grid for 2 player
						}
					}


                });

				playerGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						if (difficulty==-1) {
							fire(actionEvent, rowi, colj,true,2 ); //Gives buttons on player grid functions in 2 player game.
						}
						else{

						}
					}


				});

				// Blue squares for each grid element
                enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
				enemyGridPane.add(enemyGrid[i][j],j+1,i+1);
                playerGridPane.add(playerGrid[i][j],j+1,i+1);
            }

        }
		
		// Sets enemy board and player board labels
		root.setTop(enemyBoardLabel);
		root2.setTop(playerBoardLabel);

		
		// Sets enemy grid in center
        root.setCenter(enemyGridPane);

		// Sets player grid in center and end result at bottom
        root2.setCenter(playerGridPane);
		root2.setBottom(result);

		// VBOX playerEnemyGridBridge allows us to have 2 labels at the enemy's BorderPane bottom
		// First, the hit message, Second, the player BorderPane
		playerEnemyGridBridge.getChildren().add(hit);
		playerEnemyGridBridge.getChildren().add(root2);
        root.setBottom(playerEnemyGridBridge);

        // Sets dark gray background color.
        root.setStyle("-fx-background-color: #D8D8D8;");


		//Centering stuff
		BorderPane.setAlignment(playerEnemyGridBridge, Pos.CENTER);
		BorderPane.setAlignment(enemyGridPane,Pos.CENTER);
		BorderPane.setAlignment(playerGridPane, Pos.CENTER);
		BorderPane.setAlignment(enemyBoardLabel, Pos.CENTER);
		BorderPane.setAlignment(playerBoardLabel, Pos.CENTER);
		placeShipGrid.setAlignment(Pos.CENTER);
		placeShipGrid2.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(placeShipGrid, Pos.CENTER);
		BorderPane.setAlignment(placeShipLabel,Pos.CENTER);
        BorderPane.setAlignment(placeShipGrid2, Pos.CENTER);
		playerEnemyGridBridge.setAlignment(Pos.TOP_CENTER);
		BorderPane.setAlignment(hit,Pos.CENTER);
		BorderPane.setAlignment(result, Pos.CENTER);
		enemyGridPane.setAlignment(Pos.CENTER);
		playerGridPane.setAlignment(Pos.CENTER);

        // Set up game.
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(placeShips); // Start with the placeShips scene for Player 1.
        primaryStage.show();
		

    }


    /**
     * Updates the GUI board after every fire. Color depending on hit, miss, empty, or unhit player ships on a vs Computer game.
	 * @param p2 Is it a 2 player game?
     */
    private void updateBoard(boolean p2){
        for (int i = 0; i< enemyGrid.length; i++){
            for (int j = 0; j< enemyGrid[i].length; j++){
                if(enemyBoard.getBoardElement(i,j)==1){
                    enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("redsquare.jpg"), 25, 25, true, true)));
                }
                else if (enemyBoard.getBoardElement(i,j)==2){
                    enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("greensquare.jpg"), 25, 25, true, true)));
                }
                if(playerBoard.getBoardElement(i,j)==3){
                	if(!p2) { // Dont show ships in 2 player game.
						playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("yellowsquare.jpg"), 25, 25, true, true)));
					}
                }
                else if(playerBoard.getBoardElement(i,j)==2){
                    playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("greensquare.jpg"), 25, 25, true, true)));
                }
                else if(playerBoard.getBoardElement(i,j)==1){
                    playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("redsquare.jpg"), 25, 25, true, true)));
                }
                else{

                }

            }
        }
    }

    /** Checks to see if game is over. Prints a message if so.
     * @param p2 2 player game?
     */
    private void checkWin(boolean p2){
        if (!p2) { // Player vs computer
			if (enemyBoard.getNumberOfShipElements() == 0 && playerBoard.getNumberOfShipElements() == 0) {
				hit.setText("It's a tie!");
				hit.setTextFill(Color.web("Red"));
			} else if (enemyBoard.getNumberOfShipElements() == 0) {
				hit.setText("You win! Congratulations!");
				hit.setTextFill(Color.web("Red"));
			} else if (playerBoard.getNumberOfShipElements() == 0) {
				hit.setText("You lose. Better luck next time.");
				hit.setTextFill(Color.web("Red"));
			}
		}
		else{ // Player vs Player
			if (enemyBoard.getNumberOfShipElements() == 0 && playerBoard.getNumberOfShipElements() == 0) {
				hit.setText("It's a tie!");
				hit.setTextFill(Color.web("Red"));
			} else if (enemyBoard.getNumberOfShipElements() == 0) {
				hit.setText(startMenu.getPlayerName()+" Wins! Congratulations!");
				hit.setTextFill(Color.web("Red"));
			} else if (playerBoard.getNumberOfShipElements() == 0) {
				hit.setText(startMenu.getPlayer2Name()+" Wins! Congratulations!");
				hit.setTextFill(Color.web("Red"));
			}
		}

    }

    /**
     * Runs the place ship window for the game.
     * @param primaryStage Stage to show
     * @param p2 Is it a 2 player game?
     * @param player Which player?
     */
    private void placeShips(Stage primaryStage, boolean p2, int player){

        for(int i = 0; i<10; i++){
            for (int j = 0; j<10; j++){
                // Initialize element
                placeShipGridElements[i][j] = new Label("");
                placeShipGridElements[i][j].setMaxWidth(1);
                placeShipGridElements[i][j].setMaxHeight(1);
                // Sets a blue image for unplaced ships.
                placeShipGridElements[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"),25,25,true,true)));

                // Hold the row and column variables for event handling definitions
                int row = i;
                int col = j;

                // This allows the game to know that a drag event was started.
                placeShipGridElements[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        placeShipGridElements[row][col].startFullDrag();
                    }
                });


                // This adds the coordinates of the square entered to the shipVector array. Also, changes the square to yellow.
                placeShipGridElements[i][j].setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent event) {
                        int[] coords = new int[2];
                        coords[0]  = GridPane.getRowIndex((Label)event.getSource());
                        coords[1]  = GridPane.getColumnIndex((Label)event.getSource());
                        if(!shipVector.contains(coords)) {
                            shipVector.add(coords);
                            //System.out.println(coords[0] + " " + coords[1]);
                            placeShipGridElements[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("yellowsquare.jpg"), 25, 25, true, true)));
                        }
                    }
                });

                /* This is where the main logic for the ship placement is done.
                First we check to see what the size of the vector holding the coordinates is and if it matches the length of the ship to place.
                Next, we see if its a valid ship placement on the board.
                Finally, we place the ship.
                 */
                placeShipGridElements[i][j].setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {

                    @Override
                    public void handle(MouseDragEvent event) {
                        //System.out.println("vector size " + shipVector.size());
                        // Checks to see that the selected squares are the same length of the ship placed.
                        if (shipVector.size() != ships[shipCounter].getLength()) {
                            for (int i = 0; i < shipVector.size(); i++) {
                                placeShipGridElements[shipVector.elementAt(i)[0]][shipVector.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                            }
                            shipVector.clear();
                        }
						/* Checks to see that the ship placement is valid.
						If it is, increase the counter to place the next ship.
						Once all ships are placed, move on to the main game.
						 */
                        else if (playerBoard.isValidPlacement(shipVector)) {
                            playerBoard.placeShip(shipVector, ships[shipCounter]);
                            shipCounter+=1;
                            shipVector.clear();
                            if(p2){
                                placeShipLabel.setText(startMenu.getPlayerName()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter,4)].getName() + " with length of " + ships[Math.min(shipCounter,4)].getLength());

                            }
                            else {
                                placeShipLabel.setText("Please place all ships by clicking and dragging your selection." + "\nPlease place " + ships[Math.min(shipCounter, 4)].getName() + " with length of " + ships[Math.min(shipCounter, 4)].getLength());
                            }
                            if(shipCounter>=ships.length){
                                playerBoard.printBoard();
                                enemyAI = new AI(playerBoard);
                                enemyAI.setDifficulty(startMenu.getDifficulty());
                                if(!p2) {
                                    primaryStage.setScene(mainGame);
                                }
                                else{
                                    placeShipRoot2.setTop(placeShipLabel);
                                    placeShipLabel.setText(startMenu.getPlayer2Name()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter2,4)].getName() + " with length of " + ships[Math.min(shipCounter2,4)].getLength());
                                    BorderPane.setAlignment(placeShipLabel,Pos.CENTER);
                                    primaryStage.setScene(placeShips2);
                                }

                            }
                        }
                        // If the placement is invalid, clear the vector with the coordinates and make the squares blue again.
                        else {
                            for (int i = 0; i < shipVector.size(); i++) {
                                placeShipGridElements[shipVector.elementAt(i)[0]][shipVector.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                            }
                            shipVector.clear();
                        }
                    }

                });




                // Adds the new label element to the grid.
                placeShipGrid.add(placeShipGridElements[i][j],j,i);
            }
        }
        if(p2){
            for(int i = 0; i<10; i++){
                for (int j = 0; j<10; j++){
                    // Initialize element
                    placeShipGridElements2[i][j] = new Label("");
                    placeShipGridElements2[i][j].setMaxWidth(1);
                    placeShipGridElements2[i][j].setMaxHeight(1);
                    // Sets a blue image for unplaced ships.
                    placeShipGridElements2[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"),25,25,true,true)));

                    // Hold the row and column variables for event handling definitions
                    int row = i;
                    int col = j;

                    // This allows the game to know that a drag event was started.
                    placeShipGridElements2[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            placeShipGridElements2[row][col].startFullDrag();
                        }
                    });


                    // This adds the coordinates of the square entered to the shipVector array. Also, changes the square to yellow.
                    placeShipGridElements2[i][j].setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                        @Override
                        public void handle(MouseDragEvent event) {
                            int[] coords = new int[2];
                            coords[0]  = GridPane.getRowIndex((Label)event.getSource());
                            coords[1]  = GridPane.getColumnIndex((Label)event.getSource());
                            if(!shipVector.contains(coords)) {
                                shipVector.add(coords);
                                placeShipGridElements2[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("yellowsquare.jpg"), 25, 25, true, true)));
                            }
                        }
                    });

                /* This is where the main logic for the ship placement is done.
                First we check to see what the size of the vector holding the coordinates is and if it matches the length of the ship to place.
                Next, we see if its a valid ship placement on the board.
                Finally, we place the ship.
                 */
                    placeShipGridElements2[i][j].setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {

                        @Override
                        public void handle(MouseDragEvent event) {
                            //System.out.println("vector size " + shipVector.size());
                            // Checks to see that the selected squares are the same length of the ship placed.
                            if (shipVector.size() != ships2[shipCounter2].getLength()) {
                                for (int i = 0; i < shipVector.size(); i++) {
                                    placeShipGridElements2[shipVector.elementAt(i)[0]][shipVector.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                                }
                                shipVector.clear();
                            }
						/* Checks to see that the ship placement is valid.
						If it is, increase the counter to place the next ship.
						Once all ships are placed, move on to the main game.
						 */
                            else if (pBoard2.isValidPlacement(shipVector)) {
                                pBoard2.placeShip(shipVector, ships2[shipCounter2]);
                                shipCounter2+=1;
                                shipVector.clear();
                                placeShipLabel.setText(startMenu.getPlayer2Name()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter2,4)].getName() + " with length of " + ships[Math.min(shipCounter2,4)].getLength());
                                if(shipCounter2>=ships2.length){
                                    pBoard2.printBoard();
                                    enemyAI = new AI(playerBoard);
                                    enemyAI.setDifficulty(startMenu.getDifficulty());
                                    enemyBoard = new EnemyBoard(pBoard2);

                                    primaryStage.setScene(mainGame);



                                }
                            }
                            // If the placement is invalid, clear the vector with the coordinates and make the squares blue again.
                            else {
                                for (int i = 0; i < shipVector.size(); i++) {
                                    placeShipGridElements2[shipVector.elementAt(i)[0]][shipVector.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                                }
                                shipVector.clear();
                            }
                        }

                    });




                    // Adds the new label element to the grid.
                    placeShipGrid2.add(placeShipGridElements2[i][j],j,i);
                }
            }

        }
    }


    /**
     * Handles the firing and the game logic.
     * @param actionEvent A button click.
	 * @param rowi The row to be fired on.
	 * @param colj The column to be fired on.
	 * @param player2 Is it a 2 player game?
     * @param player Which player are we setting the fire behaviour for?
     */
    public void fire(ActionEvent actionEvent, int rowi, int colj,boolean player2, int player) {
		if (!player2) { // Player vs computer logic
			if (enemyBoard.getNumberOfShipElements() > 0 && playerBoard.getNumberOfShipElements() > 0) { //Don't let the game continue once 1 player is dead.
				if (enemyBoard.getBoardElement(rowi, colj) == 3 || enemyBoard.getBoardElement(rowi, colj) == 0) { // Check to make sure it hasn't been fired on already.
					enemyBoard.fire(rowi, colj);
					// Printing message to player hit or miss.
					if (enemyBoard.getBoardElement(rowi, colj) == 2) {
						hit.setText("Hit: " + enemyBoard.getShipFiredOn(rowi, colj) + " with length of "
								+ Integer.toString(enemyBoard.getShipFiredOnLength(rowi, colj)));
					} else {
						hit.setText("Miss");
					}


					enemyAI.runDifficulty(); //Get AIS next move
					playerBoard.fire(enemyAI.getRow(), enemyAI.getCol());
					updateBoard(player2); // Update the game board
					checkWin(player2); // Check if win.
				}
			}
		}
		else{ // Player vs Player Logic
			if (enemyBoard.getNumberOfShipElements() > 0 && playerBoard.getNumberOfShipElements()>0){ // Check if game is over
				if (player ==2) { // Player 2 will fire on Player 1 Board
					if (player2Turn) { // Only allow action if its player 2's turn.
						if (playerBoard.getBoardElement(rowi, colj) == 3 || playerBoard.getBoardElement(rowi, colj) == 0) { // Only allow if it hasnt been fired on before
							playerBoard.fire(rowi, colj);

							// Update hit/miss message
							if (playerBoard.getBoardElement(rowi, colj) == 2) {
								hit.setText("Hit: " + playerBoard.getShipFiredOn(rowi, colj) + " with length of "
										+ Integer.toString(playerBoard.getShipFiredOnLength(rowi, colj)) + ". It is now P1's Turn.");
							} else {
								hit.setText("Miss" + ". It is now P1's Turn.");
							}
                            player2Turn = false; // Player 1's turn
						}



					}
				}
				else{ // Player 1's turn. Fire on player 2's board.
					if(!player2Turn) {
						if (enemyBoard.getBoardElement(rowi, colj) == 3 || enemyBoard.getBoardElement(rowi, colj) == 0) { // Check if not fired on already.
							enemyBoard.fire(rowi, colj);


                            // Update hit/miss message
							if (enemyBoard.getBoardElement(rowi, colj) == 2) {
								hit.setText("Hit: " + enemyBoard.getShipFiredOn(rowi, colj) + " with length of "
										+ Integer.toString(enemyBoard.getShipFiredOnLength(rowi, colj)) + ". It is now P2's Turn.");
							} else {
								hit.setText("Miss" + ". It is now P2's Turn.");
							}
                            player2Turn = true; // Player 2's turn now.
						}

					}
				}

				updateBoard(player2); // Update the game board
				checkWin(player2); // Check if win.

			}

		}
	}

}
