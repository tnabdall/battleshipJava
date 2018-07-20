import java.util.Random;

public class AI {
	
	private int difficulty = 0; // The difficulty of the AI. 0 for normal, 1 for challenge, 2 for impossible.

	private GameBoard playerBoard; // A copy of the player's board.

	private int row; // The row location of the AI
	private int col; // The column location of the AI

	private int initRow; // Row of the initial hit of the ship
	private int initCol; // Column of the initial hit of the ship

	//	//private boolean hitTwice; // If AI hit a ship twice

	private String direction; // Direction the AI is facing.
	
	private String ship = "Unknown"; // Keeps track of ship that was hit. AI's target ship.
	private static int counter; // How many hits on a ship

	public int getCounter() {
		return counter;
	}

	public String getDirection() {
		return direction;
	}


	public AI(){
		GameBoard playerBoard = new GameBoard();
		counter = 0;
	}

	public AI(GameBoard playerBoard) {
		this.playerBoard = playerBoard;
		counter = 0;
	}

	/** Mutator method to change the value of counter
	 *
	 */
	public static void addToCounter() {

		counter += 1;
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
	// There's a more elegant solution. Placeholder.
	public void runDifficulty(){
		System.out.println("runDifficulty");
		if (difficulty == 0){
			normalDifficulty();
		} else if (difficulty == 1) {
			challengeDifficulty();
		} else if (difficulty == 2) {
			impossibleDifficulty();
		}
	}

	/** The AI for normal difficulty
	 * Initially checks to see if previous played location was a hit. If so it plays strategically,
	 * otherwise it plays randomly.
	 */
	public void normalDifficulty() {
		// boolean hit = checkHit(); // counter is the solution.
		if (counter == 0) {
			playRandom();
		} else {
			playStrategic();
		}
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

	/** Method for AI to fire on a random location.
	 * Randomizes row and column. Checks to see if location has been played on already.
	 * If the location has been played on it will randomize another location until an empty
	 * location is found.
	 */
	public void playRandom() {
		randomRow();
		randomCol();
		boolean playedSpace = playerBoard.hasFired(row, col);
		while (playerBoard.locStatus(row, col) == 1 || playerBoard.locStatus(row, col) == 2 ) {
			randomRow();
			randomCol();
		} if ((playerBoard.locStatus(row, col)) == 3) {
			int[] hit = {row, col};
			ship = playerBoard.getShipFiredOn(hit);
			initRow = row;
			initCol = col;
			//counter++;
		}
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
			System.out.println("playStrategic counter 1");
			determineShipPlane();
		} else if (counter > 1){
			System.out.println("playStrategic counter 2");
			shootToKill();
		}
	}

	public String determineShipPlane() {
		System.out.println("determineShipPlane");
		boolean failure = true;
		int[] coords = new int[2];
		if (playerBoard.locStatus(row, col) == 1) {
			System.out.println("ShipPlane switch");
			switch(direction) {
				case "left":
					direction = "right";
					moveDirection();
					break;
				case "right":
					direction = "left";
					moveDirection();
					break;
				case "down":
					direction = "up";
					moveDirection();
					break;
				case "up":
					direction = "down";
					moveDirection();
					break;
				default:
					break;
			}
		} else;
		while (failure) {
			System.out.println("determineShipPlane while loop");
			direction = randomDirection();
			if (direction == "left") {
				coords[0] = row;
				coords[1] = col - 1;
				if ((col - 1) >= 0 && (playerBoard.locStatus(row, col - 1) == 0 || ship != playerBoard.getShipFiredOn(coords))) {
					System.out.println(ship + "..." + playerBoard.getShipFiredOn(coords));
					moveDirection();
					failure = false;
					//counter++;
				} else;
			} else if (direction == "right") {
				coords[0] = row;
				coords[1] = col + 1;
				if ((col + 1) <= 9 && (playerBoard.locStatus(row, col + 1) == 0 || ship != playerBoard.getShipFiredOn(coords))) {
					System.out.println(ship + "..." + playerBoard.getShipFiredOn(coords));
					moveDirection();
					failure = false;
					//counter++;
				} else;
			} else if (direction == "down") {
				coords[0] = row - 1;
				coords[1] = col;
				if ((row - 1) >= 0 && (playerBoard.locStatus(row - 1, col) == 0 || ship != playerBoard.getShipFiredOn(coords))) {
					System.out.println(ship + "..." + playerBoard.getShipFiredOn(coords));
					moveDirection();
					failure = false;
					//counter++;
				} else;
			} else if (direction == "up") {
				coords[0] = row + 1;
				coords[1] = col;
				if ((row + 1) <= 9 && (playerBoard.locStatus(row + 1, col) == 0 || ship != playerBoard.getShipFiredOn(coords))) {
					System.out.println(ship + "..." + playerBoard.getShipFiredOn(coords));
					moveDirection();
					failure = false;
					//counter++;
				} else;
			} else {
				return direction;
			}
		}  return direction;
	}

	/** If the AI hit previously it will try to hit adjacent to
	 * 	that spot randomly until it hits again.
 	 */
	/* public String determineShipPlane() {
		boolean failure = true;
		while (failure) {
			direction = randomDirection();
			switch (direction) {
				case "left":
					if (!(col - 1 < 0)) {
						if (!playerBoard.hasFired(row, col - 1)) {
							moveDirection();
							failure = false;
							counter += 1;
							return direction;
						} else {
							return direction;
						}
					} else {
						return direction;
					}
				case "right":
					if (!(col + 1 > 9)) {
						if (!playerBoard.hasFired(row, col + 1)) {
							moveDirection();
							failure = false;
							counter += 1;
							return direction;
						} else {
							return direction;
						}
					} else {
						return direction;
					}
				case "down":
					if (!(row - 1 < 0)) {
						if (!playerBoard.hasFired(row - 1, col)) {
							moveDirection();
							failure = false;
							counter += 1;
							return direction;
						} else {
							return direction;
						}
					} else {
						return direction;
					}
				case "up":
					if (!(row + 1 > 9)) {
						if (!playerBoard.hasFired(row + 1, col)) {
							moveDirection();
							failure = false;
							counter += 1;
							return direction;
						} else {
							return direction;
						}
					} else {
						return direction;
					}
				default:
					return direction;
			}
		} return direction;
	} */

	/** Method to randomize the direction of the AI's next move after hitting once.
	 * @return direction is the randomized direction.
	 */
	public String randomDirection() {
		if (counter == 1) {
			Random rand = new Random();
			String directions[] = {"left", "right", "down", "up"};
			direction = directions[rand.nextInt(4)];
		} return direction;
	}

	/** Method to move the AI's location depending on the direction it is facing.
	 */
	public void moveDirection(){
		if (direction == "left") {
			col -= 1;
		} else if (direction == "right") {
			col += 1;
		} else if (direction == "down") {
			row -= 1;
		} else if (direction == "up") {
			row += 1;
		}
	}


	public void shootToKill() {
		if (sunk) {
			System.out.println("shootToKill checkShipStatus");
			counter = 0;
			ship = "Unknown";
			playRandom();
			return;
		} else;
		if (playerBoard.locStatus == 2

	}

	/** AI tries to sink the ship it's targetting. Counter must equal 3 or greater to get to this point.
	 *
	 */
	public void shootToKill() {
		System.out.println("shootToKill" + counter);
		int[] coords = new int[2];
		boolean sunk = checkShipStatus();
		if (sunk) {
			System.out.println("shootToKill checkShipStatus");
			counter = 0;
			ship = "Unknown";
			playRandom();
			return;
		} else;
		if ((playerBoard.locStatus(row, col)) == 2 && !(row + 1 > 9 || row - 1 < 0 || col + 1 > 9 || col - 1 < 0)){
			System.out.println("moveDirection");
			moveDirection();
			coords[0] = row;
			coords[1] = col;
			if (playerBoard.locStatus(row,col) == 4 || ship != playerBoard.getShipFiredOn(coords)){
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
					default:;
				} moveDirection();
			} else;
		} else;
		if (direction == "left" || direction == "right") {
			System.out.println("shootToKill left right");
			coords[0] = row;
			coords[1] = col;
			if ((playerBoard.locStatus(row, col) == 0 || playerBoard.locStatus(row, col) == 3) && ship != playerBoard.getShipFiredOn(coords)){
				System.out.println("shootToKill left right has fired");
			} else {
				System.out.println("shootToKill left right else");
				if (playerBoard.locStatus(row, col) == 2) {
					if (direction == "left") {
						direction = "right";
						for (int i = 0; i < counter; i++) {
							moveDirection();

						} //counter++;
					} else if (direction == "right") {
						direction = "left";
						for (int i = 0; i < counter; i++) {
							moveDirection();

						} //counter++;
					} else ;
				} else {
					if (direction == "left") {
						direction = "right";
						for (int i = 0; i < counter; i++) {
							moveDirection();

						} //counter++;
					} else if (direction == "right") {
						direction = "left";
						for (int i = 0; i < counter; i++) {
							moveDirection();

						} //counter++;
					} else;
				}
			}
		} else if (direction == "down" || direction == "up") {
			System.out.println("shootToKill down up");
			coords[0] = row;
			coords[1] = col;
			if ((playerBoard.locStatus(row, col) == 0 || playerBoard.locStatus(row, col) == 3) && ship != playerBoard.getShipFiredOn(coords)) {
				System.out.println("shootToKill up down has fired");
			} else {
				System.out.println("shootToKill down up else");
				if (playerBoard.locStatus(row, col) == 2) {
					if (direction == "down") {
						direction = "up";
						for (int i = 0; i < counter; i++) {
							moveDirection();

						}    //counter++;
					} else if (direction == "up") {
						direction = "down";
						for (int i = 0; i < counter; i++) {
							moveDirection();

						}    //counter++;
					} else ;
				} else {
					if (direction == "down") {
						direction = "up";
						for (int i = 0; i <= counter; i++) {
							moveDirection();

						}    //counter++;
					} else if (direction == "up") {
						direction = "down";
						for (int i = 0; i <= counter; i++) {
							moveDirection();

						}    //counter++;
					} else ;
				}
			}
		}
	}

	/** Checks the status of the ship the AI is targetting
	 * @return sunk is the boolean for if the targetted ship has been sunk or not.
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




	/*/** Checks to see if previous played spot was a hit
	* Probably do not need because of counter implementation.
	public boolean checkHit(){
		boolean hit = false;
		if (playerBoard.board[row][col] == 3) {
			hit = true;
		} else;
		return hit;
	} */
	

	/* switch(direction) {
		case 0: if (!playerBoard.hasFired(row + 1, col) {
			row += 1;
			failure = false;
			counter += 1;
			return direction;
		} else {
			break;
		}
		case 1: if (!playerBoard.hasFired(row - 1, col) {
			row -= 1;
			failure = false;
			counter += 1;
			return direction;
		} else {
			break;
		}
		case 2: if (!playerBoard.hasFired(row, col + 1) {
			col += 1;
			failure = false;
			counter += 1;
			return direction;
		} else {
			break;
		}
		case 3: if (!playerBoard.hasFired(row, col + 1) {
			col -= 1;
			failure = false;
			counter += 1;
			return direction;
		} else {
			break;
		}
		default: break; */








	/* public void playStrategic() {
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

	public void playToSink(String direction) {
		String ship = enemyBoard.getShipFiredOn();
		if (ship == "Carrier") {
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
	} */
	
}
