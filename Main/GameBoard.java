package Main;

import java.util.Scanner;
import java.util.Random;

/** Main.GameBoard template that is used by subclasses Main.PlayerBoard and Main.EnemyBoard.
Contains common methods and instance variables for both boards.
Not to be instantiated into its own object.
*/
public abstract class GameBoard{

    private Ship[] ships = new Ship[5]; // Variable for ships. May implement get number of ships later to allow game to be flexible.

    private int[][] board = new int[10][10]; // Variable containing the board. 10x 10 matrix.
	
	private int numberOfShipElements = 0; // Ship Health.

	private static int NUMROWS = 10; // Number of rows on GameBoard. Default 10
	private static int NUMCOLS = 10; // Number of Columns on GameBoard. Default 10

	/**
	 * Subclasses should be able to print Board to GUI console
	 */
	public abstract void printBoard();

	/**
	Constructor for Main.GameBoard.
	Automatically initializes the board to a blank slate and adds default 5 ships to the game.
	*/
    public GameBoard(){
		this(10,10);
    }

	/**
	 * Initializes board to blank slate with number of rows and columns and adds 5 ships to the game.
	 * @param numberOfRows # of rows on board
	 * @param numberOfCols # of cols on board
	 */
	public GameBoard(int numberOfRows, int numberOfCols){
		setNUMROWS(numberOfRows);
		setNUMCOLS(numberOfCols);
		InitializeBoard();
		ships[0] = new Ship("Carrier",5);
		ships[1] = new Ship("Battleship", 4);
		ships[2] = new Ship("Submarine", 3);
		ships[3] = new Ship("Cruiser",3);
		ships[4] = new Ship("Destroyer",2);
		numberOfShipElements = 17;
	}

	/**
	 * Copy constructor
	 * @param other Other GameBoard to copy
	 */
	public GameBoard(GameBoard other){
		setNUMROWS(other.getNUMROWS());
		setNUMCOLS(other.getNUMCOLS());
    	for (int i = 0; i<other.getShips().length; i++){
    		ships[i] = new Ship(other.getShips()[i]);
		}
		for (int i = 0; i<NUMROWS; i++){
    		for (int j = 0; j<NUMCOLS; j++){
    			setBoardElement(i,j,other.getBoardElement(i,j));
    			if(other.getBoardElement(i,j)==3){
    				this.numberOfShipElements+=1;
				}
			}
		}


	}

	/** 
	Initializes every element in the board array to 0 to symbolize it being empty.
	*/
    private void InitializeBoard(){
    	board = new int[NUMROWS][NUMCOLS];
        for(int i=0; i<this.board.length;i++){
            for(int j=0; j<this.board[i].length;j++){
                this.board[i][j]=0;
            }
        }
    }

	/** Assumes that a ship has been hit and checks which ship based on coordinates.
	@param coords An array containing the row and column of the ship element
	 @return The name of the ship fired on.
	*/
	public String getShipFiredOn(int[] coords){
		int[][] shipcoords;
		if (isValidCoordinate(coords[0],coords[1])) {
			for (int i = 0; i < ships.length; i++) {
				shipcoords = ships[i].getAllCoords();
				for (int j = 0; j < shipcoords.length; j++) {
					if (shipcoords[j][0] == coords[0] && shipcoords[j][1] == coords[1]) {
						return ships[i].getName();
					}
				}
			}
		}
		return "Unknown";
	}

	/** Method similar to getShipFired On except that it returns the ship object
	 * @param row is the row that the Main.AI was when it got a hit
	 * @param col is the column that the Main.AI was when it got a hit
	 * @return the ship object that the Main.AI hit
	 */
	public Ship getShipObject(int row, int col) {
		int[][] shipcoords;
		if (isValidCoordinate(row, col)) {
			for (int i = 0; i < ships.length; i++) {
				shipcoords = ships[i].getAllCoords();
				for (int j = 0; j < shipcoords.length; j++) {
					if (shipcoords[j][0] == row && shipcoords[j][1] == col) {
						//For testing
						return ships[i];
					}
				}
			}
		}
		return null;
	}

