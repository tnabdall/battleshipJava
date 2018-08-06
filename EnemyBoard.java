import java.util.Random;
/** EnemyBoard contains the enemy's game board and functions that the player can use to fire on the enemy.
*/
public class EnemyBoard extends GameBoard{

	/**
	 * Default constructor to initialize enemy board.
	 */
	public EnemyBoard(){
		super();
	}

	/** Copy constructor to copy the 2nd player board for GUI
	 * @param g The gameBoard to copy
	 */
	public EnemyBoard(GameBoard g){
		super(g);
	}

	/** Fires at square on board. To be used by GUI.
	 @param row Row to fire at.
	 @param col Column to fire at.
	 */
	public void fire(int row, int col){
		int element = getBoardElement(row,col);
		int[] coords = new int[2];
		coords[0] = row;
		coords[1] = col;
		if(element==3){
			System.out.println("Hit: "+ getShipFiredOn(coords));
			setBoardElement(row,col,2);
			decrementShipElements();

		}
		else if (element==1 || element == 2){
		}
		else{
			System.out.println("Miss");
			setBoardElement(row,col,1);
		}
	}

	/**
	 * Prints the Enemy's Game Board. This function will not display + for an unhit ship so the player cannot cheat.
	 */
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

	/**
	 * Shoot a random spot on the enemy's game board that has not been shot before. Purely for quick testing of game code. Equivalent to Random AI.
	 */
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