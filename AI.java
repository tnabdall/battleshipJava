import java.util.Random;

public class AI {
	
	private int difficulty = 0; // The difficulty of the AI. 0 for normal, 1 for challenge.
	private int row; // The row location of the AI
	private int col; // The column location of the AI
	private boolean hitTwice; // If AI hit a ship twice
	private String direction; // Direction of the AIs next fire
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	/** Getter method for row
	*/
	public int getRow() {
		return row;
	}
	
	/** Getter method for column
	*/
	public int getCol() {
		return col;
	}
	
	/** Calculates next move depending on the difficulty setting.
	*/
	// There's a more elegant solution. Placeholder.
	public void runDifficulty(){
		if (difficulty == 0){
			normalDifficulty();
		} else if (difficulty == 1) {
			challengeDifficulty();
		}
	}
	
	/** Randomizes the row to hit
	*/
	public int randomRow() {
		Random row = new Random();
		row = nextInt(10)
		this.row = row;
	}
	
	/** Randomizes the column to hit
	*/
	public int randomCol() {
		Random col = new Random();
		col = nextInt(10);
		this.col = col;
	}
	
	/** Checks to see if previous played spot was a hit
	*/
	public boolean checkHit(){
		boolean hit = false;
		if (enemyBoard.board[row][col] == 1) { // 1 or 2 for hit?
			hit = true;
			int shipLength = declaredShip();
			shipCoordinates = new int[shipLength][shipLength];
			shipCoordinates[0][] = col;
			shipCoordinates[][0] = row;
		} else;
		return hit;
	}
	
	/** The AI for normal difficulty
	* Initially checks to see if previous played location was a hit. If so it plays strategically,
	* otherwise it plays randomly.
	*/
	public void normalDifficulty() {
		boolean hit = checkHit();
		if (hit == true) {
			playStrategic();
		} else {
			playRandom();
		}
	}
	
	/** The AI for challenge difficulty
	*/
	public void challengeDifficulty() {
		
	}
	

	// May not need.
	public int getDifficulty() {
		return difficulty;
	}

	
	/** Method for AI to fire on a random location.
	* Randomizes row and column. Checks to see if location has been played on already. 
	* If the location has been played on it will randomize another location until an empty 
	* location is found.
	*/
	public void playRandom() {
		randomRow();
		randomCol();
		boolean playedSpace = enemyBoard.hasFired(row, col);
		while (playedSpace = true) {
			randomRow();
			randomCol();
			playedSpace = enemyBoard.hasFired(row, col);
		} 
	}
	
	
	/** If the AI got a hit on previous turn, then AI plays strategically.
	*/
	public void playStrategic() {
		// boolean hit = checkHit();
		int counter; // For the number of previous hits.
		
		int left = col - 1;
		int top = row + 1;
		int right = col + 1;
		int bottom = row - 1;
		
		int positions[] = {left, top, right, bottom};
		boolean cont = false;
		
		Random rPositon = new Random();
		rPosition = nextInt(4);
		
		// AI's next move depends on if they know the direction of the ship (ie. hit twice) or not.
		if (hitTwice = false) {
			// Loop that brute forces the next reasonable position to play if they hit once previously.
			while (cont = false){
				switch (rPosition) {
				case 0: fired = enemyBoard.hasFired(row, left);
						if (fired = false) {
							if (enemyBoard.board[row][left] == 3) {
								col = left;
								cont = true;
								boolean hitTwice = true;
								direction = "horizontal";
							} else {
								cont = true;
							} break;
						} else {
							rPosition = nextInt(4)
							break;
						}
				
				case 1: fired = enemyBoard.hasFired(top, col);
						if (fired = false) {
							if (enemyBoard.board[top][col] == 3) {
								row = top;
								cont = true;
								boolean hitTwice = true;
								direction = "vertical";
							} else {
								cont = true;
							} break;
						} else {
							rPosition = nextInt(4)
							break;
						}
				case 2: fired = enemyBoard.hasFired(row, right);
						if (fired = false) {
							if (enemyBoard.board[row][right] == 3) {
								col = right;
								cont = true;
								boolean hitTwice = true;
								direction = "horizontal";
							} else {
								cont = true;
							} break;
						} else {
							rPosition = nextInt(4)
							break;
						}
				case 3: fired = enemyBoard.hasFired(bottom, col);
						if (fired = false) {
							if (enemyBoard.board[bottom][col] == 3) {
								row = bottom;
								cont = true;
								boolean hitTwice = true;
								direction = "vertical";
							} else {
								cont = true;
							} break;
						} else {
							rPosition = nextInt(4)
							break;
						}
				default: break;
				}
			}
		} else {
			playToSink(direction);
			
		}
	}

	/**
	 * If AI hit a ship at least twice and it's not the Destroyer it will continue to try to sink the ship.
	 */
	/* Work in progress
	* The current issue is changing the direction of the AI for its next move. For example, if the AI hit the carrier 
	* placed horizontally in the middle and kept firing to the right it will eventually miss. The AI needs to know to
	* go left from the where it initially hit then the AI needs to know that the direction to fire has changed
	* for the move after.
	* I'm considering a nextMove() method which considers all these things and chooses a firing direction.
	*/
	public void playToSink(String direction) {
		int calledShip = enemyBoard.hitName();
		if (calledShip == "Carrier") {
			if (board[row][col] == 2) {
				if (!ship[0].checkStatus) {


					if (direction == "horizontal") {
						if (!enemyBoard.hasFired(row, col - 1));
						col = col - 1;
					} else {
						int counter = 0
						for (int i = 0, i < 5, i++) {
							if (enemyBoard(Firedship[0].coords[i][0], enemyBoard(Firedship[0].coords[i][1]);
								counter++;
						} else;
						col = col + counter;

					}
				}
			} else {

			}
		} else if (calledShip == "Battleship") {

		} else if (calledShip == "Submarine") {

		} else if (calledShip == "Cruiser") {

		}
	
	}
		
	
	public void 
	
	
	public int getCarrierStatus() {
		return carrier;
	}
	
	public int getBattleshipStatus() {
		return battleship;
	}
	
	public int getSubmarineStatus() {
		return submarine;
	}
	
	public int getCruiserStatus() {
		return cruiser;
	}
	
	public int getDestroyerStatus() {
		return destroyer;
	}
	
}