	/**
	 * Checks to make sure the coordinate is not out of bounds in array
	 * @param row Row to check
	 * @param col Column to check
	 * @return True or false
	 */
	private boolean isValidCoordinate(int row, int col){
		return row >= 0 && row <= NUMROWS && col >= 0 && col <= NUMCOLS;
	}

	
	/** Assumes that a ship has been hit and checks which ship based on coordinates.
	@param row The row fired on.
	@param col The column fired on.
	 @return the name of the ship fired on
	*/
	public String getShipFiredOn(int row, int col){
		int[][] shipcoords;
		if (isValidCoordinate(row, col)) {
			for (int i = 0; i < ships.length; i++) {
				shipcoords = ships[i].getAllCoords();
				for (int j = 0; j < shipcoords.length; j++) {
					if (shipcoords[j][0] == row && shipcoords[j][1] == col) {
						return ships[i].getName();
					}
				}
			}
		}
		return "Unknown";
	}
	
	/** Gets the length of the ship that is just fired upon. Used to aid players by letting them know the length of the ship for decisions.
	@param coords An array containing the row and column of the ship element
	 @return the length of the ship fired on, -1 if nothing
	*/
	public int getShipFiredOnLength(int[] coords){
		int row = coords[0];
		int col = coords[1];
		int[] shipcoords;
		if (isValidCoordinate(row, col)) {
			for (int i = 0; i < ships.length; i++) {
				for (int j = 0; j < ships[i].getLength(); j++) {
					shipcoords = ships[i].getCoords(j);
					if (shipcoords[0] == row && shipcoords[1] == col) {
						return ships[i].getLength();
					}
				}

			}
		}
		return -1;
	}
	
	/** Gets the length of the ship that is just fired upon. Used to aid players by letting them know the length of the ship for decisions.
	@param row The row fired on
	@param col The column fired on
	 @return the length of the ship fired on, -1 if nothing
	*/
	public int getShipFiredOnLength(int row, int col){
		int[] shipcoords;
		if (isValidCoordinate(row, col)) {
			for (int i = 0; i < ships.length; i++) {
				for (int j = 0; j < ships[i].getLength(); j++) {
					shipcoords = ships[i].getCoords(j);
					if (shipcoords[0] == row && shipcoords[1] == col) {
						return ships[i].getLength();
					}
				}

			}
		}
		return -1;
	}

