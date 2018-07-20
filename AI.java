import java.util.Random;

public class AI {
	
	private int difficulty = 0; // The difficulty of the AI. 0 for normal, 1 for challenge, 2 for impossible, 3 for random

	private PlayerBoard playerBoard; // A copy of the player's board.

	private int row; // The row location of the AI
	private int col; // The column location of the AI
	private int status; // The status of the position of the AI
	private String direction; // Direction the AI is facing.

	/**
	* Variables for enemy ship
	*/
	private int initRow; // Row of the initial hit of the ship
	private int initCol; // Column of the initial hit of the ship
	private String ship = "Unknown"; // Keeps track of ship that was hit. AI's target ship.
	private static int counter; // How many hits on a ship

	/** Getter method for counter
	*/
	public int getCounter() {
		return counter;
	}
	
	/** Getter method for the direction the AI is facing
	*/
	public String getDirection() {
		return direction;
	}

	
	/** Default constructor for AI
	*/
	public AI(){
		PlayerBoard playerBoard = new PlayerBoard();
		counter = 0;
	}
	
	/** Constructor for AI
	* @param playerBoard is the GameBoard object of the user's board
	*/

	public AI(PlayerBoard playerBoard) {


		this.playerBoard = playerBoard;
		counter = 0;
	}
	
	/** Sets the position of the AI to the location it first hit a unique ship.
	*/
	public void initPosition(){
		row = initRow;
		col = initCol;
	}
	
	/** Mutator method to change the value of counter
	 *
	 */
	public static void addToCounter() {

		counter = counter + 1;
	}

	/** Setter method for difficulty
	 * @param difficulty is the difficulty the AI will be set to.
	 */
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
	public void runDifficulty(){
		if (difficulty == 0){
			normalDifficulty();
		} else if (difficulty == 1) {
			challengeDifficulty();
		} else if (difficulty == 2) {
			impossibleDifficulty();
		} else if (difficulty == 3) {
			randomDifficulty();
		}
	}

	/** The AI for normal difficulty
	 * Initially checks to see if previous played location was a hit. If so it plays strategically,
	 * otherwise it plays randomly.
	 */
	public void normalDifficulty() {
		if (counter == 0) {
			playRandom();
		} else if (counter > 0){
			playStrategic();
		} else;
	}

	/** The AI for challenge difficulty.
	 *	Will be programmed so that when it hits it will end up sinking the ship
	 * without missing. It will know the coordinates of the ship after hitting once.
	 */
	public void challengeDifficulty() {

	}

	/** AI for impossible difficulty.
	 *  Will be programmed to always hit.
	 */
	public void impossibleDifficulty() {
	}
	
	/** AI for random difficulty.
	* Randomly plays locations that haven't been fired at
	*/
	public void randomDifficulty() {
		randomRow();
		randomCol();
		while (playerBoard.locStatus(row, col) == 1 || playerBoard.locStatus(row, col) == 2 ) {
			randomRow();
			randomCol();
		}
	}

	/** Method for AI to fire on a random location.
	 * Randomizes row and column. Checks to see if location has been played on already.
	 * If the location has been played on it will randomize another location until an empty
	 * location is found.
	 */
	public void playRandom() {
		randomRow();
		randomCol();
		while (playerBoard.locStatus(row, col) == 1 || playerBoard.locStatus(row, col) == 2 ) {
			randomRow();
			randomCol();
		} if ((playerBoard.locStatus(row, col)) == 3) {
			ship = playerBoard.getShipFiredOn(row, col);
			initRow = row;
			initCol = col;
		} else;
	}

	/** Randomizes the row to hit
	 */
	public void randomRow() {
		Random num = new Random();
		row = num.nextInt(10);
		this.row = row;
	}

	/** Randomizes the column to hit
	 */
	public void randomCol() {
		Random num = new Random();
		col = num.nextInt(10);
		this.col = col;
	}

	/** If the AI got a hit on the previous turn (ie. counter > 0), then AI plays strategically.
	 * On one hit (ie. counter == 1) the AI will try to determine the plane of the ship it hit.
	 * On two hits or more (ie. counter > 1) the AI will try to sink the ship.
	 */
	public void playStrategic() {
		if (counter == 1) {
			determineShipPlane();
		} else if (counter > 1){
			shootToSink();
		}
	}
	
