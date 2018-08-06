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


public class BattleshipApp extends Application {

    // Variables to help run game.
    private static PlayerBoard pboard = new PlayerBoard();
    private static EnemyBoard eboard = new EnemyBoard();
    private static AI enemyAI = new AI(pboard);


    // Enemy grid root
    private BorderPane root = new BorderPane();
    private GridPane enemy = new GridPane();
    private static Button[][] enemyGrid = new Button[10][10];
	
    // Player grid root
    private BorderPane root2 = new BorderPane();
    private GridPane player = new GridPane();
    private static Button[][] playerGrid = new Button[10][10];
    // Labels for the end game result and hit/miss messages
	private static Label result = new Label("");
	private static Label hit = new Label("");

	// Labels for the enemy and game board
	private Label enlbl = new Label("Enemy Board");
	private Label pllbl = new Label("Player Board");

	// A-J and 1-10 labels for enemy and player boards.
	private Label[] columns = new Label[10];
	private Label[] rows = new Label[10];
	private Label[] columns2 = new Label[10];
	private Label[] rows2 = new Label[10];

	// Bridges together the player and enemy boards.
	private VBox bridge = new VBox();

	// Column constraints to make the boards resizable to window size.
	//private ColumnConstraints[] colCons = new ColumnConstraints[10];
	//private ColumnConstraints[] colCons2 = new ColumnConstraints[10];

	// Variables to create the scene for placing ships.
	private BorderPane placeShipRoot = new BorderPane();
	private GridPane placeShipGrid = new GridPane();
	private Label[][] placeShipGridElements = new Label[10][10];

	private BorderPane placeShipRoot2 = new BorderPane();
	private GridPane placeShipGrid2 = new GridPane();
	private Label[][] placeShipGridElements2 = new Label[10][10];

    // Holds the coordinates of the ship that is trying to be placed.
	private Vector<int[]> shipvect = new Vector<int[]>();
	// The array of ships from player board to be placed.
	private Ship[] ships = pboard.getShips();
	private Ship[] ships2 = pBoard2.getShips();
	// A counter to go through the ships array. Helps with ending ship placement.
	private int shipCounter = 0;
	private int shipCounter2 =0; //for Player 2

	// Label to be placed on top of Placing Ships scene
	private Label placeShipLabel;


	private StartMenu startMenu = new StartMenu(); //For Game Configuration

	private static PlayerBoard pBoard2 = new PlayerBoard(); // Used to setup player 2 board.
	private boolean player2Turn = false; // Turn logic for 2 players.

    private Scene placeShips = new Scene(placeShipRoot,350,350); // First scene to place the player's ships
    private Scene placeShips2 = new Scene(placeShipRoot2, 350, 350);
    private Scene mainGame = new Scene(root, 400, 640); // Second scene to run the battleship game



    public static void main(String[] args){
        launch(args);
    }