	/** Makes a random board by placing random ships.
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
			coords[0] = randr.nextInt(NUMROWS);
			coords[1] = randc.nextInt(NUMCOLS);
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
				placeRandomShip(coords,dir,ships[validPlacements]);
				validPlacements+=1;// Increase counter
			}	
		}	
	}
	
	/** Places a ship on the board without further prompt from the user.
	@param firstcoord The row and the column to start placing the ship.
	@param direction The direction to place the rest of the ship in.
	@param battleship The ship being placed with a name and length
	*/	
	private void placeRandomShip(int[] firstcoord, String direction, Ship battleship){
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
        boolean isValid = true;

        //First check to see if the extended last coordinate exists on board
        if (direction.equals("r") && col+length>NUMCOLS){
            isValid = false;
        }
        else if(direction.equals("l") && col-length<-1){
            isValid = false;
        }
        else if(direction.equals("u") && row-length<-1){
            isValid = false;
        }
        else if(direction.equals("d") && row+length>NUMROWS){
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
	
	
	/** Converts the element in the board matrix to a symbol
	@param num Symbolic number to be converted to a string.
	@return A string to be printed out.
	*/
    public String boardMarker(int num){
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
	
	/** Converts the element in the board matrix to a symbol
	@param num Symbolic number to be converted to a string.
	@param hideShip True to not display the ship element for (3).
	@return A string to be printed out.
	*/
    public String boardMarker(int num, boolean hideShip){
        
		if (!hideShip){
			return boardMarker(num);
		}
		else{
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
					marker = " "; //Own battleship (user)
					break;
				default:
					marker = "?";
					break;
			}
			return marker;
		}
	}
	
	/** Asks the player for coordinates in the form "A1".
	1st part of placing a ship.
	@return getCoordinates Two element array where first is the board's row index and the second is the board's column index.
	*/
    public int[] getCoordinates(){
        int[] coords = new int[2];
        Scanner keyboard = new Scanner(System.in);
        String scoords;
        String lastRowChar = Character.toString((char) ("a".charAt(0)+NUMROWS-1));
        String lastCol = Integer.toString(NUMCOLS);
        do{
            System.out.print("Which coordinate would you like? (eg. A3): ");
            scoords = keyboard.next();
            scoords = scoords.trim();
			scoords = scoords.toLowerCase();
            if(!scoords.matches("^[a-"+lastRowChar+"]([1-9]|[10-"+lastCol+"])$")){
                System.out.println("Not a valid coordinate.");
            }
        } while(!scoords.matches("^[a-"+lastRowChar+"]([1-9]|[10-"+lastCol+"])$")); // Forces string to be within the range of Game Coordinates
        char letter = scoords.charAt(0); // Gets column letter
        int column = (int) letter; // Turns into an integer and subtracts 97 to get the proper column (since a is 97).
        column-=97;
        int row = Integer.parseInt(scoords.substring(1)); // Gets the row number.
        row-=1;
        coords[0] = row;
        coords[1] = column;
        return coords;
    }

	/**
	 *
	 * @param row Row of element to get
	 * @param col Col of element to get
	 * @return 0,1,2, or 3. 0 means nothing placed there. 1 is a miss marker. 2 is a hit marker. 3 is a ship placed there but unhit.
	 */
	public int getBoardElement(int row, int col){
		return board[row][col];
	}

	/**
	 * Returns Main.Ship array in gameboard
	 * @return Main.Ship array in gameboard
	 */
	public Ship[] getShips(){
		return ships;
	}
	
	
	/** Returns number of ship elements remaining on board.
	@return Number of ship elements remaining (like health)
	*/
	public int getNumberOfShipElements(){
		return numberOfShipElements;
	}
	
	/**
	 * Setter for the game board element
	 * @param row Row to set
	 * @param col Column to set
	 * @param element 1,2, or 3 for miss, hit, or battleship placed
	 */
	public void setBoardElement(int row, int col, int element){
		if (element>=0 && element<=3 && isValidCoordinate(row,col)){
			board[row][col]=element;
		}
	}

	/**
	 * Reduces the number of ship elements by 1 once a ship is hit.
	 */
	public void decrementShipElements(){
		if(numberOfShipElements>0) {
			numberOfShipElements -= 1;
		}
	}

    /** Asks the player for a coordinate to fire on the enemy's game board and checks if hit or miss.
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
    public boolean hasFired(int row, int col){
        boolean fired = false;
        int element = getBoardElement(row,col);
		fired = element == 1 || element == 2;
        return fired;
    }

	/**
	 * Gets the number of rows in the board
	 * @return Number of rows in board
	 */
	public int getNUMROWS() {
		return NUMROWS;
	}

	/**
	 * Gets the number of columns in the board
	 * @return Number of columns in the board
	 */
	public int getNUMCOLS() {
		return NUMCOLS;
	}

	/**
	 * Sets the static variable for the number of rows. Effectively final because can only be set once.
	 * @param numRows Number of rows.
	 */
	private static void setNUMROWS(int numRows){
		if (NUMROWS == 10 && numRows>0 && numRows <100){
			NUMROWS = numRows;
		}
	}

	/**
	 * Sets the static variable for the number of columns. Effectively final because can only be set once.
	 * @param numCols
	 */
	private static void setNUMCOLS(int numCols){
		if (NUMCOLS == 10 && numCols>0 && numCols <100){
			NUMCOLS = numCols;
		}
	}

}