	/** Method that initiates after the the AI hit's a ship after the first time
	* The AI then plays randomly on locations adjacent to the initial hit location until
	* it gets another hit
	*/
	public String determineShipPlane() {
		boolean failure = true;
		if (playerBoard.locStatus(row, col) == 1) {
			initPosition();
			while (failure) {
				direction = randomDirection();
				System.out.println(direction);
				moveDirection();
				status = playerBoard.locStatus(row, col);
				if (status == 0) {
					failure = false;
				} else if (status == 1) {
					initPosition();
				} else if (status == 2) {
					initPosition();
				} else if (status == 3) {
					if (ship == playerBoard.getShipFiredOn(row, col)){
						failure = false;
					} else {
						initPosition();
					}
				} else if (status == 4) {
					initPosition();
				}
			}
		} else if (playerBoard.locStatus(row, col) == 2) {
			while (failure) {

				//System.out.println("determineShipPlane while loop");
				//System.out.println("row: " + initRow + ". col: " + initCol);
				direction = randomDirection();
				//System.out.println(direction);
				moveDirection();
				//System.out.println("row: " + row + ". col: " + col);
				status = playerBoard.locStatus(row, col);
				if (status == 0) {
					//System.out.println("DSS 0");
					failure = false;
				} else if (status == 1) {
					//System.out.println("DSS 1");
					initPosition();
				} else if (status == 2) {
					//System.out.println("DSS 2");
					initPosition();
				} else if (status == 3) {
					//System.out.println("DSS 3");
					if (ship == playerBoard.getShipFiredOn(row, col)){
						//System.out.println("DSS 3 - ship!");

						failure = false;
					} else {
						initPosition();
					}
				} else if (status == 4) {

					//System.out.println("DSS 4");

					initPosition();
				}
			}
		} return direction;
	}
	
	
	/** Method to switch the direction the AI is facing
	*/
	public void switchDirection() {
		switch (direction) {
			case "left":
				direction = "right";
				break;
			case "right":
				direction = "left";
				break;
			case "down":
				direction = "up";
				break;
			case "up":
				direction = "down";
				break;
			default:
				break;
		} 
	}

	/** Method to randomize the direction of the AI's next move after hitting once.
	 * @return direction is the randomized direction.
	 */
	public String randomDirection() {
		Random rand = new Random();
		String directions[] = {"left", "right", "up", "down"};
		direction = directions[rand.nextInt(4)];
		return direction;
	}

	/** Method to move the AI to the next location depending on the direction it is facing.
	 */
	public void moveDirection(){
		if (direction == "left") {
			col -= 1;
		} else if (direction == "right") {
			col += 1;
		} else if (direction == "up") {
			row -= 1;
		} else if (direction == "down") {
			row += 1;
		}
	}
	
	/** After the AI determines the plane of the ship it hit (ie. after two hits) it will try to sink it
	*/
	public void shootToSink() {
		boolean sunk = checkShipStatus();
		if (sunk) {
			counter = 0;
			ship = "Unknown";
			playRandom();
			return;
		}
		status = playerBoard.locStatus(row, col);
		if (status == 1) {
			initPosition();
			switchDirection();
			moveDirection();
			return;
		} else if (status == 2) {
			moveDirection();
			status = playerBoard.locStatus(row, col);
			if (status == 1) {
				initPosition();
				switchDirection();
				moveDirection();
				return;
			} else if (status == 2) {
				initPosition();
				switchDirection();
				moveDirection();
			} else if (status == 3) {
				if (ship == playerBoard.getShipFiredOn(row, col)){
					return;
				} else {
					initPosition();
					switchDirection();
					moveDirection();
					return;
				}
			} else if (status == 4) {
				initPosition();
				switchDirection();
				moveDirection();
				return;
			}
		}
	}
	
	/** Checks the status of the ship the AI is targeting
	 * @return sunk is the boolean for if the targeted ship has been sunk or not.
	 */
	public boolean checkShipStatus() {
		boolean sunk = false;
		if (counter == 2 && ship == "Destroyer") {
			sunk = true;
			return sunk;
		} else if (counter == 3 && ship == "Cruiser") {
			sunk = true;
			return sunk;
		} else if (counter == 3 && ship == "Submarine") {
			sunk = true;
			return sunk;
		} else if (counter == 4 && ship == "Battleship") {
			sunk = true;
			return sunk;
		} else if (counter == 5 && ship == "Carrier") {
			sunk = true;
			return sunk;
		} else {
			return sunk;
		}
	}
}
