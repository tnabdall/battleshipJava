import java.util.Scanner;

/** PlayerBoard is the board that contains the player's ships and coordinates.
It will contain functions that allow the enemy AI to receive information about the player's board to help it make decisions.
*/
class PlayerBoard extends GameBoard{
	/** Method similar to hasFired() except it provides the exact status of the location.
	 * empty == 0, miss == 1, hit == 2, unhit ship == 3, out of bounds == 4
	 * @return status returns the value of a location.
	 */
	public int locStatus(int row, int col) {
		int status;
		try {
			status = getBoardElement(row,col);
		} catch (ArrayIndexOutOfBoundsException e) {
			status = 4; // Location is out of bounds.
		}
		return status;
	}
	
	/** Places a ship with user input. Asks user for coordinates and direction of ship.
	@param battleship Ship being placed.
	*/
    public void placeShip(Ship battleship){
        int[] firstcoord;
        Scanner keyboard = new Scanner(System.in);
        String direction;
		// Outer loop to see if placement is valid.
        do {
			// Call function to get coordinates in a valid format.
            firstcoord = getCoordinates();
			// Inner loop to ensure that the direction matches u, r, d, or l for proper use by the program.
            do {
                System.out.print("What direction would you like to place the rest of the ship?\n Type u,r,l,d for up, right, left, or down: ");
                direction = keyboard.next();
                direction = direction.trim().toLowerCase();
            }while(!direction.matches("^(u|r|d|l)$")); // reg exp to ensure u, r, d, or l are entered.
			
			//Lets user know its not a valid placement and prints the board.
			if(!isValidPlacement(firstcoord,direction,battleship)){
				System.out.println("Not a valid placement."); 
				printBoard();
			}
        }while(!isValidPlacement(firstcoord,direction,battleship));

        setBoardElement(firstcoord[0],firstcoord[1],3); // 3 means ship is there but hidden to enemy?
		battleship.setCoords(firstcoord,0);
		// Sets the rest of the board to 3 according to direction of placement.
		int[] nextCoords = new int[2];
        for (int i = 0; i<battleship.getLength()-1; i++){
            if (direction.equalsIgnoreCase("u")){
				nextCoords[0] = firstcoord[0]-i-1;
				nextCoords[1] = firstcoord[1];
                
            }
            else if (direction.equalsIgnoreCase("d")){
				nextCoords[0] = firstcoord[0]+i+1;
				nextCoords[1] = firstcoord[1];
            }
            else if (direction.equalsIgnoreCase("l")){
				nextCoords[0] = firstcoord[0];
				nextCoords[1] = firstcoord[1]-i-1;
            }
            else if (direction.equalsIgnoreCase("r")){
				nextCoords[0] = firstcoord[0];
				nextCoords[1] = firstcoord[1]+i+1;
            }
			setBoardElement(nextCoords[0],nextCoords[1],3);
			battleship.setCoords(nextCoords,i+1);
        }
        printBoard();

    }
	
	/** Function to place all ships with user input. No extra parameters needed. Should be called to set up a user board.
	*/
    public void placeAllShips(){
		Ship[] ships = getShips();
        for (int i = 0; i<ships.length; i++){
            System.out.println("Please place the " + ships[i].getName() + " with length of " + ships[i].getLength() +".");
            placeShip(ships[i]);
        }
    }
	

	
	/** Fires at square on board. To be used by enemy AI.
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
			//AI.addToCounter(); IMPLEMENT LATER

		}
		else if (element==1 || element == 2){
		}
		else{
			System.out.println("Miss");
			setBoardElement(row,col,1);
		}
	}
	
	/** Prints the game board. Can be used for user or enemy boards.
	*/
    public void printBoard(){
		System.out.println("      PLAYER BOARD     ");
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
                System.out.print(boardMarker(getBoardElement(i,j))); //Print marker according to board
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border

    }
	
}