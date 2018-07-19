import java.util.Scanner;
import java.util.Random;
public class GameBoard{

    private Ship[] ships = new Ship[5]; // Variable for ships. May implement get number of ships later to allow game to be flexible.

    private int[][] board = new int[10][10]; // Variable containing the board. 10x 10 matrix.
	
	private int numberOfShipElements = 0;
	
	/**
	Constructor for GameBoard.
	Automatically initializes the board to a blank slate and adds default 5 ships to the game.
	*/
    public GameBoard(){
        InitializeBoard();
        ships[0] = new Ship("Carrier",5);
        ships[1] = new Ship("Battleship", 4);
        ships[2] = new Ship("Submarine", 3);
        ships[3] = new Ship("Cruiser",3);
        ships[4] = new Ship("Destroyer",2);
		numberOfShipElements = 17;
    }
	
	/**
	Returns game board to player.
	@return Returns the game board.
	*/
    public int[][] getBoard() {
        return board; 
    }
	
	public Ship[] getShips(){
		return ships;
	}
	
	/** Returns number of ship elements remaining on board.
	@return Number of ship elements remaining (like health)
	*/
	public int getNumberOfShipElements(){
		return numberOfShipElements;
	}
	
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
		if(board[coords[0]][coords[1]]==3){
			System.out.println("Hit");
			board[coords[0]][coords[1]]=2;
			numberOfShipElements -=1;
		}
		else{
			System.out.println("Miss");
			board[coords[0]][coords[1]]=1;
		}
	}
	
	/** Checks to see if the square has been fired on before.
	@param row Row to check for fire.
	@param col Column to check for fire.
	@return Boolean value that indicates whether the square has been fired upon.
	*/
	public boolean hasFired(int row, int col){
		boolean fired = false;
		if (board[row][col]==1 || board[row][col] ==2){
			fired = true;
		}
		else{
			fired = false;
		}
		return fired;
	}
	
	/** Fires at square on board. To be used by enemy AI.
	@param row Row to fire at.
	@param col Column to fire at.
	*/
	public void enemyFire(int row, int col){
		if(board[row][col]==3){
			//System.out.println("Hit");
			board[row][col]=2;
			numberOfShipElements -=1;
		}
		else if (board[row][col]==1 || board[row][col] == 2){
		}
		else{
			//System.out.println("Miss");
			board[row][col]=1;
		}
	}
	
	/** 
	Initializes every element in the board array to 0 to symbolize it being empty.
	*/
    private void InitializeBoard(){
        for(int i=0; i<this.board.length;i++){
            for(int j=0; j<this.board[i].length;j++){
                this.board[i][j]=0;
            }
        }
    }
	
	/** Asks the player for coordinates in the form "A1".
	1st part of placing a ship.
	@ return getCoordinates Two element array where first is the board's row index and the seonc is the board's column index.
	*/
    public int[] getCoordinates(){
        int[] coords = new int[2];
        Scanner keyboard = new Scanner(System.in);
        String scoords;
        do{
            System.out.print("Which coordinate would you like? (eg. A3): ");
            scoords = keyboard.next();
            scoords = scoords.trim();
        } while(!scoords.matches("^[A-J|a-j]([1-9]|10)$")); // Forces string to be within the range of Game Coordinates
        scoords = scoords.toLowerCase();
        char letter = scoords.charAt(0);
        int column = (int) letter;
        column-=97;
        int row = Integer.parseInt(scoords.substring(1));
        row-=1;
        coords[0] = row;
        coords[1] = column;
        return coords;
    }
	
	/** Makes a random board. Mostly for enemy board use.
	*/
	public void makeRandomBoard(){
		// Random variables for row, column, and direction
		Random randr = new Random();
		Random randc = new Random();
		Random randd = new Random();
		// coords contains row then column
		int[] coords = new int[2];
		int dirn; // for random direction, convert to string later
		String dir ="";
		int validPlacements = 0; //Number of ships placed correctly
		while (validPlacements<5){
			coords[0] = randr.nextInt(10);
			coords[1] = randc.nextInt(10);
			dirn = randd.nextInt(4);
			// Converts dirn to a direction readable by isValidPlacement function
			switch(dirn){
				case 0:
					dir = "u";
					break;
				case 1:
					dir = "d";
					break;
				case 2:
					dir = "l";
					break;
				case 3:
					dir = "r";
					break;
			}
			// Checks to see if the ship can be placed at the coordinates and direction.
			if (isValidPlacement(coords,dir,ships[validPlacements])){
				/*System.out.print(coords[0]);
				System.out.print(coords[1]);
				System.out.println(dir);
				*/
				placeRandomShip(coords,dir,ships[validPlacements]);
				validPlacements+=1;// Increase counter

			}
			
		}
		
		
	}
	
	
	/** Checks to see if a ship can be placed given the first coordinate and direction of placement.
	@param firstcoord An array containing the row and column of the first coordinate of the ship.
	@param direction A string that indicates what direction to continue placing the ship.
	@param battleship The ship being placed. Important for the ships length.
	@return A boolean indicating whether the placement is valid.
	*/
    public boolean isValidPlacement(int[] firstcoord, String direction, Ship battleship){
        int row = firstcoord[0];
        int col = firstcoord[1];
        int length = battleship.getLength();
        //System.out.println(row);
        //System.out.println(col);
        //System.out.println(length);
        boolean isValid = true;

        //First check to see if the extended last coordinate exists on board
        if (direction.equals("r") && col+length>10){
            isValid = false;
        }
        else if(direction.equals("l") && col-length<-1){
            isValid = false;
        }
        else if(direction.equals("u") && row-length<-1){
            isValid = false;
        }
        else if(direction.equals("d") && row+length>10){
            isValid = false;
        }

        // Returns false here so as to not execute the rest of the function which may cause an exception error.
        if(!isValid){
            //System.out.print("Not a valid placement.");
            return isValid;
        }

        //Second check if the board already has a ship placed there, so they don't overlap.
		//right
        if(direction.equals("r")){
            for (int i = 0; i<length; i++){
                if(this.board[row][col+i]==3){
                    isValid = false;
                }
            }
        }
		//left
        else if(direction.equals("l")){
            for (int i = 0; i<length; i++){
                if(this.board[row][col-i]==3){
					isValid = false;
                }
            }
        }
		//up
        else if(direction.equals("u")){
            for (int i = 0; i<length; i++){
                if(this.board[row-i][col]==3){
                    isValid = false;
                }
            }
        }
		//down
        else if(direction.equals("d")){
            for (int i = 0; i<length; i++){
                if(this.board[row+i][col]==3){
                    isValid = false;
                }
            }
        }

        return isValid;
    }
	
	/** Places a ship on the board without further prompt from the user.
	@param firstcoord The row and the column to start placing the ship.
	@param direction The direction to place the rest of the ship in.
	@param battleship The ship being placed with a name and length
	*/	
	public void placeRandomShip(int[] firstcoord, String direction, Ship battleship){
		if(isValidPlacement(firstcoord,direction,battleship)){ // checks to see if placement is valid
			board[firstcoord[0]][firstcoord[1]] = 3; // 3 means ship is there but hidden to enemy?
			battleship.setCoords(firstcoord,0);
			// Based on direction, places the rest of the ship by changing the elements in the board to number 3.
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
				board[nextCoords[0]][nextCoords[1]] = 3;
				battleship.setCoords(nextCoords,i+1);
			}
		}
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

        board[firstcoord[0]][firstcoord[1]] = 3; // 3 means ship is there but hidden to enemy?
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
			board[nextCoords[0]][nextCoords[1]] = 3;
			battleship.setCoords(nextCoords,i+1);
        }
        printBoard();

    }
	
	/** Function to place all ships with user input. No extra parameters needed. Should be called to set up a user board.
	*/
    public void placeAllShips(){
        for (int i = 0; i<this.ships.length; i++){
            System.out.println("Please place the " + ships[i].getName() + " with length of " + ships[i].getLength() +".");
            placeShip(ships[i]);
        }
    }
	
	/** Converts the element in the board matrix to a symbol
	@param num Symbolic number to be converted to a string.
	@return A string to be printed out.
	*/
    private String boardMarker(int num){
        String marker;
        switch(num){
            case 0:
                marker = " "; //Empty space (user) or Unknown (enemy board)
                break;
            case 1:
                marker = "X"; //Miss (enemy)
                break;
            case 2:
                marker = "O"; //Hit (enemy)
                break;
            case 3:
                marker = "+"; //Own battleship (user)
                break;
            default:
                marker = "?";
                break;
        }
        return marker;

    }
	
	public void printEnemyBoard(){
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
				if(this.board[i][j] != 3){
					System.out.print(boardMarker(this.board[i][j])); //Print marker according to board
				}
				else{
					System.out.print(" ");
				}
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border
		
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
                System.out.print(boardMarker(this.board[i][j])); //Print marker according to board
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border

    }
	
	

}