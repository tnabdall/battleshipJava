import java.util.Random;
/** EnemyBoard contains the enemy's game board and functions that the player can use to fire on the enemy.
*/
public class EnemyBoard extends GameBoard{
	
	/** Asks the player for a coordinate to fire on and checks if hit or miss.
	Updates the board accordingly.
	*/
	public void fire(){
		System.out.print("Choose a coordinate to fire on: ");
		int[] coords = getCoordinates();
		while(hasFired(coords[0],coords[1])){
			System.out.println("You have already fired there. Choose another coordinate.");
			coords = getCoordinates();
		}
		if(getBoardElement(coords[0],coords[1])==3){
			System.out.println("Hit: " + getShipFiredOn(coords) +" (" + getShipFiredOnLength(coords)+")");
			setBoardElement(coords[0],coords[1],2);
			decrementShipElements();
		}
		else{
			System.out.println("Miss");
			setBoardElement(coords[0],coords[1],1);
		}
	}
	
	/** Checks to see if the square has been fired on before.
	@param row Row to check for fire.
	@param col Column to check for fire.
	@return Boolean value that indicates whether the square has been fired upon.
	*/
	private boolean hasFired(int row, int col){
		boolean fired = false;
		int element = getBoardElement(row,col);
		if (element==1 || element ==2){
			fired = true;
		}
		else{
			fired = false;
		}
		return fired;
	}
	
	public void printBoard(){
		System.out.println("      ENEMY BOARD     ");
		System.out.println("   A B C D E F G H I J"); // Column letters
        //System.out.println(" --------------------"); // Top border
        for (int i = 0; i<10; i++){
            if (i<9){
                System.out.print(i+1+" "); // Print row number
            }
            else{
                System.out.print(i+1); // So that all rows are aligned as 10 takes an extra character space.
            }
            for (int j = 0; j<10; j++){
                System.out.print("|"); // Print border
				// Replaces '+' printout for own ship with " " to indicate unknown space.
				if(getBoardElement(i,j) != 3){
					System.out.print(boardMarker(getBoardElement(i,j))); //Print marker according to board
				}
				else{
					System.out.print(" ");
				}
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border
		
	}
	
	public void randomFire(){
		Random rrow = new Random();
		Random rcol = new Random();
		int[] coords = new int[2];
		coords[0] = rrow.nextInt(10);
		coords[1] = rcol.nextInt(10);
		while(hasFired(coords[0],coords[1])){
			coords[0] = rrow.nextInt(10);
			coords[1] = rcol.nextInt(10);
		}
			
		if(getBoardElement(coords[0],coords[1])==3){
			System.out.println("Hit: " + getShipFiredOn(coords) +" (" + getShipFiredOnLength(coords)+")");
			setBoardElement(coords[0],coords[1],2);
			decrementShipElements();
		}
		else{
			System.out.println("Miss");
			setBoardElement(coords[0],coords[1],1);
		}
		
	}
	
	
}