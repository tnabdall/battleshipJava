import java.util.Scanner;
import java.util.Random;

/** GameBoard template that is used by subclasses PlayerBoard and EnemyBoard.
Contains common methods and instance variables for both boards.
Not to be instantiated into its own object.
*/
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
	Initializes every element in the board array to 0 to symbolize it being empty.
	*/
    private void InitializeBoard(){
        for(int i=0; i<this.board.length;i++){
            for(int j=0; j<this.board[i].length;j++){
                this.board[i][j]=0;
            }
        }
    }
	
	
	/** Assumes that a ship has been hit and checks which ship based on coordinates.
	@param coords An array containing the row and column of the ship element
	*/
	public String getShipFiredOn(int[] coords){
		int[][] shipcoords;
		for (int i = 0; i< ships.length; i++){
			shipcoords = ships[i].getAllCoords();
			for (int j = 0; j< shipcoords.length; j++){
				if (shipcoords[j][0] == coords[0] && shipcoords[j][1] == coords[1]){
					return ships[i].getName();
				}
			}
		}
		return "Unknown";
	}
	
	/** Assumes that a ship has been hit and checks which ship based on coordinates.
	@param row The row fired on.
	@param col The column fired on.
	*/
	public String getShipFiredOn(int row, int col){
		int[][] shipcoords;
		for (int i = 0; i< ships.length; i++){
			shipcoords = ships[i].getAllCoords();
			for (int j = 0; j< shipcoords.length; j++){
				if (shipcoords[j][0] == row && shipcoords[j][1] == col){
					return ships[i].getName();
				}
			}
		}
		return "Unknown";
	}
	
	/** Gets the length of the ship that is just fired upon. Used to aid players by letting them know the length of the ship for decisions.
	@param coords An array containing the row and column of the ship element
	*/
	public int getShipFiredOnLength(int[] coords){
		int row = coords[0];
		int col = coords[1];
		int[] shipcoords;
		for (int i = 0; i<ships.length; i++){
			for (int j = 0; j<ships[i].getLength(); j++){
				shipcoords = ships[i].getCoords(j);
				if(shipcoords[0] == row && shipcoords[1] == col){
					return ships[i].getLength();
				}
			}
		
		}
		return -1;
	}
	
	/** Gets the length of the ship that is just fired upon. Used to aid players by letting them know the length of the ship for decisions.
	@param row The row fired on
	@param col The column fired on
	*/
	public int getShipFiredOnLength(int row, int col){
		int[] shipcoords;
		for (int i = 0; i<ships.length; i++){
			for (int j = 0; j<ships[i].getLength(); j++){
				shipcoords = ships[i].getCoords(j);
				if(shipcoords[0] == row && shipcoords[1] == col){
					return ships[i].getLength();
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
    protected boolean isValidPlacement(int[] firstcoord, String direction, Ship battleship){
        int row = firstcoord[0];
        int col = firstcoord[1];
        int length = battleship.getLength();
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
	

	
	/** Converts the element in the board matrix to a symbol
	@param num Symbolic number to be converted to a string.
	@return A string to be printed out.
	*/
    protected String boardMarker(int num){
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
    protected String boardMarker(int num, boolean hideShip){
        
		if (hideShip == false){
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
	@ return getCoordinates Two element array where first is the board's row index and the second is the board's column index.
	*/
    public int[] getCoordinates(){
        int[] coords = new int[2];
        Scanner keyboard = new Scanner(System.in);
        String scoords;
        do{
            System.out.print("Which coordinate would you like? (eg. A3): ");
            scoords = keyboard.next();
            scoords = scoords.trim();
            if(!scoords.matches("^[A-J|a-j]([1-9]|10)$")){
                System.out.println("Not a valid coordinate.");
            }
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
	
	/**
	Returns game board to player.
	@return Returns the game board.
	*/
    public int[][] getBoard() {
        return board; 
    }
	

	/**
	 *
	 * @param row Row of element to get
	 * @param col Col of element to get
	 * @return 0,1,2, or 3. 0 means nothing placed there. 1 is a miss marker. 2 is a hit marker. 3 is a ship placed there.
	 */
	public int getBoardElement(int row, int col){
		return board[row][col];
	}

	/**
	 * Returns Ship array in gameboard
	 * @return Ship array in gameboard
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
		if (element>=0 && element<=3){
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

}