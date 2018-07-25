import javafx.application.Application;
import javafx.scene.Scene;
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

public class BattleshipApp extends Application {

    private int enemyFireRow = 0;
    private int enemyFireColumn = 0;
    private int playerFireRow = 0;
    private int playerFireColumn = 0;

    private PlayerBoard pboard = new PlayerBoard();
    private EnemyBoard eboard = new EnemyBoard();
    private AI AI = new AI(pboard);

    private BorderPane root = new BorderPane();
    private GridPane enemy = new GridPane();
    private Button[][] enemyGrid = new Button[10][10];
    private BorderPane root2 = new BorderPane();
    private GridPane player = new GridPane();
    private Button[][] playerGrid = new Button[10][10];
	private Label result = new Label("");
	private Label hit = new Label("");
	private Label enlbl = new Label("Enemy Board");
	private Label pllbl = new Label("Player Board");
	
	private Label[] columns = new Label[10];
	private Label[] rows = new Label[10];
	private Label[] columns2 = new Label[10];
	private Label[] rows2 = new Label[10];
	private VBox bridge = new VBox();
	private ColumnConstraints[] colCons = new ColumnConstraints[10];
	private ColumnConstraints[] colCons2 = new ColumnConstraints[10];

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        pboard.makeRandomBoard();
        eboard.makeRandomBoard();
		AI.setDifficulty(3);

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

		
		
		//root.setLeft(rowbox);
		
		//root.setRight(hit);
		
		//root2.setLeft(new VBox(rowbox));
		
		



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
				player.setHgap(5);
				player.setVgap(5);
				enemy.setHgap(5);
				enemy.setVgap(5);
				
                char col = (char)(j+65);
                String lbl = Character.toString(col)+Integer.toString(i+1);
                int rowi = i;
                int colj = j;
                
				// Method that runs the game logic when you click on the enemy board
                enemyGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
						if(eboard.getNumberOfShipElements()>0 && pboard.getNumberOfShipElements()>0){ //Don't let the game continue once 1 player is dead.
							eboard.fire(rowi,colj);
							// Printing message to player hit or miss.
							if(eboard.getBoardElement(rowi,colj)==2){ 
								hit.setText("Hit: " + eboard.getShipFiredOn(rowi,colj) + " with length of "
								+ Integer.toString(eboard.getShipFiredOnLength(rowi,colj)));
							}
							else{
								hit.setText("Miss");
							}
							System.out.println("Player shot at " + rowi+" " + colj);

							AI.runDifficulty(); //Get AIS next move
					
							System.out.println("Enemy shot at " + AI.getRow()+" " +AI.getCol());
							pboard.fire(AI.getRow(),AI.getCol());
							updateBoard(); // Update the game board
							checkWin(); // Check if win.
						}

                    }
                });
				/*
                playerGrid[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        pboard.fire(rowi, colj);
                        updateBoard();
						checkWin();

                    }
                });
				*/
				
				//enemyGrid[i][j].setText(lbl);
				enemy.add(enemyGrid[i][j],j+1,i+1);
                //playerGrid[i][j].setText(lbl);
                player.add(playerGrid[i][j],j+1,i+1);
            }
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

        Scene scene = new Scene(root, 420, 750);
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
		

    }
	
	// Updates board upon hit.
    public void updateBoard(){
        for (int i = 0; i< enemyGrid.length; i++){
            for (int j = 0; j< enemyGrid[i].length; j++){
                enemyGrid[i][j].setText(eboard.boardMarker(eboard.getBoardElement(i,j),true));
                playerGrid[i][j].setText(pboard.boardMarker(pboard.getBoardElement(i,j)));
            }
        }
    }
	
	// Checks to see if game is over. Prints a message if so.
	public void checkWin(){
		if (eboard.getNumberOfShipElements()==0 && pboard.getNumberOfShipElements() == 0){
			result.setText("It's a tie!");
		}
		else if (eboard.getNumberOfShipElements() == 0){
			result.setText("You win! Congratulations!");
		}
		else if (pboard.getNumberOfShipElements()==0){
			result.setText("You lose. Better luck next time.");
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