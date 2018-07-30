import java.util.Vector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
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
    protected static PlayerBoard pboard = new PlayerBoard();
    protected static EnemyBoard eboard = new EnemyBoard();
    protected static AI AI = new AI(pboard);

    // Enemy grid root
    private BorderPane root = new BorderPane();
    private GridPane enemy = new GridPane();
    protected static Button[][] enemyGrid = new Button[10][10];
    // Player grid root
    private BorderPane root2 = new BorderPane();
    private GridPane player = new GridPane();
    protected static Button[][] playerGrid = new Button[10][10];
    // Labels for the end game result and hit/miss messages
	protected static Label result = new Label("");
	protected static Label hit = new Label("");

	// Labels for the enemy and game board
	private Label enlbl = new Label("Enemy Board");
	private Label pllbl = new Label("Player Board");

	// A-J and 1-10 labels for enemy and player boards.
	private Label[] columns = new Label[10];
	private Label[] rows = new Label[10];
	private Label[] columns2 = new Label[10];
	private Label[] rows2 = new Label[10];

	// Bridges together the player and enemy baords.
	private VBox bridge = new VBox();

	// Column constraints to make the boards resizable to window size.
	private ColumnConstraints[] colCons = new ColumnConstraints[10];
	private ColumnConstraints[] colCons2 = new ColumnConstraints[10];

	// Variables to create the scene for placing ships.
	private BorderPane placeShipRoot = new BorderPane();
	private GridPane placeShipGrid = new GridPane();
	private Label[][] placeShipGridElements = new Label[10][10];

    // Holds the coordinates of the ship that is trying to be placed.
	private Vector<int[]> shipvect = new Vector<int[]>();
	// The array of ships from player board to be placed.
	private Ship[] ships = pboard.getShips();
	// A counter to go through the ships array. Helps with ending ship placement.
	private int shipCounter = 0;

	// Label to be placed on top of Placing Ships scene
	private Label placeShipLabel = new Label("Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[0].getName() + " with length of " + ships[0].getLength());


	private StartMenu startMenu = new StartMenu(); //For Game Configuration





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
		Scene placeShips = new Scene(placeShipRoot,350,350); // First scene to place the player's ships
		Scene mainGame = new Scene(root, 400, 640); // Second scene to run the battleship game
		eboard.makeRandomBoard();

    	placeShipRoot.setCenter(placeShipGrid); // Puts the grid at the center of the scene
		placeShipRoot.setTop(placeShipLabel); // Lets you know how and what ships to place

		// For game configuration
		startMenu.start();
		pllbl.setText(startMenu.getPlayerName()+"'s Board");
		System.out.println(startMenu.getMessage().getText());
		if (startMenu.getMessage().getText().equals("Level 1")){
			AI.setDifficulty(3); //random
		}
		else{
			AI.setDifficulty(0); //normal
		}

        // Iterate through all the grid elements in both player and enemy boards to set them up.
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

                // This is not used right now. Could add some functionality later.
				placeShipGridElements[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						int[] coords = new int[2];
						coords[0]  = GridPane.getRowIndex((Label)event.getSource());
						coords[1]  = GridPane.getColumnIndex((Label)event.getSource());
						//shipvect.add(coords);
						//System.out.println(coords[0]+ " "+coords[1]);
						//placeShipGridElements[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("yellowsquare.jpg"),25,25,true,true)));


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
							placeShipLabel.setText("Please place all ships by clicking and dragging your selection."+"\nPlease place "+ ships[Math.min(shipCounter,4)].getName() + " with length of " + ships[Math.min(shipCounter,4)].getLength());
							if(shipCounter>=ships.length){
							    pboard.printBoard();
							    AI = new AI(pboard);
								primaryStage.setScene(mainGame);
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
			colCons[i] = new ColumnConstraints();
			colCons[i].setPercentWidth(100/(enemyGrid.length+2));
			colCons2[i] = new ColumnConstraints();
			colCons2[i].setPercentWidth(100/(enemyGrid.length+2));
			// Creates new buttons and adds some spacing.
            for (int j = 0; j< enemyGrid.length; j++){
				playerGrid[i][j] = new Button();
				enemyGrid[i][j] = new Button();
				playerGrid[i][j].setPadding(new Insets(0,0,0,0));
				enemyGrid[i][j].setPadding(new Insets(0,0,0,0));
                GridPane.setMargin(playerGrid[i][j], new Insets(0, 0, 0, 0));
                GridPane.setMargin(enemyGrid[i][j], new Insets(0, 0, 0, 0));

				
                char col = (char)(j+65);
                String lbl = Character.toString(col)+Integer.toString(i+1);
                int rowi = i;
                int colj = j;
                
				// Method that runs the game logic when you click on the enemy board
                enemyGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
						ButtonFire fire = new ButtonFire(rowi, colj);
						fire.fire(actionEvent);
					}


                });

                enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
                playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bluesquare.jpg"), 25, 25, true, true)));
				enemy.add(enemyGrid[i][j],j+1,i+1);

                player.add(playerGrid[i][j],j+1,i+1);
            }
            // Add column constraints to make game look neater.
			enemy.getColumnConstraints().add(colCons[i]);
			player.getColumnConstraints().add(colCons2[i]);
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
		root.setAlignment(enlbl, Pos.CENTER);
		root2.setAlignment(pllbl, Pos.CENTER);
		placeShipGrid.setAlignment(Pos.CENTER);
		placeShipRoot.setAlignment(placeShipGrid, Pos.CENTER);
		placeShipRoot.setAlignment(placeShipLabel,Pos.CENTER);
		bridge.setAlignment(Pos.TOP_CENTER);


        primaryStage.setTitle("Battleship");
        primaryStage.setScene(placeShips);
        primaryStage.show();
		

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