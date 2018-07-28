import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ButtonFire extends BattleshipApp {

    private int rowi, colj;


    public ButtonFire(int i,int j){
        rowi = i;
        colj = j;

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

    public void fire(ActionEvent actionEvent){
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

}
