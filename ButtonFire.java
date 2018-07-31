import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Handles the events for Clicking a button in the GUI. Clicking will fire on the playerboard.
 * Protected access to the BattleshipApp in order to alter the playerboard and other variables.
 */
public class ButtonFire extends BattleshipApp {

    private int rowi, colj;

    /**
     * Sets the row and column to fire on.
     * @param i The row
     * @param j The column
     */
    public ButtonFire(int i,int j){
        rowi = i;
        colj = j;

    }

    /**
     * Updates the GUI board after every fire.
     */
    public void updateBoard(){
        for (int i = 0; i< enemyGrid.length; i++){
            for (int j = 0; j< enemyGrid[i].length; j++){
                if(eboard.getBoardElement(i,j)==1){
                    enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("redsquare.jpg"), 25, 25, true, true)));
                }
                else if (eboard.getBoardElement(i,j)==2){
                    enemyGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("greensquare.jpg"), 25, 25, true, true)));
                }
                if(pboard.getBoardElement(i,j)==3){
                    playerGrid[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("yellowsquare.jpg"), 25, 25, true, true)));
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
     *
     */
    public void checkWin(){
        if (eboard.getNumberOfShipElements()==0 && pboard.getNumberOfShipElements() == 0){
            result.setText("It's a tie!");
            result.setTextFill(Color.web("Red"));
        }
        else if (eboard.getNumberOfShipElements() == 0){
            result.setText("You win! Congratulations!");
            result.setTextFill(Color.web("Red"));
        }
        else if (pboard.getNumberOfShipElements()==0){
            result.setText("You lose. Better luck next time.");
            result.setTextFill(Color.web("Red"));
        }

    }

    /**
     * Handles the firing and the game logic.
     * @param actionEvent A button click.
     */
    public void fire(ActionEvent actionEvent){
        if(eboard.getNumberOfShipElements()>0 && pboard.getNumberOfShipElements()>0){ //Don't let the game continue once 1 player is dead.
            if(eboard.getBoardElement(rowi,colj)==3 || eboard.getBoardElement(rowi,colj)==0 ) { // Check to make sure it hasn't been fired on already.
                eboard.fire(rowi, colj);
                // Printing message to player hit or miss.
                if (eboard.getBoardElement(rowi, colj) == 2) {
                    hit.setText("Hit: " + eboard.getShipFiredOn(rowi, colj) + " with length of "
                            + Integer.toString(eboard.getShipFiredOnLength(rowi, colj)));
                } else {
                    hit.setText("Miss");
                }
                System.out.println("Player shot at " + rowi + " " + colj);

                AI.runDifficulty(); //Get AIS next move

                System.out.println("Enemy shot at " + AI.getRow() + " " + AI.getCol());
                pboard.fire(AI.getRow(), AI.getCol());
                updateBoard(); // Update the game board
                checkWin(); // Check if win.
            }
        }
    }

}