	/**
	 * Runs the GUI for the game.
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception
    {

		// For game configuration
		startMenu.start();
		placeShipLabel = new Label(startMenu.getPlayerName()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[0].getName() + " with length of " + ships[0].getLength());
		pllbl.setText(startMenu.getPlayerName()+"'s Board");
		int difficulty = startMenu.getDifficulty();
		boolean p2 = false;
		if (difficulty  == -1){
			p2 = true;
		}
		System.out.println(p2);
		if(p2){
			enlbl.setText(startMenu.getPlayer2Name()+"'s Board");
		}

		if (!p2) {
			enemyAI.setDifficulty(startMenu.getDifficulty());
		}
		else{
			//2 Player code.
		}
		pllbl.setTextFill(Color.web(startMenu.getPlayerColor()));
		if(p2){
            enlbl.setTextFill(Color.web(startMenu.getPlayerColor()));
        }


		System.out.println(difficulty);


		if(!p2) {
			eboard.makeRandomBoard();
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


        //pboard.makeRandomBoard();


        root.setPadding(new Insets(10,10,10,10));
		
		// Sets up A-J and 1-10 Labels for each board.
		for (int i = 0; i<10; i++){
			columns[i] = new Label(Character.toString((char)(i+65)));
			rows[i] = new Label(Integer.toString(i+1));
			columns2[i] = new Label(Character.toString((char)(i+65)));
			rows2[i] = new Label(Integer.toString(i+1));
			enemy.add(columns[i],i+1,0);
			enemy.add(rows[i],0,i+1);
			player.add(columns2[i],i+1,0);
			player.add(rows2[i],0,i+1);
		}

        // For enemy's grid. Updates the row and column to fire upon.
        for (int i = 0; i< enemyGrid.length; i++){
			// Sets column constraints for each grid member.
			//colCons[i] = new ColumnConstraints();
			//colCons[i].setPercentWidth(100/(enemyGrid.length+4));
			//colCons2[i] = new ColumnConstraints();
			//colCons2[i].setPercentWidth(100/(enemyGrid.length+4));
			
			// Creates new buttons and adds some spacing.
            for (int j = 0; j< enemyGrid.length; j++){
				playerGrid[i][j] = new Button();
				enemyGrid[i][j] = new Button();
				playerGrid[i][j].setPadding(new Insets(0,0,0,0));
				enemyGrid[i][j].setPadding(new Insets(0,0,0,0));
                GridPane.setMargin(playerGrid[i][j], new Insets(1, 1, 1, 1));
                GridPane.setMargin(enemyGrid[i][j], new Insets(1, 1, 1, 1));

				
                char col = (char)(j+65);
                String lbl = Character.toString(col)+Integer.toString(i+1);
                int rowi = i;
                int colj = j;
                
				// Method that runs the game logic when you click on the enemy board
                enemyGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                    	if (difficulty==-1) {
							fire(actionEvent, rowi, colj,true,1 );
						}
						else{
                    		fire(actionEvent, rowi, colj, false,1);
						}
					}


                });

				playerGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						if (difficulty==-1) {
							fire(actionEvent, rowi, colj,true,2 );
						}
						else{

						}
					}


				});

                enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
				enemy.add(enemyGrid[i][j],j+1,i+1);

                player.add(playerGrid[i][j],j+1,i+1);
            }
            // Add column constraints to make game look neater.
			//enemy.getColumnConstraints().add(colCons[i]);
			//player.getColumnConstraints().add(colCons2[i]);
        }
		
		// Sets enemy board and player board labels
		root.setTop(enlbl);
		root2.setTop(pllbl);

		
		// Sets enemy grid in center
        root.setCenter(enemy);

		// Sets player grid in center and end result at bottom
        root2.setCenter(player);
		root2.setBottom(result);
		// VBOX bridge allows us to have 2 labels at the enemy's BorderPane bottom
		// First, the hit message, Second, the player BorderPane
		bridge.getChildren().add(hit);
		bridge.getChildren().add(root2);
        root.setBottom(bridge);


		//Centering stuff
		root.setAlignment(bridge, Pos.CENTER);
		root.setAlignment(enemy,Pos.CENTER);
		root2.setAlignment(player, Pos.CENTER);
		root.setAlignment(enlbl, Pos.CENTER);
		root2.setAlignment(pllbl, Pos.CENTER);
		placeShipGrid.setAlignment(Pos.CENTER);
		placeShipGrid2.setAlignment(Pos.CENTER);
		placeShipRoot.setAlignment(placeShipGrid, Pos.CENTER);
		placeShipRoot.setAlignment(placeShipLabel,Pos.CENTER);
        placeShipRoot2.setAlignment(placeShipGrid2, Pos.CENTER);

		bridge.setAlignment(Pos.TOP_CENTER);
		root.setAlignment(hit,Pos.CENTER);
		root2.setAlignment(result, Pos.CENTER);
		enemy.setAlignment(Pos.CENTER);
		player.setAlignment(Pos.CENTER);


        primaryStage.setTitle("Battleship");
        primaryStage.setScene(placeShips);
        primaryStage.show();
		

    }


    /**
     * Updates the GUI board after every fire.
	 * @param p2
     */
    public void updateBoard(boolean p2){
        for (int i = 0; i< enemyGrid.length; i++){
            for (int j = 0; j< enemyGrid[i].length; j++){
                if(eboard.getBoardElement(i,j)==1){
                    enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("redsquare.jpg"), 25, 25, true, true)));
                }
                else if (eboard.getBoardElement(i,j)==2){
                    enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("greensquare.jpg"), 25, 25, true, true)));
                }
                if(pboard.getBoardElement(i,j)==3){
                	if(!p2) {
						playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("yellowsquare.jpg"), 25, 25, true, true)));
					}
                }
                else if(pboard.getBoardElement(i,j)==2){
                    playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("greensquare.jpg"), 25, 25, true, true)));
                }
                else if(pboard.getBoardElement(i,j)==1){
                    playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("redsquare.jpg"), 25, 25, true, true)));
                }
                else{

                }
                //enemyGrid[i][j].setText(eboard.boardMarker(eboard.getBoardElement(i,j),true));
                //playerGrid[i][j].setText(pboard.boardMarker(pboard.getBoardElement(i,j)));
            }
        }
    }

    /** Checks to see if game is over. Prints a message if so.
     * @param p2 2 player game?
     */
    public void checkWin(boolean p2){
        if (!p2) {
			if (eboard.getNumberOfShipElements() == 0 && pboard.getNumberOfShipElements() == 0) {
				hit.setText("It's a tie!");
				hit.setTextFill(Color.web("Red"));
			} else if (eboard.getNumberOfShipElements() == 0) {
				hit.setText("You win! Congratulations!");
				hit.setTextFill(Color.web("Red"));
			} else if (pboard.getNumberOfShipElements() == 0) {
				hit.setText("You lose. Better luck next time.");
				hit.setTextFill(Color.web("Red"));
			}
		}
		else{
			if (eboard.getNumberOfShipElements() == 0 && pboard.getNumberOfShipElements() == 0) {
				hit.setText("It's a tie!");
				hit.setTextFill(Color.web("Red"));
			} else if (eboard.getNumberOfShipElements() == 0) {
				hit.setText(startMenu.getPlayerName()+" Wins! Congratulations!");
				hit.setTextFill(Color.web("Red"));
			} else if (pboard.getNumberOfShipElements() == 0) {
				hit.setText(startMenu.getPlayer2Name()+" Wins! Congratulations!");
				hit.setTextFill(Color.web("Red"));
			}
		}

    }

    public void placeShips(Stage primaryStage, boolean p2, int player){

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


                // This adds the coordinates of the square entered to the shipvect array. Also, changes the square to yellow.
                placeShipGridElements[i][j].setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent event) {
                        int[] coords = new int[2];
                        coords[0]  = GridPane.getRowIndex((Label)event.getSource());
                        coords[1]  = GridPane.getColumnIndex((Label)event.getSource());
                        if(!shipvect.contains(coords)) {
                            shipvect.add(coords);
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
                        //System.out.println("vector size " + shipvect.size());
                        // Checks to see that the selected squares are the same length of the ship placed.
                        if (shipvect.size() != ships[shipCounter].getLength()) {
                            for (int i = 0; i < shipvect.size(); i++) {
                                placeShipGridElements[shipvect.elementAt(i)[0]][shipvect.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                            }
                            shipvect.clear();
                        }
						/* Checks to see that the ship placement is valid.
						If it is, increase the counter to place the next ship.
						Once all ships are placed, move on to the main game.
						 */
                        else if (pboard.isValidPlacement(shipvect)) {
                            pboard.placeShip(shipvect, ships[shipCounter]);
                            shipCounter+=1;
                            shipvect.clear();
                            if(p2){
                                placeShipLabel.setText(startMenu.getPlayerName()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter,4)].getName() + " with length of " + ships[Math.min(shipCounter,4)].getLength());

                            }
                            else {
                                placeShipLabel.setText("Please place all ships by clicking and dragging your selection." + "\nPlease place " + ships[Math.min(shipCounter, 4)].getName() + " with length of " + ships[Math.min(shipCounter, 4)].getLength());
                            }
                            if(shipCounter>=ships.length){
                                pboard.printBoard();
                                enemyAI = new AI(pboard);
                                enemyAI.setDifficulty(startMenu.getDifficulty());
                                if(!p2) {
                                    primaryStage.setScene(mainGame);
                                }
                                else{
                                    placeShipRoot2.setTop(placeShipLabel);
                                    placeShipLabel.setText(startMenu.getPlayer2Name()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter2,4)].getName() + " with length of " + ships[Math.min(shipCounter2,4)].getLength());
                                    placeShipRoot2.setAlignment(placeShipLabel,Pos.CENTER);
                                    primaryStage.setScene(placeShips2);
                                }

                            }
                        }
                        // If the placement is invalid, clear the vector with the coordinates and make the squares blue again.
                        else {
                            for (int i = 0; i < shipvect.size(); i++) {
                                placeShipGridElements[shipvect.elementAt(i)[0]][shipvect.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                            }
                            shipvect.clear();
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


                    // This adds the coordinates of the square entered to the shipvect array. Also, changes the square to yellow.
                    placeShipGridElements2[i][j].setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                        @Override
                        public void handle(MouseDragEvent event) {
                            int[] coords = new int[2];
                            coords[0]  = GridPane.getRowIndex((Label)event.getSource());
                            coords[1]  = GridPane.getColumnIndex((Label)event.getSource());
                            if(!shipvect.contains(coords)) {
                                shipvect.add(coords);
                                //System.out.println(coords[0] + " " + coords[1]);
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
                            //System.out.println("vector size " + shipvect.size());
                            // Checks to see that the selected squares are the same length of the ship placed.
                            if (shipvect.size() != ships2[shipCounter2].getLength()) {
                                for (int i = 0; i < shipvect.size(); i++) {
                                    placeShipGridElements2[shipvect.elementAt(i)[0]][shipvect.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                                }
                                shipvect.clear();
                            }
						/* Checks to see that the ship placement is valid.
						If it is, increase the counter to place the next ship.
						Once all ships are placed, move on to the main game.
						 */
                            else if (pBoard2.isValidPlacement(shipvect)) {
                                pBoard2.placeShip(shipvect, ships2[shipCounter2]);
                                shipCounter2+=1;
                                shipvect.clear();
                                placeShipLabel.setText(startMenu.getPlayer2Name()+". Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter2,4)].getName() + " with length of " + ships[Math.min(shipCounter2,4)].getLength());
                                if(shipCounter2>=ships2.length){
                                    pBoard2.printBoard();
                                    enemyAI = new AI(pboard);
                                    enemyAI.setDifficulty(startMenu.getDifficulty());
                                    eboard = new EnemyBoard(pBoard2);

                                    primaryStage.setScene(mainGame);



                                }
                            }
                            // If the placement is invalid, clear the vector with the coordinates and make the squares blue again.
                            else {
                                for (int i = 0; i < shipvect.size(); i++) {
                                    placeShipGridElements2[shipvect.elementAt(i)[0]][shipvect.elementAt(i)[1]].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                                }
                                shipvect.clear();
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
     */
    public void fire(ActionEvent actionEvent, int rowi, int colj,boolean player2, int player) {
		if (!player2) {
			if (eboard.getNumberOfShipElements() > 0 && pboard.getNumberOfShipElements() > 0) { //Don't let the game continue once 1 player is dead.
				if (eboard.getBoardElement(rowi, colj) == 3 || eboard.getBoardElement(rowi, colj) == 0) { // Check to make sure it hasn't been fired on already.
					eboard.fire(rowi, colj);
					// Printing message to player hit or miss.
					if (eboard.getBoardElement(rowi, colj) == 2) {
						hit.setText("Hit: " + eboard.getShipFiredOn(rowi, colj) + " with length of "
								+ Integer.toString(eboard.getShipFiredOnLength(rowi, colj)));
					} else {
						hit.setText("Miss");
					}
					//System.out.println("Player shot at " + rowi + " " + colj);

					enemyAI.runDifficulty(); //Get AIS next move

					//System.out.println("Enemy shot at " + enemyAI.getRow() + " " + enemyAI.getCol());
					pboard.fire(enemyAI.getRow(), enemyAI.getCol());
					updateBoard(player2); // Update the game board
					checkWin(player2); // Check if win.
				}
			}
		}
		else{
			if (eboard.getNumberOfShipElements() > 0 && pboard.getNumberOfShipElements()>0){
				if (player ==2) {
					if (player2Turn) {
						if (pboard.getBoardElement(rowi, colj) == 3 || pboard.getBoardElement(rowi, colj) == 0) {
							pboard.fire(rowi, colj);

							if (pboard.getBoardElement(rowi, colj) == 2) {
								hit.setText("Hit: " + pboard.getShipFiredOn(rowi, colj) + " with length of "
										+ Integer.toString(pboard.getShipFiredOnLength(rowi, colj)) + ". It is now P1's Turn.");
							} else {
								hit.setText("Miss" + ". It is now P1's Turn.");
							}
                            player2Turn = false;
						}



					}
				}
				else{
					if(!player2Turn) {
						if (eboard.getBoardElement(rowi, colj) == 3 || eboard.getBoardElement(rowi, colj) == 0) {
							eboard.fire(rowi, colj);

							if (eboard.getBoardElement(rowi, colj) == 2) {
								hit.setText("Hit: " + eboard.getShipFiredOn(rowi, colj) + " with length of "
										+ Integer.toString(eboard.getShipFiredOnLength(rowi, colj)) + ". It is now P2's Turn.");
							} else {
								hit.setText("Miss" + ". It is now P2's Turn.");
							}
                            player2Turn = true;
						}

					}
				}

				updateBoard(player2); // Update the game board
				checkWin(player2); // Check if win.

			}

		}
	}

}

/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class AddingNumbersApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane root = new BorderPane();
        // Margin of 10 pixels
        root.setPadding(new Insets(10,10,10,10));
        Button btnAdd;
        TextField txtNum1, txtNum2;
        Label lblSum;
        // Add a label message in the top. We create the
        // label without a named reference since the label
        // is read-only; we never change it so no reference is needed.
        root.setTop(new Label("Enter an integer into each textbox " +
                "and click the button to compute the sum."));
        // The label that will display the sum goes into the bottom.
        // Initially it is just a blank string.
        lblSum = new Label("");
        root.setBottom(lblSum);
        // Create a GridPane in the center of the BorderPane
        GridPane center = new GridPane();
        center.setVgap(5);
        center.setHgap(5);
        txtNum1 = new TextField("0"); // Default text of 0
        txtNum1.setPrefWidth(150);
        txtNum2 = new TextField("0");
        txtNum2.setPrefWidth(150);
        center.add(new Label("Number 1"), 0, 0);
        center.add(new Label("Number 2"), 0, 1);
        center.add(txtNum1, 1, 0);
        center.add(txtNum2, 1, 1);
        btnAdd = new Button("Add Numbers");
        center.add(btnAdd, 1, 2);
        root.setCenter(center);
        // Set the event handler when the button is clicked
        btnAdd.setOnAction(new EventHandler<ActionEvent>()
                           {
                               @Override
                               public void handle(ActionEvent event)
                               {
                                   int num1 = Integer.parseInt(txtNum1.getText());
                                   int num2 = Integer.parseInt(txtNum2.getText());
                                   int sum = num1 + num2;
                                   lblSum.setText("The sum is " + sum);
                               }
                           }
        );
        Scene scene = new Scene(root, 450, 150);
        primaryStage.setTitle("Compute the Sum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
 */